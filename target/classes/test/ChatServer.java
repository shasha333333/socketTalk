package test;

import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 8888;
    private static ArrayList<Socket> clientSockets = new ArrayList<Socket>();
    private static ArrayList<String> clientNames = new ArrayList<String>();
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/chat";
    private static final String DB_USER = "username";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {
        try {
            // 建立数据库连接
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database.");

            //网络连接
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                // 执行查询
                statement = connection.createStatement();
                String query = "SELECT * FROM users";
                resultSet = statement.executeQuery(query);

                // 处理查询结果
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    // 根据需要进行操作，例如将用户名和密码存储在集合中
                    System.out.println(username+" "+password);
                }

                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                // 关闭数据库连接
                resultSet.close();
                statement.close();
                connection.close();
                System.out.println("Disconnected from the database.");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataInputStream inFromClient;
        private DataOutputStream outToClient;
        private String clientName;
        private String readMessage;
        private String writeMessage;
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                inFromClient = new DataInputStream(clientSocket.getInputStream());
                outToClient = new DataOutputStream(clientSocket.getOutputStream());

                outToClient.writeUTF("START_LOGIN_UI");

                while (true){
                    readMessage = inFromClient.readUTF();
                    System.out.println("readMessage: " + readMessage);
                    // 对应操作
                    handleClientMessage();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    clientSockets.remove(clientSocket);
                    clientNames.remove(clientName);
                    clientSocket.close();
                    System.out.println(clientName + " disconnected");
                    broadcastMessage("Server", clientName + " disconnected");
                    sendOnlineClients();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleClientMessage() throws IOException {
            if (readMessage.startsWith("START")) {
                if (readMessage.equals("START_BASE_UI_SUCCESS")){
                    sendOnlineClients();
                }

                else
                outToClient.writeUTF(readMessage);

            }
            else if (readMessage.startsWith("CONNECT")){
                dealConnectMessage();
            }
            else if (readMessage.startsWith("REQUEST")){
                dealRequestMessage();
            }
            else if (readMessage.startsWith("LOGIN")) {
                dealLoginMessage();
            }
            else if(readMessage.startsWith("CHAT")){
                dealChatMessage();
            }
        }

        private void dealConnectMessage() {
            String[] parts = readMessage.split("@");
            String friendName = parts[1];
            for (String clientName:clientNames) {
                if (friendName.equals(clientName)){
                    sendConnectClients(clientSockets.get(clientNames.indexOf(clientName)));
                    break;
                }
            }
        }

        private void dealRequestMessage() {
            String[] parts = readMessage.split("@");
            String friendName = parts[1];
            for (String clientName:clientNames) {
                if (friendName.equals(clientName)){
                    sendRequestClients(clientSockets.get(clientNames.indexOf(clientName)));
                    break;
                }
            }
        }

        private void dealLoginMessage() throws IOException {
            String[] parts = readMessage.split("@");
            String username = parts[1];
            String password = parts[2];
            System.out.println(username+" "+password);
            boolean loginSuccess = verifyLogin(username, password);
            boolean onlineExist = false;
            System.out.println("loginSuccess:"+loginSuccess);
            //检查是否在线
            for (String clientName:clientNames) {
                if (username.equals(clientName)){
                    onlineExist = true;
                }
            }
            System.out.println("onlineExist:"+onlineExist);

            if (onlineExist){
                outToClient.writeUTF("LOGIN_ONLINE_EXIST");
            }//已在线处理
            else if (loginSuccess) {
                // Send dealLoginMessage success message to client
                // Add user to online user list
                // Ask for client name and add to list of clients
//                        outToClient.writeUTF("Enter your name:");
//                        clientName = inFromClient.readUTF();
                clientName = username;
                System.out.println("New client connected: " + clientName);
                clientNames.add(clientName);
                clientSockets.add(clientSocket);
                outToClient.writeUTF("LOGIN_SUCCESS"+"@"+username);
//                System.out.println(111);
                // Send list of online clients to new client
            }//登录成功处理
            else {
                // Send dealLoginMessage failure message to client
                clientName = "FAILED_CLIENT";
                outToClient.writeUTF("LOGIN_FAILED");
            }//失败处理
        }

        private void dealChatMessage() {
            if(readMessage.startsWith("CHAT_IN_GROUP")){
                String[] parts = readMessage.split("@");
//                    System.out.println(Arrays.toString(parts));
                String chatMessage = parts[1];
                System.out.println(clientName + ": " + chatMessage);
                broadcastMessage(clientName, chatMessage);
            }
            else if(readMessage.startsWith("CHAT_IN_SINGLE")){
                String[] parts = readMessage.split("@");
//                    System.out.println(Arrays.toString(parts));
                String sender = parts[1];
                String friendName = parts[2];
                String chatMessage = parts[3];
                Socket targerSocket = null;
                for (String clientName:clientNames) {
                    if (friendName.equals(clientName)){
                        targerSocket = clientSockets.get(clientNames.indexOf(clientName));
                        break;
                    }
                }
                System.out.println(sender + " to " + friendName + ": " + chatMessage);
                sendChatClients(targerSocket,sender,chatMessage);
            }

        }

        private boolean verifyLogin(String username, String password) {
            // Query the database to verify dealLoginMessage credentials
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Returns true if there is a matching row in the result set
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;
        }
        
        private void broadcastMessage(String sender, String message) {
            //广播时使用多线程
            for (Socket socket : clientSockets) {
//                if (socket != clientSocket) {
                Thread broadcastThread = new Thread(()->{
                    try {
                        DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                        outToClient.writeUTF("START_GROUP_CHAT_UI");
                        outToClient.writeUTF("CHAT_IN_GROUP"+"@"+sender + ": " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                broadcastThread.start();

//                }
            }
        }

        private void sendOnlineClients() {
            for (Socket socket : clientSockets) {
//                if (socket != clientSocket) {
                    Thread sendClientsThread = new Thread(() -> {
                        try {
                            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                            String message = "ONLINE" + "@" + "Online Clients: " + String.join(", ", clientNames);
                            outToClient.writeUTF(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    sendClientsThread.start();
//                }
            }
        }

        private void sendOnlineClients(Socket socket) {
            Thread sendClientsThread = new Thread(()->{
                try {
                    DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                    String message = "ONLINE"+"@"+"Online Clients: " + String.join(", ", clientNames);
                    outToClient.writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            sendClientsThread.start();
        }

        private void sendRequestClients(Socket socket) {
            Thread sendClientsThread = new Thread(()->{
                try {
                    DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                    String message = "REQUEST"+"@"+"Online Clients: " + String.join(", ", clientNames);
                    outToClient.writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            sendClientsThread.start();
        }

        private void sendConnectClients(Socket socket) {
            Thread sendClientsThread = new Thread(()->{
                try {
                    DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                    String message = "CONNECT"+"@"+"Online Clients: " + String.join(", ", clientNames);
                    outToClient.writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            sendClientsThread.start();
        }

        private void sendChatClients(Socket socket,String sender,String chatMessage){
            Thread sendClientsThread = new Thread(()->{
                try {
                    DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                    writeMessage = "START_SINGLE_CHAT_UI"+"@"+sender;
                    outToClient.writeUTF(writeMessage);
                    writeMessage = "CHAT_IN_SINGLE"+"@"+ sender + "@" + chatMessage;
                    System.out.println(writeMessage);
                    outToClient.writeUTF(writeMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            sendClientsThread.start();
        }
    }
}


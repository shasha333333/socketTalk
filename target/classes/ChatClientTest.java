
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClientTest {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8888;
    private static final int CLIENT_PORT = 7777;

    private String username;
    private Socket serverSocket;
    private DatagramSocket clientSocket;
    private InetAddress serverAddress;
    private ArrayList<String> onlineUsers;
    // 其他需要的组件和变量




    public ChatClientTest() {
        // 初始化用户界面
        initializeUI();

        try {
            serverAddress = InetAddress.getByName(SERVER_HOST);
            serverSocket = new Socket(SERVER_HOST, SERVER_PORT);
            clientSocket = new DatagramSocket(CLIENT_PORT);

            // 启动接收服务器反馈的线程
            Thread receiveThread = new Thread(this::receiveFromServer);
            receiveThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        // 初始化用户界面组件
        // 包括登录界面、聊天窗口等
        // 添加事件处理程序
    }

    private void login(String username) {
        this.username = username;

        try {
            // 发送登录信息给服务器
            OutputStream outputStream = serverSocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(username);

            // 接收服务器的在线用户列表
            InputStream inputStream = serverSocket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String onlineUsersStr = dataInputStream.readUTF();
            onlineUsers = new ArrayList<>(Arrays.asList(onlineUsersStr.split(",")));

            // 更新用户界面显示在线用户列表
            // updateOnlineUsers(onlineUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToServer(String message) {
        try {
            // 发送消息给服务器
            OutputStream outputStream = serverSocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveFromServer() {
        try {
            while (true) {
                // 接收服务器的反馈信息
                InputStream inputStream = serverSocket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                String feedback = dataInputStream.readUTF();

                // 更新用户界面显示服务器的反馈信息
                // displayFeedback(feedback);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startChatWithUser(String user) {
        try {
            // 初始化与其他用户的聊天窗口，建立UDP通信
            // 通过UDP向其他用户发送消息，并在本地初始化对话窗口显示消息
            this.serverAddress = serverAddress;

            DataInputStream inFromServer = new DataInputStream(serverSocket.getInputStream());
            DataOutputStream outToServer = new DataOutputStream(serverSocket.getOutputStream());
            System.out.println("startChat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChatClientTest client = new ChatClientTest();
            }
        });
    }
}


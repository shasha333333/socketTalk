package test1;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private int port;
    private List<ClientHandler> clients;

    public ChatServer(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);

                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message, ClientHandler excludeClient) {
        for (ClientHandler client : clients) {
            if (client != excludeClient) {
                client.sendMessage(message);
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                // Receive the username from the client
                username = in.readUTF();
                System.out.println("New user joined: " + username);

                // Send a welcome message to the client
                String welcomeMessage = "Welcome, " + username + "!";
                sendMessage(welcomeMessage);

                // Broadcast the user's arrival to other clients
                String userJoinedMessage = username + " joined the chat.";
                broadcastMessage(userJoinedMessage, this);

                // Read and broadcast messages from the client
                String clientMessage;
                while (true) {
                    clientMessage = in.readUTF();
                    String broadcastMessage = username + ": " + clientMessage;
                    broadcastMessage(broadcastMessage, this);
                }

            } catch (IOException e) {
                System.out.println("User disconnected: " + username);
                clients.remove(this);
                broadcastMessage(username + " left the chat.", null);
            }
        }

        public void sendMessage(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 8888;

/*        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }*/

        ChatServer server = new ChatServer(port);
        server.start();
    }
}


import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<>();
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket);
                clients.add(client);
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public synchronized void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8888;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        Server server = new Server(port);
        server.start();
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataInputStream in;
        private DataOutputStream out;
        private String clientName;

        public ClientHandler(Socket socket) {
            clientSocket = socket;
            try {
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                // Get client name
                clientName = in.readUTF();
                System.out.println(clientName + " connected.");

                // Notify other clients about new connection
                broadcast(clientName + " joined the chat.");

                // Receive and broadcast messages
                while (true) {
                    String message = in.readUTF();
                    if (message.equals("/leave")) {
                        break;
                    }
                    broadcast(clientName + ": " + message);
                }

                // Notify other clients about disconnection
                broadcast(clientName + " left the chat.");

                // Clean up
                removeClient(this);
                clientSocket.close();
                in.close();
                out.close();
                System.out.println(clientName + " disconnected.");

            } catch (IOException e) {
                e.printStackTrace();
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
}

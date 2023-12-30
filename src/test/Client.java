import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private String host;
    private int port;
    private String username;

    public Client(String host, int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
    }

    public void start() {
        try (Socket socket = new Socket(host, port);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the chat server");
            // Send the username to the server
            out.writeUTF(username);

            // Start a thread to receive messages from the server
            Thread serverListener = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String message;
                        while (true) {
                            message = in.readUTF();
                            System.out.println(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            serverListener.start();

            // Read input from the user and send it to the server
            String input;
            while (true) {
                input = scanner.nextLine();
                out.writeUTF(input);
                if (input.equals("/leave")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8888;
        String username = "guest";

/*        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        if (args.length > 2) {
            username = args[2];
        }*/

        Client client = new Client(host, port, username);
        client.start();
    }
}


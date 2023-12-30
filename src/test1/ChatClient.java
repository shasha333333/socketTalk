package test1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient extends JFrame {
    private String host;
    private int port;
    private String username;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ChatClient(String host, int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;

        // Set up the GUI
        setTitle("Chat Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 300);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        bottomPanel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

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
                            displayMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            serverListener.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            try {
                out.writeUTF(message);
                messageField.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void displayMessage(String message) {
        chatArea.append(message + "\n");
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8888;
        String username = "guest";

        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        if (args.length > 2) {
            username = args[2];
        }

        ChatClient client = new ChatClient(host, port, username);
        client.setVisible(true);
        client.connect();
    }
}

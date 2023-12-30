package test;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Scanner;
//import com.vdurmont.emoji.EmojiParser;
//import com.fasterxml.*;

public class ChatClient extends JFrame {
/*    private String serverAddress;
    private int port;
    private static final long serialVersionUID = 1L;*/
    private Socket socket;

//    private MessageReceiverThread messageReceiverThread;

    private DataInputStream inFromServer;
    private DataOutputStream outToServer;

    private String readMessage;
    private String writeMessage;

    private String username = "测试";
//    private List<String> friends ;
    private String [] friends  = new String[]{"shasha","说谎","沙沙","好友4"};

//    private UDPClientServer udpClientServer;
    private AccountRegistrationLoginUI accountRegistrationLoginUI;
    private AccountRegistrationUI accountRegistrationUI;
    private GroupChatUI groupChatUI;
    private SingleChatUI singleChatUI;
    private BaseUI baseUI;

    public ChatClient() {

    }

    public ChatClient(String serverAddress, int port) {
        //参数初始化
        init(serverAddress, port);
    }

/*

    public class AccountRegistrationLogin extends JFrame {
        private JFrame frame;
        private JTextField usernameField;
        private JPasswordField passwordField;

        public AccountRegistrationLogin() {
            frame = new JFrame("Account Registration & Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new GridLayout(3, 2));

            JLabel usernameLabel = new JLabel("Username:");
            usernameField = new JTextField();

            JLabel passwordLabel = new JLabel("Password:");
            passwordField = new JPasswordField();

            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    AccountRegistrationUI.main(null);
                }
            });

            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    login();
                }
            });

            frame.add(usernameLabel);
            frame.add(usernameField);
            frame.add(passwordLabel);
            frame.add(passwordField);
            frame.add(registerButton);
            frame.add(loginButton);

            frame.setVisible(true);
            // 添加窗口关闭事件处理
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

*/
/*    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // 执行注册逻辑
        // 可以将用户名和密码存储在数据库或文件中
        System.out.println(username+" "+password);

        JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }*//*


        private void login() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());


            try {
                outToServer.writeUTF("LOGIN"+" "+username+" "+password);
            } catch (IOException ex) {
                messageArea.append("Error reading from server: " + ex.getMessage() + "\n");
            }

            try {
                String message= inFromServer.readUTF();
                if(message.equals("FAILED") ){
                    JOptionPane.showMessageDialog(frame, "Login Failed!", "Failed", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (IOException e) {
                messageArea.append("Error reading from server: " + e.getMessage() + "\n");
            }
        }
    }
*/


    /* private void initGUI() {
        this.setBounds(0,0,FinalData.SCREEN_SIZE.width/2,FinalData.SCREEN_SIZE.height/2);
//        this.setResizable(false);
//        this.setLocationRelativeTo(null);
        //历史消息框
        messageArea = new JTextArea(16, 40);
        messageArea.setEditable(false);

        //分为上下两个区域
        northPane = new JScrollPane(messageArea);
        add(northPane,BorderLayout.CENTER);
        southPanel = new JPanel(new BorderLayout());
        add(southPanel,BorderLayout.SOUTH);

        //消息区域
        //文本编辑区域
        textArea = new JTextArea("按下enter发送",16,40);
        textArea.setEditable(true);
//        textArea.addActionListener(this);
//        JPanel textPane = new JPanel(textField);

        JScrollPane textPane = new JScrollPane(textArea);
        southPanel.add(textPane,BorderLayout.WEST);


//        add(textPane,BorderLayout.SOUTH);

//        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//        add(splitPane);
        //发送按钮
        sendButton = new JButton("发送");
        sendButton.addActionListener(this);
        sendButton.setFont(new Font("宋体",Font.PLAIN,30));
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(sendButton,BorderLayout.NORTH);
        southPanel.add(buttonPanel,BorderLayout.EAST);
//        add(buttonPanel,BorderLayout.SOUTH);、

        //历史消息与文本编辑分割
        splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,northPane,southPanel);
        splitPane1.setDividerLocation(FinalData.SCREEN_SIZE.height/3);
        add(splitPane1);

        //输入框与发送按钮分割
        splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,textPane,buttonPanel);
        splitPane2.setDividerLocation(FinalData.SCREEN_SIZE.width*3/8);
        southPanel.add(splitPane2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        pack();
        setVisible(false);
        // 添加窗口关闭事件处理
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 关闭当前窗口
                dispose();
            }
        });
    }*/

    //建立连接
    private void init(String serverAddress, int port) {
        try{
//            this.serverAddress = serverAddress;
//            this.port = port;
            socket = new Socket(serverAddress, port);
            inFromServer = new DataInputStream(socket.getInputStream());
            outToServer = new DataOutputStream(socket.getOutputStream());
//            Thread receiveThread = new Thread(new ReceiveThread());
//            receiveThread.start();
            accountRegistrationLoginUI = new AccountRegistrationLoginUI();
            accountRegistrationUI = new AccountRegistrationUI();
            groupChatUI = new GroupChatUI();
            singleChatUI = new SingleChatUI();
            baseUI = new BaseUI();

//            // 创建发送消息线程
//            Thread sendThread = new Thread(new SendThread());
//            sendThread.start();
//            accountRegistrationLogin = new AccountRegistrationLogin();
        }catch(IOException e){
//            messageArea.append("Error reading from server: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            while (true){
                readMessage = inFromServer.readUTF();
                System.out.println("readMessage: "+readMessage);
                handleServerMessage(readMessage);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
/*//        try {
//
//            // 创建锁对象
//            Object lock = new Object();
//            accountRegistrationLogin = new AccountRegistrationLogin(lock);
////            chatUI = new ChatUI(lock);
//
//
//            accountRegistrationLogin.start();
//
//            // 等待注册完成
//            synchronized (lock) {
//                try {
//                    lock.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
////            chatUI.start();
//        }*/
        finally {
            try {
                socket.close();
            } catch (IOException e) {
//                messageArea.append("Error closing socket: " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }
    }

 /*   public Socket getSocket() {
        return socket;
    }*/

/*    public void sendChatMessage() {
        String message = messageArea.getText();
        if (!message.isEmpty()) {
            try {
                outToServer.writeUTF(message);
                messageArea.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

/*
    public static String emojiRecovery(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\[e\\]([0-9a-fA-F]+)\\[/e\\]", new AbstractMatcher() {
            @Override
            protected String match(CharSequence charSequence) {
                int codePoint = Integer.parseInt(charSequence.toString().substring(3, charSequence.length() - 4), 16);
                return String.valueOf(Character.toChars(codePoint));
            }
        });
    }

    public static String emojiConvert(String str) {
        if (str == null) {
            return null;
        }
        return EmojiParser.parseToAliases(str, EmojiParser.FitzpatrickAction.REMOVE);
    }
*/

/*    public void run() {
        // 接收消息
        try {
            String message;
            while ((message = inFromServer.readUTF()) != null) {
                messageArea.append(message + "\n");
            }

        } catch (IOException e) {
            messageArea.append("Error reading from server: " + e.getMessage() + "\n");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                messageArea.append("Error closing socket: " + e.getMessage() + "\n");
            }
        }
    }*/
    private void sendMessage(String message) {
        try {
            outToServer.writeUTF(message);
            System.out.println("writeMessage: "+message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.out.println("Usage: java ChatClientGUI <serverAddress> <port>");
//            return;
//        }
        String serverAddress = "localhost";
        int port = 8888;
//
//        // 创建锁对象
//        Object lock = new Object();

        ChatClient client = new ChatClient(serverAddress, port);
        client.start();
//        AccountRegistrationLogin accountRegistrationLogin = new AccountRegistrationLogin(serverAddress,port,lock);
//        ChatUI chatUI = new ChatUI(serverAddress,port,lock);
//        accountRegistrationLogin.start();

        // 等待注册完成
//        synchronized (lock) {
//            try {
//                lock.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//        chatUI.start();
    }

/*
//    private static class MessageReceiver extends Thread {
//        private Socket clientSocket;
//        private MessageListener messageListener;
//
//        public MessageReceiver(Socket clientSocket) {
//            this.clientSocket = clientSocket;
//        }
//
//        public void setMessageListener(MessageListener listener) {
//            this.messageListener = listener;
//        }
//
//        @Override
//        public void run() {
//            try {
//                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
//
//                // 循环接收客户端的消息并处理
//                String message;
//                while ((message = in.readUTF()) != null) {
//                    System.out.println("Received message: " + message);
//
//                    // 在这里将接收到的消息传递给监听器进行处理
//                    if (messageListener != null) {
//                        messageListener.onMessageReceived(message);
//                    }
//
//                    // 示例：向客户端发送回复消息
//                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//                    out.println("Server reply: " + message);
//                }
//
//                // 关闭连接
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
*/


    private void handleServerMessage(String message) {
        if (message.startsWith("START")){
            if (message.equals("START_LOGIN_UI")){
                if(!accountRegistrationLoginUI.startFlag){
                    accountRegistrationLoginUI.start();
                }

            }// 根据接收到的消息内容进行逻辑处理
            else if (message.equals("START_GROUP_CHAT_UI")) {
                if(!groupChatUI.startFlag){
                    groupChatUI.start();
                }

            }
            else if (message.startsWith("START_SINGLE_CHAT_UI")) {

                if (!singleChatUI.startFlag){
                    String[] parts = message.split("@");
//                    System.out.println(Arrays.toString(parts));
                    String friendName = parts[1];
                    singleChatUI.start(friendName);
                }

            }
            else if (message.equals("START_REGISTRATION_UI")) {
                // 注册成功，可以做出相应的操作
                if (!accountRegistrationUI.startFlag){
                    accountRegistrationUI.start();
                }

            }
            else if (message.equals("START_BASE_UI")){
                if (!baseUI.startFlag){
                    baseUI.start();
                    sendMessage("START_BASE_UI_SUCCESS");
                }

            }
        }

        else if (message.startsWith("LOGIN")){
            Component frame = new JFrame();
            if (message.equals("LOGIN_FAILED")) {
                JOptionPane.showMessageDialog(frame, "Login Failed!", "Failed", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (message.startsWith("LOGIN_SUCCESS")) {
//                    JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                    int result = JOptionPane.showOptionDialog(frame, "Login Successful!", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);\
                String[] parts = message.split("@");
//                    System.out.println(Arrays.toString(parts));
                this.username = parts[1];
                Object[] options = {"确定"};
                JOptionPane.showOptionDialog(frame, "Login Successful!", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                sendMessage("START_BASE_UI");
            }
            else if (message.equals("LOGIN_ONLINE_EXIST")) {
                JOptionPane.showMessageDialog(frame, "ONLINE_EXIST", "Failed", JOptionPane.INFORMATION_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(frame, "UNKNOWN_ERROR", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (message.startsWith("ONLINE")){
            System.out.println(readMessage);
//            chatUI.messageArea.append(readMessage+"\n");
        }
        else if (message.startsWith("CHAT")){
            if (message.startsWith("CHAT_IN_SINGLE")){

                String[] parts = readMessage.split("@");
//                    System.out.println(Arrays.toString(parts));
                String name = parts[1];
                String chatMessage = parts[2];
//                singleChatUI.start(name);
                singleChatUI.messageArea.append(name+":"+chatMessage+"\n");
                singleChatUI.textArea.setText("");
                System.out.println("CHAT_IN_SINGLE: "+chatMessage);
            }
            else if (message.startsWith("CHAT_IN_GROUP")){
                String[] parts = readMessage.split("@");
//                    System.out.println(Arrays.toString(parts));
                String chatMessage = parts[1];
                groupChatUI.messageArea.append(chatMessage+"\n");
//                groupChatUI.textArea.setText("");
                System.out.println(chatMessage);
            }
        }
        else if (message.startsWith("REQUEST")) {

        } else{
//            chatUI.messageArea.append(readMessage);
        }

    }

/*
    private class MessageReceiverThread extends Thread {
        @Override
        public void run() {
            try {
                while ((readMessage = inFromServer.readUTF()) != null) {
                    // 接收到消息后进行处理
                    handleServerMessage(readMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/

/*
    class ReceiveThread implements Runnable {
        @Override
        public void run() {
            try {
//                inFromServer = new DataInputStream(socket.getInputStream());
                while ((readMessage = inFromServer.readUTF())!= null) {
//                    accountRegistrationLogin
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class SendThread implements Runnable {
        @Override
        public void run() {
            try {
//                outToServer = new DataOutputStream(socket.getOutputStream());
                while (writeMessage != null) {
                    outToServer.writeUTF(writeMessage);
                    // 在这里处理发送的消息
                    System.out.println("发送消息：" + writeMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/



//    private static class UDPClientServer extends Thread{
//            DatagramSocket udpSocket;
//            DatagramPacket receivePacket;
//            DatagramPacket sendPacket;
//            byte[] receiveData;
//            byte[] sendData;
//            int port;
//            int serverPort;
//            String serverHost;
//            String sendMessage;
//            String receiveMessage;
//            public UDPClientServer(String serverHost,int serverPort){
//                this.serverHost = serverHost;
//                this.serverPort = serverPort;
//            }
//            public void run() {
//                try {
//                    // 创建一个 DatagramSocket 对象并指定端口号
//                    udpSocket = new DatagramSocket(0);
//                    // 获取分配端口号
//                    port = udpSocket.getPort();
//                    // 创建接收数据的缓冲区
//                    receiveData = new byte[1024];
//                    // 创建接收数据的 DatagramPacket 对象
//                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
//
//
//                    // 创建要发送的数据
//                    sendMessage = "";
//
//
//
//                    // 指定服务器的 IP 地址和端口号
//                    InetAddress serverAddress = InetAddress.getByName(serverHost);
//
//                    // 启动一个线程来接收数据
//                    Thread receiveThread = new Thread(() -> {
//                        try {
//                            while (true) {
//                                // 接收数据包
//                                udpSocket.receive(receivePacket);
//
//                                // 处理接收到的数据
//                                receiveMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
//                                System.out.println("Received message: " + receiveMessage);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//                    receiveThread.start();
//
//
//                    // 从控制台读取用户输入的消息，并发送给服务器
//                    Scanner scanner = new Scanner(System.in);
//                    while (!sendMessage.equals("exit")) {
//                        // 读取用户输入的消息
//                        sendMessage = scanner.nextLine();
//
//                        // 将消息转换为字节数组
//                        sendData = sendMessage.getBytes();
//
//                        // 创建发送的数据包
//                        sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
//
//                        // 发送数据包
//                        udpSocket.send(sendPacket);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                finally {
//                    // 关闭 socket
//                    udpSocket.close();
//                    System.out.println("UDP client exited.");
//                }
//            }
//    }

    private class BaseUI extends JFrame {
        private static final long serialVersionUID = 1L;
        private JPanel northPane;
        private JPanel southPanel;
        private JPanel buttonPanel;
        private JSplitPane splitPane1;
        private JSplitPane splitPane2;
        private JTextArea textArea;
        private JTextArea messageArea;
        private JButton sendButton;
        private JList<String> contactList;
        private boolean startFlag = false;

        public BaseUI() {super("BaseUI");}

/*    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // 执行注册逻辑
        // 可以将用户名和密码存储在数据库或文件中
        System.out.println(username+" "+password);

        JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }*/

        private void initGUI() {
            this.setBounds(0,0,FinalData.SCREEN_SIZE.width/4,FinalData.SCREEN_SIZE.height/2);
//        this.setResizable(false);
//        this.setLocationRelativeTo(null);
            //历史消息框

            //分为上下两个区域
            northPane = new JPanel(new BorderLayout());
            add(northPane,BorderLayout.NORTH);

            CardLayout cardLayout = new CardLayout();
            southPanel = new JPanel(cardLayout);
            add(southPanel,BorderLayout.SOUTH);

            splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,northPane,southPanel);
            splitPane1.setDividerLocation(FinalData.SCREEN_SIZE.height/9);
            add(splitPane1);

            String[] name = {"群聊","好友"};


            BorderLayout border1 = new BorderLayout();
            BorderLayout border2 = new BorderLayout();
            JPanel card1 = new JPanel(border1);
            JPanel card2 = new JPanel(border2);
            JLabel label1 = new JLabel(name[0]);
            JLabel label2 = new JLabel(name[1]);
            card1.add(label1,border1.SOUTH);
            card2.add(label2,border2.SOUTH);

            southPanel.add(card1,"群聊");
            southPanel.add(card2,"好友");

//            cardLayout.first(southPanel);


            //上半部面板设置
            JLabel usernameLabel = new JLabel("当前用户：" + username);
            northPane.add(usernameLabel,BorderLayout.NORTH);

            JMenuBar menuBar = new JMenuBar();
            northPane.add(menuBar,BorderLayout.SOUTH);

            JButton messageButton = new JButton(name[0]);
            JButton relativesButton = new JButton(name[1]);

            JButton enterGroupChat = new JButton("进入群聊");
            enterGroupChat.addActionListener(e->groupChat());
            card1.add(enterGroupChat,BorderLayout.NORTH);

            messageButton.addActionListener(e->cardLayout.first(southPanel));
            relativesButton.addActionListener(e->cardLayout.last(southPanel));

            menuBar.add(messageButton,BorderLayout.WEST);
            menuBar.add(relativesButton,BorderLayout.EAST);



            contactList = new JList<>(friends);
            card2.add(contactList);

//            contactList.addListSelectionListener(e-> System.out.println("当前选中项目："+contactList.getSelectedValue()));

            contactList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        // 处理选择事件
                        System.out.println("当前选中项目："+contactList.getSelectedValue());
                        writeMessage = contactList.getSelectedValue();
                        singleChat(writeMessage);
//                        Object selectedValue = contactList.getSelectedValue();
                        // 其他操作...
                    }
                }
            });

//        pack();

            // 添加窗口关闭事件处理
            setVisible(true);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

/*        //建立网络连接
        private void init(String serverAddress, int port) {
            try{
                this.serverAddress = serverAddress;
                this.port = port;
                socket = new Socket(serverAddress, port);
                inFromServer = new DataInputStream(socket.getInputStream());
                outToServer = new DataOutputStream(socket.getOutputStream());
            }catch(IOException e){
                messageArea.append("Error reading from server: " + e.getMessage() + "\n");
            }
        }*/

        public void start() {
            this.startFlag = true;
            initGUI();
//            synchronized (lock) {
//                // 注册完成后调用 notify() 方法唤醒主线程
//                lock.notify();
//            }
        }

        private void sendRequestMessage(String name) {
            String requestMessage = "REQUEST"+"@" + name;
//            System.out.println(requestMessage);
            sendMessage(requestMessage);
        }

        private void singleChat(String name){
            writeMessage = "START_SINGLE_CHAT_UI"+"@" + name;
            sendMessage(writeMessage);
        }

        private void groupChat(){
            writeMessage = "START_GROUP_CHAT_UI";
            sendMessage(writeMessage);
        }

/*
    public static String emojiRecovery(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\[e\\]([0-9a-fA-F]+)\\[/e\\]", new AbstractMatcher() {
            @Override
            protected String match(CharSequence charSequence) {
                int codePoint = Integer.parseInt(charSequence.toString().substring(3, charSequence.length() - 4), 16);
                return String.valueOf(Character.toChars(codePoint));
            }
        });
    }

    public static String emojiConvert(String str) {
        if (str == null) {
            return null;
        }
        return EmojiParser.parseToAliases(str, EmojiParser.FitzpatrickAction.REMOVE);
    }
*/

    }

    /**传递消息尚未完成*/
    private class GroupChatUI extends JFrame {
        private static final long serialVersionUID = 1L;
        private JScrollPane northPane;
        private JPanel southPanel;
        private JPanel buttonPanel;
        private JSplitPane splitPane1;
        private JSplitPane splitPane2;
        private JTextArea textArea;
        private JTextArea messageArea;
        private JButton sendButton;
        private boolean startFlag = false;

        public GroupChatUI() {
            super("GroupChatUI");
        }

        private void initGUI() {
            this.setBounds(0,0,FinalData.SCREEN_SIZE.width/2,FinalData.SCREEN_SIZE.height/2);
//        this.setResizable(false);
//        this.setLocationRelativeTo(null);
            //历史消息框
            messageArea = new JTextArea(16, 40);
            messageArea.setEditable(false);

            //分为上下两个区域
            northPane = new JScrollPane(messageArea);
            add(northPane,BorderLayout.CENTER);
            southPanel = new JPanel(new BorderLayout());
            add(southPanel,BorderLayout.SOUTH);

            //消息区域
            //文本编辑区域
            textArea = new JTextArea(16,40);
            textArea.setEditable(true);

            JScrollPane textPane = new JScrollPane(textArea);
            southPanel.add(textPane,BorderLayout.WEST);

//        add(textPane,BorderLayout.SOUTH);

//        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//        add(splitPane);
            //发送按钮
            sendButton = new JButton("发送");
//            sendButton.addActionListener(this);
            sendButton.addActionListener(e -> sendChatMessage());
            sendButton.setFont(new Font("宋体",Font.PLAIN,30));
            buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.add(sendButton,BorderLayout.NORTH);
            southPanel.add(buttonPanel,BorderLayout.EAST);
//        add(buttonPanel,BorderLayout.SOUTH);、

            //历史消息与文本编辑分割
            splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,northPane,southPanel);
            splitPane1.setDividerLocation(FinalData.SCREEN_SIZE.height/3);
            add(splitPane1);

            //输入框与发送按钮分割
            splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,textPane,buttonPanel);
            splitPane2.setDividerLocation(FinalData.SCREEN_SIZE.width*3/8);
            southPanel.add(splitPane2);

//        pack();

            // 添加窗口关闭事件处理
            setVisible(true);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

        public void start() {
            this.startFlag = true;
            initGUI();
//            synchronized (lock) {
//                // 注册完成后调用 notify() 方法唤醒主线程
//                lock.notify();
//            }
        }

        private void sendChatMessage() {
            writeMessage = "CHAT_IN_GROUP"+"@"+textArea.getText();
            textArea.setText("");
            sendMessage(writeMessage);
        }
    }

    /**传递消息尚未完成*/
    private class SingleChatUI extends JFrame {
        private static final long serialVersionUID = 1L;
        private JScrollPane northPane;
        private JPanel southPanel;
        private JPanel buttonPanel;
        private JSplitPane splitPane1;
        private JSplitPane splitPane2;
        private JTextArea textArea;
        private JTextArea messageArea;
        private JButton sendButton;
        private String friendName;
        private boolean startFlag = false;

        public SingleChatUI() {
//            super("SingleChatUI");
        }

        private void initGUI() {
            this.setTitle(friendName);
            this.setBounds(0,0,FinalData.SCREEN_SIZE.width/2,FinalData.SCREEN_SIZE.height/2);
//        this.setResizable(false);
//        this.setLocationRelativeTo(null);
            //历史消息框
            messageArea = new JTextArea(16, 40);
            messageArea.setEditable(false);

            //分为上下两个区域
            northPane = new JScrollPane(messageArea);
            add(northPane,BorderLayout.CENTER);
            southPanel = new JPanel(new BorderLayout());
            add(southPanel,BorderLayout.SOUTH);

            //消息区域
            //文本编辑区域
            textArea = new JTextArea(16,40);
            textArea.setEditable(true);

            JScrollPane textPane = new JScrollPane(textArea);
            southPanel.add(textPane,BorderLayout.WEST);

//        add(textPane,BorderLayout.SOUTH);

//        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//        add(splitPane);
            //发送按钮
            sendButton = new JButton("发送");
//            sendButton.addActionListener(this);
            sendButton.addActionListener(e -> sendChatMessage());
            sendButton.setFont(new Font("宋体",Font.PLAIN,30));
            buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.add(sendButton,BorderLayout.NORTH);
            southPanel.add(buttonPanel,BorderLayout.EAST);
//        add(buttonPanel,BorderLayout.SOUTH);、

            //历史消息与文本编辑分割
            splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,northPane,southPanel);
            splitPane1.setDividerLocation(FinalData.SCREEN_SIZE.height/3);
            add(splitPane1);

            //输入框与发送按钮分割
            splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,textPane,buttonPanel);
            splitPane2.setDividerLocation(FinalData.SCREEN_SIZE.width*3/8);
            southPanel.add(splitPane2);



//        pack();

            // 添加窗口关闭事件处理
            setVisible(true);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

        public void start(String friendName) {
            this.friendName = friendName;
            this.startFlag = true;
            initGUI();
        }

        private void sendChatMessage() {
            writeMessage = "CHAT_IN_SINGLE"+"@"+username+"@"+friendName+"@"+textArea.getText();
            messageArea.append(username+":"+textArea.getText()+"\n");
            textArea.setText("");
            sendMessage(writeMessage);
        }

/*
    public static String emojiRecovery(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\[e\\]([0-9a-fA-F]+)\\[/e\\]", new AbstractMatcher() {
            @Override
            protected String match(CharSequence charSequence) {
                int codePoint = Integer.parseInt(charSequence.toString().substring(3, charSequence.length() - 4), 16);
                return String.valueOf(Character.toChars(codePoint));
            }
        });
    }

    public static String emojiConvert(String str) {
        if (str == null) {
            return null;
        }
        return EmojiParser.parseToAliases(str, EmojiParser.FitzpatrickAction.REMOVE);
    }
*/
    }
    
    private class AccountRegistrationUI extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton registerButton;
        private boolean startFlag = false;

        private static final String url = "jdbc:mysql://localhost:3306/chat";
        private static final String username = "username";
        private static final String password = "password";

        public AccountRegistrationUI() {
//            super(serverAddress,port);
//            this.socket = socket;
        }

        public void start() {
            this.startFlag = true;
            initGUI();
        }

        public void run() {
            // 执行注册逻辑
            // 注册完成后通知主线程

        }

        private void initGUI() {
            setTitle("账号注册");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(3, 2));

            JLabel usernameLabel = new JLabel("用户名:");
            JLabel passwordLabel = new JLabel("密码:");

            usernameField = new JTextField();
            passwordField = new JPasswordField();

            registerButton = new JButton("注册");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    registerAccount();
                }
            });

            add(usernameLabel);
            add(usernameField);
            add(passwordLabel);
            add(passwordField);
            add(registerButton);

            pack();
            setVisible(true);
        }

        private void registerAccount() {
            String enteredUsername = usernameField.getText();
            String enteredPassword = new String(passwordField.getPassword());

            try {
                Connection connection = DriverManager.getConnection(url, username, password);

                // 检查用户名是否已存在
                if (isUsernameExists(connection, enteredUsername)) {
                    JOptionPane.showMessageDialog(this, "用户名已存在！");
                    return;
                }

                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, enteredUsername);
                statement.setString(2, enteredPassword);

                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "注册成功！");
                    usernameField.setText("");
                    passwordField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "注册失败！");
                }

                statement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private boolean isUsernameExists(Connection connection, String username) throws SQLException {
            String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            statement.close();

            return (count > 0);
        }

    }

    private class AccountRegistrationLoginUI extends JFrame {
        private JFrame frame;
        private JTextField usernameField;
        private JPasswordField passwordField;

        private boolean startFlag = false;

        public AccountRegistrationLoginUI() {
//            super(serverAddress, port);
//            this.socket = socket;

//            initGUI();
        }

        public void start() {
            this.startFlag = true;
            initGUI();
        }

        private void initGUI() {
            frame = new JFrame("Account Registration & Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new GridLayout(3, 2));

            JLabel usernameLabel = new JLabel("Username:");
            usernameField = new JTextField();

            JLabel passwordLabel = new JLabel("Password:");
            passwordField = new JPasswordField();

            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(e -> register());

            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(e -> login());

            frame.add(usernameLabel);
            frame.add(usernameField);
            frame.add(passwordLabel);
            frame.add(passwordField);
            frame.add(registerButton);
            frame.add(loginButton);

            frame.setVisible(true);
            // 添加窗口关闭事件处理
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

/*    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // 执行注册逻辑
        // 可以将用户名和密码存储在数据库或文件中
        System.out.println(username+" "+password);

        JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }*/
        private void register(){
            writeMessage = "START_REGISTRATION_UI" ;
            sendMessage(writeMessage);
        }


        private void login() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            writeMessage = "LOGIN" + "@" + username + "@" + password;
            sendMessage(writeMessage);
//            if (readMessage.equals("FAILED")) {
//                    JOptionPane.showMessageDialog(frame, "Login Failed!", "Failed", JOptionPane.INFORMATION_MESSAGE);
//                } else if (readMessage.equals("SUCCESS")) {
////                    JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
////                    int result = JOptionPane.showOptionDialog(frame, "Login Successful!", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
//                    Object[] options = {"确定"};
//                    int result = JOptionPane.showOptionDialog(frame, "Login Successful!", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
//                    // 判断用户点击的按钮是确定按钮（0）还是关闭窗口（-1）
////                    unshowAccountRegistrationLogin();
////                    accountRegistrationLogin.setVisible(false);
////                    System.out.println("111");
////                    accountRegistrationLogin.dispose();
////                    System.out.println("121");
//                    chatUI = new ChatUI();
//                    chatUI.start();
//
//
//                } else if (readMessage.equals("ONLINE_EXIST")) {
//                    JOptionPane.showMessageDialog(frame, "ONLINE_EXIST", "Failed", JOptionPane.INFORMATION_MESSAGE);
//                } else {
//                    JOptionPane.showMessageDialog(frame, "UNKNOWN_ERROR", "Error", JOptionPane.INFORMATION_MESSAGE);
//                }
        }

//        private void sendMessage(String writeMessage) {
//            try {
//                outToServer.writeUTF(writeMessage);
//            } catch (IOException ex) {
//                JOptionPane.showMessageDialog(frame, "Error reading from server: " + ex.getMessage() + "\n", "Error", JOptionPane.INFORMATION_MESSAGE);
//            }
//        }
    }

}

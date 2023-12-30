/*
package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
//import com.vdurmont.emoji.EmojiParser;
//import com.fasterxml.*;


public class ChatUI extends ChatClient {
    private String serverAddress;
    private int port;
    private static final long serialVersionUID = 1L;
    private Socket socket;
    private Object lock;
    DataInputStream inFromServer;
    DataOutputStream outToServer;
    private JScrollPane northPane;
    private JPanel southPanel;
    private JPanel buttonPanel;
    private JSplitPane splitPane1;
    private JSplitPane splitPane2;
    private JTextArea textArea;
    private JTextArea messageArea;
    private JButton sendButton;

    public ChatUI() {

    }

    public ChatUI(Object lock) {
        super("ChatUI");
        this.lock = lock;
//        // 网络连接
//        init(serverAddress, port);
//        // 构建GUI
//        initGUI();
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

        // 添加窗口关闭事件处理
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 关闭当前窗口
                dispose();
            }
        });
        setVisible(true);
//        show();
    }

    //建立网络连接
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 发送消息
        String input;
        input = textArea.getText();
        try {
            outToServer.writeUTF(input);
            textArea.setText("");
        } catch (IOException ex) {
            messageArea.append("Error reading from server: " + ex.getMessage() + "\n");
        }
    }

    public void start() {
        initGUI();
        synchronized (lock) {
            // 注册完成后调用 notify() 方法唤醒主线程
            lock.notify();
        }
    }


    public void sendMessage() {
        String message = messageArea.getText();
        if (!message.isEmpty()) {
            try {
                outToServer.writeUTF(message);
                messageArea.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

*/
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
*//*


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
    }*//*


    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.out.println("Usage: java ChatClientGUI <serverAddress> <port>");
//            return;
//        }

        String serverAddress = "localhost";
        int port = 8888;
//        ChatUI client = new ChatUI(serverAddress, port);
    }
}
*/

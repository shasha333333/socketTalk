//package test;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Scanner;
//
//public class AccountRegistrationUI extends ChatClient {
//    private JTextField usernameField;
//    private JPasswordField passwordField;
//    private JButton registerButton;
//    private Object lock;
//
//    private static final String url = "jdbc:mysql://localhost:3306/chat";
//    private static final String username = "username";
//    private static final String password = "password";
//
//    public AccountRegistrationUI(String serverAddress,int port,Object lock) {
//        super(serverAddress,port);
//        this.lock = lock;
//    }
//
//    public void start() {
//        Thread serverListener = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                initGUI();
//                synchronized (lock) {
//                    // 注册完成后调用 notify() 方法唤醒主线程
//                    lock.notify();
//                }
//            }
//        });
//        serverListener.start();
//    }
//    public void run() {
//        // 执行注册逻辑
//        // 注册完成后通知主线程
//
//    }
//    private void initGUI() {
//        setTitle("账号注册");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new GridLayout(3, 2));
//
//        JLabel usernameLabel = new JLabel("用户名:");
//        JLabel passwordLabel = new JLabel("密码:");
//
//        usernameField = new JTextField();
//        passwordField = new JPasswordField();
//
//        registerButton = new JButton("注册");
//        registerButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                registerAccount();
//            }
//        });
//
//        add(usernameLabel);
//        add(usernameField);
//        add(passwordLabel);
//        add(passwordField);
//        add(registerButton);
//
//        pack();
//        setVisible(true);
//    }
//
//    private void registerAccount() {
//        String enteredUsername = usernameField.getText();
//        String enteredPassword = new String(passwordField.getPassword());
//
//        try {
//            Connection connection = DriverManager.getConnection(url, username, password);
//
//            // 检查用户名是否已存在
//            if (isUsernameExists(connection, enteredUsername)) {
//                JOptionPane.showMessageDialog(this, "用户名已存在！");
//                return;
//            }
//
//            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, enteredUsername);
//            statement.setString(2, enteredPassword);
//
//            int rowsInserted = statement.executeUpdate();
//
//            if (rowsInserted > 0) {
//                JOptionPane.showMessageDialog(this, "注册成功！");
//                usernameField.setText("");
//                passwordField.setText("");
//            } else {
//                JOptionPane.showMessageDialog(this, "注册失败！");
//            }
//
//            statement.close();
//            connection.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private boolean isUsernameExists(Connection connection, String username) throws SQLException {
//        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, username);
//
//        ResultSet resultSet = statement.executeQuery();
//        resultSet.next();
//        int count = resultSet.getInt(1);
//
//        statement.close();
//
//        return (count > 0);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
////                new AccountRegistrationUI();
//            }
//        });
//    }
//}

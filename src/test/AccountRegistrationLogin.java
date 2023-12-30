/*
package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AccountRegistrationLogin extends ChatClient {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Object lock;
    public AccountRegistrationLogin(String serverAddress,int port,Object lock) {
        super(serverAddress,port);
        this.lock = lock;
//        initGUI();
    }

    public void start() {
        Thread serverListener = new Thread(new Runnable() {
            @Override
            public void run() {
                initGUI();
                synchronized (lock) {
                    // 注册完成后调用 notify() 方法唤醒主线程
                    lock.notify();
                }
            }
        });
        serverListener.start();
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
            JOptionPane.showMessageDialog(frame, "Error reading from server: " + ex.getMessage() + "\n", "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        try {
            String message= inFromServer.readUTF();
            if(message.equals("FAILED") ){
                JOptionPane.showMessageDialog(frame, "Login Failed!", "Failed", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(message.equals("SUCCESS")){
                JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(frame, "UNKNOWN_ERROR", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading from server: " + e.getMessage() + "\n", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}*/

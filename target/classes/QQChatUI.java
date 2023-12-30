
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QQChatUI {
    private JFrame frame;
    private JList<String> contactList;
    private JTextArea chatArea;
    private JButton sendButton;
    private JTextField messageField;

    public QQChatUI() {
        initialize();
    }

    private void initialize() {
        // 创建主窗口
        frame = new JFrame("QQ Chat");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        // 创建联系人列表
        contactList = new JList<>(new String[]{"Friend 1", "Friend 2", "Friend 3"});
        frame.getContentPane().add(new JScrollPane(contactList), BorderLayout.WEST);

        // 创建聊天区域
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // 创建消息输入框和发送按钮
        JPanel messagePanel = new JPanel();
        frame.getContentPane().add(messagePanel, BorderLayout.SOUTH);
        messagePanel.setLayout(new BorderLayout(0, 0));

        messageField = new JTextField();
        messagePanel.add(messageField, BorderLayout.CENTER);
        messageField.setColumns(10);

        sendButton = new JButton("Send");
        messagePanel.add(sendButton, BorderLayout.EAST);

        // 注册发送按钮的事件监听器
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    chatArea.append("Me: " + message + "\n");
                    messageField.setText("");
                }
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        QQChatUI chatUI = new QQChatUI();
        chatUI.show();
    }
}

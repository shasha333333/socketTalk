

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClientServer extends Thread{
    DatagramSocket udpSocket;
    DatagramPacket receivePacket;
    DatagramPacket sendPacket;
    byte[] receiveData;
    byte[] sendData;
    int port;
    int serverPort;
    String serverHost;
    String sendMessage;
    String receiveMessage;
    public UDPClientServer(String serverHost,int serverPort){
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }
    public void run() {
        try {
            // 创建一个 DatagramSocket 对象并指定端口号
            udpSocket = new DatagramSocket(0);
            // 获取分配端口号
            port = udpSocket.getPort();
            // 创建接收数据的缓冲区
            receiveData = new byte[1024];
            // 创建接收数据的 DatagramPacket 对象
            receivePacket = new DatagramPacket(receiveData, receiveData.length);


            // 创建要发送的数据
            sendMessage = "";

            
            
            // 指定服务器的 IP 地址和端口号
            InetAddress serverAddress = InetAddress.getByName(serverHost);

            // 启动一个线程来接收数据
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        // 接收数据包
                        udpSocket.receive(receivePacket);

                        // 处理接收到的数据
                        receiveMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        System.out.println("Received message: " + receiveMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();


            // 从控制台读取用户输入的消息，并发送给服务器
            Scanner scanner = new Scanner(System.in);
            while (!sendMessage.equals("exit")) {
                // 读取用户输入的消息
                sendMessage = scanner.nextLine();

                // 将消息转换为字节数组
                sendData = sendMessage.getBytes();

                // 创建发送的数据包
                sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

                // 发送数据包
                udpSocket.send(sendPacket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // 关闭 socket
            udpSocket.close();
            System.out.println("UDP client exited.");
        }
    }
}


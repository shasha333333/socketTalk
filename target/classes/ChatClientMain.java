//package test;
//
//public class ChatClientMain {
//    public static void main(String[] args) {
//        String serverAddress = "localhost";
//        int port = 8888;
//
//        // 创建锁对象
//        Object lock = new Object();
//
////        ChatClient client = new ChatClient(serverAddress, port);
////        client.start();
//        AccountRegistrationLogin accountRegistrationLogin = new AccountRegistrationLogin(serverAddress,port,lock);
//        ChatUI chatUI = new ChatUI();
//        accountRegistrationLogin.start();
//
//        // 等待注册完成
//        synchronized (lock) {
//            try {
//                lock.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        chatUI.start();
//    }
//}

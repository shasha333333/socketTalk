/**
 * 注意：启动该程序前需现在本地机上创建“chat”的mysql数据库，
 * 创建对应用户名DB_USER，配置其密码：DB_PASSWORD，
 * 并赋予DB_USER在“chat”上的所有权限
 * 请确保该程序运行时mysql也在运行
 */

package test;

import java.sql.*;

public class DatabaseSetup {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chat";
    private static final String DB_USER = "username";
    private static final String DB_PASSWORD = "password";

    private static Connection connection = null;
    private static Statement statement = null;


    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database.");


            statement = connection.createStatement();

            // 创建 users 表
            String createUserTable = "CREATE TABLE users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "password VARCHAR(50) NOT NULL" +
                    "friends TEXT" +
                    ")";
            statement.executeUpdate(createUserTable);
            System.out.println("Created users table.");

//            String add = "ALTER TABLE users ADD COLUMN friends TEXT";
//            statement.executeUpdate(add);

            // 创建 messages 表
            String createMessageTable = "CREATE TABLE messages (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "sender VARCHAR(50) NOT NULL," +
                    "receiver VARCHAR(50) NOT NULL," +
                    "message VARCHAR(255) NOT NULL," +
                    "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            statement.executeUpdate(createMessageTable);
            System.out.println("Created messages table.");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null){
                try{
                    statement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            System.out.println("Disconnected from the database.");
        }
    }
}

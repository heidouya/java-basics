package _12exception;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 捕获异常
public class Demo2 {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:111/db", "111", "111");
        } catch (SQLException e){
            System.out.println(STR."错误信息：\{e.getMessage()}");
        } finally {
            System.out.println("不管走 try 还是 catch 都会走finally里的代码，可以做一些清理工作");
        }

        //try-with-resources 语句是一种用于自动管理资源的机制。它在 Java 7 中引入，旨在简化资源管理，确保资源在使用完毕后自动关闭，从而避免资源泄漏问题。
        try (
                FileReader fr = new FileReader("test.txt");
                BufferedReader br = new BufferedReader(fr)
        ) {
            String line = br.readLine();
        } catch (IOException e) {
            System.out.println(STR."错误信息：\{e.getMessage()}");
        }
    }
}

package _02network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            // 1. 创建ServerSocket，绑定监听端口 9000
            serverSocket = new ServerSocket(9000);
            System.out.println("TCP服务端启动，端口：9000，等待客户端连接...");

            // 2. accept() 阻塞方法：没有客户端连接就卡住，有连接返回Socket对象
            socket = serverSocket.accept();
            System.out.println(STR."客户端已接入，客户端IP：\{socket.getInetAddress()}");

            // 3. 获取输入流，读取客户端发送的数据
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientMsg = br.readLine();
            System.out.println(STR."收到客户端消息：\{clientMsg}");

            // 4. 获取输出流，向客户端回复消息
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            osw.write(STR."服务端收到！你的消息是：\{clientMsg}");
            osw.flush(); // 刷新缓冲区，数据才会真正发送出去

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            // 5. 关闭资源
            try {
                if(socket != null) socket.close();
                if(serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
package _02network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPChatServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            // 1. 服务端绑定端口8899，监听客户端连接
            serverSocket = new ServerSocket(8899);
            System.out.println("【服务端】启动成功，端口8899，等待客户端连接...");
            // 阻塞等待客户端接入
            socket = serverSocket.accept();
            System.out.println(STR."【服务端】客户端已连接：\{socket.getInetAddress()}");

            // 开启子线程：专门用来读取客户端发来的消息（防止读操作阻塞主线程输入）
            Socket finalSocket = socket;
            new Thread(() -> {
                try {
                    // 输入流：读取客户端消息
                    BufferedReader br = new BufferedReader(new InputStreamReader(finalSocket.getInputStream()));
                    String msg;
                    // 循环读取客户端消息
                    while ((msg = br.readLine()) != null) {
                        if ("exit".equals(msg)) {
                            System.out.println("【服务端】客户端请求退出聊天");
                            break;
                        }
                        System.out.println(STR."\n【收到客户端】：\{msg}");
                        System.out.print("【我(服务端)】：");
                    }
                } catch (IOException e) {
                    System.out.println("【服务端】连接断开");
                }
            }).start();

            // ========== 主线程：服务端控制台输入，发送消息给客户端 ==========
            Scanner scanner = new Scanner(System.in);
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            while (true) {
                System.out.print("【我(服务端)】：");
                String sendMsg = scanner.nextLine();
                osw.write(STR."\{sendMsg}\n"); // 追加换行符，客户端readLine识别结束
                osw.flush(); // 刷新缓冲区，发送数据
                if ("exit".equals(sendMsg)) {
                    System.out.println("【服务端】准备退出");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("【服务端】连接关闭");
        }
    }
}
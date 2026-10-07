package _02network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPChatClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            // 1. 连接本机服务端IP+端口
            socket = new Socket("127.0.0.1", 8899);
            System.out.println("【客户端】成功连上服务端！");

            // 子线程：持续读取服务端推送的消息
            Socket finalSocket = socket;
            new Thread(() -> {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(finalSocket.getInputStream()));
                    String msg;
                    while ((msg = br.readLine()) != null) {
                        if ("exit".equals(msg)) {
                            System.out.println("【客户端】服务端退出聊天");
                            break;
                        }
                        System.out.println(STR."\n【收到服务端】：\{msg}");
                        System.out.print("【我(客户端)】：");
                    }
                } catch (IOException e) {
                    System.out.println("【客户端】连接断开");
                }
            }).start();

            // 主线程：控制台输入消息发送给服务端
            Scanner scanner = new Scanner(System.in);
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            while (true) {
                System.out.print("【我(客户端)】：");
                String sendMsg = scanner.nextLine();
                osw.write(STR."\{sendMsg}\n");
                osw.flush();
                if ("exit".equals(sendMsg)) {
                    System.out.println("【客户端】准备退出");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("【客户端】连接关闭");
        }
    }
}
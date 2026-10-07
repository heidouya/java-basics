package _02network;
/*
# Java TCP 简易聊天程序
功能说明：
- 服务端，可以接收一个客户端连接
- 客户端和服务端**双向聊天**：两边都可以输入文字发送消息
- 输入 `exit` 即可退出聊天，关闭连接
> 底层：TCP字节流；这里用换行`\n`作为消息分隔符（简易处理粘包）

## 1. 服务端代码 TCPChatServer.java
```java
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
```

## 2. 客户端代码 TCPChatClient.java
```java
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
```

# 运行步骤
1. 先运行 `TCPChatServer`，等待客户端连接
```
【服务端】启动成功，端口8899，等待客户端连接...
```
2. 再运行 `TCPChatClient`
```
【客户端】成功连上服务端！
```
3. 两边控制台互相输入文字，回车发送；输入`exit`关闭聊天。

## 核心原理说明
1. **多线程分工**
    - 子线程：专门`readLine()`读取对方消息（阻塞读）
    - 主线程：Scanner读取控制台输入，发送消息
    > 如果单线程写，读消息阻塞，就没法同时输入发送消息。
2. 消息分隔：使用`\n`换行，`readLine()`读到换行才返回，简易解决粘包
3. 退出机制：发送`exit`字符串，双方收到后结束循环，关闭socket触发四次挥手

## 现有缺点（面试可以说）
1. 当前版本**只能支持1个客户端**，多客户端需要在服务端外层套while循环，每来一个客户端新建线程
2. 异常处理比较简单，网络异常时容错弱
3. 使用换行分隔消息，消息内容不能自带换行；生产环境要用「长度头+消息体」方案
*/
public class Demo7 {
}

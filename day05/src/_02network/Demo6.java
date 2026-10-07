package _02network;
/*
# Java TCP网络编程
## 核心知识点
TCP是**面向连接、可靠传输**协议，通信前建立三次握手，通信结束四次挥手。
Java TCP核心两个类：
1. `ServerSocket`：服务端套接字，**绑定端口，监听客户端连接**，`accept()`阻塞等待客户端接入
2. `Socket`：客户端套接字，主动发起连接；服务端accept成功后也会返回Socket，用来读写IO流

> TCP本质：字节流通信，没有消息边界，会存在粘包问题。
> IO流：`getInputStream()`读取对方数据；`getOutputStream()`向外写数据。

---
## 示例1：基础版TCP（单次一问一答）
### 服务端 TcpServer.java
```java
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
            System.out.println("客户端已接入，客户端IP：" + socket.getInetAddress());

            // 3. 获取输入流，读取客户端发送的数据
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientMsg = br.readLine();
            System.out.println("收到客户端消息：" + clientMsg);

            // 4. 获取输出流，向客户端回复消息
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            osw.write("服务端收到！你的消息是：" + clientMsg);
            osw.flush(); // 刷新缓冲区，数据才会真正发送出去

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭资源
            try {
                if(socket != null) socket.close();
                if(serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### 客户端 TcpClient.java
```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TcpClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            // 1. 创建Socket，指定服务端IP和端口，发起TCP连接（底层完成三次握手）
            socket = new Socket("127.0.0.1", 9000);
            System.out.println("客户端成功连接服务端");

            // 2. 获取输出流，发送消息给服务端
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            String msg = "Hello TCP！";
            osw.write(msg);
            osw.write("\n"); // 换行符，服务端readLine靠换行判断一行结束
            osw.flush();

            // 3. 获取输入流，读取服务端返回的响应
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String resp = br.readLine();
            System.out.println("收到服务端回复：" + resp);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭socket，触发四次挥手断开连接
            try {
                if(socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### ✅ 运行步骤
1. **先启动 TcpServer**，控制台输出：`TCP服务端启动，端口：9000，等待客户端连接...`
2. **再启动 TcpClient**
服务端控制台：
```
TCP服务端启动，端口：9000，等待客户端连接...
客户端已接入，客户端IP：/127.0.0.1
收到客户端消息：Hello TCP！
```
客户端控制台：
```
客户端成功连接服务端
收到服务端回复：服务端收到！你的消息是：Hello TCP！
```

> 缺点：基础版只能处理**1个客户端一次通信**，通信完毕服务端直接关闭。

---
## 示例2：多线程版TCP服务端（支持多个客户端同时连接）
> 核心思路：主线程循环调用`accept()`等待客户端；每接入一个客户端，新开独立线程单独处理通信。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadTcpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        System.out.println("多线程TCP服务端启动，端口9000");

        // 循环：持续等待新客户端接入
        while(true){
            Socket socket = serverSocket.accept();
            System.out.println("新客户端连接：" + socket.getInetAddress());

            // 开启新线程，单独处理当前客户端
            new Thread(() -> {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msg = br.readLine();
                    System.out.println("收到消息：" + msg);

                    OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
                    osw.write("服务端已收到消息：" + msg);
                    osw.flush();

                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
```
客户端代码继续使用上面的 `TcpClient.java`，可以多次启动客户端，同时和服务端通信。

---
## ⚠️ 重点注意事项
1. `flush()`：字符流有缓冲区，不调用flush，数据会留在缓冲区，发不出去。
2. `readLine()`：读取一行，**依靠换行符`\n`**，发送的时候必须带上换行，否则阻塞。
3. 粘包问题：TCP是字节流，没有消息边界。上面代码用换行符作为消息分隔符，是简易解决粘包方案。生产环境推荐：**消息头(长度)+消息体**。
4. `accept()`、`read()`、`readLine()`都是**阻塞方法**：没有数据/连接时，线程卡住等待。
5. 关闭socket：调用close()，底层触发四次挥手释放连接。

## 📌 面试核心问题
1. ServerSocket 和 Socket 的区别？
> ServerSocket运行在服务端，只负责监听端口，接收客户端连接；Socket两端都有，负责实际的数据收发。
2. 三次握手代码在哪里执行？
> `new Socket(ip,port)`客户端发起连接，`accept()`返回前，操作系统内核完成三次握手，Java代码看不到握手细节。
3. TCP为什么会粘包？
> TCP是字节流协议，没有数据包边界，缓冲区会合并数据。
*/
public class Demo6 {
    public static void main(String[] args) {

    }
}

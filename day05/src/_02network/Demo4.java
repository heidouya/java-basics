package _02network;
/*
# Java UDP 网络编程
## UDP核心特点
UDP：**用户数据报协议**
- ✅ **无连接**：不需要提前建立连接，直接发包，没有三次握手
- ✅ **不可靠**：只管发送，不确认对方是否收到，丢包不会重传
- ✅ 基于**数据报包**（DatagramPacket），一次发一个包，有数据边界
- ✅ 开销小、速度快；适合直播、视频通话、DNS查询、消息广播

Java UDP核心两个类（都在`java.net`包）
1. `DatagramSocket`：**数据报套接字**，用来收发数据包，相当于码头
2. `DatagramPacket`：**数据报包**，封装要发送的数据、目标IP、端口，相当于集装箱

> UDP不分严格意义的客户端/服务端，两端都是DatagramSocket；
> 只是约定：**接收方绑定固定端口（服务端），发送方随机端口（客户端）**

---
# 1. UDP接收端（服务端，绑定端口，等待收包） UdpServer.java
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer {
    public static void main(String[] args) throws Exception {
        // 在9999端口开启DatagramSocket，等待接收数据
        DatagramSocket socket = new DatagramSocket(9999);
        System.out.println("UDP接收端启动，端口：9999，等待消息...");

        // 准备字节数组，存放接收的数据
        byte[] buffer = new byte[1024];
        // 创建数据包，用来存放收到的数据
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        // receive() 阻塞方法：没有收到包就卡住
        socket.receive(packet);

        // 解析数据包
        // packet.getData() 获取字节数组；packet.getLength() 实际收到字节长度
        String msg = new String(packet.getData(),0,packet.getLength());
        System.out.println("收到消息：" + msg);
        // 获取发送方IP和端口
        System.out.println("发送方IP：" + packet.getAddress());
        System.out.println("发送方端口：" + packet.getPort());

        socket.close();
    }
}
```

# 2. UDP发送端（客户端，发送数据包） UdpClient.java
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
    public static void main(String[] args) throws Exception {
        // 发送端DatagramSocket，不传端口，系统自动分配随机端口
        DatagramSocket socket = new DatagramSocket();

        String content = "Hello UDP！我是客户端消息";
        byte[] data = content.getBytes();

        // 目标：接收端IP + 端口
        InetAddress targetIp = InetAddress.getByName("127.0.0.1");
        int targetPort = 9999;

        // 封装数据包：数据，数据长度，目标IP，目标端口
        DatagramPacket packet = new DatagramPacket(data, data.length, targetIp, targetPort);

        // 发送数据包
        socket.send(packet);
        System.out.println("UDP消息发送完成！");

        socket.close();
    }
}
```

## 运行顺序
1. 先启动 **UdpServer** 阻塞等待数据包
```
UDP接收端启动，端口：9999，等待消息...
```
2. 再启动 **UdpClient**
服务端输出：
```
收到消息：Hello UDP！我是客户端消息
发送方IP：/127.0.0.1
发送方端口：54321（随机端口，每次不一样）
```

## 双向通信升级版：收到消息后，服务端回消息给客户端
> UDP可以收发互相回复，不需要长连接
### UdpServer2（收消息并回复）
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9999);
        System.out.println("UDP双向服务端启动 9999");
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        socket.receive(packet);
        String receiveMsg = new String(packet.getData(),0,packet.getLength());
        System.out.println("收到客户端：" + receiveMsg);

        // 回复消息，使用packet里自带的发送方IP和端口
        String resp = "我是服务端，已收到你的UDP消息";
        byte[] respData = resp.getBytes();
        DatagramPacket respPacket = new DatagramPacket(
                respData, respData.length,
                packet.getAddress(), packet.getPort()
        );
        socket.send(respPacket);
        System.out.println("回复消息发送成功");

        socket.close();
    }
}
```

### UdpClient2（发消息，等待接收服务端回复）
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        int port = 9999;

        // 发送
        String msg = "服务端你好";
        byte[] data = msg.getBytes();
        DatagramPacket sendPkt = new DatagramPacket(data, data.length, ip, port);
        socket.send(sendPkt);

        // 准备接收服务端回复
        byte[] buf = new byte[1024];
        DatagramPacket recvPkt = new DatagramPacket(buf, buf.length);
        socket.receive(recvPkt);

        String respMsg = new String(recvPkt.getData(),0,recvPkt.getLength());
        System.out.println("收到服务端回复：" + respMsg);

        socket.close();
    }
}
```

## UDP重点知识点
1. `DatagramSocket`
   - 接收端必须指定固定端口，别人才能找到你
   - 发送端可以不写端口，操作系统自动分配随机端口
2. `DatagramPacket`
   - 发送包：构造器传入【数据、长度、目标IP、端口】
   - 接收包：只需要传入字节数组，receive填充数据+发送方地址信息
3. `receive()` 阻塞方法，没有数据包就卡住
4. **UDP有包边界！**
   一次send对应一次receive，不会像TCP字节流粘包；包太大会丢包（UDP最大报文一般不超64KB）

## UDP vs TCP（再巩固，面试必考）
| 特性 | TCP | UDP |
| ---- | ---- | ---- |
| 连接 | 面向连接，三次握手 | 无连接，直接发包 |
| 可靠性 | 可靠，确认、重传、拥塞控制 | 不可靠，无确认，可能丢包 |
| 数据单元 | 字节流，无边界，存在粘包 | 数据报，有边界，一包一收 |
| Java类 | ServerSocket、Socket | DatagramSocket、DatagramPacket |
| 适用场景 | 文件传输、网页、聊天 | DNS、直播、音视频、广播 |

## 面试常见问题
Q：UDP既然不可靠，为什么还要用？
> A：可靠性需要额外开销。音视频场景允许少量丢包，优先保证实时性；TCP重传等待会造成明显延迟卡顿。

Q：UDP可以广播吗？
> A：可以，目标IP填广播地址（如`255.255.255.255`），同一局域网所有主机都能收到这个包。
*/
public class Demo4 {
}

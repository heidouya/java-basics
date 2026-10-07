package _02network;
/*
# UDP 的3种通信方式
UDP 基于**数据报**，无连接，支持：**单播、广播、组播(多播)**
> 单播：一对一；广播：局域网内所有人接收；组播：加入同一个组的成员接收

## 1. 单播（最常用，前面写的就是单播）
一对一通信，发给指定IP。
特点：目标IP是单独一台主机。

### 简洁代码：UDP单播（服务端+客户端）
UDP服务端（接收方，绑定端口 8888）
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpUnicastServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(8888);
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        System.out.println("UDP单播服务端启动，等待消息...");

        socket.receive(packet); // 阻塞收包
        String msg = new String(packet.getData(),0,packet.getLength());
        System.out.println("收到：" + msg);
        socket.close();
    }
}
```

UDP客户端（发送方）
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpUnicastClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        String content = "UDP单播消息";
        byte[] data = content.getBytes();
        InetAddress target = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(data, data.length, target, 8888);
        socket.send(packet);
        System.out.println("发送成功");
        socket.close();
    }
}
```
运行顺序：先启动服务端，再启动客户端。

---
## 2. UDP广播
发给**同一个局域网内所有主机**。
广播地址：`255.255.255.255`（有限广播）
> 注意：路由器一般不转发广播包，广播只能在当前局域网。

### UDP广播发送端
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpBroadcastSender {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        String msg = "UDP广播消息，局域网所有人收到";
        byte[] data = msg.getBytes();
        // 广播地址
        InetAddress broadcastIp = InetAddress.getByName("255.255.255.255");
        DatagramPacket packet = new DatagramPacket(data, data.length, broadcastIp, 8888);
        socket.send(packet);
        System.out.println("广播发送完成");
        socket.close();
    }
}
```
接收端代码和上面**单播服务端完全一样**。
> 小坑：Windows防火墙可能拦截广播包。

---
## 3. UDP组播（多播 Multicast）
发给**加入同一个组播组的主机**。
组播IP范围：`224.0.0.0 ~ 239.255.255.255`
Java 使用 `MulticastSocket`（继承自DatagramSocket）
> 组播常用于直播、视频流。

### UDP组播接收端（加入组 224.0.0.1，端口8888）
```java
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UdpMulticastReceiver {
    public static void main(String[] args) throws Exception {
        MulticastSocket socket = new MulticastSocket(8888);
        InetAddress group = InetAddress.getByName("224.0.0.1");
        socket.joinGroup(group); // 加入组播组
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        System.out.println("已加入组播，等待消息...");
        socket.receive(packet);
        String msg = new String(packet.getData(),0,packet.getLength());
        System.out.println("收到组播消息：" + msg);
        socket.leaveGroup(group); // 离开组
        socket.close();
    }
}
```

### UDP组播发送端
```java
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.InetAddress;

public class UdpMulticastSender {
    public static void main(String[] args) throws Exception {
        MulticastSocket socket = new MulticastSocket();
        String msg = "这是组播消息";
        byte[] data = msg.getBytes();
        InetAddress groupIp = InetAddress.getByName("224.0.0.1");
        DatagramPacket packet = new DatagramPacket(data, data.length, groupIp, 8888);
        socket.send(packet);
        System.out.println("组播消息发送成功");
        socket.close();
    }
}
```
> 运行：先开接收端加入组，再运行发送端。

## 简单对比总结
1. **单播**：一对一，目标是单个IP；最常用
2. **广播**：一对全部局域网主机；地址255.255.255.255；路由器不转发
3. **组播**：一对一组；IP段224~239；需要加入组；适合流媒体

面试一句话记忆：
**单播一对一，广播一网全收到，组播只发给入组的人。**

## 面试小问题
Q：UDP广播和组播区别？
- 广播：局域网所有机器都收到，不管程序要不要；会造成网络风暴。
- 组播：只有加入组播组的程序才接收，更加节省带宽。
*/
public class Demo5 {
}

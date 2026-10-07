package _02network;

import java.io.IOException;
import java.net.*;
/*
# 在写 UDP 广播代码的时候，启动了两个 UDP 单播的接收代码，第二个启动的时候报：Address already in use

```
java.net.BindException: Address already in use: Cannot bind
```
> 原因：**DatagramSocket绑定端口的时候，同一个主机上，同一个端口只能被一个程序绑定**。
你两个接收程序都写了 `new DatagramSocket(8888)`，两个程序抢同一个端口，第二个必然报错。

UDP广播原理：发送方发包到**目标端口8888**，局域网内所有监听**8888端口**的进程都能收到广播包。
但是默认情况下：操作系统不允许**多个进程同时绑定同一个端口**。

## 解决方案：开启端口复用！
Java里 `DatagramSocket` 提供 `setReuseAddress(true)`，允许**多个Socket绑定同一个端口**。
> ⚠️ 注意：**必须在new DatagramSocket之前设置这个参数，或者在绑定端口之前！**
> 一旦Socket已经绑定端口，再调用setReuseAddress就无效。

---

# 修改后的广播接收端代码（支持多开，多个程序监听同一个端口）
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpBroadcastReceiver {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(null); // 先不绑定端口
        socket.setReuseAddress(true); // 开启端口复用，允许多个程序绑定8888
        socket.bind(new java.net.InetSocketAddress(8888)); // 现在再绑定端口

        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        System.out.println("广播接收端启动，端口8888，等待广播消息...");

        socket.receive(packet);
        String msg = new String(packet.getData(),0,packet.getLength());
        System.out.println("收到广播消息：" + msg);
        socket.close();
    }
}
```

广播发送端代码不变：
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpBroadcastSender {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        String msg = "【广播】所有监听8888端口的程序都能收到这条消息";
        byte[] data = msg.getBytes();
        InetAddress broadcastIp = InetAddress.getByName("255.255.255.255");
        DatagramPacket packet = new DatagramPacket(data, data.length, broadcastIp, 8888);
        socket.send(packet);
        System.out.println("广播包发送完成");
        socket.close();
    }
}
```

# 测试步骤
1. 启动第一个 `UdpBroadcastReceiver` ✅
2. **再启动第二个 `UdpBroadcastReceiver`**，不会报端口占用！✅
3. 运行 `UdpBroadcastSender`
4. 你会发现：**两个接收程序，同时收到同一条广播消息**

> Windows / Linux / Mac 都支持 `setReuseAddress(true)`，但是操作系统底层机制略有差异。

# 重要坑点总结
1. ❌ 错误写法：
```java
DatagramSocket socket = new DatagramSocket(8888); // 直接绑定端口
socket.setReuseAddress(true); // 太晚了！已经绑定，复用失效
```

2. ✅ 正确顺序：
```java
DatagramSocket socket = new DatagramSocket(null); // 无绑定
socket.setReuseAddress(true); // 开启复用
socket.bind(端口); // 再绑定
```

3. 防火墙问题：
Windows防火墙，有可能拦截`255.255.255.255`广播包，本地测试如果收不到，可以临时关闭防火墙测试。

4. 区分：
- **单播**：数据包发给单个IP，只有对应IP+端口进程收到
- **广播**：数据包发给局域网所有主机，**本机多个监听同端口的进程，全部收到**（前提开启端口复用）

# 面试考点
Q：UDP广播，本机多个进程监听同一个端口，怎么实现？
A：通过 `setReuseAddress(true)` 设置地址复用，**必须在bind绑定端口之前调用**，允许多个DatagramSocket绑定同一个端口，广播包会分发给所有监听该端口的Socket。
*/
public class UdpBroadcastReceiver1 {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(null); // 先不绑定端口
        socket.setReuseAddress(true); // 开启端口复用，允许多个程序绑定8888
        socket.bind(new java.net.InetSocketAddress(8888)); // 现在再绑定端口

        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        System.out.println("广播接收端启动，端口8888，等待广播消息...");

        socket.receive(packet);
        String msg = new String(packet.getData(),0,packet.getLength());
        System.out.println(STR."收到广播消息：\{msg}");
        socket.close();
    }
}

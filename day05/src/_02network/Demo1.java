package _02network;
/*
# 网络编程
## 一、什么是网络编程
**网络编程，就是让两台不同主机上的程序，通过网络互相收发数据。**
在Java里，就是利用`java.net`包下的API（Socket、InetAddress等），实现跨主机进程通信。

> 本质：**进程和进程之间的数据交互**。
> 同一台电脑：不同进程通信可以用管道、共享内存；
> 跨电脑：就要走网络，这就是网络编程。

Java网络编程，是**应用层调用操作系统提供的Socket接口**，底层依赖TCP/IP协议栈。

## 二、网络编程三要素（面试必背）
1. **IP地址（InetAddress）**
    - 作用：定位网络上**哪一台主机**
    - IPv4：4字节，例如`192.168.1.100`；IPv6 16字节
    - 特殊IP：
      - `127.0.0.1`：本机回环地址，只能自己访问自己，不会走网卡
      - `localhost`：域名，解析就是127.0.0.1
    - Java中用`InetAddress`类来表示IP。

2. **端口号**
    - 作用：一台主机上运行很多程序，**端口定位主机上的哪个进程**
    - 范围：`0 ~ 65535`（2个字节）
    - 约定：
      - 0~1023：**知名端口（保留端口）**，系统服务占用，如80(HTTP)、443(HTTPS)、21(FTP)，自己程序尽量不要用
      - 1024~65535：动态端口，我们写代码用这一段
    - 注意：**一个端口同一时间只能被一个进程占用**，端口冲突会报错。

3. **传输协议**
    - 作用：规定数据传输的规则（怎么发、怎么收、出错怎么办）
    - 两大核心协议：**TCP、UDP**
    - TCP：面向连接、可靠、三次握手四次挥手，有确认重传，适合文件、网页、聊天（Socket）
    - UDP：无连接、不可靠、开销小，只管发不管对方收没收到，适合直播、视频通话、DNS查询（DatagramSocket）

> 一句话记三要素：**IP找主机，端口找程序，协议定规则。**

## 三、简单理解通信流程
客户端程序 → 【IP:端口】 → 网络 → 服务器程序
比如：访问百度 `www.baidu.com:80`
- IP：百度服务器IP
- 端口：80
- 协议：TCP

## 四、极简Java演示代码（UDP，直观体现三要素）
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpDemo {
    public static void main(String[] args) throws Exception {
        // 1. 创建UDP Socket
        DatagramSocket socket = new DatagramSocket();
        String msg = "你好，网络编程";
        byte[] data = msg.getBytes();

        // 三要素：IP + 端口 + 协议(UDP)
        InetAddress ip = InetAddress.getByName("127.0.0.1"); // IP
        int port = 9999; // 端口

        // 封装数据包
        DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
        socket.send(packet);
        System.out.println("消息发送完成");
        socket.close();
    }
}
```

## 面试常问补充
Q：IP和端口区别？
A：IP定位**机器**；端口定位**机器上的应用进程**，两者组合叫 **Socket地址（IP:Port）**。

Q：localhost 和 127.0.0.1 区别？
A：localhost是域名，解析得到127.0.0.1，访问本机，不经过物理网卡。
*/
public class Demo1 {
}

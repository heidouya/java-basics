package _02network;

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
        System.out.println(STR."收到消息：\{msg}");
        // 获取发送方IP和端口
        System.out.println(STR."发送方IP：\{packet.getAddress()}");
        System.out.println(STR."发送方端口：\{packet.getPort()}");

        socket.close();
    }
}

package _02network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpBroadcastReceiver2 {
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

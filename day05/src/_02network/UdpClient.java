package _02network;

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

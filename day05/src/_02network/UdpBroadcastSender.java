package _02network;

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

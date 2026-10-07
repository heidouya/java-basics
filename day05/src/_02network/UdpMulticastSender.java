package _02network;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

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

package _02network;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UdpMulticastReceiver2 {
    public static void main(String[] args) throws Exception {
        MulticastSocket socket = new MulticastSocket(8888);

        InetAddress group = InetAddress.getByName("224.0.0.2");
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

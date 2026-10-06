package _02network;

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
        System.out.println(STR."收到服务端回复：\{respMsg}");

        socket.close();
    }
}

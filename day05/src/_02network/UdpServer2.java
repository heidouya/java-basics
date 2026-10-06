package _02network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9999);
        System.out.println("UDP双向服务端启动 9999");
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        socket.receive(packet);
        String receiveMsg = new String(packet.getData(),0,packet.getLength());
        System.out.println(STR."收到客户端：\{receiveMsg}");

        // 回复消息，使用packet里自带的发送方IP和端口
        String resp = "我是服务端，已收到你的UDP消息";
        byte[] respData = resp.getBytes();
        DatagramPacket respPacket = new DatagramPacket(
                respData, respData.length,
                packet.getAddress(), packet.getPort()
        );
        socket.send(respPacket);
        System.out.println("回复消息发送成功");

        socket.close();
    }
}

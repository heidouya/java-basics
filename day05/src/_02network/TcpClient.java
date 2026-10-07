package _02network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TcpClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            // 1. 创建Socket，指定服务端IP和端口，发起TCP连接（底层完成三次握手）
            socket = new Socket("127.0.0.1", 9000);
            System.out.println("客户端成功连接服务端");

            // 2. 获取输出流，发送消息给服务端
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            String msg = "Hello TCP！";
            osw.write(msg);
            osw.write("\n"); // 换行符，服务端readLine靠换行判断一行结束
            osw.flush();

            // 3. 获取输入流，读取服务端返回的响应
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String resp = br.readLine();
            System.out.println(STR."收到服务端回复：\{resp}");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            // 4. 关闭socket，触发四次挥手断开连接
            try {
                if(socket != null) socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

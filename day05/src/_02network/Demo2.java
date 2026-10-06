package _02network;

import java.net.InetAddress;
import java.net.UnknownHostException;
/*
# Java InetAddress 介绍
`java.net.InetAddress` 用于**表示IP地址**（IPv4 / IPv6），可以获取主机名、IP，完成域名解析。
> 注意：**InetAddress 没有公开构造方法**，不能 `new InetAddress()`，只能通过静态工厂方法获取实例。

## 常用静态方法
1. `InetAddress.getByName(String host)`：根据主机名/域名获取IP对象（域名解析）
2. `InetAddress.getLocalHost()`：获取本机地址
3. `InetAddress.getAllByName(String host)`：域名可能对应多个IP，返回全部地址数组
4. `InetAddress.getByAddress(byte[] addr)`：直接用字节数组构造IP对象

## 常用实例方法
- `getHostName()`：获取主机名
- `getHostAddress()`：获取IP字符串
- `isReachable(int timeout)`：测试是否能连通（底层ping，受防火墙限制不一定准）

> ⚠️ 异常：会抛出 `UnknownHostException` 未知主机异常，必须捕获。

## 完整可运行代码
```java
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressDemo {
    public static void main(String[] args) {
        try {
            // 1. 获取本机 InetAddress
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("=====本机信息=====");
            System.out.println("本机主机名：" + localHost.getHostName());
            System.out.println("本机IP地址：" + localHost.getHostAddress());
            System.out.println();

            // 2. 根据域名获取IP
            InetAddress baidu = InetAddress.getByName("www.baidu.com");
            System.out.println("=====百度域名解析=====");
            System.out.println("主机名：" + baidu.getHostName());
            System.out.println("IP地址：" + baidu.getHostAddress());
            System.out.println();

            // 3. 获取域名对应的全部IP（一个域名可绑定多个服务器IP）
            InetAddress[] allIps = InetAddress.getAllByName("www.baidu.com");
            System.out.println("=====百度所有IP列表=====");
            for (InetAddress ip : allIps) {
                System.out.println(ip.getHostAddress());
            }
            System.out.println();

            // 4. 测试连通性（timeout 单位毫秒，类似ping，防火墙可能拦截返回false）
            boolean reachable = baidu.isReachable(3000);
            System.out.println("是否可达(3秒超时)：" + reachable);

        } catch (UnknownHostException e) {
            // 域名解析失败时抛出
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## 运行输出示例（你的机器结果会不一样）
```
=====本机信息=====
本机主机名：DESKTOP-XXXX
本机IP地址：192.168.1.100

=====百度域名解析=====
主机名：www.baidu.com
IP地址：180.101.49.11

=====百度所有IP列表=====
180.101.49.11
180.101.49.10

是否可达(3秒超时)：true
```

## 重要注意点
1. `isReachable()` **不是真正ICMP ping**。Windows下会尝试TCP连接7号端口，很多环境会被防火墙拦截，结果不准；生产环境建议自己写Socket连接测试。
2. `getLocalHost()` 在多网卡机器上，不一定返回外网IP，可能返回127.0.0.1。
3. 域名解析会走本机DNS缓存，修改hosts文件会影响解析结果。
4. InetAddress是**不可变对象**，线程安全。
*/
public class Demo2 {
    public static void main(String[] args) throws UnknownHostException {
        try {
            // 1. 获取本机 InetAddress
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("=====本机信息=====");
            System.out.println(STR."本机主机名：\{localHost.getHostName()}");
            System.out.println(STR."本机IP地址：\{localHost.getHostAddress()}");
            System.out.println();

            // 2. 根据域名获取IP
            InetAddress baidu = InetAddress.getByName("www.baidu.com");
            System.out.println("=====百度域名解析=====");
            System.out.println(STR."主机名：\{baidu.getHostName()}");
            System.out.println(STR."IP地址：\{baidu.getHostAddress()}");
            System.out.println();

            // 3. 获取域名对应的全部IP（一个域名可绑定多个服务器IP）
            InetAddress[] allIps = InetAddress.getAllByName("www.baidu.com");
            System.out.println("=====百度所有IP列表=====");
            for (InetAddress ip : allIps) {
                System.out.println(ip.getHostAddress());
            }
            System.out.println();

            // 4. 测试连通性（timeout 单位毫秒，类似ping，防火墙可能拦截返回false）
            boolean reachable = baidu.isReachable(3000);
            System.out.println(STR."是否可达(3秒超时)：\{reachable}");

        } catch (UnknownHostException e) {
            // 域名解析失败
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

package _04polymorphism;

// 2. 子类1：鸿蒙手机（重写开机方法）
public class HarmonyPhone extends HarmonyDevice {
    @Override
    public void powerOn() {
        System.out.println("手机开机，自动连接蓝牙");
    }

    public void takePhoto() {
        System.out.println("调用了手机拍照功能");
    }
}

package _04polymorphism;

// 3. 子类2：鸿蒙平板（重写开机方法）
public class HarmonyPad extends HarmonyDevice {
    @Override
    public void powerOn() {
        System.out.println("平板开机，自动横屏");
    }

    public void setScreenSize() {
        System.out.println("调用平板专属的设置屏幕方法");
    }
}
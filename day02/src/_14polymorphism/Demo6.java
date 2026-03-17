package _14polymorphism;

/*
    interface 中的多态
*/
public class Demo6 {
    public static void main(String[] args) {
        DrawingApp app = new DrawingApp();
        app.drawShape(new Circle());     // 输出：画圆形
        app.drawShape(new Rectangle());  // 输出：画矩形
    }
}
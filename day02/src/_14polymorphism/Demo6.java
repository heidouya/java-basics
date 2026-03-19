package _14polymorphism;

/*静态方法示例2 - 工厂方法*/
public class Demo6 {
    public static void main(String[] args) {
        Animal dog = AnimalFactory.createAnimal("dog");
        Animal cat = AnimalFactory.createAnimal("cat");
    }
}

class AnimalFactory {
    // 静态工厂方法
    public static Animal createAnimal(String type) {
        switch (type.toLowerCase()) {
            case "dog":
                return new Dog2();
            case "cat":
                return new Cat2();
            case "bird":
                return new Bird2();
            default:
                throw new IllegalArgumentException("未知动物类型: " + type);
        }
    }
}

class Animal2 {}
class Dog2 extends Animal {}
class Cat2 extends Animal{}
class Bird2 extends Animal{}

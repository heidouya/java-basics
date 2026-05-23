package _05static;

/*静态方法示例2 - 工厂方法*/
public class Demo4 {
    public static void main(String[] args) {
        Animal dog = AnimalFactory.createAnimal("dog");
        Animal cat = AnimalFactory.createAnimal("cat");
    }
}

class AnimalFactory {
    // 静态工厂方法
    public static Animal createAnimal(String type) {
        return switch (type.toLowerCase()) {
            case "dog" -> new Dog();
            case "cat" -> new Cat();
            case "bird" -> new Bird();
            default -> throw new IllegalArgumentException("未知动物类型: " + type);
        };
    }
}

class Animal {}
class Dog extends Animal {}
class Cat extends Animal{}
class Bird extends Animal{}

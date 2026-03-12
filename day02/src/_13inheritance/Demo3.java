package _13inheritance;

/*
    @Override：@Override是Java中的一个注解（Annotation），用于标识一个方法声明打算重写父类或接口中的方法。它是一个编译时注解，不会影响代码运行。

    子类可以通过 @Override 重写父类方法，重写规则：
    1. 方法名、参数列表必须相同
    2. 返回值类型必须相同或是子类
    3. 访问修饰符不能比父类更严格
    4. 不能抛出比父类更多的异常

    @Override的主要作用：
    1. 编译检查：确保方法正确重写
    2. 代码自文档：明确表明方法是重写的
    3. 防止错误：避免方法签名写错
    4. 提高可维护性：当父类方法变更时及时发现

    使用建议：
    1. 总是使用：只要是重写的方法，都应该加上
    2. 不要滥用：只在真正重写时使用
    3. 注意签名：确保方法签名完全匹配
    4. 区分重载：重载方法不要用@Override
*/
public class Demo3 {
    public static void main(String[] args) {
        Cat cat = new Cat("招财", 3);
        cat.makeSound();
    }
}

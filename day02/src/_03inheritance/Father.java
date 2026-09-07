package _03inheritance;
// 继承必须遵循 is-a 原则
public class Father {
    void play(){}
}

// Father 和 Son 不能继承，因为违背 is-a 原则
// 如果Son 中想使用 Father 中的内容，可以使用组合，组合必须遵循 has-a 原则
class Son {
    Father father;

    void test() {
        father.play();
    }
}
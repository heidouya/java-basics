package _01generics;

public class Demo3 {
    public static void main(String[] args) {
        Repository<User> userRepository = new UserRepository();
        userRepository.save(new User());
        User user = userRepository.findById(1);
        System.out.println(user);
    }
}

interface Repository<T> {
    T findById(int id);
    void save(T entity);
}

class User {}

// 方式1：实现时指定具体类型
class UserRepository implements Repository<User> {
    public User findById(int id) { return null; }
    public void save(User entity) {  }
}

// 方式2：实现时保留泛型参数
class GenericRepository<T> implements Repository<T> {
    public T findById(int id) { return null; }
    public void save(T entity) {  }
}

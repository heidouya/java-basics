package _03proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
# Java 动态代理
## 一、概念
**动态代理**：程序运行时，动态生成代理类对象，不用提前写代理类源码。
作用：**不修改原有目标类代码，在方法执行前后增加额外逻辑**（增强）。

Java有两种：
1. **JDK动态代理**：JDK原生，**只能代理接口**，基于反射。
2. **CGLIB动态代理**：第三方库，**代理普通类（不需要接口）**，通过继承目标类实现。

> 静态代理：编码阶段就写好代理类；动态代理：运行时自动生成。

## 二、常用场景
1. Spring AOP（事务、日志、权限、接口耗时统计）
2. MyBatis Mapper：Mapper只有接口，没有实现类，底层JDK代理
3. Dubbo / Feign RPC：远程调用接口包装
4. 统一埋点、异常捕获、参数校验

## 三、JDK动态代理 可运行极简代码
```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 1. 定义接口（JDK代理必须要有接口）
interface UserService {
    void addUser();
}

// 2. 目标实现类
class UserServiceImpl implements UserService {
    @Override
    public void addUser() {
        System.out.println("执行：新增用户");
    }
}

// 3. 调用处理器，写增强逻辑
class MyInvocationHandler implements InvocationHandler {
    private final Object target; // 被代理的目标对象

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 方法执行前增强
        System.out.println("【前置】记录日志");
        // 执行原目标方法
        Object result = method.invoke(target, args);
        // 方法执行后增强
        System.out.println("【后置】统计耗时");
        return result;
    }

    // 生成代理对象
    public Object getProxy() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }
}

// 测试
public class JdkProxyDemo {
    public static void main(String[] args) {
        UserService target = new UserServiceImpl();
        MyInvocationHandler handler = new MyInvocationHandler(target);
        UserService proxy = (UserService) handler.getProxy();
        proxy.addUser();
    }
}
```
运行输出：
```
【前置】记录日志
执行：新增用户
【后置】统计耗时
```

### 核心关键点
- `Proxy.newProxyInstance`：运行时动态创建代理类
- `invoke`：代理对象调用任何方法，都会进入这个方法

## 四、CGLIB 简短示例（代理普通类，无需接口）
> 注意：JDK8之后需要引入cglib依赖；Spring内置CGLIB

```java
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

// 普通类，不需要接口
class OrderService {
    public void createOrder() {
        System.out.println("创建订单");
    }
}

public class CglibDemo {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(OrderService.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            System.out.println("CGLIB前置增强");
            Object res = proxy.invokeSuper(obj, args);
            System.out.println("CGLIB后置增强");
            return res;
        });
        OrderService proxy = (OrderService) enhancer.create();
        proxy.createOrder();
    }
}
```

## 五、两者对比总结
| 类型 | 条件 | 原理 |
| ---- | ---- | ---- |
| JDK动态代理 | 目标**必须实现接口** | 实现目标接口 |
| CGLIB | 目标**不能是final类** | 继承目标类 |

## 六、一句话
动态代理是运行期生成代理对象，在不改动原有代码的前提下对方法增强；业务很少手写，但Spring AOP、MyBatis、RPC框架底层大量使用。
*/
public class Demo1 {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        UserService proxy = ProxyUtil.createProxy(userService);
        boolean res = proxy.addUser();
        System.out.println(STR."用户是否添加成功：\{res}");
    }
}

class ProxyUtil {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("【前置】记录日志");
                        long start = System.nanoTime();
                        Object result = method.invoke(target, args);
                        long time = System.nanoTime() - start;
                        System.out.println(STR."【后置】统计耗时：\{time} ns");
                        return result;
                    }
                }
        );
    }
}

interface UserService {
    boolean addUser();
}

class UserServiceImpl implements UserService {
    @Override
    public boolean addUser() {
        System.out.println("执行：新增用户");
        return true;
    }
}
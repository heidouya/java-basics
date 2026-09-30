package _01thread;
/*
# 为啥生产环境不推荐使用Executors.newFixedThreadPool()、Executors.newSingleThreadExecutor()、Executors.newCachedThreadPool()创建线程池呢

`Executors` 几个快捷创建线程池的方法**底层使用无界阻塞队列**，任务堆积时队列无限扩容，最终把内存占满，触发 **OOM（内存溢出）**；部分还会允许创建无限多线程，打爆CPU。阿里开发手册明确禁止生产直接用它们，推荐手动 `new ThreadPoolExecutor()`。

下面逐个拆解，结合底层源码、风险点、生活例子。

## 1. Executors.newFixedThreadPool(n)

```
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
```

参数：

- corePoolSize = maximumPoolSize = nThreads
- 队列：`LinkedBlockingQueue` **无界队列，默认容量 Integer.MAX_VALUE（21亿左右）**

### 风险

固定数量线程。当任务处理速度 < 任务提交速度：
新任务不会触发拒绝策略，全部丢进无界队列。队列会一直往里面塞任务，对象越来越多，堆内存持续上涨 → **OOM**。

> 售票厅例子：窗口固定3个，排队队伍没有长度上限。顾客源源不断来，队伍无限变长，最终内存撑爆。
> 拒绝策略永远不会触发，因为队列永远装得下。

## 2. Executors.newSingleThreadExecutor()

```
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}
```

底层和 fixedThreadPool 一样，**无界队列 LinkedBlockingQueue**，只是线程固定为1。

### 风险

同样无限堆积任务。任务提交速度大于单线程处理速度，队列持续膨胀，OOM。

> 售票厅：只有1个售票员，队伍无限长。

## 3. Executors.newCachedThreadPool()

```
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
```

队列：`SynchronousQueue`，不存储任务，来了任务直接交给线程；
`maximumPoolSize = Integer.MAX_VALUE`。

### 风险

任务提交速度远大于任务处理速度时，**会无限新建线程**。
每一个来不及处理的任务都会创建新线程，线程数量持续暴涨。
线程本身占用栈内存，大量线程会：

1. 占用大量内存
2. CPU上下文切换爆炸，系统卡死，OOM

> 售票厅例子：没有固定窗口，顾客一来就临时招聘售票员，最多可以招21亿个。短时间大量顾客涌入，疯狂招人，整个大厅资源耗尽。

# 汇总对比表

| 线程池 | 队列 | 最大线程数 | 主要风险 |
| --- | --- | --- | --- |
| newFixedThreadPool | LinkedBlockingQueue（无界） | 固定 | 任务堆积，队列无限增大 → OOM |
| newSingleThreadExecutor | LinkedBlockingQueue（无界） | 1 | 任务堆积，队列无限增大 → OOM |
| newCachedThreadPool | SynchronousQueue | Integer.MAX_VALUE | 大量任务涌入，无限创建线程 → OOM、CPU打满 |

# 为什么Executors要提供这几个方法？

**方便学习、本地测试、小工具脚本快速写代码。**
它是简化API，牺牲生产安全性换开发便捷性。
生产环境流量不可控，峰值流量一来就踩坑。

# ✅ 生产正确写法：手动 new ThreadPoolExecutor

手动指定**有界队列**，限制队列长度，配置拒绝策略。流量打满时触发拒绝策略，保护系统，而不是OOM。

```
ThreadPoolExecutor pool = new ThreadPoolExecutor(
        5,
        10,
        10L,
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(100), // 有界队列，最多排队100个任务
        Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.CallerRunsPolicy()
);
```

> 队列设置上限，任务超过阈值触发拒绝策略，保护服务雪崩。

# 补充2个面试常问点

1. **无界队列为什么不会触发拒绝策略？**
线程数达到核心线程之后，任务优先入队。无界队列永远可以put任务，永远达不到`maximumPoolSize`，拒绝策略永远不执行。
2. 那是不是`ScheduledThreadPoolExecutor`（newScheduledThreadPool）也不推荐？
是的，它底层队列也是无界的，大量定时任务堆积也会OOM，同样不建议生产直接使用Executors快捷方法。

# 一句话记忆（面试背诵）

> newFixedThreadPool、newSingleThreadExecutor 使用无界队列，任务堆积会OOM；newCachedThreadPool最大线程数无上限，高并发下无限创建线程，耗尽资源。生产禁止使用Executors快捷创建线程池，手动创建ThreadPoolExecutor并指定有界队列。
*/
public class Demo18 {
}

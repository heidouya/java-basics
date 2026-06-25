package _06block;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
/*
当你需要在类加载时执行一段初始化逻辑（且只执行一次）时，就用静态代码块——比如加载配置、注册驱动、填充缓存、加载本地库。
*/
public class Demo2 {
    public static void main(String[] args) {
        System.out.println(Demo2.getDbUrl());
    }

    // 静态变量
    private static final Properties configProps;

    // 静态代码块：加载配置文件
    static {
        configProps = new Properties();
        try {
            // 需要处理异常、读取文件等多步操作
            InputStream is = DatabaseConfig.class.getResourceAsStream("./db.properties");
            configProps.load(is);
        } catch (IOException e) {
            throw new RuntimeException("加载数据库配置失败", e);
        }
    }

    public static String getDbUrl() {
        return configProps.getProperty("db.url");
    }
}

class DatabaseConfig {}

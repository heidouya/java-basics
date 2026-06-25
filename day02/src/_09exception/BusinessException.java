package _09exception;

// 自定义运行时异常
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
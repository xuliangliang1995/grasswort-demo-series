package hash.consistenthash.exception;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException() {
        super("当前服务不可用");
    }
}

package aop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xuliangliang
 * @Description 幂等性
 * @Date 2020/10/23
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {}

package aop;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/10/23
 */
@Service
public class DomainService {

    /**
     * do something
     * @return
     */
    public Object doSomeThing() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Object();
    }

    /**
     * do something quickly
     */
    @Idempotent
    public void doSomeThingQuickly() {
        throw new aop.PessimisticLockingFailureException();
    }
}

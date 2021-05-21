package circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/17
 */
@Component
public class CircularDependency002 {
    @Autowired
    private CircularDependency001 circularDependency001;

    public void doSomething() {
        System.out.println(circularDependency001 != null);
    }
}

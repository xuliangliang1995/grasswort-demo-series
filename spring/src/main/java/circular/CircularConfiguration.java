package circular;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/17
 */
@EnableAspectJAutoProxy
@Configuration
@ComponentScan("circular")
public class CircularConfiguration {
}

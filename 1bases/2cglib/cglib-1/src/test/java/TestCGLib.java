import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author l
 * @Date 2022/10/24 11:12
 */
public class TestCGLib {
    public static void main(String[] args) {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(Hello.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("abc");
                return methodProxy.invokeSuper(o,objects);
            }
        });

        Hello o = (Hello) enhancer.create();
        o.sayHello();

//        Hello hello = new Hello();
//        hello.sayHello();

    }
}

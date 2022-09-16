package com.cl.learn.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.Factory;

import java.lang.reflect.Proxy;

/**
 * @Author l
 * @Date 2022/9/12 14:56
 *
    原理：
        在运行配置中 VM option 选项中，填写如下配置
            -Dcglib.debugLocation=/Users/l/develop/clProject/0-java/0-intellij/Learn0/1bases/2cglib/cglib-1/out/
        将cglib代理对象生成的类的class，保存下来
            其中UserServiceImpl$$EnhancerByCGLIB$$e84bec64 即为 UserServiceImpl 的代理类
        当得到代理对象 userService_proxy 并执行test()方法是，执行的就是 UserServiceImpl$$EnhancerByCGLIB$$e84bec64 中的test方法
        public final void test() {
            MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;  // 获取CGLib自定义方法拦截器
            if (var10000 == null) {
                CGLIB$BIND_CALLBACKS(this);
                var10000 = this.CGLIB$CALLBACK_0;
            }
            if (var10000 != null) {
                var10000.intercept(this, CGLIB$test$1$Method, CGLIB$emptyArgs, CGLIB$test$1$Proxy); // 执行拦截器中的方法
            } else {
                super.test();
            }
        }
            如何获取自定义拦截器CGLIB$CALLBACK_0(MethodInterceptor) var10000
            private static final void CGLIB$BIND_CALLBACKS(Object var0) {
                UserServiceImpl$$EnhancerByCGLIB$$e84bec64 var1 = (UserServiceImpl$$EnhancerByCGLIB$$e84bec64)var0;
                if (!var1.CGLIB$BOUND) {
                    var1.CGLIB$BOUND = true;

                    // CGLIB$THREAD_CALLBACKS 是一个ThreadLocal 作用是在线程的一个存储空间
                    // 详情看源码或 https://www.liaoxuefeng.com/wiki/1252599548343744/1306581251653666 spring事务原理中也有用到，用于存放连接池
                    Object var10000 = CGLIB$THREAD_CALLBACKS.get();
                    ...
                    var1.CGLIB$CALLBACK_0 = (MethodInterceptor)((Callback[])var10000)[0];
                }
            }
                何时调用的CGLIB$THREAD_CALLBACKS.set(object) 方法
                    CGLIB$SET_THREAD_CALLBACKS(Callback[] var0) 方法中去调用
                    CGLIB$SET_THREAD_CALLBACKS 在 newInstance方法中调用
                    newInstance方法是UserServiceImpl$$EnhancerByCGLIB$$e84bec64实现的Factory（工厂）定义的方法 newInstance 用于生成新的代理对象
                    如：
                        Factory userService_proxy = (Factory) userCglib(UserServiceImpl.class);
                        userService_proxy.newInstance(new MyMethodInterceptor());
                然后并没用用其去创建新的代理对象，所以并不是在此出设置

        而是在enhancer.create()方法中
        其中，先将参数封装为key，创建代理对象后，一起换成在一个map中 Map<key,代理类> 注意是代理类并非代理对象
        然后 Object result = super.create(key); 创建代理对象

        protected Object create(Object key) {
            try{
                ...
                this.key = key;
                Object obj = data.get(this, getUseCache()); // 获取代理类
                if (obj instanceof Class) {     // obj 如果是个类
                    return firstInstance((Class) obj);  // 就用该方法去创建该类的代理对象并返回
                }
                return nextInstance(obj);
            }
            ...
        }
            protected Object firstInstance(Class type) throws Exception {
                ...
                else {
                    return createUsingReflection(type);
                }
            }
                private Object createUsingReflection(Class type) {
                    setThreadCallbacks(type, callbacks);    // 设置callback
                                                            // 对 CGLIB$SET_THREAD_CALLBACKS 方法进行invoke
                                                            // setter.invoke(null, new Object[]{callbacks});
                    try {
                        if (argumentTypes != null) {
                            return ReflectUtils.newInstance(type, argumentTypes, arguments);
                        } else {
                            return ReflectUtils.newInstance(type);
                        }
                    }
                    finally {
                        // clear thread callbacks to allow them to be gc'd
                        setThreadCallbacks(type, null);
                    }
                }

 整体思路
    创建增强器Enhancer对象，并设置自定义方法拦截器callback，设置需要代理的对象
    使用Enhancer 得到代理类
        得到代理类的之后，获取代理类的 CGLIB$SET_THREAD_CALLBACKS 方法 并 用 null 去 invoke 该方法(static的)
        代理类的 CGLIB$SET_THREAD_CALLBACKS 方法 对ThreadLocal进行了set(callback[])

    从而在代理对象调用test方法时，能解释获取到自定义的callback，并执行其中的intercept方法，从而实现增强




 *
 *
 *
 *
 */
public class Main {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        // 执行原始对象方法
        UserService userService = new UserServiceImpl();
        userService.test();
        userService.test2();

        // 执行代理对象方法
        UserService userService_proxy = userCglib(UserServiceImpl.class);
//        UserService userService_proxy = userJDKProxy(UserService.class, userService);
        userService_proxy.test();
        userService_proxy.test2();

    }

    // 使用CGLib生成代理对象
    private static <T> T userCglib(Class<T> clazz) {
        // 1 创建一个 Enhancer 对象 enhancer 增强器
        Enhancer enhancer=new Enhancer();
        // 2 设置要代理的原始类
        enhancer.setSuperclass(clazz);
        // 3 设置回调方法 方法拦截
        enhancer.setCallback(new MyMethodInterceptor());
        // 4 利用enhancer 创建代理对象
        T t= (T) enhancer.create();
        return t;
    }

    private static <T> T userJDKProxy(Class<T> clazz, T t) throws InstantiationException, IllegalAccessException {
        T t_proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MyInvocationHandler(t));
        return t_proxy;
    }


}

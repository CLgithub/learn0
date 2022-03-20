/**
 * @Author l
 * @Date 2022/3/20 13:38
 */
public class Test2 {
    public static void main(String[] args) throws ClassNotFoundException {
//        Class<?> aClass = Class.forName("javax.servlet.ServletRegistration"); //能找到
        Class<?> aClass = Class.forName("javax.jms.ConnectionFactory"); // 抛出异常
        System.out.println(aClass);
    }
}

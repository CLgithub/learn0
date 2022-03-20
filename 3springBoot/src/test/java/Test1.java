import org.springframework.beans.factory.BeanClassLoaderAware;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * @Author l
 * @Date 2022/3/17 11:16
 */
public class Test1  implements BeanClassLoaderAware {

    private ClassLoader classLoader;


    public static void main(String[] args) throws ClassNotFoundException {
//        Class<?> class_Test1= Test1.class.getClassLoader().loadClass("Test1");
//        System.out.println(class_Test1);
        URLClassLoader urlClassLoader= (URLClassLoader) Test1.class.getClassLoader();
        URL[] urLs = urlClassLoader.getURLs();
        for(URL url: urLs){
            System.out.println(url.toString());
        }
    }


    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader= (URLClassLoader) Test1.class.getClassLoader();
    }
}

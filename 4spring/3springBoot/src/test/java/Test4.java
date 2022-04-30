/**
 * @Author l
 * @Date 2022/3/22 11:47
 */
public class Test4 {
    private static final String SYSTEM_PROPERTY_JAVA_AWT_HEADLESS = "java.awt.headless";

    public static void main(String[] args) {
//        System.setProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, System.getProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, Boolean.toString(true)));
        System.setProperty("java.awt.headless",
                System.getProperty("java.awt.headless","true")
        );
        System.out.println("abc");

    }
}

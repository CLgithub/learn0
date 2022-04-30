/**
 * @Author l
 * @Date 2022/3/22 11:30
 */
public class Test3 {
    public static void main(String[] args) {

        RuntimeException runtimeException = new RuntimeException();
        try {
            System.out.println(runtimeException);
            throw runtimeException;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            System.out.println("-------");
            // 返回一个表示该线程堆栈转储的堆栈跟踪元素数组
            StackTraceElement[] stackTrace = runtimeException.getStackTrace();
            for(StackTraceElement stackTraceElement: stackTrace){
                System.out.println(stackTraceElement);
            }

        }
    }
}

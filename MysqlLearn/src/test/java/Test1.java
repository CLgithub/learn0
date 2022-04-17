import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @Author l
 * @Date 2022/4/16 20:58
 */
public class Test1 {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        ArrayList arrayList=new ArrayList();
        Class<ArrayList> arrayListClass = ArrayList.class;
//        Field[] declaredFields = arrayListClass.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
        Field default_capacity = arrayListClass.getDeclaredField("DEFAULT_CAPACITY");
        Object o = default_capacity.get(arrayList);
            System.out.println(default_capacity.getName()+" = "+o);
    }
}

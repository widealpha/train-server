package cn.widealpha.train.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("all")
public class ObjectUtil {
    /**
     * 从from中copy非空覆盖to中变量
     * @param from
     * @param to
     */
    public static void copyOnNotNull(Object from, Object to) {
        try {
            if (from == null || to == null){
                return;
            }
            if (!from.getClass().getTypeName().equals(to.getClass().getTypeName())) {
                return;
            }
            for (Field field : from.getClass().getDeclaredFields()) {
                Object o = getFieldValue(from, field.getName());
                if (o != null) {
                    setFieldValue(to, field.getName(), o);
                }
            }
        } catch (Exception ignore) {
        }

    }

    /**
     * 首字母大写
     *
     * @param letter
     * @return
     */
    public static String upperFirstLetter(String letter) {
        if (StringUtil.isEmpty(letter)) {
            return letter;
        }
        String firstLetter = letter.substring(0, 1).toUpperCase();
        return firstLetter + letter.substring(1);
    }

    /**
     * 反射获得属性值（通过get方法）
     *
     * @param obj
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        if (obj == null || StringUtil.isEmpty(fieldName)) {
            return obj;
        }
        Class<?> userCla = obj.getClass();
        String getMethodName = "get" + upperFirstLetter(fieldName);
        Method method = userCla.getMethod(getMethodName);
        obj = method.invoke(obj);
        return obj;
    }

    /**
     * 反射设置属性值（通过set方法）
     *
     * @param obj
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object setFieldValue(Object obj, String fieldName, Object fieldValue) throws Exception {
        if (obj == null || StringUtil.isEmpty(fieldName)) {
            return obj;
        }
        Class<?> userCla = obj.getClass();
        String setMethodName = "set" + upperFirstLetter(fieldName);
        Method method = userCla.getMethod(setMethodName, fieldValue.getClass());
        obj = method.invoke(obj, fieldValue);
        return obj;
    }

    /**
     * 反射获得值(直接反射字段取值)
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue1(Object obj, String fieldName) throws Exception {
        if (obj == null || StringUtil.isEmpty(fieldName)) {
            return obj;
        }
        Class userCla = obj.getClass();
        do {
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                Object objVal = f.get(obj);//得到此属性的值
                if (f.getName().equals(fieldName)) {
                    return objVal;
                }
            }
            userCla = userCla.getSuperclass();
        } while (userCla != null);
        return null;
    }

    /**
     * 反射设置值(直接给字段赋值)
     *
     * @param obj
     * @param fieldName
     * @param fieldValue
     * @throws Exception
     */
    public static Object setFieldValue1(Object obj, String fieldName, Object fieldValue) throws Exception {
        if (obj == null || StringUtil.isEmpty(fieldName)) {
            return obj;
        }
        Class<?> userCla = obj.getClass();
        do {
            Field[] fs = userCla.getDeclaredFields();
            for (Field f : fs) {
                f.setAccessible(true); //设置些属性是可以访问的
                if (f.getName().equals(fieldName)) {
                    f.set(obj, fieldValue);
                    return obj;
                }
            }
            userCla = userCla.getSuperclass();
        } while (userCla != null);
        return obj;
    }
}

package org.ruanwei.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanUtils {

    public static void print(Object obj) {
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                Object r = f.get(obj);
                System.out.printf("%s = %s\t", f.getName(), r);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    public static void print(Object obj, String msg) {
        System.out.printf("%s\n", msg);
        Class cls = obj.getClass();
        System.out.printf("[%s]:", cls.getSimpleName());
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                Object r = f.get(obj);
                System.out.printf("%s = %s\t", f.getName(), r);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    public static <T> T copy(Object orig, Class<T> c) {
        try {
            T desc = c.newInstance();
            PropertyUtils.copyProperties(desc, orig);
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object clone(Object orig) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        return copy(orig, orig.getClass());
    }

}

package cn.widealpha.train.util;

import java.util.Random;

/**
 * String工具类
 * @author kmh
 * @date 2021/7/13
 */
public class StringUtil {
    /**
     * 生成指定长度的随机字符串(只包含大写)
     * @param length 生成随机串的长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否为null或者为空
     * @param s 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String s){
        return s == null || s.isEmpty();
    }

    /**
     * 判断字符串是否有任何一个为null或者空
     * @param strings 判空字符串列表
     * @return 是否含有空
     */
    public static boolean anyEmpty(String ...strings){
        for (String s :strings){
            if (s == null || s.isEmpty()){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是全部为null或者空
     * @param strings 判空字符串列表
     * @return 是否全部空
     */
    public static boolean allEmpty(String ...strings){
        for (String s :strings){
            if (s != null && !s.isEmpty()){
                return false;
            }
        }
        return true;
    }
}

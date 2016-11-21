package cn.ibabygroup.pros.imservice.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by tianmaogen on 2016/8/17.
 */
public class Utils {

    public static Random rand = new Random();
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static int randInt() {
        int randomNum = rand.nextInt(Integer.MAX_VALUE-1);
        return randomNum;
    }

    /**
     * 将long类型的时间戳转换成以“2016-8-8 13:12:11”格式定义的String类型
     * @param time
     * @return
     */
    public static String convertTimeStr2Long(long time) {
        String timeStr = dateFormat.format(new Date(time));
        return timeStr;
    }

    /**
     * 截取一个包含汉字的字符串的前n个字节的算法
     */
    public static String subStringByByte(String str, int len) {

        byte[] b = str.getBytes();

        if (len == 1) {// 当只取1位时
            if (b[0] < 0)
                return new String(b, 0, 2);
            else
                return new String(b, 0, len);
        } else {
            if (b[len - 1] < 0 && b[len - 2] > 0) { // 判断最后一个字节是否为一个汉字的第一个字节
                return new String(b, 0, len - 1);
            }
        }
        return new String(b, 0, len);
    }

    public static void main(String [] args) {
        String str = "啊实打实的阿萨rwe德阿啊实打实asdas的阿1adasdsd231*(asda)*()&*(萨得到d德阿啊实打实的阿萨德阿啊实打实的阿萨德阿啊实打实的阿萨德阿啊实打实的阿萨德阿";
        System.out.println(str.getBytes().length);
        String subStr = subStringByByte(str, 30);
        System.out.println(subStr.getBytes().length);
    }
}

package com.im.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author 肖健
 * @descrption 输入工具类
 * @projectName chat-room
 */
public class InputUtil {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static String getLine(){
       return SCANNER.nextLine();
    }
    /**
     * 从控制台获取一个给定范围区间内的整数
     *
     * @param tip
     * @param min
     * @param max
     * @return
     */
    public static int getInputInteger(String tip, int min, int max){
        System.out.println(tip);
        while (true){
            if(SCANNER.hasNextInt()){
                int number = SCANNER.nextInt();
                if(number >= min && number <= max){
                    return number;
                } else {
                    System.out.println("输入错误，请输入" + min + "~" + max + "之间的整数");
                }
            } else {
                System.out.println("输入错误，请输入" + min + "~" + max + "之间的整数");
                SCANNER.next();
            }
        }
    }

    /**
     * 从控制台中获取一个给定数组中的数字
     *
     * @param tip
     * @param values
     * @return
     */
    public static int getInputInteger(String tip,int...values){
        System.out.println(tip);
        while (true){
            if(SCANNER.hasNextInt()){
                int number = SCANNER.nextInt();
                for(int value: values){
                    if(number == value) {
                        return number;
                    }
                }
                System.out.println("输入错误，只能输入" + Arrays.toString(values) + "中的整数");

            } else {
                System.out.println("输入错误，只能输入" + Arrays.toString(values) + "中的整数");
                SCANNER.next();
            }
        }
    }

    /**
     * 从控制台获取一个字符串
     *
     * @param tip
     * @return
     */
    public static String getInputText(String tip){
        System.out.println(tip);
        return SCANNER.next();
    }

    /**
     * 从控制台获取一个给定日期格式的日期
     *
     * @param tip
     * @param format
     * @return
     */
    public static String getInputDate(String tip, String format){
        System.out.println(tip);
        while (true){
            String dateStr = SCANNER.next();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            boolean valid = true;
            try {
                sdf.parse(dateStr);
            } catch (ParseException e) {
                valid = false;
            }
            if(valid){
                return dateStr;
            } else {
                System.out.println("日期格式错误，请重新输入：（" + format + "）");
            }
        }
    }
}

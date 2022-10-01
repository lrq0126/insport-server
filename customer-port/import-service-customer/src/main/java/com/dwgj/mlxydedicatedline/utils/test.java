package com.dwgj.mlxydedicatedline.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class test {

    public static void main(String[] args) {

        System.out.println(toSerialCode());
        //String str = toSerialCode(84);
    }



    public static String toSerialCode() {

            String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
            Random rand = new Random();
            StringBuffer flag = new StringBuffer();
            for (int j = 0; j < 6; j++)
            {
                flag.append(sources.charAt(rand.nextInt(9)) + "");
            }
        return flag.toString();
    }

    public static void test() throws Exception {
        List<Double> list = new ArrayList<>();
        list.add(1.0);
        list.add(2.0);
        list.add(3.0);
        AtomicReference<Double> t = new AtomicReference<>((double) 0);
        list.forEach(listt -> {
            if (listt != 2) {
                t.updateAndGet(v -> new Double((double) (v + listt)));
            }
        });
        System.out.println("输出：" + t);
    }


    public static void NumericalTransformation() {
        int length = 11;
        int width = 18;
        int height = 18;
        int[] arr = {length, width, height};


        for (int i : arr) {
            System.out.println(i);
        }
    }

}

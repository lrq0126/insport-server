package com.example.warehouse.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Configuration
public class TreadUtil {

//    public static void imageAsynchronize(){
//        // 创建一个线程执行 doOneThing
//        Thread doOneThingThread = new Thread();
//        doOneThingThread.start();
//
//        doOtherThing();
//        // 等待 doOneThing 线程执行完成
//        doOneThingThread.join();
//    }


    public static void serviceThing() {
        System.out.println("开始处理Service事务");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("serviceThing ---->>> success");
    }

    public static void imageUploadThing() {
        System.out.println("开始处理图片上传业务");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("imageUploadThing ---->>> success");
    }

    public synchronized static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch("TreadUtil");
        stopWatch.start();

        // 异步调用版本
        testAsynchronize();

        stopWatch.stop();
        System.out.println(stopWatch);
    }

    private static void testAsynchronize() throws InterruptedException {
        System.out.println("-------------------- testAsynchronize --------------------");

        // 创建一个线程执行 doOneThing
        Thread serviceThingThread = new Thread(TreadUtil::serviceThing, "serviceThing-Thread");
        serviceThingThread.start();

        imageUploadThing();
        // 等待 serviceThingThread 线程执行完成
        serviceThingThread.join();
    }
}

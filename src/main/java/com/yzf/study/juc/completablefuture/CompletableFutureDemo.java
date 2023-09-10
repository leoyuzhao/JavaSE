package com.yzf.study.juc.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * @description: CompletableFuture 使用案例
 * @author leo
 * @date 2023/9/7 16:42
 * @version 1.0
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws Exception{

        CompletableFuture completableFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Hello World");
            }
        });
        // get 方法阻塞主线程
        Object o = completableFuture.join();
        System.out.println(o);
        System.out.println("main线程执行完毕。");
    }

}

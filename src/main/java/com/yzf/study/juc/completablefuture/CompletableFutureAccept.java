package com.yzf.study.juc.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author leo
 * @version 1.0
 * @description: 消费执行结果
 * @date 2023/9/8 11:52
 */
public class CompletableFutureAccept {

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(3);

        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("111");
            return 1;
        }, pool).thenApply((f) -> {
            // int i = 1/0;
            System.out.println("222");
            return f + 2;
        }).thenApply((f) -> {
            System.out.println("333");
            return f + 3;
        }).thenAccept(a -> {
            System.out.println(a);
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });

        System.out.println("main方法执行 其他任务");
        pool.shutdown();

    }

}

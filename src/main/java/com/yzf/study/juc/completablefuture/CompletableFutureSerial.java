package com.yzf.study.juc.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author leo
 * @version 1.0
 * @description: 顺序执行任务
 * @date 2023/9/8 11:34
 */
public class CompletableFutureSerial {

    public static void main(String[] args) {
        testConsume();

    }

    public static void testApply() {

        ExecutorService pool = Executors.newFixedThreadPool(3);

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("111");
            return 1;
        }, pool).thenApply((f) -> {
            int i = 1/0;
            System.out.println("222");
            return f + 2;
        }).thenApply((f) -> {
            System.out.println("333");
            return f + 3;
        }).whenComplete((res, e) -> {
            System.out.println("res：" + res);
            if (e != null) {
                System.out.println("exception：" + e);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });

        System.out.println("main方法执行 其他任务");
        pool.shutdown();

    }

    public static void testHandle() {

        ExecutorService pool = Executors.newFixedThreadPool(3);

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("111");
            return 1;
        }, pool).handle((f, e) -> {
            int i = 1/0;
            System.out.println("222");
            return f + 2;
        }).handle((f, e) -> {
            System.out.println("333");
            System.out.println("333 res：" + f);
            return f + 3;
        }).whenComplete((res, e) -> {
            System.out.println("res：" + res);
            if (e != null) {
                System.out.println("exception：" + e);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return 1;
        });

        System.out.println(future.join());

        System.out.println("main方法执行了");
        pool.shutdown();

    }

    public static void testConsume(){
        Integer join = CompletableFuture.supplyAsync(() -> 1).thenApply((a) -> {
            return a + 2;
        }).join();
        System.out.println("res：" + join);
        System.out.println(CompletableFuture.supplyAsync(() -> 1).thenAccept((a) -> {}).join());
        System.out.println(CompletableFuture.supplyAsync(() -> 1).thenRun(() -> {}).join());
    }

}

package com.yzf.study.juc.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAsync {

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(3);

        /*
        CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("1." + Thread.currentThread().getName());
            return 1;
        }, pool).thenRun(()->{
            System.out.println("2." + Thread.currentThread().getName());
        }).thenRun(()->{
            System.out.println("3." + Thread.currentThread().getName());
        }).thenRun(()->{
            System.out.println("4." + Thread.currentThread().getName());
        });
        */


        CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("21." + Thread.currentThread().getName());
            return 1;
        }, pool).thenRunAsync(()->{
            System.out.println("22." + Thread.currentThread().getName());
        }).thenRun(()->{
            System.out.println("23." + Thread.currentThread().getName());
        }).thenRun(()->{
            System.out.println("24." + Thread.currentThread().getName());
        });

        pool.shutdown();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

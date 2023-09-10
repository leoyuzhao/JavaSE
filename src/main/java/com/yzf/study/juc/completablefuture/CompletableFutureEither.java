package com.yzf.study.juc.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author leo
 * @version 1.0
 * @description: 判断异步线程谁先执行完成
 * @date 2023/9/8 14:50
 */
public class CompletableFutureEither {

    public static void main(String[] args) {
        CompletableFuture<String> aplayer = CompletableFuture.supplyAsync(() -> {

            System.out.println("A come in");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "A PLAYER";
        });

        CompletableFuture<String> bplayer = CompletableFuture.supplyAsync(() -> {

            System.out.println("B come in");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "B PLAYER";
        });

        CompletableFuture<Object> future = aplayer.applyToEither(bplayer, (f) -> {
            return f + " is winner";
        });

        System.out.println(future.join());


    }

}

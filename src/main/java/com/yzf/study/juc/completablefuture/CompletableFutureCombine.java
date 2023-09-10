package com.yzf.study.juc.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * @description: 结果合并
 * @author leo
 * @date 2023/9/8 14:53
 * @version 1.0
 */
public class CompletableFutureCombine {


    public static void main(String[] args) {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            return 1;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            return 2;
        });

        CompletableFuture<Integer> future3 = future1.thenCombine(future2, (x, y) -> {
            return x + y;
        });

        System.out.println(future3.join());

    }

}

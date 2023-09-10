package com.yzf.study.juc.completablefuture;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author leo
 * @version 1.0
 * @description: 获取异步返回结果
 * @date 2023/9/8 11:17
 */
public class CompletableFutureReturn {

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "abc";
        });
        // 阻塞 & 抛出异常
        // String s = completableFuture.get();
        // 阻塞 不抛出异常
        // String s = completableFuture.join();
        // 中断执行，并给出返回值
        // completableFuture.complete("123");
        // System.out.println(completableFuture.join());
        // 立即获取执行结果，如未执行完成，给出替代值
        String now = completableFuture.getNow("123");
        System.out.println("res：" + now);
    }

}

package com.yzf.study.juc.completablefuture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author leo
 * @version 1.0
 * @description: 比价案例，证明 多线程 性能优异
 * @date 2023/9/7 16:45
 */
public class CompletableFuturePrice {


    static List<BookStore> bookStores = new ArrayList<>();

    static {
        bookStores.add(new BookStore("京东"));
        bookStores.add(new BookStore("当当"));
        bookStores.add(new BookStore("天猫"));
    }

    public static void main(String[] args) {


        Long start = System.currentTimeMillis();
        List<String> result = bookStores.stream().map(bs -> {
            return bs.getStoreName() + " mysql " + bs.findPriceByGoodsName("mysql");
        }).collect(Collectors.toList());
        Long end = System.currentTimeMillis();
        System.out.println("res：" + result);
        System.out.println("mm：" + (end - start));

        start = System.currentTimeMillis();
        result = bookStores.stream().map(bs -> {
            return CompletableFuture.supplyAsync(()->{
               return bs.getStoreName() + " mysql " + bs.findPriceByGoodsName("mysql");
            });
        }).collect(Collectors.toList()).stream().map(a -> {
            return a.join();
        }).collect(Collectors.toList());

        end = System.currentTimeMillis();
        System.out.println("res：" + result);
        System.out.println("mm：" + (end - start));

    }


}

@Data
@NoArgsConstructor
@AllArgsConstructor
class BookStore {

    private String storeName;


    public double findPriceByGoodsName(String goodsName) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getRandom(90, 100);
    }

    private static double getRandom(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Invalid range [" + min + ", " + max + "]");
        }
        return min + Math.random() * (max - min);
    }

}

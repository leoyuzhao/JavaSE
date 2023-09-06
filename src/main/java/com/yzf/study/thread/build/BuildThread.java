package com.yzf.study.thread.build;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author leo
 * @version 1.0
 * @description: 实践线程创建的方式
 * @date 2023/9/6 8:28
 */
public class BuildThread {

    public static void main(String[] args) throws Exception {
        test02();
    }

    /**
     * @description: 有返回值的线程
     * @return: void
     * @author leo
     * @date: 2023/9/6 8:31
     */
    public static void test01() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 123;
            }
        };
        // 提交执行内容
        Future<Integer> future = pool.submit(callable);
        // 关闭线程池
        pool.shutdown();
        // 获取结果
        Integer integer = future.get();

        System.out.println(integer);

    }

    /**
     * @description: 通过线程池实现线程
     *
     * @return: void
     * @author leo
     * @date: 2023/9/6 8:37
     */
    public static void test02() throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("你好");
            }
        });
    }

}

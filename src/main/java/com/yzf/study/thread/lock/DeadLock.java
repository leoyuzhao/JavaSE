package com.yzf.study.thread.lock;

/**
 * @description: 死锁
 * @author leo
 * @date 2023/9/6 14:46
 * @version 1.0
 */
public class DeadLock {

    static Object mirror = new Object();
    static Object comb = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mirror){
                    System.out.println(Thread.currentThread().getName() + "，拿到了镜子。");
                    synchronized (comb){
                        System.out.println(Thread.currentThread().getName() + "，拿到了梳子。");
                    }
                }
            }
        }, "张三");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (comb){
                    System.out.println(Thread.currentThread().getName() + "，拿到了梳子。");
                    synchronized (mirror){
                        System.out.println(Thread.currentThread().getName() + "，拿到了镜子。");
                    }
                }
            }
        }, "李四");

        t1.start();
        t2.start();


    }

}



package com.yzf.study.juc.stop;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadStop {

    // volatile 内存可见性
    static volatile boolean isStop = false;
    static volatile AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        m3();
    }


    /**
     * @description: 使用 标志位 终止线程
     * @author leo
     * @date 2023/9/9 22:22
     * @version 1.0
     */
    public static void m1() {

        Thread t1 = new Thread(() -> {
            while (true){
                System.out.println(Thread.currentThread().getName() + " run ....");
                if (isStop){
                    System.out.println(Thread.currentThread().getName() + " stop ....");
                    break;
                }
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            isStop = true;
        }, "t2");
        t2.start();


    }

    public static void m2() {

        Thread t1 = new Thread(() -> {
            while (true){
                System.out.println(Thread.currentThread().getName() + " run ....");
                if (atomicBoolean.get()){
                    System.out.println(Thread.currentThread().getName() + " stop ....");
                    break;
                }
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            atomicBoolean.set(true);
        }, "t2");
        t2.start();


    }

    public static void m3() {

        Thread t1 = new Thread(() -> {
            while (true){
                System.out.println(Thread.currentThread().getName() + " run ....");
                if (Thread.currentThread().isInterrupted()){
                    System.out.println(Thread.currentThread().getName() + " stop ....");
                    break;
                }
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            t1.interrupt();
        }, "t2");
        t2.start();


    }

}

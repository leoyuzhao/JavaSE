package com.yzf.study.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 公平锁 非公平锁 测试
 * @author leo
 * @version 1.0
 * @date 2023/9/8 17:13
 */
public class TestFairLock {

    public static void main(String[] args) {

        TicketStore ticketStore = new TicketStore();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (ticketStore.ticket > 0){
                    ticketStore.sell();
                }
            }
        }, "a");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (ticketStore.ticket > 0){
                    ticketStore.sell();
                }
            }
        }, "b");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (ticketStore.ticket > 0){
                    ticketStore.sell();
                }
            }
        }, "c");

        t1.start();
        t2.start();
        t3.start();

    }

}

class TicketStore {

    volatile int ticket = 100;
    private ReentrantLock lock = new ReentrantLock(true);


    public void sell() {
        try {
            lock.lock();
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + " 卖出了第 " + ticket + " 张票。");
                ticket--;
            }
        } finally {
            lock.unlock();
        }
    }


}


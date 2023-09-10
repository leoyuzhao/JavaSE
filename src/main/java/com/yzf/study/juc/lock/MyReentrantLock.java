package com.yzf.study.juc.lock;

public class MyReentrantLock {

    private volatile int count = 0;
    private Thread thread = null;

    public synchronized void lock() throws Exception {

        while (thread != null && thread != Thread.currentThread()) {
            this.wait();
        }

        if (thread == null || thread == Thread.currentThread()) {
            thread = Thread.currentThread();
            count++;
        }

    }

    public synchronized void unlock() {
        if (thread == null || thread != Thread.currentThread()) {
            return;
        }
        if (thread == Thread.currentThread()) {
            count--;
            if (count <= 0) {
                this.thread = null;
                this.count = 0;
                this.notifyAll();
            }
        }
    }

    public static void main(String[] args) {

        TestTicket tt = new TestTicket();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (tt.ticket > 0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    tt.sell();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (tt.ticket > 0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    tt.sell();
                }
            }
        }, "t2");



        t1.start();
        t2.start();

    }


}


class TestTicket {

    volatile int ticket = 50;
    private MyReentrantLock lock = new MyReentrantLock();

    public void sell() {
        try {
            lock.lock();
            if (ticket > 0) {
                System.out.println(Thread.currentThread() + "，卖出了第 " + this.ticket + " 张票。");
                ticket--;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}

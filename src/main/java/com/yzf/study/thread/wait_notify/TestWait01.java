package com.yzf.study.thread.wait_notify;

public class TestWait01 {

    public static void main(String[] args) {


        PrintNumber printNumber = new PrintNumber();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (printNumber) {
                        while (printNumber.number != 1) {
                            try {
                                printNumber.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + " print：" + printNumber.number);
                        printNumber.number++;
                        printNumber.notifyAll();
                    }
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (printNumber) {
                        while (printNumber.number != 2) {
                            try {
                                printNumber.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + " print：" + printNumber.number);
                        printNumber.number ++;
                        printNumber.notifyAll();
                    }
                }
            }
        }, "t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (printNumber) {
                        while (printNumber.number != 3) {
                            try {
                                printNumber.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + " print：" + printNumber.number);
                        printNumber.number = 1;
                        printNumber.notifyAll();
                    }
                }
            }
        }, "t3");

        t1.start();
        t2.start();
        t3.start();


    }


}

class PrintNumber {

    int number = 1;

}
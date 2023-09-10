package com.yzf.study.thread.stop;

public class TestStop {

    public static void main(String[] args) throws Exception {

        MyThread myThread = new MyThread();
        myThread.start();

        Thread.sleep(300);
        myThread.interrupt();
        Thread.sleep(300);
        System.out.println(myThread.isInterrupted());

    }

}

class MyThread extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + " run ...");
        }
        System.out.println(Thread.currentThread().isInterrupted());
        System.out.println("中断执行");
    }
}








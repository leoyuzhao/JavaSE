package com.yzf.study.thread.wait_notify;

public class TestWait {
    public static void main(String[] args) {


        Light light = new Light();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        light.turnOff();
                        Thread.sleep(1000);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        light.turnOn();
                        Thread.sleep(1000);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();


    }
}

class Light{

    private int status = 0;
    private static final int OFF = 0;
    private static final int ON = 1;

    public synchronized int getStatus(){
        return this.status;
    }

    public synchronized void turnOff() throws InterruptedException {
        while (getStatus() == OFF){
            this.wait();
        }
        this.status = OFF;
        System.out.println(Thread.currentThread().getName() + "，关灯了");
        this.notifyAll();
    }

    public synchronized void turnOn() throws InterruptedException {
        while (getStatus() == ON){
            this.wait();
        }
        this.status = ON;
        System.out.println(Thread.currentThread().getName() + "，开灯了");
        this.notifyAll();
    }


}
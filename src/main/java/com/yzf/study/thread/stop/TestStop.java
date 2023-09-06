package com.yzf.study.thread.stop;

public class TestStop {

    public static void main(String[] args) throws Exception{

        MyThread myThread = new MyThread();
        myThread.start();

        Thread.sleep(8000);
        myThread.interrupt();

    }

}

class MyThread extends Thread{
    @Override
    public void run() {
        while (!this.isInterrupted()){
            try {
                Thread.sleep(900);
                System.out.println("myThread run ...");
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}








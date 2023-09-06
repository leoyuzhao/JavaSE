package com.yzf.study.thread.synchronize;

/**
 * @description: 同步方法
 * @author leo
 * @date 2023/9/6 13:50
 * @version 1.0
 */
public class TicketDemo02 {

    public static void main(String[] args) {
        Runnable runnable = new TicketHandleRunnable();
        Thread thread1 = new Thread(runnable,"窗口1");
        Thread thread2 = new Thread(runnable,"窗口2");
        Thread thread3 = new Thread(runnable,"窗口3");
        thread1.start();
        thread2.start();
        thread3.start();
    }

}


class TicketHandleRunnable implements Runnable {

    int ticket = 100;

    public synchronized boolean sellTicket(){
        if (ticket <= 0) {
            return false;
        }
        System.out.println(Thread.currentThread().getName() + "，卖出了第：" + ticket + " 张票。");
        ticket--;
        return true;
    }


    @Override
    public void run() {
        while (true) {
            boolean b = sellTicket();
            if (!b){
                break;
            }
        }
    }
}

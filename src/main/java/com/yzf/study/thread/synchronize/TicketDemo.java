package com.yzf.study.thread.synchronize;

/**
 * @description: 同步代码块
 * @author leo
 * @date 2023/9/6 13:56
 * @version 1.0
 */
public class TicketDemo {

    public static void main(String[] args) {
        TicketHandle t1 = new TicketHandle();
        t1.setName("窗口1");
        TicketHandle t2 = new TicketHandle();
        t2.setName("窗口2");
        TicketHandle t3 = new TicketHandle();
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }

}


class TicketHandle extends Thread {

    static int ticket = 100;

    @Override
    public void run() {

        while (true) {
            // 注意锁唯一
            synchronized (TicketHandle.class) {
                if (ticket <= 0) {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "，卖出了第：" + ticket + " 张票。");
                ticket--;
            }
        }

    }
}

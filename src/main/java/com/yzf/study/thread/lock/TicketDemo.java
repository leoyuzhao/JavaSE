package com.yzf.study.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author leo
 * @version 1.0
 * @description: 同步代码块
 * @date 2023/9/6 13:56
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
    static Lock lock = new ReentrantLock();

    @Override
    public void run() {

        /*
        while (true) {
            // 注意锁唯一
            lock.lock();
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "，卖出了第：" + ticket + " 张票。");
                ticket--;
            }else{
                lock.unlock();
                // 直接跳出，未释放锁，会导致其他线程一直阻塞
                break;
            }
            lock.unlock();
        }
        */
        while (true) {
            try {
                // 注意锁唯一
                lock.lock();
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + "，卖出了第：" + ticket + " 张票。");
                    ticket--;
                }else{
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }

    }
}

package com.yzf.study.thread.priority;

import java.util.ArrayList;
import java.util.List;

public class TestPriority {

    public static void main(String[] args) {

        List<Thread> list = new ArrayList<>();
        Thread t = null;
        for (int i = 0; i < 5; i++) {
            int temp = i;
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "：" + temp);
                }
            }, "t" + i);
            t.setPriority(i + 1);

            list.add(t);
        }

        for (Thread temp : list) {
            temp.start();
        }


    }









}

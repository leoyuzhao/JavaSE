package com.yzf.study.thread.doublecheck;

public class TestDoubleCheck {

    public static void main(String[] args) {
        Thread thread = null;
        for (int i = 0; i < 5; i++) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    SingleTone instance = SingleTone.getInstance();
                    System.out.println(instance);
                }
            });
            thread.start();
        }
    }

}


class SingleTone{

    static volatile SingleTone instance = null;

    public synchronized static SingleTone getInstance(){
        if (instance == null){
            instance = new SingleTone();
        }
        return  instance;
    }


    public static SingleTone getInstance1(){
        // t1 t2
        if (instance == null) {
            // t3 t4
            synchronized (SingleTone.class){
                if (instance == null){
                    // t5
                    instance = new SingleTone();
                }
            }
        }
        return  instance;
    }


}

package com.android.wwh.javabasis.concurrent.semaphore;

import java.util.concurrent.Semaphore;

/**
 * 使用信号量机制，实现互斥访问打印机
 */
public class MutexPrint {

    /**
     * 定义初始值为1的信号量
     */
    private final Semaphore semaphore = new Semaphore(1);

    /**
     * 模拟打印操作
     *
     * @param str
     * @throws InterruptedException
     */
    public void print(String str) throws InterruptedException {
        //请求许可
        semaphore.acquire();

        System.out.println(Thread.currentThread().getName() + " enter ...");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " printing ..." + str);
        System.out.println(Thread.currentThread().getName() + " out ...");
        //释放许可
        semaphore.release();
    }

    public static void main(String[] args) {
        final MutexPrint print = new MutexPrint();

        /**
         * 开启10个线程，抢占打印机
         */
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    try {
                        print.print("helloworld");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }

}

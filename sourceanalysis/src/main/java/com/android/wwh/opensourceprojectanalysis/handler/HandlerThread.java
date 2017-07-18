package com.android.wwh.opensourceprojectanalysis.handler;

/**
 * Created by we-win on 2017/7/18.
 */

public class HandlerThread extends Thread {
//    int mPriority;
//    int mTid = -1;
//    Looper mLooper;
//
//    public HandlerThread(String name) {
//        super(name);
//        mPriority = Process.THREAD_PRIORITY_DEFAULT;
//    }
//
//    protected void onLooperPrepared() {
//    }
//
//    @Override
//    public void run() {
//        mTid = Process.myTid();
//        // 创建了一个Looper对象，并且把该对象放到了该线程范围内的变量中（sThreadLocal），
//        // 在Looper对象的构造过程中，初始化了一个MessageQueue，作为该Looper对象成员变量。
//        Looper.prepare();
//        synchronized (this) {
//            mLooper = Looper.myLooper();
//            // 因为的mLooper在一个线程中执行，而我们的handler是在UI线程初始化的，
//               也就是说，我们必须等到mLooper创建完成，才能正确的返回getLooper();wait(),notify()就是为了解决这两个线程的同步问题。
//            notifyAll();
//        }
//        Process.setThreadPriority(mPriority);
//        onLooperPrepared();
//        // 不断的循环从MessageQueue中取消息处理了，当没有消息的时候会阻塞，有消息的到来的时候会唤醒。
//        Looper.loop();
//        mTid = -1;
//    }
//
//    public Looper getLooper() {
//        if (!isAlive()) {
//            return null;
//        }
//
//        // If the thread has been started, wait until the looper has been created.
//        synchronized (this) {
//            while (isAlive() && mLooper == null) {
//                try {
//                    //     ???
//                    wait();
//                } catch (InterruptedException e) {
//                }
//            }
//        }
//        // 就是我们在run方法中创建的mLooper。
//        return mLooper;
//    }
}
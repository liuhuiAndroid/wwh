package com.android.wwh.opensourceprojectanalysis.EventBus;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by we-win on 2017/7/7.
 */

public class EventBus {

    private static EventBus eventBus = new EventBus();
    private Handler mHandler;

    /**
     * 使用恶汉模式创建单例
     *
     * @return
     */
    public static EventBus getInstatnce() {
        return eventBus;
    }

    private EventBus() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    /*
     * 我们的强大的map，存储我们的方法
     * 使用了一个Map存储所有的方法，key为参数的类型class；value为CopyOnWriteArrayList<SubscribeMethod>
     * CopyOnWriteArrayList是什么东西？
     * 一个参数类型对应很多方法，所以value是个CopyOnWriteArrayList。
     */
    private static Map<Class, CopyOnWriteArrayList<SubscribeMethod>> mSubscribeMethodsByEventType = new HashMap<Class, CopyOnWriteArrayList<SubscribeMethod>>();

    public void register(Object subscriber) {

        Class clazz = subscriber.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        CopyOnWriteArrayList<SubscribeMethod> subscribeMethods = null;
        /**
         * 遍历该类的所有方法
         */
        for (Method method : methods) {
            String methodName = method.getName();
            /**
             * 判断方法是否以onEvent的开头
             */
            if (methodName.startsWith("onEvent")) {
                SubscribeMethod subscribeMethod = null;
                // 方法命中提前在什么线程运行。默认在UI线程
                String threadMode = methodName.substring("onEvent".length());
                ThreadMode mode = ThreadMode.UI;

                Class<?>[] parameterTypes = method.getParameterTypes();

                // 参数的个数为1
                if (parameterTypes.length == 1) {
                    Class<?> eventType = parameterTypes[0];

                    synchronized (this) {

                        if (mSubscribeMethodsByEventType.containsKey(eventType)) {
                            subscribeMethods = mSubscribeMethodsByEventType
                                    .get(eventType);
                        } else {
                            subscribeMethods = new CopyOnWriteArrayList<SubscribeMethod>();
                            mSubscribeMethodsByEventType.put(eventType,
                                    subscribeMethods);
                        }
                    }

                    if (threadMode.equals("Async")) {
                        mode = ThreadMode.Async;
                    }
                    // 提取出method，mode，方法所在类对象，存数的类型封装成为SubscribeMethod
                    subscribeMethod = new SubscribeMethod(method, mode,
                            subscriber);
                    // 存在Map里面
                    subscribeMethods.add(subscribeMethod);
                }
            }

        }
    }

    enum ThreadMode {
        UI, Async
    }


    /**
     * 这里我们封装了一个SubscribeMethod，
     * 这个里面存储了我们需要运行方法的所有参数，毕竟我们运行时，需要该方法，该方法所在的对象，以及在什么线程运行；
     * 三个对象足以，当然也缺一不可了~~
     */
    class SubscribeMethod {
        Method method;
        ThreadMode threadMode;
        Object subscriber;

        public SubscribeMethod(Method method, ThreadMode threadMode,
                               Object subscriber) {
            this.method = method;
            this.threadMode = threadMode;
            this.subscriber = subscriber;
        }

    }

    // ==========================POST==========================================================

    private ThreadLocal<PostingThread> mPostingThread = new ThreadLocal<PostingThread>() {
        @Override
        public PostingThread get() {
            return new PostingThread();
        }
    };

    public void post(Object eventTypeInstance) {
        //拿到该线程中的PostingThread对象
        PostingThread postingThread = mPostingThread.get();
        postingThread.isMainThread = Looper.getMainLooper() == Looper
                .myLooper();
        //将事件加入事件队列
        List<Object> eventQueue = postingThread.mEventQueue;
        eventQueue.add(eventTypeInstance);
        //防止多次调用
        if (postingThread.isPosting) {
            return;
        }
        postingThread.isPosting = true;
        //取出所有事件进行调用
        while (!eventQueue.isEmpty()) {
            Object eventType = eventQueue.remove(0);
            postEvent(eventType, postingThread);
        }
        postingThread.isPosting = false;

    }

    /**
     * 这里学习了源码，也搞了个当前线程中的变量，存储了一个事件队列以及事件的状态；
     */
    class PostingThread {
        List<Object> mEventQueue = new ArrayList<Object>();
        boolean isMainThread;
        boolean isPosting;
    }

    /**
     * 最终发布的事件先加入到事件队列，然后再取出来调用postEvent
     *
     * @param eventType
     * @param postingThread
     */
    private void postEvent(final Object eventType, PostingThread postingThread) {
        CopyOnWriteArrayList<SubscribeMethod> subscribeMethods = null;
        synchronized (this) {
            // 直接根据参数类型，去map改到该方法
            subscribeMethods = mSubscribeMethodsByEventType.get(eventType
                    .getClass());
        }

        for (final SubscribeMethod subscribeMethod : subscribeMethods) {
            // 根据其threadMode，如果在UI线程，则判断当前线程，如果是UI线程，直接调用，否则通过handler执行；
            if (subscribeMethod.threadMode == ThreadMode.UI) {
                if (postingThread.isMainThread) {
                    invokeMethod(eventType, subscribeMethod);
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            invokeMethod(eventType, subscribeMethod);
                        }
                    });
                }
            } else {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        invokeMethod(eventType, subscribeMethod);
                        return null;
                    }
                };
            }
        }
    }

    /**
     * invokeMethod很简单，就是反射调用方法了
     *
     * @param eventType
     * @param subscribeMethod
     */
    private void invokeMethod(Object eventType, SubscribeMethod subscribeMethod) {
        try {
            subscribeMethod.method.invoke(subscribeMethod.subscriber, eventType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * unregister时，由于我们没有存任何的辅助状态，我们只能再去遍历了方法了~~
     * 不过通过这个，也能反应出EventBus内部好几个Map的作用了~~
     *
     * @param subscriber
     */
    public void unregister(Object subscriber) {
        Class clazz = subscriber.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        List<SubscribeMethod> subscribeMethods = null;

        for (Method method : methods) {
            String methodName = method.getName();

            if (methodName.startsWith("onEvent")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    synchronized (this) {
                        mSubscribeMethodsByEventType.remove(parameterTypes[0]);
                    }
                }
            }
        }

    }

}

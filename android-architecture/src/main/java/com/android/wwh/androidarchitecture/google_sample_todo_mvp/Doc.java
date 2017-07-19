package com.android.wwh.androidarchitecture.google_sample_todo_mvp;

/**
 * Created by lh on 2017/7/19.
 */

public class Doc {
    /**
     *
     通过上面的分析，在来梳理一下整个步骤：

     1 官方MVP实例，通过协议类XXXContract来对View和Presenter的接口进行内部继承。
     是对BaseView和BasePresenter的进一步封装，所以我们实现的View和Presenter也只需要继承XXXContract中的对应内部接口就行。

     2 activity的作用主要是创建View（这里是相应的fragment），以及创建presenter，并把view传递给presenter
     （完成presenter对view实例关联操作）

     3 在presenter的实现类的构造函数中，通过view的setPresenter，让view获得了presenter实例。
     这样view中就可以对Presenter中的方法进行操作了。（完成view对presenter实例关联操作）

     4 在presenter的实现类中，可以对Model数据进行操作。实例中，数据的获取、存储、数据状态变化都是model层的任务，
     presenter会根据需要调用该层的数据处理逻辑并在需要时将回调传入。
     这样model、presenter、view都只处理各自的任务，此种实现确实是单一职责最好的诠释。

     */
}

/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.wwh.androidarchitecture.google_sample_todo_mvp;

/**
 * 将基础的View层的操作放在BaseView里面
 * 可以将加载的loading，以及加载错误页面，加载失败页面等操作放在BaseView里面，这是每个View都会有的
 * @param <T>
 */
public interface BaseView<T> {

    /**
     * MVP中Presenter和View层是需要交互的
     * 这里通过setPresenter操作，我们也就可以获得相应的Presenter的实例在View层直接mPresenter.xxx()进行交互了。
     * 通过泛型进行操作
     */
    void setPresenter(T presenter);

}

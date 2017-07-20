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
 * This specifies the contract between the view and the presenter.
 * 通过一个协议类XXXContract来对View和Presenter的接口进行继承
 * 将基础的View层的操作放在BaseView里面,对基础的Presenter层的操作放在BasePresenter里面,减少后续代码的重复。
 * 一个协议类也将View和Presenter管理起来，方便操作。
 */
public interface TaskDetailContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMissingTask();

        void hideTitle();

        void showTitle(String title);

        void hideDescription();

        void showDescription(String description);

        void showCompletionStatus(boolean complete);

        void showEditTask(String taskId);

        void showTaskDeleted();

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void editTask();

        void deleteTask();

        void completeTask();

        void activateTask();
    }
}

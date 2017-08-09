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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Listens to user actions from the UI ({@link TaskDetailFragment}), retrieves the data and updates
 * the UI as required.
 * Presenter的真正实现类，在这里进行model层和view层的交互。
 * 继承自XXXXXXContract.Presenter类
 */
public class TaskDetailPresenter{//implements TaskDetailContract.Presenter {

    private final TasksRepository mTasksRepository;

    private final TaskDetailContract.View mTaskDetailView;

    @Nullable
    private String mTaskId;

    /**
     * 在构造函数中，初始化数据层和调用view的setPresenter方法传入自身实例
     * @param taskId
     * @param tasksRepository
     * @param taskDetailView
     */
    public TaskDetailPresenter(@Nullable String taskId,
                               @NonNull TasksRepository tasksRepository,
                               @NonNull TaskDetailContract.View taskDetailView) {
        mTaskId = taskId;
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null!");
        mTaskDetailView = checkNotNull(taskDetailView, "taskDetailView cannot be null!");

        // View层就可以获得相应的Presenter的实例
        mTaskDetailView.setPresenter(this);
    }

    /**
     * 在fragment view层调用的start方法，将在这里进行数据操作
     */
    @Override
    public void start() {
        openTask();
    }

    private void openTask() {
        // 判空处理
        if (Strings.isNullOrEmpty(mTaskId)) {
            //也可以调用View层的方法
            mTaskDetailView.showMissingTask();
            return;
        }

        // 更新view层的状态
        mTaskDetailView.setLoadingIndicator(true);
        // 获取该条Task数据
        mTasksRepository.getTask(mTaskId, new TasksDataSource.GetTaskCallback() {
            /**
             * 数据获取成功，更新view
             */
            @Override
            public void onTaskLoaded(Task task) {
                // The view may not be able to handle UI updates anymore
                // View已经被用户回退
                if (!mTaskDetailView.isActive()) {
                    return;
                }
                // 获取到task数据，并更新UI
                mTaskDetailView.setLoadingIndicator(false);
                if (null == task) {
                    mTaskDetailView.showMissingTask();
                } else {
                    showTask(task);
                }
            }

            /**
             * 数据获取失败，更新view
             */
            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                // 显示数据获取失败时的状态
                if (!mTaskDetailView.isActive()) {
                    return;
                }
                mTaskDetailView.showMissingTask();
            }
        });
    }

    @Override
    public void editTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTaskDetailView.showEditTask(mTaskId);
    }

    @Override
    public void deleteTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTasksRepository.deleteTask(mTaskId);
        mTaskDetailView.showTaskDeleted();
    }

    @Override
    public void completeTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTasksRepository.completeTask(mTaskId);
        mTaskDetailView.showTaskMarkedComplete();
    }

    @Override
    public void activateTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTasksRepository.activateTask(mTaskId);
        mTaskDetailView.showTaskMarkedActive();
    }

    private void showTask(@NonNull Task task) {
        String title = task.getTitle();
        String description = task.getDescription();

        if (Strings.isNullOrEmpty(title)) {
            mTaskDetailView.hideTitle();
        } else {
            mTaskDetailView.showTitle(title);
        }

        if (Strings.isNullOrEmpty(description)) {
            mTaskDetailView.hideDescription();
        } else {
            mTaskDetailView.showDescription(description);
        }
        mTaskDetailView.showCompletionStatus(task.isCompleted());
    }
}

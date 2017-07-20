/*
 * Copyright (C) 2015 The Android Open Source Project
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

import android.support.v4.app.Fragment;

/**
 * Main UI for the task detail screen.
 * Fragment是MVP中View的实现类，它不与Model层进行交互，只和presenter的实例进行交互。
 * Fragment是对Contract接口里面内部接口View的实现
 */
public class TaskDetailFragment extends Fragment {//implements TaskDetailContract.View {
//
//    @NonNull
//    private static final String ARGUMENT_TASK_ID = "TASK_ID";
//
//    @NonNull
//    private static final int REQUEST_EDIT_TASK = 1;
//
//    private TaskDetailContract.Presenter mPresenter;
//
//    private TextView mDetailTitle;
//
//    private TextView mDetailDescription;
//
//    private CheckBox mDetailCompleteStatus;
//
//    public static TaskDetailFragment newInstance(@Nullable String taskId) {
//        Bundle arguments = new Bundle();
//        arguments.putString(ARGUMENT_TASK_ID, taskId);
//        TaskDetailFragment fragment = new TaskDetailFragment();
//        fragment.setArguments(arguments);
//        return fragment;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mPresenter.start();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.taskdetail_frag, container, false);
//        setHasOptionsMenu(true);
//        mDetailTitle = (TextView) root.findViewById(R.id.task_detail_title);
//        mDetailDescription = (TextView) root.findViewById(R.id.task_detail_description);
//        mDetailCompleteStatus = (CheckBox) root.findViewById(R.id.task_detail_complete);
//
//        // Set up floating action button
//        FloatingActionButton fab =
//                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.editTask();
//            }
//        });
//
//        return root;
//    }
//
//    /**
//     * 当前View的Presenter实例再次获取，并与presenter进行交互
//     * @param presenter
//     */
//    @Override
//    public void setPresenter(@NonNull TaskDetailContract.Presenter presenter) {
//        mPresenter = checkNotNull(presenter);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_delete:
//                mPresenter.deleteTask();
//                return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.taskdetail_fragment_menu, menu);
//    }
//
//    @Override
//    public void setLoadingIndicator(boolean active) {
//        if (active) {
//            mDetailTitle.setText("");
//            mDetailDescription.setText(getString(R.string.loading));
//        }
//    }
//
//    @Override
//    public void hideDescription() {
//        mDetailDescription.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void hideTitle() {
//        mDetailTitle.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void showDescription(@NonNull String description) {
//        mDetailDescription.setVisibility(View.VISIBLE);
//        mDetailDescription.setText(description);
//    }
//
//    @Override
//    public void showCompletionStatus(final boolean complete) {
//        Preconditions.checkNotNull(mDetailCompleteStatus);
//
//        mDetailCompleteStatus.setChecked(complete);
//        mDetailCompleteStatus.setOnCheckedChangeListener(
//                new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            mPresenter.completeTask();
//                        } else {
//                            mPresenter.activateTask();
//                        }
//                    }
//                });
//    }
//
//    @Override
//    public void showEditTask(@NonNull String taskId) {
//        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
//        intent.putExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId);
//        startActivityForResult(intent, REQUEST_EDIT_TASK);
//    }
//
//    @Override
//    public void showTaskDeleted() {
//        getActivity().finish();
//    }
//
//    public void showTaskMarkedComplete() {
//        Snackbar.make(getView(), getString(R.string.task_marked_complete), Snackbar.LENGTH_LONG)
//                .show();
//    }
//
//    @Override
//    public void showTaskMarkedActive() {
//        Snackbar.make(getView(), getString(R.string.task_marked_active), Snackbar.LENGTH_LONG)
//                .show();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_EDIT_TASK) {
//            // If the task was edited successfully, go back to the list.
//            if (resultCode == Activity.RESULT_OK) {
//                getActivity().finish();
//            }
//        }
//    }
//
//    /**
//     * 实现的Contract.View类里面的方法
//     * @param title
//     */
//    @Override
//    public void showTitle(@NonNull String title) {
//        mDetailTitle.setVisibility(View.VISIBLE);
//        mDetailTitle.setText(title);
//    }
//
//    @Override
//    public void showMissingTask() {
//        mDetailTitle.setText("");
//        mDetailDescription.setText(getString(R.string.no_data));
//    }
//
//    @Override
//    public boolean isActive() {
//        return isAdded();
//    }

}

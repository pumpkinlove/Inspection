package com.miaxis.inspection.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.TaskAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.Task;
import com.miaxis.inspection.entity.comm.TaskTime;
import com.miaxis.inspection.model.local.greenDao.gen.TaskDao;
import com.miaxis.inspection.model.local.greenDao.gen.TaskTimeDao;
import com.miaxis.inspection.utils.DateUtil;
import com.miaxis.inspection.utils.RemindFrequencyType;
import com.miaxis.inspection.view.activity.ItemListActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskToDoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_task_to_do)
    RecyclerView rvTaskToDo;
    @BindView(R.id.srl_task_to_do)
    SwipeRefreshLayout srlTaskToDo;
    Unbinder unbinder;

    private List<Task> taskList;
    private TaskAdapter adapter;

    public TaskToDoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_to_do, container, false);
        unbinder = ButterKnife.bind(this, view);

        initData();
        initView();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("====", "task to do onResume");
        srlTaskToDo.setRefreshing(true);
        onRefresh();
    }

    private void initData() {
        taskList = new ArrayList<>();
        adapter = new TaskAdapter(taskList, getContext());

    }

    private void initView() {
        rvTaskToDo.setAdapter(adapter);
        rvTaskToDo.setLayoutManager(new LinearLayoutManager(getContext()));
        srlTaskToDo.setOnRefreshListener(this);
        initLoadMoreListener(adapter);
        adapter.setListener(new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(View view, int position) {
                Intent toItemListIntent = new Intent(getContext(), ItemListActivity.class);
                toItemListIntent.putExtra("task", taskList.get(position));
                startActivity(toItemListIntent);
            }
        });
    }

    @Override
    public void onRefresh() {
        Observable
                .create(new ObservableOnSubscribe<List<Task>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<Task>> e) throws Exception {
                        e.onNext(findToDoTask());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Task>>() {
                    @Override
                    public void accept(List<Task> toDoTaskList) throws Exception {
                        taskList = toDoTaskList;
                        adapter.setTaskList(taskList);
                        adapter.notifyDataSetChanged();
                        srlTaskToDo.setRefreshing(false);
                    }

                });
    }

    private void initLoadMoreListener(final RecyclerView.Adapter adapter) {

        rvTaskToDo.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<String> e) {
                            try {
                                Thread.sleep(500);
                                e.onNext("loadMore");
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
//                                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

    }

    private List<Task> findToDoTask() {
        TaskDao taskDao = Inspection_App.getInstance().getDaoSession().getTaskDao();
        List<Task> taskList = taskDao.loadAll();
        List<Task> toDoTaskList = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i ++) {
            Task task = taskList.get(i);
            switch (task.getCircleType()) {
                case RemindFrequencyType.NEVER:
                    TaskTime taskTime = task.getTaskTime().get(0);
                    String startTime = DateUtil.getTimeOfDay(taskTime.getTaskStartTime());
                    Date startTimeDate = DateUtil.strToDate(startTime, "yyyy-MM-dd HH:mm:ss");

                    String endTime = DateUtil.getTimeOfDay(taskTime.getTaskEndTime());
                    Date endTimeDate = DateUtil.strToDate(endTime, "yyyy-MM-dd HH:mm:ss");

                    Long cur = System.currentTimeMillis();

                    if (cur <= endTimeDate.getTime() && cur >= startTimeDate.getTime()) {
                        toDoTaskList.add(task);
                    }

                    break;
                case RemindFrequencyType.PER_DAY:
                    List<TaskTime> taskTimeList = task.getTaskTime();
                    for (int j = 0; j < taskTimeList.size(); j ++) {
                        TaskTime tt = taskTimeList.get(j);
                        String st = DateUtil.getTimeOfDay(tt.getTaskStartTime());
                        Date std = DateUtil.strToDate(st, "yyyy-MM-dd HH:mm:ss");

                        String et = DateUtil.getTimeOfDay(tt.getTaskEndTime());
                        Date etd = DateUtil.strToDate(et, "yyyy-MM-dd HH:mm:ss");

                        Long c = System.currentTimeMillis();

                        if (c <= etd.getTime() && c >= std.getTime()) {
                            toDoTaskList.add(task);
                            break;
                        }
                    }
                    break;
                case RemindFrequencyType.PER_WEEK:
                    toDoTaskList.add(task);
                    break;
                case RemindFrequencyType.PER_MONTH:
                    toDoTaskList.add(task);
                    break;
                case RemindFrequencyType.PER_SEASON:
                    toDoTaskList.add(task);
                    break;
                case RemindFrequencyType.PER_YEAR:
                    toDoTaskList.add(task);
                    break;

            }
        }
        return toDoTaskList;


    }


}

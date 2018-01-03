package com.miaxis.inspection.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.TaskAdapter;
import com.miaxis.inspection.entity.Task;

import java.util.ArrayList;
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

    private void initData() {
        taskList = new ArrayList<>();
        Task t = new Task();
        t.setName("网点日常检查");
        t.setBeginTime("开始时间：8:30");
        t.setEndTime("结束时间：15.30");
        Task t1 = new Task();
        t1.setName("网点日常检查2");
        t1.setBeginTime("开始时间：8:30");
        t1.setEndTime("结束时间：15.30");
        Task t2 = new Task();
        t2.setName("网点日常检查3");
        t2.setBeginTime("开始时间：8:30");
        t2.setEndTime("结束时间：15.30");
        Task t3 = new Task();
        t3.setName("网点日常检查");
        t3.setBeginTime("开始时间：8:30");
        t3.setEndTime("结束时间：15.30");
        Task t4 = new Task();
        t4.setName("网点日常检查2");
        t4.setBeginTime("开始时间：8:30");
        t4.setEndTime("结束时间：15.30");
        Task t5 = new Task();
        t5.setName("网点日常检查3");
        t5.setBeginTime("开始时间：8:30");
        t5.setEndTime("结束时间：15.30");
        taskList.add(t);
        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);
        taskList.add(t4);
        taskList.add(t5);
        adapter = new TaskAdapter(taskList, getContext());

    }

    private void initView() {
        rvTaskToDo.setAdapter(adapter);
        rvTaskToDo.setLayoutManager(new LinearLayoutManager(getContext()));
        srlTaskToDo.setOnRefreshListener(this);
        initLoadMoreListener(adapter);
    }

    @Override
    public void onRefresh() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) {
                try {
                    Thread.sleep(500);
                    e.onNext("onRefresh");
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
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
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
}

package com.miaxis.inspection.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.MainFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MyTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTaskFragment extends Fragment {

    @BindView(R.id.tl_task)
    TabLayout tlTask;
    @BindView(R.id.vp_task)
    ViewPager vpTask;
    Unbinder unbinder;

    private static final String[] TITLES = {"待执行", "执行中", "已完成"};
    private MainFragmentAdapter adapter;

    public MyTaskFragment() {
        // Required empty public constructor
    }

    public static MyTaskFragment newInstance() {
        return new MyTaskFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_task, container, false);
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

    private void initData(){
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new TaskToDoFragment());
        fragmentList.add(new TaskDoingFragment());
        fragmentList.add(new TaskDoneFragment());

        adapter = new MainFragmentAdapter(getChildFragmentManager(), fragmentList);

    }

    private void initView() {
        vpTask.setAdapter(adapter);
        vpTask.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlTask));
        tlTask.setupWithViewPager(vpTask, true);
        vpTask.setOffscreenPageLimit(20);

        initTabLayout();
        vpTask.setCurrentItem(0);
        tlTask.getTabAt(0).select();
    }

    private void initTabLayout() {

        for (int i=0; i < tlTask.getTabCount(); i++) {
            TabLayout.Tab tab = tlTask.getTabAt(i);
            if (tab != null) {
                tab.setText(TITLES[i]);
            }
        }

    }
}

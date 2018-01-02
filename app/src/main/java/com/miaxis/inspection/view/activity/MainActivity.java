package com.miaxis.inspection.view.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.MainFragmentAdapter;
import com.miaxis.inspection.view.fragment.HomePageFragment;
import com.miaxis.inspection.view.fragment.MyTaskFragment;
import com.miaxis.inspection.view.fragment.PersonalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements HomePageFragment.OnFragmentInteractionListener,
        PersonalFragment.OnFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.tl_main)
    TabLayout tlMain;

    private MainFragmentAdapter adapter;

    private List<Drawable> normalIconList;
    private List<Drawable> pressedIconList;

    public static final String[] TITLES = {"首页", "我的任务", "个人中心"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    @Override
    protected void initData() {
        normalIconList = new ArrayList<>();
        normalIconList.add(getResources().getDrawable(R.drawable.tab1_n));
        normalIconList.add(getResources().getDrawable(R.drawable.tab2_n));
        normalIconList.add(getResources().getDrawable(R.drawable.tab3_n));

        pressedIconList = new ArrayList<>();
        pressedIconList.add(getResources().getDrawable(R.drawable.tab1_p));
        pressedIconList.add(getResources().getDrawable(R.drawable.tab2_p));
        pressedIconList.add(getResources().getDrawable(R.drawable.tab3_p));

        List<Fragment> fragmentList = new ArrayList<>();
        HomePageFragment homePageFragment = HomePageFragment.newInstance("", "");
        fragmentList.add(homePageFragment);
        MyTaskFragment myTaskFragment = MyTaskFragment.newInstance();
        fragmentList.add(myTaskFragment);
        PersonalFragment personalFragment = PersonalFragment.newInstance("", "");
        fragmentList.add(personalFragment);

        adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList);
    }

    @Override
    protected void initView() {
        vpMain.setAdapter(adapter);
        vpMain.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlMain));
        tlMain.setupWithViewPager(vpMain, true);
        vpMain.setOffscreenPageLimit(20);

        initTabLayout();
        vpMain.setCurrentItem(1);
        tlMain.getTabAt(1).select();
    }

    private void initTabLayout() {

        for (int i=0; i < tlMain.getTabCount(); i++) {
            TabLayout.Tab tab = tlMain.getTabAt(i);
            if (tab != null) {
                tab.setIcon(normalIconList.get(i));
            }
        }

        tlMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                tab.setIcon(pressedIconList.get(position));
                toolbar.setTitle(TITLES[position]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(normalIconList.get(tab.getPosition()));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

package com.example.administrator.tomato;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private PagerSlidingTabStrip tabs;//滑动导航栏
    private ViewPager pager; //页面切换
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar); //自定义标题栏
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
    }

    public class MyAdapter extends FragmentPagerAdapter {

        private String[] titles = { getString(R.string.title_task),
//                "分析",
                "统计"
                };

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:{
                    return TaskFragment.newInstance(position);
                }
                case 1:{
                    return StatisticsFragment.newInstance(position);
                }

            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
        //charSequence是一个接口，表示char值的一个可读序列
    }
}

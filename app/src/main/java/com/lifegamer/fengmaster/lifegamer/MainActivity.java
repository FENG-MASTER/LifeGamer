package com.lifegamer.fengmaster.lifegamer;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.lifegamer.fengmaster.lifegamer.fragment.AboutFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.SettingFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.achievement.AchievementFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.reward.RewardFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.HeroInfoFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.item.ItemFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.skill.SkillFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.StatisticsFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.task.TaskFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.TopInfoFragment;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nv_main)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;


    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    TopInfoFragment topInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initInfo();

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_view_toc).color(Color.WHITE).sizeDp(30));
        //点击图标弹出导航栏
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        toolbar.setSubtitle(R.string.task);
        initNav();
        //showInfo();
        changeToFragment(0);
        ViewUtil.addCoopView(coordinatorLayout);
    }

    private void initNav(){
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isCheckable()){
            item.setChecked(!item.isChecked());
        }

        switch (item.getItemId()){
            case R.id.menu_setting:
                return true;
            case R.id.menu_show_info:
                topInfoFragment.toggle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getItemId()){
            case R.id.nav_task:
                //任务
                changeToFragment(0);
                break;

            case R.id.nav_reward:
                //奖励
                changeToFragment(1);
                break;

            case R.id.nav_item:
                //物品仓库
                changeToFragment(2);
                break;

            case R.id.nav_skill:
                //能力
                changeToFragment(3);
                break;


            case R.id.nav_achievement:
                //成就
                changeToFragment(4);
                break;

            case R.id.nav_statistics:
                //统计
                changeToFragment(5);
                break;


            case R.id.nav_hero:
                //英雄信息
                changeToFragment(6);
                break;
            case R.id.nav_about:
                //关于
                changeToFragment(7);
                break;

            case R.id.nav_setting:
                changeToFragment(8);
                break;
            case R.id.nav_exit:
                //退出
                finish();
                break;
            default:


        }

        return true;
    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,fragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewUtil.addCoopView(null);
    }

    private void initInfo(){
        topInfoFragment=new TopInfoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_top,topInfoFragment).commit();
    }


    private void changeToFragment(int i){

        switch (i){
            case 0:
                changeFragment(new TaskFragment());
                break;
            case 1:
                changeFragment(new RewardFragment());
                break;
            case 2:
                changeFragment(new ItemFragment());
                break;
            case 3:
                changeFragment(new SkillFragment());
                break;
            case 4:
                changeFragment(new AchievementFragment());
                break;
            case 5:
                changeFragment(new StatisticsFragment());
                break;
            case 6:
                changeFragment(new HeroInfoFragment());
                break;
            case 7:
                changeFragment(new AboutFragment());
                break;
            case 8:
                changeFragment(new SettingFragment());
                break;
            default:

        }
        toolbar.setSubtitle(getResources().getStringArray(R.array.main_sp)[i]);

    }


}

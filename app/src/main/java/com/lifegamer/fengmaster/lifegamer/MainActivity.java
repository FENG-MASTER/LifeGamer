package com.lifegamer.fengmaster.lifegamer;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.lifegamer.fengmaster.lifegamer.fragment.AchievementFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.RewardFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.HeroInfoFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.ItemFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.skill.SkillFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.StatisticsFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.TaskFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.TopInfoFragment;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;

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
    @BindView(R.id.sp_toolbar)
    Spinner toolbarSpinner;

    TopInfoFragment topInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initInfo();

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_input_add);
        toolbar.setSubtitle("任务");
        initNav();
        //showInfo();

        ViewUtil.setCoopView(coordinatorLayout);
    }

    private void initNav(){
        navigationView.setNavigationItemSelectedListener(this);
        toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeToFragment(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        switch (item.getItemId()){
            case R.id.nav_task:
                changeToFragment(0);
                break;

            case R.id.nav_reward:
                changeToFragment(1);
                break;

            case R.id.nav_item:
                changeToFragment(2);
                break;

            case R.id.nav_skill:
                changeToFragment(3);
                break;


            case R.id.nav_achievement:
                changeToFragment(4);
                break;

            case R.id.nav_statistics:
                changeToFragment(5);
                break;


            case R.id.nav_hero:
                changeToFragment(6);
                break;
            default:


        }
        drawerLayout.closeDrawers();
        return true;
    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,fragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewUtil.setCoopView(null);
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
            default:

        }
        toolbar.setSubtitle(getResources().getStringArray(R.array.main_sp)[i]);
        toolbarSpinner.setSelection(i,true);

    }


}

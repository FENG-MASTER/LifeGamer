package com.lifegamer.fengmaster.lifegamer;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.lifegamer.fengmaster.lifegamer.fragment.HeroInfoFragment;
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

    Fragment topInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initInfo();

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_input_add);
        toolbar.setSubtitle("成就");
        initNav();
        showInfo();

        ViewUtil.setCoopView(coordinatorLayout);
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
                if (item.isChecked()){
                    showInfo();
                }else {
                    hideInfo();
                }
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
            case R.id.nav_achievement:
                changeFragment(new TopInfoFragment());
                break;
            case R.id.nav_hero:
                changeFragment(new HeroInfoFragment());
                break;
            default:


        }
        drawerLayout.closeDrawers();
        return true;
    }

    private void changeFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.fl_content,fragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewUtil.setCoopView(null);
    }

    private void initInfo(){
        topInfoFragment=new TopInfoFragment();
        getFragmentManager().beginTransaction().replace(R.id.fl_top,topInfoFragment).commit();
    }

    private void showInfo(){
        getFragmentManager().beginTransaction().setCustomAnimations(R.animator.animator_fragment_show,R.animator.animator_fragment_hiden).show(topInfoFragment).commit();
    }

    private void hideInfo(){
        getFragmentManager().beginTransaction().setCustomAnimations(R.animator.animator_fragment_show,R.animator.animator_fragment_hiden).hide(topInfoFragment).commit();
    }
}

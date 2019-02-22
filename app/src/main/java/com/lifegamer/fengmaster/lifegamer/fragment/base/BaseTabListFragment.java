package com.lifegamer.fengmaster.lifegamer.fragment.base;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.BaseViewPagerFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.AbsBaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.AllTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.BaseTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.TodayTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.util.PreferenceUtil;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.umeng.commonsdk.debug.E;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基础fragment,自带一个tab,并且里面的所有fragment全是列表形式
 * Created by qianzise on 2017/10/10.
 */

public abstract class BaseTabListFragment extends Fragment implements OnItemSelectListener {

    @BindView(R.id.tl_fragment_base_list)
    TabLayout tabLayout;
    @BindView(R.id.vp_fragment_base_list)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton actionButton;
    @BindView(R.id.coordinatorLayout_fragment)
    CoordinatorLayout coordinatorLayout;

    protected List<BaseFragment> fragments = new ArrayList<>();

    protected BaseViewPagerFragmentAdapter fragmentAdapter;

    private List<BaseRecyclerViewAdapter> hiddenAdapters=new ArrayList<>();

    public BaseTabListFragment() {
        // Required empty public constructor
        createFragments();
    }

    protected void createFragments(){
        fragments.clear();
        Class[] classes = getAdapterClasses();

        for (Class aClass : classes) {
            if (PreferenceUtil.checkIfShow(aClass.getSimpleName())) {
                try {
                    Constructor constructor = aClass.getConstructor(null);
                    BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) constructor.newInstance(null);
                    if (adapter.hasData()) {
                        adapter.addItemSelectListener(this);
                        addAdapter(adapter);
                    }else {
                        hiddenAdapters.add(adapter);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ViewUtil.removeCoopView(coordinatorLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewUtil.addCoopView(coordinatorLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        if (container.getTag()!=null){
//            //防止多次调用出现白屏bug
//            container.removeView((View) container.getTag());
//            View view = (View) container.getTag();
//            container.setTag(null);
//            return view;
//        }

        View inflate = inflater.inflate(R.layout.fragment_base_tab_list, container, false);
        ButterKnife.bind(this, inflate);

        fragmentAdapter = new BaseViewPagerFragmentAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        actionButton.setOnClickListener(view -> onActionButtonClick());
        actionButton.setOnLongClickListener(this::onActionButtonLongClick);
        container.setTag(inflate);
        return inflate;
    }

    public void addAdapter(AbsBaseRecyclerViewAdapter adapter) {
        fragments.add(new BaseRecyclerViewFragment().setAdapter(adapter));
        if (fragmentAdapter!=null){
            fragmentAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 左下角按钮点击回调
     */
    public abstract void onActionButtonClick();

    /**
     * 左下角按钮长按回调
     */
    public boolean onActionButtonLongClick(View view){
        return false;
    }

    public abstract Class[] getAdapterClasses();


    /**
     * 通知tab可能有变化,比如新增了tab
     */
    protected void notifyTabChange(){
        List<BaseRecyclerViewAdapter> remove=new ArrayList<>();
        for (BaseRecyclerViewAdapter adapter : hiddenAdapters) {
            if (adapter.hasData()) {
                remove.add(adapter);
                adapter.addItemSelectListener(this);
                addAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }

        if (!remove.isEmpty()){
            hiddenAdapters.removeAll(remove);
            fragmentAdapter.notifyDataSetChanged();
        }

    }

}

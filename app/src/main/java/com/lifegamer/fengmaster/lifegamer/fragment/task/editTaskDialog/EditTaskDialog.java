package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.BaseViewPagerFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditTaskBinding;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseFragment;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/16.
 */

public class EditTaskDialog extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.vp_dialog_edit_task_content)
    ViewPager viewPager;
    @BindView(R.id.tl_dialog_edit_task)
    TabLayout tabLayout;

    private Task task;

    private List<EditTaskDialog.SaveableFragment> fragmentList;

    public EditTaskDialog() {
    }

    public void setTask(Task task){
        this.task=task;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogEditTaskBinding binding=DialogEditTaskBinding.inflate(inflater);
        ButterKnife.bind(this,binding.getRoot());
        binding.setTask(task);

        fragmentList=new ArrayList<>();
        fragmentList.add(new EditTaskExtraFragment().setTask(task));
        fragmentList.add(new EditTaskTimeFragment());
        fragmentList.add(new EditTaskRewardFragment());

        //这里getChildFragmentManager 否则会报找不到id错误
        viewPager.setAdapter(new BaseViewPagerFragmentAdapter(getChildFragmentManager(),fragmentList));
        tabLayout.setupWithViewPager(viewPager);

        binding.btDialogEditTaskNo.setOnClickListener(this);
        binding.btDialogEditTaskOk.setOnClickListener(this);

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_dialog_edit_task_ok:
                //确认

                //全部子fragment保存
                Stream.of(fragmentList).forEach(SaveableFragment::save);
                dismiss();

                break;
            case R.id.bt_dialog_edit_task_no:
                //取消
                dismiss();

        }
    }

    public static abstract class SaveableFragment extends BaseFragment{
        abstract void save();
    }
}

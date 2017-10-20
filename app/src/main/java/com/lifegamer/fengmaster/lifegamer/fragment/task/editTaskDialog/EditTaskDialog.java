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
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.BaseViewPagerFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.command.command.task.UpdateTaskCommand;
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



    private Task task;

    private List<EditTaskDialog.SaveableFragment> fragmentList;

    private DialogEditTaskBinding binding;

    public EditTaskDialog() {
        setCancelable(false);
    }

    public void setTask(Task task){
        this.task=task;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DialogEditTaskBinding.inflate(inflater);

        binding.setTask(task);

        fragmentList=new ArrayList<>();
        fragmentList.add(new EditTaskExtraFragment().setTask(task));
        fragmentList.add(new EditTaskTimeFragment().setTask(task));
        fragmentList.add(new EditTaskRewardFragment().setTask(task));
        fragmentList.add(new EditTaskPunishFragment());

        //这里getChildFragmentManager 否则会报找不到id错误
        binding.vpDialogEditTaskContent.setAdapter(new BaseViewPagerFragmentAdapter(getChildFragmentManager(),fragmentList));
        binding.tlDialogEditTask.setupWithViewPager(binding.vpDialogEditTaskContent);

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
                save();

                dismiss();

                break;
            case R.id.bt_dialog_edit_task_no:
                //取消
                dismiss();

        }
    }

    private void save(){
        task.setName(binding.etDialogEditTaskName.getText().toString());
        task.setDesc(binding.etDialogEditTaskDesc.getText().toString());

        //最终更新命令
        Game.getInstance().getCommandManager().executeCommand(new UpdateTaskCommand(task));
    }

    public static abstract class SaveableFragment extends BaseFragment{
        abstract void save();
    }
}

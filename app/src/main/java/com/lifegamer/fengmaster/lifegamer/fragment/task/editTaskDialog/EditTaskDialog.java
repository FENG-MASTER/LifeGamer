package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.BaseViewPagerFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.command.command.task.AddTaskCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.task.UpdateTaskCommand;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditTaskBinding;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseDialogFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog.event.AddButtonClick;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.wight.AvatarSelectDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/16.
 */

public class EditTaskDialog extends BaseDialogFragment implements View.OnClickListener {


    /**
     * 正在编辑的任务对象
     */
    private Task task = new Task();

    /**
     * 图标
     */
    private IAvatarManager.Avatar taskAvatar;

    private List<EditTaskDialog.SaveableFragment> fragmentList;

    private DialogEditTaskBinding binding;

    public EditTaskDialog() {
        setCancelable(false);
    }


    public void setTask(Task task) {
        this.task = task;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogEditTaskBinding.inflate(inflater);

        binding.setTask(task);

        fragmentList = new ArrayList<>();
        fragmentList.add(new EditTaskExtraFragment().setTask(task));
        fragmentList.add(new EditTaskTimeFragment().setTask(task));
//        fragmentList.add(new EditTaskRewardPunishFragment().setTask(task));
        fragmentList.add(new EditTaskRewardPunishFragment().setTask(task));

        //这里getChildFragmentManager 否则会报找不到id错误
        binding.vpDialogEditTaskContent.setAdapter(new BaseViewPagerFragmentAdapter(getChildFragmentManager(), fragmentList));
        binding.tlDialogEditTask.setupWithViewPager(binding.vpDialogEditTaskContent);
        binding.vpDialogEditTaskContent.setOffscreenPageLimit(10);

        binding.btDialogEditTaskNo.setOnClickListener(this);
        binding.btDialogEditTaskOk.setOnClickListener(this);
        binding.btDialogEditTaskAdd.setOnClickListener(this);

        binding.sivDialogEditTaskIcon.setOnClickListener(this);

        binding.tlDialogEditTask.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==2){
                    openTriggerEditFragment(true);
                }else {
                    openTriggerEditFragment(false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_dialog_edit_task_ok:
                //确认

                //全部子fragment保存
                if (Stream.of(fragmentList).allMatch(SaveableFragment::save)) {
                    save();
                }

                dismiss();
                break;
            case R.id.bt_dialog_edit_task_no:
                //取消
                dismiss();
                break;

            case R.id.bt_dialog_edit_task_add:
                //点击了新增按钮,意味新增触发器
                EventBus.getDefault().post(new AddButtonClick());
                break;
            case R.id.siv_dialog_edit_task_icon:
                //选择图标
                AvatarSelectDialog dialog = new AvatarSelectDialog();
                dialog.addItemSelectListener(avatar -> {
                    taskAvatar = avatar;
                    binding.sivDialogEditTaskIcon.setImageDrawable(taskAvatar.getIcon());
                });
                dialog.show(getChildFragmentManager(), "avatarSelect");

                break;
            default:

        }
    }

    private void save() {
        task.setName(binding.etDialogEditTaskName.getText().toString());
        task.setDesc(binding.etDialogEditTaskDesc.getText().toString());
        task.setType(binding.etDialogEditTaskType.getText().toString());

        if (taskAvatar != null) {
            task.setIcon(taskAvatar.toString());
        }


        if (task.getId() != 0) {
            //更新
            //最终更新命令
            Game.getInstance().getCommandManager().executeCommand(new UpdateTaskCommand(task));

        } else {
            //新建

            Game.getInstance().getCommandManager().executeCommand(new AddTaskCommand(task));

        }


    }

    private void openTriggerEditFragment(boolean flag){
        if (flag){
            binding.btDialogEditTaskAdd.setVisibility(View.VISIBLE);
        }else {
            binding.btDialogEditTaskAdd.setVisibility(View.GONE);
        }
    }

    public static abstract class SaveableFragment extends BaseFragment {
        abstract boolean save();
    }
}

package com.lifegamer.fengmaster.lifegamer.fragment.task.lotteryDraw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 任务抽奖池对话框
 * Created by FengMaster on 19/02/21.
 */
public class LotteryDrawTaskDialog extends BaseDialogFragment {

    /**
     * 新增任务到奖池按钮
     */
    @BindView(R.id.bt_dialog_lottery_draw_task_add)
    ImageButton btAddTask;

    /**
     * 开始抽奖按钮
     */
    @BindView(R.id.bt_dialog_lottery_draw_task_start)
    ImageButton btStartLottery;

    /**
     * 任务奖池
     */
    @BindView(R.id.rv_dialog_lottery_draw_task_list)
    RecyclerView rvTaskList;

    private LotteryDrawTaskAdapter lotteryDrawTaskAdapter;

    public LotteryDrawTaskDialog() {

        setCancelable(false);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_lottery_draw_task,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }


    private void initView(){
        lotteryDrawTaskAdapter = new LotteryDrawTaskAdapter();
        rvTaskList.setAdapter(lotteryDrawTaskAdapter);
        rvTaskList.setItemAnimator(new DefaultItemAnimator());
        rvTaskList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }


    @OnClick(R.id.bt_dialog_lottery_draw_task_cancel)
    public void cancel(){
        //关闭对话框
       dismiss();
    }
}

package com.lifegamer.fengmaster.lifegamer.fragment.task.lotteryDraw;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseDialogFragment;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SearchAndSelectDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    /**
     * 开始抽奖按钮
     */
    @OnClick(R.id.bt_dialog_lottery_draw_task_start)
    public void lotteryDrawTaskNow(){
        List<Task> taskList = lotteryDrawTaskAdapter.getTaskList();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.base_dialog_single_input, null);
        EditText editText=view.findViewById(R.id.et_base_dialog_single_input);
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setCancelable(false).setTitle("输入要抽的数量").setView(view).setPositiveButton("抽抽抽!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int num=-1;

                try{
                    num=Integer.valueOf(editText.getText().toString());
                }catch (Exception e ){
                    ViewUtil.showToast("请输入数字");
                }

                if (num>0&&num<taskList.size()){
                    List<Integer> candysList=new ArrayList<>();
                    //开始抽奖
                    Random random=new Random();
                    for (int i = 0; i < num; i++) {
                        int r=random.nextInt(taskList.size());
                        candysList.add(r);

                    }

                    ViewUtil.showToast("抽中"+candysList.get(0));


                    dialog.dismiss();
                }else {
                    ViewUtil.showToast("您输入的数字必须小于奖池任务数");

                }




            }
        }).show();




    }


    /**
     * 点击新增抽奖池任务按钮
     */
    @OnClick(R.id.bt_dialog_lottery_draw_task_add)
    public void addLotteryDrawTask(){
        List<Task> allLotteryDrawTask = Stream.of(Game.getInstance().getTaskManager().getAllTask()).
                filter(value -> value.getRepeatType()==Task.REP_LOTTERY_DRAW).filterNot(value -> Stream.of(lotteryDrawTaskAdapter.getTaskList()).anyMatch(t1 -> t1.getId()==value.getId())).
                collect(Collectors.toList());


        if (allLotteryDrawTask == null || allLotteryDrawTask.isEmpty()) {
            //没有可用任务
            ViewUtil.showToast("没有任务可供选择");
            return;
        }

        SearchAndSelectDialog<Task> dialog = new SearchAndSelectDialog<Task>();
        dialog.setItemList(allLotteryDrawTask).setItemKeyFunction(Task::getName);
        dialog.addItemSelectListener(selectTasks -> {
            for (Task s : selectTasks) {
                lotteryDrawTaskAdapter.addTask(s);
            }
            lotteryDrawTaskAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show(getFragmentManager(), "select");


    }


}

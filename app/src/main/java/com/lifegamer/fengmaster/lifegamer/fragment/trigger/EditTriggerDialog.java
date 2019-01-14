package com.lifegamer.fengmaster.lifegamer.fragment.trigger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.ItemSelectObservable;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseDialogFragment;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.AbsTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFailTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishTimesTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.lifegamer.fengmaster.lifegamer.wight.MyItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * 触发器编辑页面
 * Created by FengMaster on 19/01/11.
 */
public class EditTriggerDialog extends BaseDialogFragment implements ItemSelectObservable<Trigger> {

    /**
     * 当前编辑的触发器对象
     */
    private Trigger trigger;

    private OnItemSelectListener<Trigger> listener;

    @BindView(R.id.rv_dialog_edit_trigger_list)
    public RecyclerView recyclerView;

    /**
     * 触发器触发条件
     */
    @BindView(R.id.sp_dialog_edit_trigger_type)
    public Spinner conditionSpinner;

    /**
     * 触发器参数
     */
    @BindView(R.id.et_dialog_edit_trigger_parms)
    public EditText parmsEditText;

    @BindView(R.id.til_dialog_edit_trigger_parms)
    public TextInputLayout parmsLayout;

    private EditTriggerListAdapter editTriggerListAdapter;

    private static final String[] condtionList = {
            TaskFinishTriggerCondition.class.getName(),
            TaskFailTriggerCondition.class.getName(),
            TaskFinishTimesTriggerCondition.class.getName()};


    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_edit_trigger, container, false);
        ButterKnife.bind(this, inflate);
        initView();
        return inflate;
    }

    private void initView() {
        editTriggerListAdapter= new EditTriggerListAdapter(trigger);
        recyclerView.setAdapter(editTriggerListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new MyItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    /**
     * 触发条件改变
     *
     * @param view
     * @param index
     */
    @OnItemSelected(R.id.sp_dialog_edit_trigger_type)
    public void conditionSelect(View view, int index) {
        String[] parms = getContext().getResources().getStringArray(R.array.trigger_type_parm);
        if (index >= 0 && index <= parms.length - 1) {
            if (parms[index] != null && !parms[index].isEmpty()) {
                //有的条件有参数,需要显示参数设置框
                parmsLayout.setHint(parms[index]);
                parmsLayout.setVisibility(View.VISIBLE);
            } else {
                parmsLayout.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.bt_dialog_edit_trigger_ok)
    public void submit(View view) {
        //提交修改触发器
        if (listener!=null){
            trigger.getTriggerInfo().setAchievements(editTriggerListAdapter.getAchievements());
            trigger.getTriggerInfo().setSkills(editTriggerListAdapter.getSkills());
            trigger.getTriggerInfo().setItems(editTriggerListAdapter.getItems());
            listener.onItemSelect(trigger);
        }
    }

    @OnClick(R.id.bt_dialog_edit_trigger_no)
    public void cancel() {
        //取消
        dismiss();
    }


    @Override
    public void addItemSelectListener(OnItemSelectListener<Trigger> listener) {
        this.listener=listener;
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<Trigger> listener) {

    }
}

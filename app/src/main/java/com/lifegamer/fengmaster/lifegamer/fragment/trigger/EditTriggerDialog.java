package com.lifegamer.fengmaster.lifegamer.fragment.trigger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.ItemSelectObservable;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseDialogFragment;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFailTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishTimesTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.util.ArrayUtil;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.lifegamer.fengmaster.lifegamer.wight.MyItemDecoration;
import com.lifegamer.fengmaster.lifegamer.wight.SearchAndSelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * 触发器编辑页面
 * Created by FengMaster on 19/01/11.
 */
public class EditTriggerDialog extends BaseDialogFragment implements ItemSelectObservable<TriggerInfo>, OnItemSelectListener<SelectItem> {

    /**
     * 当前编辑的触发器对象
     */
    private TriggerInfo triggerInfo;

    private OnItemSelectListener<TriggerInfo> listener;

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

    @BindView(R.id.til_dialog_edit_trigger_lp)
    public TextInputLayout lpLayout;

    @BindView(R.id.et_dialog_edit_trigger_lp)
    public EditText lpEditText;

    private EditTriggerListAdapter editTriggerListAdapter;

    /**
     * 获取的金币数
     */
    private int earnLp;

    private static final String[] condtionList = {
            TaskFinishTriggerCondition.class.getName(),
            TaskFailTriggerCondition.class.getName(),
            TaskFinishTimesTriggerCondition.class.getName()};

    private static final int[] condtionDescResList = {
            R.string.task_finish_once_get,
            R.string.task_lose_once_get,
            R.string.task_finish_times_get};


    public void setTriggerInfo(TriggerInfo triggerInfo) {
        this.triggerInfo = triggerInfo;
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
        initLp(triggerInfo.getEarnLP());
        setCondtion(triggerInfo.getTriggerCondition());
        setParms(triggerInfo.getTriggerParameter());
        editTriggerListAdapter = new EditTriggerListAdapter(triggerInfo);
        recyclerView.setAdapter(editTriggerListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new MyItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    private void setParms(String parms){
        if (parms!=null){
            parmsEditText.setText(parms);
        }
    }

    private void setCondtion(String condtion){
        if (condtion!=null){
            //不是新建的

            int i = ArrayUtil.getPostion(condtionList, condtion);
            conditionSpinner.setSelection(i);
        }
    }

    private void initLp(int lp) {
        if (lp > 0) {
            lpLayout.setVisibility(View.VISIBLE);
            lpEditText.setText(String.valueOf(lp));
        } else {
            lpLayout.setVisibility(View.GONE);
        }

        earnLp = lp;
        lpEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                earnLp = Integer.valueOf(s.toString());
            }
        });

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
        triggerInfo.setTriggerParameter(parmsEditText.getText().toString());
        triggerInfo.setTriggerCondition(condtionList[conditionSpinner.getSelectedItemPosition()]);
        triggerInfo.setTriggerConditionDesc(getString(condtionDescResList[conditionSpinner.getSelectedItemPosition()]));
        triggerInfo.setAchievements(editTriggerListAdapter.getAchievements());
        triggerInfo.setSkills(editTriggerListAdapter.getSkills());
        triggerInfo.setItems(editTriggerListAdapter.getItems());
        triggerInfo.setEarnLP(earnLp);
        if (listener != null) {
            listener.onItemSelect(triggerInfo);
        }
        dismiss();
    }

    @OnClick(R.id.bt_dialog_edit_trigger_no)
    public void cancel() {
        //取消
        dismiss();
    }

    @OnClick(R.id.bt_dialog_edit_trigger_add)
    public void add() {
        //增加奖励
        SelectDialog dialog = new SelectDialog();
        List<SelectItem> itemList = new ArrayList<>();
        itemList.add(SelectItem.SKILL);
        itemList.add(SelectItem.ACHIEVEMENT);
        itemList.add(SelectItem.ITEM);
        itemList.add(SelectItem.LP);
        dialog.setItems(itemList);
        dialog.addItemSelectListener(this);
        dialog.show(getFragmentManager(), "add");
    }


    @Override
    public void onItemSelect(SelectItem selectItem) {
        switch (selectItem.getId()) {
            case SelectItem.SKILL_ID:
                //新增技能奖励
                addSkill();
                break;
            case SelectItem.ACHIEVEMENT_ID:
                //新增成就奖励
                addAchievement();
                break;
            case SelectItem.ITEM_ID:
                addItem();
                break;
            case SelectItem.LP_ID:
                lpLayout.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void addItem() {
        if (Game.getInstance().getRewardManager().getAllAvailableRewardItem() == null) {
            //没有奖励可选

            ViewUtil.showToast("没有奖励可供选择");
            return;
        }

        List<RewardItem> rewardItems = Game.getInstance().getRewardManager().getAllAvailableRewardItem();


        if (rewardItems == null || rewardItems.isEmpty()) {
            //没有奖励可选

            ViewUtil.showToast("没有奖励可供选择");
            return;
        }

        //弹出选择框

        SearchAndSelectDialog<RewardItem> dialog = new SearchAndSelectDialog<RewardItem>();
        dialog.setItemList(rewardItems).setItemKeyFunction(rewardItem -> rewardItem.getName());
        dialog.addItemSelectListener(rewardItems1 -> {
            for (RewardItem rewardItem : rewardItems1) {
                RandomItemReward reward = new RandomItemReward(rewardItem.getId(), 1, 1000);
                editTriggerListAdapter.addItem(reward);
                editTriggerListAdapter.notifyDataSetChanged();
            }
            dialog.dismiss();
        });

        dialog.show(getFragmentManager(), "select");
    }

    private void addAchievement() {
        if (Game.getInstance().getAchievementManager().getAllNoGetAchievment() == null) {
            //没有成就可选

            ViewUtil.showToast("没有成就可供选择");
            return;
        }

        List<Achievement> allAchievements =
                Stream.of(Game.getInstance().getAchievementManager().getAllNoGetAchievment()).
                        //排除已经添加了的成就
                                filterNot(value -> editTriggerListAdapter.getAchievements().contains(new AchievementReward(value.getId(), 0))).
                        collect(Collectors.toList());


        if (allAchievements == null || allAchievements.isEmpty()) {
            //没有成就可选

            ViewUtil.showToast("没有成就可供选择");
            return;
        }

        //弹出选择框
        SearchAndSelectDialog<Achievement> dialog = new SearchAndSelectDialog<Achievement>();
        dialog.setItemList(allAchievements).setItemKeyFunction(Achievement::getName);
        dialog.addItemSelectListener(selectAchievements -> {
            for (Achievement a : selectAchievements) {
                AchievementReward achievementReward = new AchievementReward(a.getId(), 1000);
                editTriggerListAdapter.addAchievement(achievementReward);
                editTriggerListAdapter.notifyDataSetChanged();
            }
            dialog.dismiss();
        });

        dialog.show(getFragmentManager(), "select");
    }

    private void addSkill() {
        //排除已经添加了的能力
        List<Skill> allSkill = Stream.of(Game.getInstance().getSkillManager().getAllSkill()).
                filterNot(value -> editTriggerListAdapter.getSkills().containsKey(value.getId())).
                collect(Collectors.toList());


        if (allSkill == null || allSkill.isEmpty()) {
            //没有可用能力
            ViewUtil.showToast("没有能力可供选择");
            return;
        }

        SearchAndSelectDialog<Skill> dialog = new SearchAndSelectDialog<Skill>();
        dialog.setItemList(allSkill).setItemKeyFunction(Skill::getName);
        dialog.addItemSelectListener(selectSkills -> {
            for (Skill s : selectSkills) {
                editTriggerListAdapter.addSkill(s.getId(), 0);
            }
            editTriggerListAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show(getFragmentManager(), "select");


    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<TriggerInfo> listener) {
        this.listener = listener;

    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<TriggerInfo> listener) {

    }
}

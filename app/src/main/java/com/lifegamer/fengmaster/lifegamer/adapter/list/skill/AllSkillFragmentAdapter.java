package com.lifegamer.fengmaster.lifegamer.adapter.list.skill;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;

import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.databinding.ItemAllSkillBinding;
import com.lifegamer.fengmaster.lifegamer.event.skill.DelSkillEvent;
import com.lifegamer.fengmaster.lifegamer.event.skill.NewSkillEvent;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/10.
 * <p>
 * 显示所有技能的适配器
 */

public class AllSkillFragmentAdapter extends BaseSkillFragmentAdapter {


    @Override
    public String getName() {
        return "全部";
    }

    @Override
    public void updateList() {
        showSkillList=Game.getInstance().getSkillManager().getAllSkill();
    }
}

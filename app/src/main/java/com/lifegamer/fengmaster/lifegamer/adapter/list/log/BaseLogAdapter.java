package com.lifegamer.fengmaster.lifegamer.adapter.list.log;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.model.Log;

/**
 *
 * 基础日志列表适配器
 * Created by FengMaster on 19/01/28.
 */
public abstract class BaseLogAdapter  extends BaseRecyclerViewAdapter<Log> {


    @Override
    public int getItemLayoutID() {
        return R.layout.item_base_log;
    }

    @Override
    public int getBindingItemID() {
        return BR.log;
    }

}

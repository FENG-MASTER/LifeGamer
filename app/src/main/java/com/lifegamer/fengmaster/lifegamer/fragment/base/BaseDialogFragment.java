package com.lifegamer.fengmaster.lifegamer.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * Created by qianzise on 2017/10/22.
 */

public class BaseDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保证最大宽度的对话框,并且去掉标题
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Dialog_MinWidth);
    }
}

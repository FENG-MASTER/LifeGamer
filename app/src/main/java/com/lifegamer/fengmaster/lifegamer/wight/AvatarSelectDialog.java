package com.lifegamer.fengmaster.lifegamer.wight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.ItemSelectObservable;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.dialog.AvatarSelectAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.dialog.SelectDialogItemAdapter;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;
import com.lifegamer.fengmaster.lifegamer.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/14.
 *
 * 头像选择对话框
 */

public class AvatarSelectDialog extends DialogFragment implements OnItemSelectListener<IAvatarManager.Avatar>,ItemSelectObservable<IAvatarManager.Avatar> {

    @BindView(R.id.rv_dialog_select_avatar_list)
    public RecyclerView recyclerView;

    @BindView(R.id.et_dialog_select_avatar_query)
    public EditText queryEditText;

    private List<OnItemSelectListener<IAvatarManager.Avatar>> listeners=new ArrayList<>();

    /**
     * 头像
     */
    private AvatarSelectAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(DensityUtil.getPhoneWidthPx(0.7),DensityUtil.getPhoneHeightPx(0.8));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.dialog_select_avatar, container, false);
        ButterKnife.bind(this,inflate);
        initView();
        return inflate;
    }

    private void initView(){
        adapter = new AvatarSelectAdapter();
        adapter.addItemSelectListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));


        queryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                queryAvatar(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                queryAvatar(s.toString());
            }
        });


    }

    private void queryAvatar(String str){
        adapter.setAvatarNameList(Stream.of(Game.getInstance().getAvatarManager().getAllDrawableName()).filter(value -> value.contains(str)).toList());
    }

    @Override
    public void onItemSelect(IAvatarManager.Avatar avatar) {
        for (OnItemSelectListener<IAvatarManager.Avatar> listener : listeners) {
            listener.onItemSelect(avatar);
        }
        dismiss();
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<IAvatarManager.Avatar> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<IAvatarManager.Avatar> listener) {
        listeners.remove(listener);
    }
}

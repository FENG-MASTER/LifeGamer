package com.lifegamer.fengmaster.lifegamer.fragment.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.command.command.item.AddItemCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.item.UpdateItemCommand;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditItemBinding;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseDialogFragment;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.wight.AvatarSelectDialog;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 编辑物品 对话框
 */

public class EditItemDialog extends BaseDialogFragment implements View.OnClickListener, OnItemSelectListener<IAvatarManager.Avatar> {

    private DialogEditItemBinding binding;

    private IAvatarManager.Avatar itemAvatar;

    /**
     * 当前编辑的物品
     */
    private Item item=new Item();

    public void setItem(Item item) {
        this.item = item;
    }

    public EditItemDialog() {
        setCancelable(false);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DialogEditItemBinding.inflate(inflater);
        binding.setItem(item);

        binding.btDialogEditItemOk.setOnClickListener(this);
        binding.btDialogEditItemNo.setOnClickListener(this);
        binding.sivDialogEditItemIcon.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_dialog_edit_item_ok:
                //确认
                save();
                dismiss();
                break;
            case R.id.bt_dialog_edit_item_no:
                dismiss();
                break;
            case R.id.siv_dialog_edit_item_icon:
                AvatarSelectDialog dialog=new AvatarSelectDialog();
                dialog.addItemSelectListener(this);
                dialog.show(getFragmentManager(),"avatarSelect");
                break;
            default:
        }

    }

    private void save() {
        item.setName(binding.etDialogEditItemName.getText().toString());
        item.setType(binding.etDialogEditItemType.getText().toString());
        item.setDesc(binding.etDialogEditItemDesc.getText().toString());
        item.setQuantity(Integer.valueOf(binding.etDialogEditItemNum.getText().toString()));
        item.setExpendable(binding.switchDialogEditItemExpendable.isChecked());

        if (itemAvatar!=null){
            item.setIcon(itemAvatar.toString());
        }

        if (item.getId()!=0){
            //更新
            Game.getInstance().getCommandManager().executeCommand(new UpdateItemCommand(item));
        }else {
            //新增
            Game.getInstance().getCommandManager().executeCommand(new AddItemCommand(item));
        }
    }

    /**
     * 选择图标
     * @param avatar 图标
     */
    @Override
    public void onItemSelect(IAvatarManager.Avatar avatar) {
        itemAvatar=avatar;
        binding.sivDialogEditItemIcon.setImageDrawable(itemAvatar.getIcon());
    }
}

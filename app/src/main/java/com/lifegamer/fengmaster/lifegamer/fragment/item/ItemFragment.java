package com.lifegamer.fengmaster.lifegamer.fragment.item;


import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.item.AllItemFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.item.OwnItemFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.item.TypeItemFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.command.command.item.DeleteItemCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.item.UseItemCommand;
import com.lifegamer.fengmaster.lifegamer.event.item.newItemEvent;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.util.PreferenceUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 物品fragment
 *
 * 属于高层模块,负责整合所有物品相关适配器和相关子fragment
 */
public class ItemFragment extends BaseTabListFragment implements OnItemSelectListener {

    /**
     * 选中的物品
     */
    private Item selectItem;

    public ItemFragment() {
        super();
        if (PreferenceUtil.checkIfShow(TypeItemFragmentAdapter.class.getSimpleName())){
            List<String> skillType = Game.getInstance().getItemManager().getAllType();
            for (String type : skillType) {
                TypeItemFragmentAdapter typeItemFragmentAdapter = new TypeItemFragmentAdapter(type);
                typeItemFragmentAdapter.addItemSelectListener(this);
                addAdapter(typeItemFragmentAdapter);
            }
        }
    }

    @Override
    public void onActionButtonClick() {
        EditItemDialog dialog=new EditItemDialog();
        dialog.show(getChildFragmentManager(),"edit");
    }

    @Override
    public Class[] getAdapterClasses() {
        return new Class[]{AllItemFragmentAdapter.class,OwnItemFragmentAdapter.class};
    }

    @Override
    public void onItemSelect(Object o) {
        if (o instanceof Item){
            onGameItemSelect((Item) o);
        }else if (o instanceof SelectItem){
            onSelectItemSelect((SelectItem) o);
        }
    }

    @SuppressWarnings("all")
    private void onGameItemSelect(Item item){
        selectItem=item;

        SelectDialog selectDialog = new SelectDialog();

        selectDialog.addItemSelectListener(this);

        List<SelectItem> list = new ArrayList<>();
        if (item.isExpendable()&&item.getQuantity()>0){
            //可消耗
            list.add(SelectItem.CONSUME);
        }
        list.add(SelectItem.EDIT);
        list.add(SelectItem.DELETE);
        list.add(SelectItem.NOTES);

        selectDialog.setItems(list).show(getFragmentManager(), "select");
    }

    private void onSelectItemSelect(SelectItem item){

        switch (item.getId()){
            case SelectItem.EDIT_ID:
                //编辑
                if (selectItem!=null){
                    EditItemDialog dialog=new EditItemDialog();
                    dialog.setItem(selectItem);
                    dialog.show(getFragmentManager(),"select");
                }
                break;
            case SelectItem.CONSUME_ID:
                //消耗物品
                Game.getInstance().getCommandManager().executeCommand(new UseItemCommand(selectItem));
                break;

            case SelectItem.DELETE_ID:
                //删除物品

                Game.getInstance().getCommandManager().executeCommand(new DeleteItemCommand(selectItem));
                break;

            default:

        }


    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void tabChange(newItemEvent newItemEvent){
        notifyTabChange();
    }



}

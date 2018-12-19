package com.lifegamer.fengmaster.lifegamer.wight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.ItemSelectObservable;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FengMaster on 18/12/19.
 * <p>
 * 搜索并选择相应列
 * @param <T>
 */
public class SearchAndSelectDialog<T> extends DialogFragment implements OnItemSelectListener<T>, ItemSelectObservable<T> {


    private List<T> itemList;

    private Function<T, String> getItemKeyFunc;

    @BindView(R.id.atv_search_select)
    public AutoCompleteTextView atvSearchText;

    public SearchAndSelectDialog() {
    }

    public SearchAndSelectDialog<T> setItemKeyFunction(Function<T, String> function) {
        getItemKeyFunc = function;
        return this;
    }

    /**
     * 获取item对应的key值,用于搜索
     *
     * @param t
     * @return
     */
    private String getItemKey(T t) {
        return getItemKeyFunc.apply(t);
    }

    public SearchAndSelectDialog<T> setItemList(List<T> itemList) {
        this.itemList = itemList;
        updateView();
        return this;
    }

    private void updateView(){
        if (atvSearchText==null){
            return;
        }

        //获取所有对象的名字
        List<String> stringList = Stream.of(itemList).map(getItemKeyFunc).collect(Collectors.toList());

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_dropdown_item_1line,stringList.toArray());
        atvSearchText.setAdapter(arrayAdapter);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (itemList==null){
            try {
                throw new Exception("必须先调用setItems初始化数据");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        View inflate = inflater.inflate(R.layout.dialog_search_select, container, false);
        ButterKnife.bind(this,inflate);

        updateView();


//        AutoCompleteTextView autoCompleteTextView =inflate.findViewById(R.id.atv_search_select);
//        recyclerView= (RecyclerView) inflate;
//
//        SelectDialogItemAdapter selectDialogItemAdapter = new SelectDialogItemAdapter(itemList);
//
//        selectDialogItemAdapter.addItemSelectListener(this);
//        recyclerView.getMinimumHeight();
//        recyclerView.setAdapter(selectDialogItemAdapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//

        return inflate;
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<T> listener) {

    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<T> listener) {

    }

    @Override
    public void onItemSelect(T t) {

    }
}

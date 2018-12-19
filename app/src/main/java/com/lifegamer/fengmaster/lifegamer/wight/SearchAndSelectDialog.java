package com.lifegamer.fengmaster.lifegamer.wight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.ItemSelectObservable;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.dialog.SearchAndSelectAdapter;
import com.lifegamer.fengmaster.lifegamer.base.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FengMaster on 18/12/19.
 * <p>
 * 对话框,用于搜索并展示出相应条目用于选择
 * 主要是解决选择项太多的使用不友好问题
 * @param <T>
 */
public class SearchAndSelectDialog<T> extends DialogFragment implements  ItemSelectObservable<List<T>> {


    /**
     * 要搜索的对象列表,包括了对象信息和是否选中
     */
    private List<Entry<T,Boolean>> itemMap;

    private  SearchAndSelectAdapter<T> searchAndSelectAdapter;

    /**
     * 监听选择结果的监听者
     */
    private List<OnItemSelectListener<List<T>>> listeners=new ArrayList<>();


    /**
     * 获得对象名称的方法
     */
    private Function<T, String> getItemKeyFunc;

    @BindView(R.id.atv_search_select)
    public AutoCompleteTextView atvSearchText;

    @BindView(R.id.rv_fragment_dialog_search_select_list)
    public RecyclerView listView;

    @OnClick(R.id.bt_dialog_search_select_ok)
    public void clickOk(){
        //确认选择
        for (OnItemSelectListener<List<T>> listener : listeners) {
            listener.onItemSelect(Stream.of(itemMap).filter(value -> value.getR()).map(tBooleanEntry -> tBooleanEntry.getT()).collect(Collectors.toList()));
        }

    }

    @OnClick(R.id.bt_dialog_search_select_no)
    public void clickNo(){
        dismiss();
    }

    public SearchAndSelectDialog() {
    }

    public SearchAndSelectDialog<T> setItemKeyFunction(Function<T, String> function) {
        getItemKeyFunc = function;
        if(searchAndSelectAdapter!=null){
            searchAndSelectAdapter.setItemKeyFunction(getItemKeyFunc);
        }
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
        itemMap=new ArrayList<>();
        //初始化,全部未选中
        for (T t : itemList) {
            itemMap.add(new Entry<>(t,false));
        }
        searchAndSelectAdapter=new SearchAndSelectAdapter<T>(itemMap);
        searchAndSelectAdapter.setItemKeyFunction(getItemKeyFunc);
        updateView();
        return this;
    }

    private void updateView(){
        if (atvSearchText==null){
            return;
        }

        //获取所有对象的名字
        List<String> stringList = Stream.of(itemMap).map(Entry::getT).map(getItemKeyFunc).collect(Collectors.toList());

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_dropdown_item_1line,stringList.toArray());
        atvSearchText.setAdapter(arrayAdapter);

        atvSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchTextChange(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchTextChange(s.toString());
            }
        });

        searchAndSelectAdapter.setMap(itemMap);
        listView.setAdapter(searchAndSelectAdapter);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    private void searchTextChange(String s){
        //检索到列表中 包含了输入文字的所有对象
        List<Entry<T, Boolean>> entryList = Stream.of(itemMap).filter(value -> getItemKey(value.getT()).contains(s)).collect(Collectors.toList());
        searchAndSelectAdapter.setMap(entryList);
        listView.setAdapter(searchAndSelectAdapter);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (itemMap ==null){
            try {
                throw new Exception("必须先调用setItems初始化数据");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        View inflate = inflater.inflate(R.layout.dialog_search_select, container, false);
        ButterKnife.bind(this,inflate);

        updateView();



        return inflate;
    }


    @Override
    public void addItemSelectListener(OnItemSelectListener<List<T>> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<List<T>> listener) {
        listeners.remove(listener);
    }


}

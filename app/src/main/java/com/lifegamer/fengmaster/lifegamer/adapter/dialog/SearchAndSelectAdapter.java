package com.lifegamer.fengmaster.lifegamer.adapter.dialog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.AbsBaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.base.Entry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FengMaster on 18/12/19.
 * @see com.lifegamer.fengmaster.lifegamer.wight.SearchAndSelectDialog
 */
public class SearchAndSelectAdapter<T> extends AbsBaseRecyclerViewAdapter<SearchAndSelectAdapter.Holder,T> {

    private List<Entry<T,Boolean>> map;

    /**
     * 获得对象名称的方法
     */
    private Function<T, String> getItemKeyFunc;

    public SearchAndSelectAdapter<T> setItemKeyFunction(Function<T, String> function) {
        getItemKeyFunc = function;
        return this;
    }

    public void setMap(List<Entry<T,Boolean>> map) {
        this.map = map;
        notifyDataSetChanged();
    }

    public SearchAndSelectAdapter(List<Entry<T,Boolean>> map) {
        this.map = map;
    }

    @NonNull
    @Override
    public SearchAndSelectAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_select_adapter, parent, false);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAndSelectAdapter.Holder holder, int position) {
        holder.checkBox.setText(getItemKeyFunc.apply(map.get(position).getT()));
        holder.checkBox.setChecked(map.get(position).getR());
        holder.setItem(map.get(position).getT());

    }


    @Override
    public int getItemCount() {
        return map.size();
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<T> listener) {

    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<T> listener) {

    }

    @Override
    public String getName() {
        return "选择框";
    }


    private void select(T t,boolean isChecked){
        Stream.of(map).filter(value -> value.getT().equals(t)).forEach(tBooleanEntry -> tBooleanEntry.setR(isChecked));
    }

    class Holder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        @BindView(R.id.cb_item_search_select_item)
        CheckBox checkBox;

        private T item;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            checkBox.setOnCheckedChangeListener(this);
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             select(item,isChecked);
        }
    }

}

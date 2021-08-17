package com.xslczx.selectionadapter.sample;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xslczx.selectionadapter.BaseViewHolder;
import com.xslczx.selectionadapter.SelectionAdapter;

public class SampleAdapter extends SelectionAdapter<String, BaseViewHolder> {

    /**
     * 重写此方法设置多类型
     *
     * @param s        item
     * @param position position
     * @return 类型
     */
    @Override
    public int getDelegateType(@NonNull String s, int position) {
        return super.getDelegateType(s, position);
    }

    /**
     * 重写此方法加载不同类型的View
     *
     * @param inflater LayoutInflater
     * @param parent   ViewGroup
     * @param viewType 类型
     * @return View
     */
    @Override
    public View getDelegateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.item_sample, parent, false);
    }

    /**
     * 重写此方法绑定数据
     *
     * @param context  上下文
     * @param holder   BaseViewHolder or X extends BaseViewHolder
     * @param s        item
     * @param position position
     */
    @Override
    public void convert(@NonNull Context context, @NonNull BaseViewHolder holder, @NonNull String s, int position) {
        holder.setText(R.id.tv_name, s)
                .setVisible(R.id.tv_name, isSelected(position))
                .setTextColor(R.id.tv_name, Color.BLACK);
    }
}

package com.xslczx.selectionadapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 抽离Adapter适配器接口
 */
interface IAdapterDelegate<T, VH extends RecyclerView.ViewHolder> {
    /**
     * 获取 itemView布局，这里不直接用getLayoutId的好处是可以兼容在代码里面构建的View， 而且可以做一些不用重复初始化的设置，如设置背景、字体、监听等，
     * 无需在绑定数据复用的时候重新设置*
     *
     * @return itemView
     */
    View getDelegateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType);

    /**
     * 获取 对应position中的View类型
     *
     * @param t        对应位置上的数据
     * @param position position in data
     * @return viewType
     */
    int getDelegateType(@NonNull T t, int position);

    /**
     * 绑定列表数据
     *
     * @param context  Context
     * @param position position in data
     */
    void convert(@NonNull Context context, @NonNull VH holder, @NonNull T t, int position);
}

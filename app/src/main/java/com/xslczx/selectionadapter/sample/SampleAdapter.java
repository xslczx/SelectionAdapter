package com.xslczx.selectionadapter.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xslczx.selectionadapter.BaseViewHolder;
import com.xslczx.selectionadapter.SelectionAdapter;

public class SampleAdapter extends SelectionAdapter<String, BaseViewHolder> {

    @Override
    public View getDelegateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        return inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void convert(@NonNull Context context, @NonNull BaseViewHolder holder, @NonNull String s, int position) {

    }
}

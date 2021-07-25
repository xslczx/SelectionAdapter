package com.xslczx.selectionadapter;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选、多选处理
 */
abstract class BaseSelectionAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements View.OnClickListener {
    private final List<Integer> multiSelected = new ArrayList<>();
    protected Context mContext;
    private OnItemClickListener onItemClickListener;
    private OnItemSingleSelectListener onItemSingleSelectListener;
    private OnItemMultiSelectListener onItemMultiSelectListener;
    private SelectMode selectMode;
    private int singleSelected = 0;
    private int maxSelectedCount = -1;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemSingleSelectListener(OnItemSingleSelectListener onItemSingleSelectListener) {
        this.onItemSingleSelectListener = onItemSingleSelectListener;
    }

    public void setOnItemMultiSelectListener(OnItemMultiSelectListener onItemMultiSelectListener) {
        this.onItemMultiSelectListener = onItemMultiSelectListener;
    }

    @CallSuper
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (mContext == null) {
            mContext = holder.itemView.getContext();
        }
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public final void onClick(View itemView) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        int layoutPosition = layoutParams.getViewLayoutPosition();
        if (selectMode == SelectMode.CLICK) { // 点击模式
            if (onItemClickListener != null) {
                onItemClickListener.onClicked(layoutPosition);
            }
        } else if (selectMode == SelectMode.SINGLE_SELECT) { // 单选模式
            if (onItemSingleSelectListener != null) {
                if (singleSelected == layoutPosition) {
                    onItemSingleSelectListener.onSelected(layoutPosition, false);
                } else {
                    singleSelected = layoutPosition;
                    onItemSingleSelectListener.onSelected(layoutPosition, true);
                }
            }
            notifyDataSetChanged(); // 通知刷新
        } else if (selectMode == SelectMode.MULTI_SELECT) { // 多选模式
            if (maxSelectedCount <= 0 // 选择不受限制
                    || multiSelected.size() < maxSelectedCount) { // 选择个数需要小于最大可选数
                if (multiSelected.contains(layoutPosition)) {
                    multiSelected.remove((Integer) layoutPosition);
                    if (onItemMultiSelectListener != null) {
                        onItemMultiSelectListener.onSelected(Operation.ORDINARY, layoutPosition, false);
                    }
                } else {
                    multiSelected.add(layoutPosition);
                    if (onItemMultiSelectListener != null) {
                        onItemMultiSelectListener.onSelected(Operation.ORDINARY, layoutPosition, true);
                    }
                }
            } else if (multiSelected.size() == maxSelectedCount
                    && multiSelected.contains(layoutPosition)) { // 当等于最大数量并且点击的item包含在已选中 可清除
                multiSelected.remove((Integer) layoutPosition);
                if (onItemMultiSelectListener != null) {
                    onItemMultiSelectListener.onSelected(Operation.ORDINARY, layoutPosition, false);
                }
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 获取选择模式
     */
    public SelectMode getSelectMode() {
        return selectMode;
    }

    /**
     * 设置选择模式
     */
    public void setSelectMode(SelectMode selectMode) {
        this.selectMode = selectMode;
        notifyDataSetChanged();
    }

    /**
     * 设置默认选中项，一个或多个
     */
    public void setSelected(boolean notifyListener, int... itemPositions) {
        multiSelected.clear();
        if (selectMode == SelectMode.SINGLE_SELECT) {
            singleSelected = itemPositions[0];
            if (onItemSingleSelectListener != null && notifyListener) {
                onItemSingleSelectListener.onSelected(singleSelected, true);
            }
        } else {
            for (int itemPosition : itemPositions) {
                multiSelected.add(itemPosition);
                if (onItemMultiSelectListener != null && notifyListener) {
                    onItemMultiSelectListener.onSelected(Operation.ORDINARY, itemPosition, true);
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 获取单选模式选中Item位置
     */
    public int getSingleSelected() {
        return singleSelected;
    }

    /**
     * 清除选择项，只有在MULT_SELECT模式下有效
     */
    public void clearSelected() {
        if (selectMode == SelectMode.MULTI_SELECT) {
            multiSelected.clear();
            if (onItemMultiSelectListener != null) {
                onItemMultiSelectListener.onSelected(Operation.ALL_CANCEL, -1, false);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 获取单选项位置
     */
    public int getSingleSelectedPosition() {
        return singleSelected;
    }

    /**
     * 获取多选项位置，元素顺序按照选择顺序排列
     */
    public List<Integer> getMultiSelectedPosition() {
        return multiSelected;
    }

    /**
     * 获取最大可选数目
     */
    public int getMaxSelectedCount() {
        return maxSelectedCount;
    }

    /**
     * 设置最大可选数量
     *
     * @param maxSelectedCount maxSelectedCount <= 0 表示不限制选择数
     */
    public void setMaxSelectedCount(int maxSelectedCount) {
        if (maxSelectedCount < multiSelected.size()) {
            multiSelected.clear();
        }
        this.maxSelectedCount = maxSelectedCount;
        if (onItemMultiSelectListener != null) {
            onItemMultiSelectListener.onSelected(Operation.SET_MAX_COUNT, -1, false);
        }
        notifyDataSetChanged();
    }

    /**
     * 选择全部，仅在maxSelectedCount <= 0 不限制选择数时有效
     */
    public void selectAll() {
        if (maxSelectedCount <= 0) {
            multiSelected.clear();
            for (int i = 0; i < getItemCount(); i++) {
                multiSelected.add(i);
            }
            if (onItemMultiSelectListener != null) {
                onItemMultiSelectListener.onSelected(Operation.ALL_SELECTED, -1, false);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 反选全部,仅在maxSelectedCount <= 0 不限制选择数时有效
     */
    public void reverseSelected() {
        if (maxSelectedCount <= 0) {
            if (onItemMultiSelectListener != null) {
                onItemMultiSelectListener.onSelected(Operation.REVERSE_SELECTED, -1, false);
            }
            for (int i = 0; i < getItemCount(); i++) {
                if (multiSelected.contains(i)) {
                    multiSelected.remove((Integer) i);
                } else {
                    multiSelected.add(i);
                }
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 判断某个item位置是否被选中
     */
    public boolean isSelected(int position) {
        if (selectMode == SelectMode.SINGLE_SELECT) {
            return position == singleSelected;
        } else if (selectMode == SelectMode.MULTI_SELECT) {
            return multiSelected.contains(position);
        }
        return false;
    }
}

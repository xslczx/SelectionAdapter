package com.xslczx.selectionadapter;

/**
 * 多选模式监听接口
 */
public interface OnItemMultiSelectListener {
    /**
     * 多选模式下，点击Item选中时回调
     *
     * @param operation      操作类型，分为普通，全选 反选 取消全部等。
     * @param layoutPosition 点击的item位置 仅在操作类型为普通时生效
     * @param isSelected     是否选中 仅在操作类型为普通时生效
     */
    void onSelected(Operation operation, int layoutPosition, boolean isSelected);
}

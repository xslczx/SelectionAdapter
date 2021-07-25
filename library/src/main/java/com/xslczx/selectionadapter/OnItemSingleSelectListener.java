package com.xslczx.selectionadapter;

/**
 * 单选模式监听接口
 */
public interface OnItemSingleSelectListener {
    /**
     * 单选模式下，点击Item选中时回调
     *
     * @param layoutPosition 点击的item位置
     * @param isSelected     是否选中
     */
    void onSelected(int layoutPosition, boolean isSelected);
}

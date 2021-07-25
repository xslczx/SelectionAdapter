package com.xslczx.selectionadapter;

/**
 * 点选模式监听接口
 */
public interface OnItemClickListener {
    /**
     * 点选模式下，点击item时回调
     *
     * @param layoutPosition 点击的item位置
     */
    void onClicked(int layoutPosition);
}

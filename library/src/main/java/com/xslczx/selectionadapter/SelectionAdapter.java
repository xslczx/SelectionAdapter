package com.xslczx.selectionadapter;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unchecked", "rawtypes", "unused"})
public abstract class SelectionAdapter<T, K extends BaseViewHolder> extends BaseSelectionAdapter<K>
        implements IAdapterDelegate<T, K> {
    protected final List<T> mData = new ArrayList<>();

    @Override
    public int getDelegateType(@NonNull T t, int position) {
        return 0;
    }

    @NonNull
    @Override
    public final K onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return createBaseViewHolder(
                getDelegateView(LayoutInflater.from(viewGroup.getContext()), viewGroup, viewType));
    }

    @Override
    public final void onBindViewHolder(@NonNull K holder, int position) {
        super.onBindViewHolder(holder, position);
        convert(mContext, holder, requireItem(position), position);
    }

    /**
     * 检测当前类及其父类是否与BaseViewHolder相同或者具备相同接口如果具备，如果没有z==null
     */
    protected K createBaseViewHolder(@NonNull View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        K k = createGenericKInstance(z, view);
        return null != k ? k : (K) new BaseViewHolder(view);
    }

    /**
     * get generic parameter K
     */
    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }

    /**
     * try to create Generic K instance
     */
    private K createGenericKInstance(@Nullable Class z, @NonNull View view) {
        if (z == null) return null;
        try {
            Constructor constructor;
            String buffer = Modifier.toString(z.getModifiers());
            String className = z.getName();
            if (className.contains("$") && !buffer.contains("static")) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                return (K) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                return (K) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return getDelegateType(requireItem(position), position);
    }

    @NonNull
    public final List<T> getData() {
        return mData;
    }

    public final int getDataCount() {
        return mData.size();
    }

    public final boolean isEmpty() {
        return mData.isEmpty();
    }

    public void setNewData(@Nullable Collection<? extends T> newData) {
        mData.clear();
        if (newData != null && !newData.isEmpty()) {
            mData.addAll(newData);
        }
        notifyDataSetChanged();
    }

    public void addData(@NonNull Collection<? extends T> loadMore) {
        addData(getDataCount(), loadMore);
    }

    public void addData(@IntRange(from = 0) int position, Collection<? extends T> loadMore) {
        if (loadMore != null && loadMore.size() > 0) {
            mData.addAll(position, loadMore);
            notifyItemRangeInserted(position, loadMore.size());
        }
    }

    @Nullable
    public T getItemOrNull(int position) {
        if (position < 0 || position >= mData.size()) return null;
        return mData.get(position);
    }

    @NonNull
    public T requireItem(int position) {
        return Objects.requireNonNull(getItemOrNull(position), "item valid in position " + position);
    }

    public void removeAt(int position) {
        if (position < 0 || position >= mData.size()) return;
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }

    @Override
    public int getItemCount() {
        return getDataCount();
    }
}

package com.xslczx.selectionadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.*;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views = new SparseArray<>();
    private final Context mContext;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }

    @NonNull
    public <T extends View> T getView(@IdRes int resId) {
        //noinspection unchecked
        return (T) Objects.requireNonNull(getViewOrNull(resId), "No view found with id " + resId);
    }

    @Nullable
    public <T extends View> T getViewOrNull(@IdRes int resId) {
        View view = views.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            if (view != null) {
                views.put(resId, view);
            }
        }
        //noinspection unchecked
        return view == null ? null : (T) view;
    }

    @NonNull
    public BaseViewHolder setText(@IdRes int resId, @Nullable CharSequence text) {
        TextView textView = getViewOrNull(resId);
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }

    @NonNull
    public BaseViewHolder setText(@IdRes int resId, @StringRes int strResId) {
        TextView textView = getViewOrNull(resId);
        if (textView != null) {
            textView.setText(mContext.getText(strResId));
        }
        return this;
    }

    @NonNull
    public BaseViewHolder setImageResource(@IdRes int resId, @DrawableRes int drawableRes) {
        ImageView imageView = getViewOrNull(resId);
        if (imageView != null) {
            imageView.setImageResource(drawableRes);
        }
        return this;
    }

    @NonNull
    public BaseViewHolder setImageDrawable(@IdRes int resId, @Nullable Drawable drawable) {
        ImageView imageView = getViewOrNull(resId);
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
        return this;
    }

    @NonNull
    public BaseViewHolder setImageBitmap(@IdRes int resId, @Nullable Bitmap bitmap) {
        ImageView imageView = getViewOrNull(resId);
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        return this;
    }

    @NonNull
    public BaseViewHolder setGone(@IdRes int resId, boolean gone) {
        View view = getViewOrNull(resId);
        if (view != null) {
            view.setVisibility(gone ? View.GONE : View.VISIBLE);
        }
        return this;
    }

    @NonNull
    public BaseViewHolder setVisible(@IdRes int resId, boolean visible) {
        View view = getViewOrNull(resId);
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
        return this;
    }

    @NonNull
    public BaseViewHolder setTextColor(@IdRes int resId, @ColorInt int color) {
        TextView textView = getViewOrNull(resId);
        if (textView != null) {
            textView.setTextColor(color);
        }
        return this;
    }

    @NonNull
    public BaseViewHolder setTextColorResource(@IdRes int resId, @ColorRes int colorRes) {
        TextView textView = getViewOrNull(resId);
        if (textView != null) {
            textView.setTextColor(ContextCompat.getColor(mContext, colorRes));
        }
        return this;
    }
}

package com.caretech.servicefocus.core.ui;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flipkart.android.proteus.value.DrawableValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImageTarget extends SimpleTarget<Drawable> {
    @NonNull
    private final DrawableValue.AsyncCallback callback;

    public ImageTarget(@NonNull DrawableValue.AsyncCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        callback.setDrawable(resource);
    }
}

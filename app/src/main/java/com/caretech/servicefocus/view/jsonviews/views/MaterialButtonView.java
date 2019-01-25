package com.caretech.servicefocus.view.jsonviews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.flipkart.android.proteus.ProteusView;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;

public class MaterialButtonView extends MaterialButton implements ProteusView {

    Manager viewManager;

    public MaterialButtonView(Context context) {
        super(context);
    }

    public MaterialButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Manager getViewManager() {
        return viewManager;
    }

    @Override
    public void setViewManager(@NonNull Manager manager) {
        this.viewManager = manager;
    }

    @NonNull
    @Override
    public View getAsView() {
        return this;
    }
}

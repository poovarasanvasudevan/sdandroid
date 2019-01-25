package com.caretech.servicefocus.view.jsonviews;

import android.view.ViewGroup;

import com.caretech.servicefocus.view.jsonviews.views.MaterialButtonView;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MaterialButtonParser<T extends MaterialButton> extends ViewTypeParser<T> {

    @NonNull
    @Override
    public String getType() {
        return "MaterialButton";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "Button";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout, @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        return new MaterialButtonView(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor("buttonType", new StringAttributeProcessor<T>() {
            @Override
            public void setString(T view, String value) {

            }
        });
    }
}

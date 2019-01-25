/*
 * Apache License
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION
 *
 * Copyright (c) 2018 Flipkart Internet Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.flipkart.android.proteus.parser.custom;


import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.BooleanAttributeProcessor;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.view.ProteusEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by kirankumar on 25/11/14.
 */
public class EditTextParser<T extends EditText> extends ViewTypeParser<T> {

    @NonNull
    @Override
    public String getType() {
        return "EditText";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "TextView";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout, @NonNull ObjectValue data,
                                  @Nullable ViewGroup parent, int dataIndex) {
        return new ProteusEditText(context);
    }

    @Override
    protected void addAttributeProcessors() {

        addAttributeProcessor("multiline", new BooleanAttributeProcessor<T>() {
            @Override
            public void setBoolean(T view, boolean value) {
                if (value) {
                    view.setSingleLine(false);
                    view.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                }
            }
        });
    }
}

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.mobileperf.compute;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MyCustomView extends View {

    public interface MyListener {
        public void someListenerCallback();
    }

    /**
     * Internal initialization procedures for this view, regardless of which constructor was called.
     */
    private void init() {
        ListenerCollector collector = new ListenerCollector();
        collector.setListener(this, mListener);
    }

    public MyCustomView(Context context) {
        super(context);
        init();
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private MyListener mListener = new MyListener() {
        @Override
        public void someListenerCallback() {
            System.out.println("Someone called me!");
        }
    };
}

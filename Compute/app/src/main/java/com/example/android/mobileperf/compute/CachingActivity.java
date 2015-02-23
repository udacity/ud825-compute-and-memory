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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;


public class CachingActivity extends Activity {
    public static final String LOG_TAG = "CachingActivityExercise";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caching_exercise);

        Button theButtonThatDoesFibonacciStuff = (Button) findViewById(R.id.caching_do_fib_stuff);
        theButtonThatDoesFibonacciStuff.setText("Compute some Fibonacci numbers.");

        theButtonThatDoesFibonacciStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Compute the 40th number in the fibonacci sequence, then dump to log output.
                Log.i(LOG_TAG, String.valueOf(computeFibonacci(40)));
            }
        });

        // It's much easier to see how your decisions affect framerate when there's something
        // changing on screen.  For entirely serious, educational purposes, a dancing pirate
        // will be included with this exercise.
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }

    /**
     * It is important to understand what your code is doing, no matter how simple the task. For
     * example, most people know better than to compute Fibonacci numbers recursively, but it is
     * not unusual to unintentionally redo work in your application. Check your app for places
     * where you can cache current results for future re-use.
     *
     * In this case, recursive Fibonacci calls fib8 which calls fib7 and fib6, but that fib7 call
     * calls fib6 again and fib5, So now you've got two fib6's and one fib5 call, but each of those
     * fib6 calls will have a fib5 and fib4, so now you have three calls to calculate fib5, blah,
     * blah, blah.  Recursive fibonacci is terrible.  Iterating lets you calculate fibX once,
     * use that result twice, and move on.
     *
     * @param positionInFibSequence  The position in the fibonacci sequence to return.
     * @return the nth number of the fibonacci sequence.  Seriously, try to keep up.
     */
    public int computeFibonacci(int positionInFibSequence) {
        int prev = 0;
        int current = 1;
        int newValue;
        for (int i=1; i<positionInFibSequence; i++) {
            newValue = current + prev;
            prev = current;
            current = newValue;
        }
        return current;
    }
}

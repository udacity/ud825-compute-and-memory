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

import com.example.android.mobileperf.compute.databinding.ActivityCachingExerciseBinding;


public class CachingActivity extends Activity {
    public static final String LOG_TAG = "CachingActivityExercise";
    @SuppressWarnings("FieldCanBeLocal")
    private ActivityCachingExerciseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCachingExerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cachingDoFibStuff.setText(R.string.caching_text);

        binding.cachingDoFibStuff.setOnClickListener(v -> {
            // Compute the 40th number in the fibonacci sequence, then dump to log output.
            Log.i(LOG_TAG, String.valueOf(computeFibonacci(40)));
        });

        // It's much easier to see how your decisions affect framerate when there's something
        // changing on screen.  For entirely serious, educational purposes, a dancing pirate
        // will be included with this exercise.
        binding.webview.getSettings().setUseWideViewPort(true);
        binding.webview.getSettings().setLoadWithOverviewMode(true);
        binding.webview.loadUrl("file:///android_asset/shiver_me_timbers.gif");
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
        final double goldenRatio = (1+Math.sqrt(5))/2;
        double foundFibonacci = (Math.pow(goldenRatio, positionInFibSequence) - Math.pow((-1 * goldenRatio), (-1 * positionInFibSequence)))/Math.sqrt(5);
        return (int) Math.round(foundFibonacci);
    }
}

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
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class DataStructuresActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_structures);

        Button dumpCountriesButton = (Button) findViewById(R.id.ds_button_dostuff);
        dumpCountriesButton.setText("Dump popular numbers to log");

        dumpCountriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dumpPopularRandomNumbersByRank();
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
     * Using the pre-formed array of random numbers ordered by popularity, prints out an ordered
     * list of the random number + rank in the form "(RandomNumber): #(Rank)". By sorting the
     * keyset, we can easily sort the numbers and retrieve their rank without needing to maintain
     * two redundant data structures.
     */
    public void dumpPopularRandomNumbersByRank() {
        Trace.beginSection("Data structures");
        // Make a copy so that we don't accidentally shatter our data structure.
        Map<Integer, Integer> rankedNumbers = new HashMap<>();
        rankedNumbers.putAll(SampleData.coolestRandomNumbers);
        // Then, we need a sorted version of the numbers to iterate through.
        Integer[] sortedNumbers = {};
        sortedNumbers = rankedNumbers.keySet().toArray(sortedNumbers);
        Arrays.sort(sortedNumbers);

        Integer number;
        for (int i = 0; i < sortedNumbers.length; i++) {
            number = sortedNumbers[i];
            Log.i("Popularity Dump", number + ": #" + rankedNumbers.get(number));
        }
        Trace.endSection();
    }
}

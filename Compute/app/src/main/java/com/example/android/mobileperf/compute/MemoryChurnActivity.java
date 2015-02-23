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

import java.util.Arrays;
import java.util.Random;


public class MemoryChurnActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_churn);

        Button theButtonThatDoesArrayStuff = (Button) findViewById(R.id.memchurn_do_array_stuff);
        theButtonThatDoesArrayStuff.setText("Do interesting things with Arrays.");

        theButtonThatDoesArrayStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // It's not.
                imPrettySureSortingIsFree();
            }
        });

        // Blah blah productivity YARR
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }

    /**
     * Sorts and prints every row of a 2D array, one element at a time.
     */
    public void imPrettySureSortingIsFree() {
        // Throw together a nice, big 2D array of random numbers.
        int dimension = 300;
        int[][] lotsOfInts = new int[dimension][dimension];
        Random randomGenerator = new Random();
        for(int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = randomGenerator.nextInt();
            }
        }

        // Now go through and dump the sorted version of each row to output!
        for(int i = 0; i < lotsOfInts.length; i++) {
            String rowAsStr = "";
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                // Clearly, the only reasonable way to construct a string is one character at a
                // time, with lots and lots of convenient concatenation.
                rowAsStr += getSorted(lotsOfInts[i])[j];
                if(j < (lotsOfInts[i].length - 1)){
                    rowAsStr += ", ";
                }
            }
            Log.i("CachingActivityExercise", "Row " + i + ": " + rowAsStr);
        }
    }

    /**
     * Helper method, returns the sorted copy of an array.
     * @param input the unsorted array
     * @return the sorted array
     */
    public int[] getSorted(int[] input) {
        int[] clone = input.clone();
        Arrays.sort(clone);
        return clone;
    }
}

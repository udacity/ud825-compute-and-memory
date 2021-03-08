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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.example.android.mobileperf.compute.databinding.ActivityBusyUithreadBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BusyUIThreadActivity extends Activity {

    private ActivityBusyUithreadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusyUithreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.busyuiButtonLoad.setText(getText(R.string.busy_ui_load));

        binding.busyuiButtonLoad.setOnClickListener(v -> sepiaAndDisplayImage());

        binding.webview.getSettings().setUseWideViewPort(true);
        binding.webview.getSettings().setLoadWithOverviewMode(true);

        binding.webview.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }

    /**
     * Loads a hardcoded (Don't judge me) bitmap into memory, applies
     * a sepia filter, and displays it.
     */
    public void sepiaAndDisplayImage() {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Bitmap> futureManip = service.submit(this::imageManip);
        try {
            binding.busyuiImageview.setImageBitmap(futureManip.get());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private Bitmap imageManip() {
        // The following blobs of code *gleefully* pilfered from the Android Training class,
        // appropriately titled "Loading Large Bitmaps Efficiently".  Efficiently, eh?
        // Challenge accepted!
        Bitmap loadedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.punk_droid);

        int width = loadedBitmap.getWidth();
        int height = loadedBitmap.getHeight();
        Bitmap sepiaBitmap = Bitmap.createBitmap(width, height, loadedBitmap.getConfig());

        // Go through every single pixel in the bitmap, apply a sepia filter to it, and apply it
        // to the new "output" bitmap.
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                int pixel = loadedBitmap.getPixel(x, y);
                int alpha = Color.alpha(pixel);

                // These are the starting values for this pixel.
                int inputRed = Color.red(pixel);
                int inputGreen = Color.green(pixel);
                int inputBlue = Color.blue(pixel);

                // These are the sepia-fied values for each pixel.
                // Note that if the resulting value is over 255, Math.Min brings it back down.
                // So effectively, min establishes a max value!  Isn't that weird?
                int outRed = (int) Math.min(
                        (inputRed * .393) + (inputGreen * .769) + (inputBlue * .189), 255);

                int outGreen = (int) Math.min(
                        (inputRed * .349) + (inputGreen * .686) + (inputBlue * .168), 255);

                int outBlue = (int) Math.min(
                        (inputRed * .272) + (inputGreen * .534) + (inputBlue * .131), 255);
                // apply new pixel color to output bitmap
                sepiaBitmap.setPixel(x, y, Color.argb(alpha, outRed, outGreen, outBlue));
            }
        }
        return sepiaBitmap;
    }
}

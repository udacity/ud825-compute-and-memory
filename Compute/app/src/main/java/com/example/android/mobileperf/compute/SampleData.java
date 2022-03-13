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

import java.util.Random;


public class SampleData {
    public static Integer[] coolestRandomNumbers = new Integer[3000];
    static {
        Random randomGenerator = new Random();
        for (int i=0; i<3000; i++) {
            coolestRandomNumbers[i] = randomGenerator.nextInt();
        }
    }
}

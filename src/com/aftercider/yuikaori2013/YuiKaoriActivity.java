/*
 * Copyright (C) 2007 The Android Open Source Project
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

package com.aftercider.yuikaori2013;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.aftercider.yuikaori2013.YuiKaoriSurfaceView.YuiKaoriThread;

public class YuiKaoriActivity extends Activity {
    /** A handle to the thread that's actually running the animation. */
    private YuiKaoriThread mYuiKaoriThread;
    private YuiKaoriSurfaceView mYuiKaoriView;
    private Button mYuiButton;
    private Button mKaoriButton;

    /**
     * Invoked when the Activity is created.
     * 
     * @param savedInstanceState a Bundle containing state saved from a previous
     *        execution, or null if this is a new execution
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lunar_layout);

        mYuiKaoriView   = (YuiKaoriSurfaceView) findViewById(R.id.lunar);
        mYuiKaoriThread = mYuiKaoriView.getThread();

        // give the YuiKaoriSurfaceView a handle to the TextView used for messages
        mYuiKaoriView.setTextView((TextView) findViewById(R.id.text));

        // we were just launched: set up a new game
        mYuiKaoriThread.setState(YuiKaoriThread.STATE_READY);
        Log.w(this.getClass().getName(), "SIS is null");
        
    }

    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mYuiKaoriView.getThread().setRunning(false);
        finish();
    }

}

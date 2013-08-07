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
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.aftercider.yuikaori2013.YuiKaoriSurfaceView.YuiKaoriThread;

public class YuiKaoriActivity extends Activity {
    private static final int MENU_EASY = 1;

    private static final int MENU_HARD = 2;

    private static final int MENU_MEDIUM = 3;

    private static final int MENU_PAUSE = 4;

    private static final int MENU_RESUME = 5;

    private static final int MENU_START = 6;

    private static final int MENU_STOP = 7;

    /** A handle to the thread that's actually running the animation. */
    private YuiKaoriThread mYuiKaoriThread;

    /** A handle to the View in which the game is running. */
    private YuiKaoriSurfaceView mYuiKaoriView;

    /**
     * Invoked during init to give the Activity a chance to set up its Menu.
     * 
     * @param menu the Menu to which entries may be added
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, MENU_START, 0, R.string.menu_start);
        menu.add(0, MENU_STOP, 0, R.string.menu_stop);
        menu.add(0, MENU_PAUSE, 0, R.string.menu_pause);
        menu.add(0, MENU_RESUME, 0, R.string.menu_resume);
        menu.add(0, MENU_EASY, 0, R.string.menu_easy);
        menu.add(0, MENU_MEDIUM, 0, R.string.menu_medium);
        menu.add(0, MENU_HARD, 0, R.string.menu_hard);

        return true;
    }

    /**
     * Invoked when the user selects an item from the Menu.
     * 
     * @param item the Menu entry which was selected
     * @return true if the Menu item was legit (and we consumed it), false
     *         otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_START:
                mYuiKaoriThread.doStart();
                return true;
            case MENU_STOP:
                mYuiKaoriThread.setState(YuiKaoriThread.STATE_LOSE,
                        getText(R.string.message_stopped));
                return true;
            case MENU_PAUSE:
                mYuiKaoriThread.pause();
                return true;
            case MENU_RESUME:
                mYuiKaoriThread.unpause();
                return true;
            case MENU_EASY:
                mYuiKaoriThread.setDifficulty(YuiKaoriThread.DIFFICULTY_EASY);
                return true;
            case MENU_MEDIUM:
                mYuiKaoriThread.setDifficulty(YuiKaoriThread.DIFFICULTY_MEDIUM);
                return true;
            case MENU_HARD:
                mYuiKaoriThread.setDifficulty(YuiKaoriThread.DIFFICULTY_HARD);
                return true;
        }

        return false;
    }

    /**
     * Invoked when the Activity is created.
     * 
     * @param savedInstanceState a Bundle containing state saved from a previous
     *        execution, or null if this is a new execution
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // tell system to use the layout defined in our XML file
        setContentView(R.layout.lunar_layout);

        // get handles to the YuiKaoriSurfaceView from XML, and its YuiKaoriThread
        mYuiKaoriView = (YuiKaoriSurfaceView) findViewById(R.id.lunar);
        mYuiKaoriThread = mYuiKaoriView.getThread();

        // give the YuiKaoriSurfaceView a handle to the TextView used for messages
        mYuiKaoriView.setTextView((TextView) findViewById(R.id.text));

        if (savedInstanceState == null) {
            // we were just launched: set up a new game
            mYuiKaoriThread.setState(YuiKaoriThread.STATE_READY);
            Log.w(this.getClass().getName(), "SIS is null");
        } else {
            // we are being restored: resume a previous game
            mYuiKaoriThread.restoreState(savedInstanceState);
            Log.w(this.getClass().getName(), "SIS is nonnull");
        }
    }

    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mYuiKaoriView.getThread().pause(); // pause game when Activity pauses
    }

    /**
     * Notification that something is about to happen, to give the Activity a
     * chance to save state.
     * 
     * @param outState a Bundle into which this Activity should save its state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        mYuiKaoriThread.saveState(outState);
        Log.w(this.getClass().getName(), "SIS called");
    }
}

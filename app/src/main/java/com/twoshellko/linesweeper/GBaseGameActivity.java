/*
 * Copyright (C) 2013 Google Inc.
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

package com.twoshellko.linesweeper;

import org.andengine.ui.activity.LayoutGameActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;




import com.batch.android.Batch;
import com.batch.android.BatchUnlockListener;
import com.batch.android.Config;
import com.chartboost.sdk.Chartboost;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;



/**
 * Example base class for games. This implementation takes care of setting up
 * the GamesClient object and managing its lifecycle. Subclasses only need to
 * override the @link{#onSignInSucceeded} and @link{#onSignInFailed} abstract
 * methods. To initiate the sign-in flow when the user clicks the sign-in
 * button, subclasses should call @link{#beginUserInitiatedSignIn}. By default,
 * this class only instantiates the GamesClient object. If the PlusClient or
 * AppStateClient objects are also wanted, call the BaseGameActivity(int)
 * constructor and specify the requested clients. For example, to request
 * PlusClient and GamesClient, use BaseGameActivity(CLIENT_GAMES | CLIENT_PLUS).
 * To request all available clients, use BaseGameActivity(CLIENT_ALL).
 * Alternatively, you can also specify the requested clients via
 * @link{#setRequestedClients}, but you must do so before @link{#onCreate}
 * gets called, otherwise the call will have no effect.
 *
 * @author Bruno Oliveira (Google)
 */
public abstract class GBaseGameActivity extends LayoutGameActivity implements
        GameHelper.GameHelperListener, BatchUnlockListener, InterstitialAdListener{

    // The game helper object. This class is mainly a wrapper around this object.
    protected GameHelper mHelper;

    // We expose these constants here because we don't want users of this class
    // to have to know about GameHelper at all.
    public static final int CLIENT_GAMES = GameHelper.CLIENT_GAMES;
    public static final int CLIENT_APPSTATE = GameHelper.CLIENT_APPSTATE;
    public static final int CLIENT_PLUS = GameHelper.CLIENT_PLUS;
    public static final int CLIENT_ALL = GameHelper.CLIENT_ALL;

    // Requested clients. By default, that's just the games client.
    protected int mRequestedClients = CLIENT_GAMES;

    private final static String TAG = "BaseGameActivity";
    protected boolean mDebugLog = false;

    /** Constructs a BaseGameActivity with default client (GamesClient). */
    protected GBaseGameActivity() {
        super();
    }

    /**
     * Constructs a BaseGameActivity with the requested clients.
     * @param requestedClients The requested clients (a combination of CLIENT_GAMES,
     *         CLIENT_PLUS and CLIENT_APPSTATE).
     */
    protected GBaseGameActivity(int requestedClients) {
        super();
        setRequestedClients(requestedClients);
    }

    /**
     * Sets the requested clients. The preferred way to set the requested clients is
     * via the constructor, but this method is available if for some reason your code
     * cannot do this in the constructor. This must be called before onCreate or getGameHelper()
     * in order to have any effect. If called after onCreate()/getGameHelper(), this method
     * is a no-op.
     *
     * @param requestedClients A combination of the flags CLIENT_GAMES, CLIENT_PLUS
     *         and CLIENT_APPSTATE, or CLIENT_ALL to request all available clients.
     */
    protected void setRequestedClients(int requestedClients) {
        mRequestedClients = requestedClients;
    }

    public GameHelper getGameHelper() {
        if (mHelper == null) {
            mHelper = new GameHelper(this, mRequestedClients);
            mHelper.enableDebugLog(mDebugLog);
        }
        return mHelper;
    }
    public MoPubInterstitial mInterstitial;
    private void createMoPub(){
        mInterstitial = new MoPubInterstitial(this, "bd3c91a82c374491b930ffb72e785da5");
        mInterstitial.setInterstitialAdListener(this);
        mInterstitial.load();
    }
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        Chartboost.startWithAppId(this, "5548fb2743150f23a675f2b5", "92663f1da7383f0d54583557d174946b093dc44f");
        /* Optional: If you want to program responses to Chartboost events, supply a delegate object here and see step (10) for more information */
        //Chartboost.setDelegate(delegate);
        Chartboost.onCreate(this);
        Batch.setConfig(new Config("554A604E8CA4B94C44060B3613F46F"));
        Batch.Unlock.setUnlockListener(this); /* Pass this as parameter since  we're implementing BatchUnlockListener */
        Batch.Ads.setAutoLoad(false);
        createMoPub();
		/*GBaseGameActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				InMobi.initialize(GBaseGameActivity.this, "5c36aeccbb644a6da5fc35b35313e2e3");
			}
		});*/
        //InMobi.initialize(this, "5c36aeccbb644a6da5fc35b35313e2e3");

        if (mHelper == null) {
            getGameHelper();
        }
        mHelper.setup(this);

    }

    //protected IMInterstitial interstitialInMobi = new IMInterstitial(this, "5c36aeccbb644a6da5fc35b35313e2e3");

    @Override
    protected void onStart() {
        super.onStart();
        mHelper.onStart(this);
        Batch.onStart(this);
        Chartboost.onStart(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        Chartboost.onPause(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mHelper.onStop();
        Batch.onStop(this);
        Chartboost.onStop(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mHelper.onDestroy(this);
        mInterstitial.destroy();
        Batch.onDestroy(this);
        Chartboost.onDestroy(this);
    }
    @Override
    protected void onNewIntent(Intent intent)
    {
        Batch.onNewIntent(this, intent);

        super.onNewIntent(intent);
    }
    @Override
    protected void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        mHelper.onActivityResult(request, response, data);
    }

    protected GoogleApiClient getApiClient() {
        return mHelper.getApiClient();
    }

    protected boolean isSignedIn() {
        return mHelper.isSignedIn();
    }

    protected void beginUserInitiatedSignIn() {
        mHelper.beginUserInitiatedSignIn();
    }

    protected void signOut() {
        mHelper.signOut();
    }

    protected void showAlert(String message) {
        mHelper.makeSimpleDialog(message).show();
    }

    protected void showAlert(String title, String message) {
        mHelper.makeSimpleDialog(title, message).show();
    }

    protected void enableDebugLog(boolean enabled) {
        mDebugLog = true;
        if (mHelper != null) {
            mHelper.enableDebugLog(enabled);
        }
    }

    @Deprecated
    protected void enableDebugLog(boolean enabled, String tag) {
        Log.w(TAG, "BaseGameActivity.enabledDebugLog(bool,String) is " +
                "deprecated. Use enableDebugLog(boolean)");
        enableDebugLog(enabled);
    }

    protected String getInvitationId() {
        return mHelper.getInvitationId();
    }

    protected void reconnectClient() {
        mHelper.reconnectClient();
    }

    protected boolean hasSignInError() {
        return mHelper.hasSignInError();
    }

    protected GameHelper.SignInFailureReason getSignInError() {
        return mHelper.getSignInError();
    }

    //@Override
    /*public void onInterstitialShown(MoPubInterstitial interstitial) {
    	mInterstitial.load();
    }*/
}

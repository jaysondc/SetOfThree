package com.shakeup.setofthree.common.interfaces;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Jayson on 4/14/2017.
 * <p>
 * This interface should be implemented by each Activity that subclasses
 * BaseGameActivity, allowing fragments to get a reference to the
 * GoogleApiClient
 */

public interface GoogleApiClientCallback {
    public GoogleApiClient getGoogleApiClient();
}

package com.example.cheshta.fingerprintauthapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by chesh on 2/9/2018.
 */

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    Context context;

    public FingerprintHandler(Context context) {
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal,0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There was an authentication error "+ errString, false);
    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Authentication failed", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error: "+ helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        this.update("You can now access the app", true);
    }

    private void update(String s, boolean b) {

        TextView tvParaLabel = ((Activity)context).findViewById(R.id.tvParaLabel);
        ImageView ivFingerprint = ((Activity)context).findViewById(R.id.ivFingerprint);

        tvParaLabel.setText(s);

        if(b == false){

            tvParaLabel.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
        }

        else {

            tvParaLabel.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            ivFingerprint.setImageResource(R.mipmap.action_done );
        }
    }
}

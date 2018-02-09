package com.example.cheshta.fingerprintauthapp;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    
    TextView tvHeadingLabel, tvParaLabel;
    ImageView ivFingerprint;

    FingerprintManager fingerprintManager;
    KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvHeadingLabel = findViewById(R.id.tvHeadingLabel);
        tvParaLabel = findViewById(R.id.tvParaLabel);
        ivFingerprint = findViewById(R.id.ivFingerprint);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager.isHardwareDetected()){

                tvParaLabel.setText("Fingerprint not detected in Device");
            }

            else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){

                tvParaLabel.setText("Permission not granted to use fingerprint scanner");
            }

            else if(!keyguardManager.isKeyguardSecure()){

                tvParaLabel.setText("Add lock to your phone in settings");
            }

            else if(!fingerprintManager.hasEnrolledFingerprints()){

                tvParaLabel.setText("You should add atleast one fingerprint to use this feature");
            }

            else {

                tvParaLabel.setText("Place your fingerprint on scanner to access the app");

                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager, null);
            }
        }
    }
}

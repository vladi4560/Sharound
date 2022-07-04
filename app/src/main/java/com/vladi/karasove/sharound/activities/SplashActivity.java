package com.vladi.karasove.sharound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.data.MyUserData;

import java.util.Arrays;
import java.util.List;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;
    private ProgressBar progressBar;
    private boolean alreadyDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViews();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                // FirebaseDB firebaseDB=FirebaseDB.getInstance();
                if (firebaseAuth.getCurrentUser() != null) {
                    //firebaseDB.hasProfile(firebaseAuth.getCurrentUser().getUid());
                    progressBar.setVisibility(View.INVISIBLE);
                    openActivity(MainActivity.class);
                } else {
                    if (!alreadyDone)
                            phoneAuth();
                }
            }
        }, SPLASH_TIME_OUT);

    }

    private void openActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        progressBar = findViewById(R.id.splash_PB);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void phoneAuth() {
        alreadyDone = true;
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build());
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    openActivity(SignUpActivity.class);
                    onSignInResult(result);

                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse r = result.getIdpResponse();
        openActivity(SignUpActivity.class);
        if (result.getResultCode() == RESULT_OK) {
            //FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
        } else {

            Log.d("pttt", result.getIdpResponse().getProviderType() + "\n" + result.getIdpResponse());
        }

    }
}

package com.vladi.karasove.sharound.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.vladi.karasove.sharound.CallBacks.CallBack_UploadImg;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.Validator;
import com.vladi.karasove.sharound.data.MyFirebase;
import com.vladi.karasove.sharound.data.MyUserData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private AppCompatSpinner yearSpinner;
    private String year="";
    private AppCompatImageView profileIMG;
    private MaterialButton signUpBtn;
    private String urlIMG;
    Validator validatorFirstName;
    Validator validatorLastName;
    private MyUserData myUserData;
    private MyFirebase myFirebase;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activuty_sign_up);
        MyUserData.initHelper();
        myUserData= MyUserData.getInstance();
        myFirebase = MyFirebase.getInstance();
        myFirebase.setCallBack_uploadImg(callBack_uploadImg);
        findViews();
        initValidator();
        initButtons();
    }

    private void findViews() {
        firstName = findViewById(R.id.signUP_EDT_firstName);
        lastName = findViewById(R.id.signUP_EDT_lastName);
        yearSpinner = findViewById(R.id.signUP_SPN_yearSpinner);
        fillSpinner();
        signUpBtn = findViewById(R.id.signUP_BTN_signUp);
        floatingActionButton = findViewById(R.id.signUP_BTN_AddItem);
        profileIMG = findViewById(R.id.signUP_IMG_profile);
    }

    private void fillSpinner() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1980; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        yearSpinner.setAdapter(adapter);
    }

    private void initValidator() {
        validatorFirstName = Validator.Builder.make(firstName)
                .addWatcher(new Validator.Watcher_StringEmpty("Name Cannot Be Empty"))
                .addWatcher(new Validator.Watcher_String("Name Contains Only Characters"))
                .build();

        validatorLastName = Validator.Builder.make(lastName)
                .addWatcher(new Validator.Watcher_StringEmpty("Name Cannot Be Empty"))
                .addWatcher(new Validator.Watcher_String("Name Contains Only Characters"))
                .build();

    }
    private void initButtons() {

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatorFirstName.validateIt() && validatorLastName.validateIt()) {
                    myUserData.setMyUser(firstName.getEditText().getText().toString(), lastName.getEditText().getText().toString(),year, urlIMG);
                    openActivity(MainActivity.class);
                } else {
                    Toast.makeText(SignUpActivity.this, "There Are Errors", Toast.LENGTH_LONG).show();
                }

            }
        });
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SignUpActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri resultUri = data.getData();
        profileIMG.setImageURI(resultUri);
        myUserData.uploadImageProfile(resultUri,this);
    }

    private void openActivity(Class activity){
        Intent intent = new Intent(this, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
    public CallBack_UploadImg callBack_uploadImg= new CallBack_UploadImg() {
        @Override
        public void urlReady(String uri, Activity activity) {
            urlIMG=uri;
        }
    };

}



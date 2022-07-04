package com.vladi.karasove.sharound.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.data.MyUserData;

public class ProfileFragment extends Fragment {
    private AppCompatActivity activity;
    private TextView firstName;
    private TextView lastName;
    private TextView phoneNum;
    private TextView birthYear;
    private MyUserData myUserData;
    public Fragment setActivity(AppCompatActivity activity) {
        this.activity = activity;
        myUserData= MyUserData.getInstance();
        return this;
    }

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        initButtons();
        return view;
    }

    private void initButtons() {
    }

    private void findViews(View view) {
        firstName=view.findViewById(R.id.profile_TXT_firstName);
        lastName=view.findViewById(R.id.profile_TXT_lastName);
        phoneNum=view.findViewById(R.id.profile_TXT_phoneNum);
        birthYear=view.findViewById(R.id.profile_TXT_birthYear);
    }
    @Override
    public void onStart() {
        super.onStart();
        loadUserDetails();
    }

    private void loadUserDetails() {
        firstName.setText(myUserData.getMyUser().getUserFirstName());
        lastName.setText(myUserData.getMyUser().getUserLastName());
        phoneNum.setText(myUserData.getMyUser().getUserPhoneNumber());
        birthYear.setText(myUserData.getMyUser().getUserBirthYear());
    }
}

package com.example.tracking.controllers;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tracking.R;
import com.example.tracking.db.AppDatabase;
import com.example.tracking.network.ApiService;
import com.example.tracking.utils.EventObserver;
import com.example.tracking.viewmodels.AddTrackingViewModel;
import com.example.tracking.viewmodels.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class AddTrackingActivity extends AppCompatActivity {
    private AddTrackingViewModel mViewModel;
    private EditText mTrackingNumInput, mMemoInput;
    private Button mSubmitBtn;
    private View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking);
        setSupportActionBar(findViewById(R.id.addTracking_toolbar));
        initViews();

        // Make a new instance of viewModel using ViewModelProvider and providing its dependencies
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(
                AppDatabase.getInstance(getApplicationContext()),
                ApiService.getInstance(getApplicationContext())
        )).get(AddTrackingViewModel.class);


        mSubmitBtn.setOnClickListener(view -> {
            String trackingNum = mTrackingNumInput.getText().toString().trim();
            String memo = mMemoInput.getText().toString().trim();
            mViewModel.onSaveTracking(trackingNum, memo);
        });


        mViewModel.mSavedEvent.observe(this, new EventObserver<>(data -> {
            Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }));

        mViewModel.mErrorEvent.observe(this, new EventObserver<>(message -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mRootView.getWindowToken(), 0);
            Snackbar.make(mRootView, message, Snackbar.LENGTH_SHORT).show();
        }));

    }

    private void initViews() {
        mTrackingNumInput = findViewById(R.id.addTracking_editText_trackingNum);
        mMemoInput = findViewById(R.id.addTracking_editText_memo);
        mSubmitBtn = findViewById(R.id.addTracking_button_submit);
        mRootView = findViewById(R.id.addTracking_root);
    }

}

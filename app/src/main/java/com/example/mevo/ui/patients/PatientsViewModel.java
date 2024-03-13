package com.example.mevo.ui.patients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PatientsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PatientsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Patients fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
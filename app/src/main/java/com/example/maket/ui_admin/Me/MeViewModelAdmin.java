package com.example.maket.ui_admin.Me;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeViewModelAdmin extends ViewModel {

    private MutableLiveData<String> mText;

    public MeViewModelAdmin() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
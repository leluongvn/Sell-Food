package com.example.maket.ui_admin.add_food;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddFoodVIewModelAdmin extends ViewModel {
    private MutableLiveData<String> mText;

    public AddFoodVIewModelAdmin() {
        mText = new MutableLiveData<>();
        mText.setValue("This is add fodd frament");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

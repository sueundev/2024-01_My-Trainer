package com.example.mytrainer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> upperBodyCount = new MutableLiveData<>();
    private final MutableLiveData<String> lowerBodyCount = new MutableLiveData<>();

    public void setUpperBodyCount(String count)
    {
        upperBodyCount.setValue(count);
    }

    public LiveData<String> getUpperBodyCount()
    {
        return upperBodyCount;
    }

    public void setLowerBodyCount(String count)
    {
        lowerBodyCount.setValue(count);
    }

    public LiveData<String> getLowerBodyCount()
    {
        return lowerBodyCount;
    }
}
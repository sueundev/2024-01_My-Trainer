package com.example.mytrainer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> upperBodyCount = new MutableLiveData<>();
    private final MutableLiveData<String> lowerBodyCount = new MutableLiveData<>();
    private String lastUpdateDate = ""; // 마지막 업데이트 날짜를 저장할 변수

    public void checkDateAndUpdate() {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (!currentDate.equals(lastUpdateDate)) {
            // 날짜가 변경되었다면 횟수 초기화
            setUpperBodyCount("0");
            setLowerBodyCount("0");
            lastUpdateDate = currentDate; // 마지막 업데이트 날짜 갱신
        }
    }

    public void setUpperBodyCount(String count) {
        upperBodyCount.setValue(count);
    }

    public LiveData<String> getUpperBodyCount() {
        checkDateAndUpdate(); // 횟수를 가져올 때 날짜 확인
        return upperBodyCount;
    }

    public void setLowerBodyCount(String count) {
        lowerBodyCount.setValue(count);
    }

    public LiveData<String> getLowerBodyCount() {
        checkDateAndUpdate(); // 횟수를 가져올 때 날짜 확인
        return lowerBodyCount;
    }
}
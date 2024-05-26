package com.example.mytrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class myPageActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editAge;
    private RadioGroup radioGroupGender;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private EditText editWeight;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        // View 초기화
        editName = findViewById(R.id.edit_name);
        editAge = findViewById(R.id.edit_age);
        radioGroupGender = findViewById(R.id.radio_group_gender);
        radioMale = findViewById(R.id.radio_male);
        radioFemale = findViewById(R.id.radio_female);
        editWeight = findViewById(R.id.edit_weight);
        saveButton = findViewById(R.id.save_button);

        // 저장 버튼 클릭 리스너 설정
        saveButton.setOnClickListener(view -> {
            // 여기에 사용자가 입력한 데이터를 처리하거나 저장하는 코드를 추가할 수 있습니다.

            // MainActivity로 이동
            Intent intent = new Intent(myPageActivity.this, MainActivity.class);
            startActivity(intent);

            // 현재 액티비티 종료 (선택적)
            finish();
        });
    }
}
package com.example.mytrainer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class exercise_upper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_upper_body);

        //화살표 아이디 연결
        ImageView leftArrowImg = findViewById(R.id.left_arrow_img);
        //화살표 클릭하면 전 액티비티 화면으로 돌아감
        leftArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
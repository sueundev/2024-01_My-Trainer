package com.example.mytrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class myPageFragment extends Fragment {
    private EditText editName;
    private EditText editAge;
    private EditText editWeight;
    private RadioGroup radioGroupGender;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private Button saveButton;

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        // View 초기화
        editName = view.findViewById(R.id.edit_name);
        editAge = view.findViewById(R.id.edit_age);
        editWeight = view.findViewById(R.id.edit_weight);
        radioGroupGender = view.findViewById(R.id.radio_group_gender);
        radioMale = view.findViewById(R.id.radio_male);
        radioFemale = view.findViewById(R.id.radio_female);
        saveButton = view.findViewById(R.id.save_button);

        // SharedPreferences 초기화
        sharedPreferences = getActivity().getSharedPreferences("MypagePreferences", Context.MODE_PRIVATE);

        // 기존 데이터 불러오기
        loadUserData();

        // 저장 버튼 클릭 리스너 설정
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자 입력 데이터 가져오기
                String name = editName.getText().toString();
                String age = editAge.getText().toString();
                String gender = getSelectedGender();
                String weight = editWeight.getText().toString();

                // 데이터 저장
                saveUserData(name, age, gender, weight);

                // 저장 완료 메시지
                Toast.makeText(getActivity(), "저장되었습니다: " + name + ", " + age + ", " + gender + ", " + weight, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void saveUserData(String name, String age, String gender, String weight) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("age", age);
        editor.putString("gender", gender);
        editor.putString("weight", weight);
        editor.apply();
    }

    private void loadUserData() {
        String name = sharedPreferences.getString("name", "");
        String age = sharedPreferences.getString("age", "");
        String gender = sharedPreferences.getString("gender", "");
        String weight = sharedPreferences.getString("weight", "");

        editName.setText(name);
        editAge.setText(age);
        editWeight.setText(weight);
        setSelectedGender(gender);
    }

    private String getSelectedGender() {
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedId == R.id.radio_male) {
            return "Male";
        } else if (selectedId == R.id.radio_female) {
            return "Female";
        } else {
            return "";
        }
    }

    private void setSelectedGender(String gender) {
        if (gender.equals("Male")) {
            radioMale.setChecked(true);
        } else if (gender.equals("Female")) {
            radioFemale.setChecked(true);
        }
    }
}
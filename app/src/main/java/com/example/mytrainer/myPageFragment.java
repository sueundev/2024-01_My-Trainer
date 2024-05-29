package com.example.mytrainer;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
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
import android.widget.TextView;

public class myPageFragment extends Fragment {
    private TextView userIdTextView;
    private TextView nameAgeTextView;
    private TextView genderWeightTextView;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        userIdTextView = view.findViewById(R.id.userIdTextView);
        nameAgeTextView = view.findViewById(R.id.nameAgeTextView);
        genderWeightTextView = view.findViewById(R.id.genderWeightTextView);

        sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String idText = sharedPreferences.getString("email", "No Email");

        loadProfileData();
        userIdTextView.setText(idText);

        view.findViewById(R.id.profileLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        return view;
    }

    private void loadProfileData() {
        String name = sharedPreferences.getString("name", "John Doe");
        String age = sharedPreferences.getString("age", "25");
        String gender = sharedPreferences.getString("gender", "Male");
        String weight = sharedPreferences.getString("weight", "70kg");

        nameAgeTextView.setText(name + " / " + age);
        genderWeightTextView.setText(gender + " / " + weight);
    }

    public void showEditDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_edit_profile);

        EditText editName = dialog.findViewById(R.id.editName);
        EditText editAge = dialog.findViewById(R.id.editAge);
        RadioGroup genderRadioGroup = dialog.findViewById(R.id.radio_group_gender);
        EditText editWeight = dialog.findViewById(R.id.editWeight);
        Button saveButton = dialog.findViewById(R.id.saveButton);

        // 기존 값을 다이얼로그에 설정합니다.
        editName.setText(sharedPreferences.getString("name", ""));
        editAge.setText(sharedPreferences.getString("age", ""));
        String gender = sharedPreferences.getString("gender", "Male");
        if (gender.equals("Male")) {
            genderRadioGroup.check(R.id.radio_male);
        } else {
            genderRadioGroup.check(R.id.radio_female);
        }
        editWeight.setText(sharedPreferences.getString("weight", ""));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String age = editAge.getText().toString();
                String gender = ((RadioButton) dialog.findViewById(genderRadioGroup.getCheckedRadioButtonId())).getText().toString();
                String weight = editWeight.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("age", age);
                editor.putString("gender", gender);
                editor.putString("weight", weight);
                editor.apply();

                nameAgeTextView.setText(name + " / " + age);
                genderWeightTextView.setText(gender + " / " + weight);

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

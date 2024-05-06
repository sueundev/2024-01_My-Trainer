package com.example.mytrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class exeFragment extends Fragment {

    private Button buttonUpperBody;
    private Button buttonLowerBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exe, container, false);

        // 버튼 참조 초기화
        buttonUpperBody = view.findViewById(R.id.button_upper_body);
        buttonLowerBody = view.findViewById(R.id.button_lower_body);

        // 상체 운동 버튼에 클릭 이벤트 리스너 설정
        buttonUpperBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // exercise_upper 액티비티로 전환
                Intent intent = new Intent(getActivity(), exercise_upper.class);
                startActivity(intent);
            }
        });

        // 하체 운동 버튼에 클릭 이벤트 리스너 설정
        buttonLowerBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // exercise_lower 액티비티로 전환
                Intent intent = new Intent(getActivity(), exercise_lower.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
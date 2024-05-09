package com.example.mytrainer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class recordFragment extends Fragment {
    Button exebtn;
    Button kcalbtn;
    Button weibtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record, container, false);

        exebtn = v.findViewById(R.id.exe_btn);
        kcalbtn = v.findViewById(R.id.kcal_btn);
        weibtn = v.findViewById(R.id.weight_btn);

        exebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), exe_record_Activity.class);
                startActivity(intent);
            }
        });

        kcalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Kcal_record_Activity.class);
                startActivity(intent);
            }
        });

        weibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Weight_record_Activity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
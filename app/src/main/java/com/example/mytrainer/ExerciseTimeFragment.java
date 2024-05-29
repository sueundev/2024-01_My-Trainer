package com.example.mytrainer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExerciseTimeFragment extends Fragment {
    private TextView textViewSelectedDate;
    private String selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_time, container, false);

        textViewSelectedDate = view.findViewById(R.id.text_view);

        Bundle args = getArguments();
        if (args != null) {
            selectedDate = args.getString("selected_date");
            textViewSelectedDate.setText(selectedDate);
        }

        return view;
    }
}
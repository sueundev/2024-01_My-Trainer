package com.example.mytrainer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class recordFragment extends Fragment {

    private TextView textViewDate;
    private CalendarView calendarView;
    private Button buttonSave;
    private String selectedDate;
    private SharedPreferences sharedPreferences;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        // View 초기화
        textViewDate = view.findViewById(R.id.text_view_date);
        calendarView = view.findViewById(R.id.calendar_view);
        buttonSave = view.findViewById(R.id.button_save);

        // SharedPreferences 초기화
        sharedPreferences = context.getSharedPreferences("CalendarData", Context.MODE_PRIVATE);

        // 초기 선택된 날짜 설정
        selectedDate = getCurrentDate();
        textViewDate.setText(selectedDate);

        // 캘린더뷰 날짜 변경 리스너 설정
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                selectedDate = sdf.format(calendar.getTime());
                textViewDate.setText(selectedDate);
            }
        });

        // 저장 버튼 클릭 리스너 설정
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그 보여주기
                showRecordDialog();
            }
        });

        return view;
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    private void showRecordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("기록 선택");
        builder.setItems(new CharSequence[]{"운동시간", "칼로리", "몸무게"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment = null;
                        switch (which) {
                            case 0:
                                fragment = new ExerciseTimeFragment();
                                break;
                            case 1:
                                fragment = new CaloriesFragment();
                                break;
                            case 2:
                                fragment = new WeightFragment();
                                break;
                        }
                        if (fragment != null) {
                            navigateToFragment(fragment, selectedDate);
                        }
                    }
                });
        builder.create().show();
    }

    private void navigateToFragment(Fragment fragment, String date) {
        Bundle bundle = new Bundle();
        bundle.putString("selected_date", date);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
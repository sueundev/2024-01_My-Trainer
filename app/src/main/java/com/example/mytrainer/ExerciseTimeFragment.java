// ExerciseTimeFragment.java
package com.example.mytrainer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseTimeFragment extends Fragment {
    private TextView textViewSelectedDate;
    private String selectedDate;
    private ExerciseLogDao exerciseLogDao;
    private ExerciseLogAdapter exerciseLogAdapter;
    private List<ExerciseLog> exerciseLogs;
    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_time, container, false);

        textViewSelectedDate = view.findViewById(R.id.text_view);
        Button btnRecordExercise = view.findViewById(R.id.btn_record_exercise);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            selectedDate = args.getString("selected_date");
            textViewSelectedDate.setText(selectedDate);
        }

        exerciseLogDao = new ExerciseLogDao(getContext());
        exerciseLogs = exerciseLogDao.getLogsByDate(selectedDate);
        exerciseLogAdapter = new ExerciseLogAdapter(exerciseLogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(exerciseLogAdapter);

        btnRecordExercise.setOnClickListener(v -> showRecordExerciseDialog());

        return view;
    }
    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void showRecordExerciseDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_record_exercise);
        EditText etUpperBody = dialog.findViewById(R.id.et_upper_body);
        EditText etLowerBody = dialog.findViewById(R.id.et_lower_body);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> {
            String upperBody = etUpperBody.getText().toString();
            String lowerBody = etLowerBody.getText().toString();
            long currentTimeMillis = System.currentTimeMillis();

            ExerciseLog log = new ExerciseLog(currentTimeMillis, upperBody, lowerBody, selectedDate);
            exerciseLogDao.insertLog(log);
            exerciseLogs.add(log);
            exerciseLogAdapter.notifyDataSetChanged();

            // 상체 운동과 하체 운동 값을 ViewModel에 설정
            sharedViewModel.setUpperBodyCount(log.getUpperBodyCount());
            sharedViewModel.setLowerBodyCount(log.getLowerBodyCount());

            dialog.dismiss();
        });

        dialog.show();
    }
}

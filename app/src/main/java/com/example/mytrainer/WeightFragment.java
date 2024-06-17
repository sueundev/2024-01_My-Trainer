package com.example.mytrainer;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightFragment extends Fragment {
    private TextView textViewSelectedDate;
    private TextView textViewWeight;
    private TextView textViewMuscle;
    private TextView textViewFat;
    private String selectedDate;
    private LineChart lineChart;
    private Button btnRecordWeight;
    private Map<String, Float[]> weightData = new HashMap<>();
    private SharedViewModel sharedViewModel;
    private List<String> sortedDates; // 데이터를 정렬된 순서로 보관하는 리스트

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        textViewSelectedDate = view.findViewById(R.id.text_view);
        textViewWeight = view.findViewById(R.id.text_view_weight);
        textViewMuscle = view.findViewById(R.id.text_view_muscle);
        textViewFat = view.findViewById(R.id.text_view_fat);
        lineChart = view.findViewById(R.id.line_chart);
        btnRecordWeight = view.findViewById(R.id.btn_record_weight);

        Bundle args = getArguments();
        if (args != null) {
            selectedDate = args.getString("selected_date");
            textViewSelectedDate.setText(selectedDate);
        }

        btnRecordWeight.setOnClickListener(v -> showRecordDialog());

        // 초기 데이터
        weightData.put("2024-05-28", new Float[]{85f, 30f, 10f});
        weightData.put("2024-05-27", new Float[]{84f, 29f, 11f});
        weightData.put("2024-05-26", new Float[]{82f, 28f, 12f});
        weightData.put("2024-05-25", new Float[]{80f, 27f, 13f});
        weightData.put("2024-05-24", new Float[]{79f, 26f, 14f});
        weightData.put("2024-05-23", new Float[]{77f, 25f, 15f});
        weightData.put("2024-05-22", new Float[]{77f, 24f, 16f});

        updateChart();

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int index = (int) e.getX();
                if (index < sortedDates.size()) {
                    String date = sortedDates.get(index);
                    Float[] values = weightData.get(date);
                    if (values != null) {
                        textViewWeight.setText(values[0] + "kg");
                        textViewMuscle.setText(values[1] + "kg");
                        textViewFat.setText(values[2] + "kg");
                    }
                }
            }

            @Override
            public void onNothingSelected() {
                textViewWeight.setText("");
                textViewMuscle.setText("");
                textViewFat.setText("");
            }
        });

        // 마지막 값 설정
        setLastValue();

        return view;
    }

    private void showRecordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_record_weight, null);

        final EditText etWeight = dialogView.findViewById(R.id.et_weight);
        final EditText etMuscle = dialogView.findViewById(R.id.et_muscle);
        final EditText etFat = dialogView.findViewById(R.id.et_fat);
        final Button btnSave = dialogView.findViewById(R.id.btn_save);
        final Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        AlertDialog dialog = builder.setView(dialogView)
                .setTitle("기록 입력")
                .create();

        btnSave.setOnClickListener(v -> {
            float weight = Float.parseFloat(etWeight.getText().toString());
            float muscle = Float.parseFloat(etMuscle.getText().toString());
            float fat = Float.parseFloat(etFat.getText().toString());
            weightData.put(selectedDate, new Float[]{weight, muscle, fat});
            sharedViewModel.setWeight(weight);
            sharedViewModel.setMuscle(muscle);
            sharedViewModel.setFat(fat);
            updateChart();
            setLastValue(); // 새로운 값을 입력한 후 마지막 값을 설정
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void updateChart() {
        List<Map.Entry<String, Float[]>> sortedData = new ArrayList<>(weightData.entrySet());
        Collections.sort(sortedData, new Comparator<Map.Entry<String, Float[]>>() {
            @Override
            public int compare(Map.Entry<String, Float[]> o1, Map.Entry<String, Float[]> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        List<Entry> entries = new ArrayList<>();
        sortedDates = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Float[]> entry : sortedData) {
            entries.add(new Entry(index++, entry.getValue()[0]));
            sortedDates.add(entry.getKey());
        }

        LineDataSet dataSet = new LineDataSet(entries, "Weight");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);  // Cubic lines 설정
        dataSet.setColor(Color.parseColor("#FFFB3F4A"));
        dataSet.setValueTextColor(Color.BLACK);

        // 선 아래쪽에 색을 채우기 위한 설정
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#FFCDD0"));  // 채울 색 설정
        dataSet.setFillAlpha(50);  // 투명도 설정 (0-255)

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // X축과 Y축의 숫자 레이블 제거
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);  // X축의 그리드 라인 제거

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawGridLines(false);  // 왼쪽 Y축의 그리드 라인 제거

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);  // 오른쪽 Y축의 그리드 라인 제거

        // 그래프 설명 제거
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);

        lineChart.invalidate(); // 차트를 갱신합니다.
    }

    private void setLastValue() {
        if (sortedDates != null && !sortedDates.isEmpty()) {
            String lastDate = sortedDates.get(sortedDates.size() - 1);
            Float[] lastValues = weightData.get(lastDate);
            if (lastValues != null) {
                textViewWeight.setText(lastValues[0] + "kg");
                textViewMuscle.setText(lastValues[1] + "kg");
                textViewFat.setText(lastValues[2] + "kg");
            }
        }
    }
}

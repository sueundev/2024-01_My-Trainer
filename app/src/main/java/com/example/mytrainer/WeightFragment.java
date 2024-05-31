package com.example.mytrainer;

import android.app.AlertDialog;
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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);

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
        weightData.put("2024-05-28", new Float[]{75f, 30f, 10f});
        weightData.put("2024-05-27", new Float[]{73f, 29f, 11f});
        weightData.put("2024-05-26", new Float[]{70f, 28f, 12f});

        updateChart();

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int index = (int) e.getX();
                String date = (new ArrayList<>(weightData.keySet())).get(index);
                Float[] values = weightData.get(date);
                if (values != null) {
                    textViewWeight.setText(values[0] + "kg");
                    textViewMuscle.setText(values[1] + "kg");
                    textViewFat.setText(values[2] + "kg");
                }
            }

            @Override
            public void onNothingSelected() {
                textViewWeight.setText("");
                textViewMuscle.setText("");
                textViewFat.setText("");
            }
        });

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
            updateChart();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
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
        final List<String> dates = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Float[]> entry : sortedData) {
            entries.add(new Entry(index++, entry.getValue()[0]));
            dates.add(entry.getKey());
        }

        LineDataSet dataSet = new LineDataSet(entries, "Weight");
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                return dates.size() > index ? dates.get(index) : "";
            }
        });

        lineChart.invalidate();
    }
}

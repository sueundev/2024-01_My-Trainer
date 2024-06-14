package com.example.mytrainer;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class myPageFragment extends Fragment {
    private LineChart lineChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        lineChart = view.findViewById(R.id.line_chart);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 85f));
        entries.add(new Entry(1f, 84f));
        entries.add(new Entry(2f, 82f));
        entries.add(new Entry(3f, 80f));
        entries.add(new Entry(4f, 79f));
        entries.add(new Entry(5f, 77f));
        entries.add(new Entry(6f, 77f));

        LineDataSet dataSet = new LineDataSet(entries, "weight");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);  // Cubic lines 설정
        dataSet.setColor(android.graphics.Color.BLUE);
        dataSet.setValueTextColor(android.graphics.Color.BLACK);

        // 선 아래쪽에 색을 채우기 위한 설정
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.BLUE);  // 채울 색 설정
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

        return view;
    }
}
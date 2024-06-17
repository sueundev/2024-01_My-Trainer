package com.example.mytrainer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CaloriesFragment extends Fragment {
    private TextView textViewSelectedDate;
    private String selectedDate;
    private RecyclerView recyclerView;
    private CaloriesAdapter adapter;
    private ArrayList<FoodItem> foodList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calories, container, false);

        textViewSelectedDate = view.findViewById(R.id.text_view);
        recyclerView = view.findViewById(R.id.recycler_view);
        Button btnRecordCal = view.findViewById(R.id.btn_record_cal);

        btnRecordCal.setOnClickListener(v -> showRecordCaloriesDialog());

        Bundle args = getArguments();
        if (args != null) {
            selectedDate = args.getString("selected_date");
            textViewSelectedDate.setText(selectedDate);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CaloriesAdapter(groupFoodByDate(foodList));
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            selectedDate = savedInstanceState.getString("selectedDate");
            textViewSelectedDate.setText(selectedDate);
            foodList = (ArrayList<FoodItem>) savedInstanceState.getSerializable("foodList");
            adapter.updateData(groupFoodByDate(foodList));
        }

        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            ArrayList<FoodItem> result = (ArrayList<FoodItem>) bundle.getSerializable("foodList");
            if (result != null) {
                foodList.addAll(result);
                adapter.updateData(groupFoodByDate(foodList));
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedDate", selectedDate);
        outState.putSerializable("foodList", foodList);
    }

    private void showRecordCaloriesDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_record_calories);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout foodContainer = dialog.findViewById(R.id.food_container);
        Button addFoodButton = dialog.findViewById(R.id.add_food_button);
        Button saveFoodButton = dialog.findViewById(R.id.save_food_button);

        addFoodButton.setOnClickListener(v -> addFoodEntry(foodContainer));

        saveFoodButton.setOnClickListener(v -> {
            saveFoodEntries(foodContainer);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void addFoodEntry(LinearLayout foodContainer) {
        View foodEntryView = getLayoutInflater().inflate(R.layout.food_entry, foodContainer, false);
        foodContainer.addView(foodEntryView);
    }

    private void saveFoodEntries(LinearLayout foodContainer) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd (E) HH:mm", Locale.getDefault()).format(new Date());

        for (int i = 0; i < foodContainer.getChildCount(); i++) {
            View foodEntryView = foodContainer.getChildAt(i);

            EditText foodName = foodEntryView.findViewById(R.id.food_name);
            EditText foodCalories = foodEntryView.findViewById(R.id.food_calories);
            EditText foodQuantity = foodEntryView.findViewById(R.id.food_quantity);

            String name = foodName.getText().toString();
            int calories = Integer.parseInt(foodCalories.getText().toString());
            int quantity = Integer.parseInt(foodQuantity.getText().toString());

            foodList.add(new FoodItem(name, calories, quantity, currentDate));
        }

        adapter.updateData(groupFoodByDate(foodList));
    }

    private Map<String, List<FoodItem>> groupFoodByDate(List<FoodItem> foodList) {
        Map<String, List<FoodItem>> groupedFood = new LinkedHashMap<>();
        for (FoodItem item : foodList) {
            String date = item.getDate();
            if (!groupedFood.containsKey(date)) {
                groupedFood.put(date, new ArrayList<>());
            }
            groupedFood.get(date).add(item);
        }
        return groupedFood;
    }
}

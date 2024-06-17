package com.example.mytrainer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CaloriesAdapter extends RecyclerView.Adapter<CaloriesAdapter.CaloriesViewHolder> {

    private List<Map.Entry<String, List<FoodItem>>> groupedFoodList;

    public CaloriesAdapter(Map<String, List<FoodItem>> groupedFood) {
        this.groupedFoodList = new ArrayList<>(groupedFood.entrySet());
    }

    @NonNull
    @Override
    public CaloriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_group, parent, false);
        return new CaloriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaloriesViewHolder holder, int position) {
        Map.Entry<String, List<FoodItem>> entry = groupedFoodList.get(position);
        String date = entry.getKey();
        List<FoodItem> foodItems = entry.getValue();

        holder.dateTextView.setText(date);
        StringBuilder itemsText = new StringBuilder();
        for (FoodItem item : foodItems) {
            itemsText.append(item.getName()).append(" ").append(item.getCalories()).append("kcal x").append(item.getQuantity()).append("\n");
        }
        holder.itemsTextView.setText(itemsText.toString().trim());
    }

    @Override
    public int getItemCount() {
        return groupedFoodList.size();
    }

    public void updateData(Map<String, List<FoodItem>> groupedFood) {
        this.groupedFoodList = new ArrayList<>(groupedFood.entrySet());
        notifyDataSetChanged();
    }

    public static class CaloriesViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView itemsTextView;

        public CaloriesViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            itemsTextView = itemView.findViewById(R.id.items_text_view);
        }
    }
}

package com.example.mytrainer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExerciseLogAdapter extends RecyclerView.Adapter<ExerciseLogAdapter.ViewHolder> {
    private List<ExerciseLog> exerciseLogs;

    public ExerciseLogAdapter(List<ExerciseLog> exerciseLogs) {
        this.exerciseLogs = exerciseLogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseLog log = exerciseLogs.get(position);
        holder.tvTime.setText(formatTime(log.getTimestamp()));
        holder.tvUpperBody.setText("상체 운동 x " + log.getUpperBodyCount());
        holder.tvLowerBody.setText("하체 운동 x " + log.getLowerBodyCount());
    }

    @Override
    public int getItemCount() {
        return exerciseLogs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvUpperBody, tvLowerBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvUpperBody = itemView.findViewById(R.id.tv_upper_body);
            tvLowerBody = itemView.findViewById(R.id.tv_lower_body);
        }
    }

    private String formatTime(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MM. dd (E) HH:mm", Locale.getDefault());
        return sdf.format(new Date(timeInMillis));
    }
}

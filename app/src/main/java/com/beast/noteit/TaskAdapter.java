package com.beast.noteit;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList;
    private final OnTaskCompleteListener listener;

    public TaskAdapter(List<Task> taskList, OnTaskCompleteListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task2, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task, listener);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTaskName, tvTaskXP, tvTaskDifficulty, tvTaskReward;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvTaskXP = itemView.findViewById(R.id.tvTaskXP);
            tvTaskDifficulty = itemView.findViewById(R.id.tvTaskDifficulty);
            tvTaskReward = itemView.findViewById(R.id.tvTaskReward);
        }

        @SuppressLint("DefaultLocale")
        public void bind(Task task, OnTaskCompleteListener listener) {
            tvTaskName.setText(task.getTask());
            tvTaskXP.setText(String.format("XP: %d", task.getXp()));
            tvTaskDifficulty.setText(String.format("Difficulty: %d", task.getDifficulty()));
            tvTaskReward.setText(task.getReward());

            itemView.setOnClickListener(v -> listener.onTaskCompleted(getAdapterPosition()));
        }
    }

    public interface OnTaskCompleteListener {
        void onTaskCompleted(int position);
    }
}

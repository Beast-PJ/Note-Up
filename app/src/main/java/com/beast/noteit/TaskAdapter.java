package com.beast.noteit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private TaskCompleteListener taskCompleteListener;

    public TaskAdapter(List<Task> taskList, TaskCompleteListener taskCompleteListener) {
        this.taskList = taskList;
        this.taskCompleteListener = taskCompleteListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTaskName.setText(task.getTask());
        holder.tvTaskDetails.setText(task.getDetails());
        holder.tvTaskXP.setText(String.format("XP: %d", task.getXp()));
        holder.tvTaskDifficulty.setText(String.format("Difficulty: %d", task.getDifficulty()));
        holder.tvTaskReward.setText(String.format("Reward: %s", task.getReward()));

        holder.itemView.setOnClickListener(v -> taskCompleteListener.onTaskComplete(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskName, tvTaskDetails, tvTaskXP, tvTaskDifficulty, tvTaskReward;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvTaskDetails = itemView.findViewById(R.id.tvTaskDetails);
            tvTaskXP = itemView.findViewById(R.id.tvTaskXP);
            tvTaskDifficulty = itemView.findViewById(R.id.tvTaskDifficulty);
            tvTaskReward = itemView.findViewById(R.id.tvTaskReward);
        }
    }

    public interface TaskCompleteListener {
        void onTaskComplete(int position);
    }
}

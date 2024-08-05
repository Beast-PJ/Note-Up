package com.beast.noteit;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> tasks;
    private OnTaskClickListener onTaskClickListener;

    public interface OnTaskClickListener {
        void onTaskClick(int position);
    }

    public TaskAdapter(List<Task> tasks, OnTaskClickListener onTaskClickListener) {
        this.tasks = tasks;
        this.onTaskClickListener = onTaskClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view, onTaskClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvTask.setText(task.getTask());
        holder.tvTaskDetails.setText("Difficulty: " + task.getDifficulty() + " | XP: " + task.getXp() + " | Reward: " + task.getReward());
        holder.itemView.setBackgroundColor(task.isCompleted() ? Color.GREEN : Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTask;
        public TextView tvTaskDetails;
        public ImageView ivComplete;
        OnTaskClickListener onTaskClickListener;

        public ViewHolder(View itemView, OnTaskClickListener onTaskClickListener) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTask);
            tvTaskDetails = itemView.findViewById(R.id.tvTaskDetails);
            ivComplete = itemView.findViewById(R.id.ivComplete);
            this.onTaskClickListener = onTaskClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onTaskClickListener.onTaskClick(getAdapterPosition());
        }
    }
}

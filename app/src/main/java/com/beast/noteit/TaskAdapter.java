package com.beast.noteit;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
        holder.itemView.setBackgroundColor(task.isCompleted() ? Color.GREEN : Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTask;
        OnTaskClickListener onTaskClickListener;

        public ViewHolder(View itemView, OnTaskClickListener onTaskClickListener) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTask);
            this.onTaskClickListener = onTaskClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onTaskClickListener.onTaskClick(getAdapterPosition());
        }
    }
}

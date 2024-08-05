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


// TaskListAdapter.java

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> taskItems;
    private Context context;

    public TaskAdapter(List<Task> taskItems, Context context) {
        this.taskItems = taskItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task taskItem = taskItems.get(position);
        holder.titleTextView.setText(taskItem.getTitle());
        holder.descriptionTextView.setText(taskItem.getDescription());
        holder.levelTextView.setText(taskItem.getLevel().toString());
        holder.completedCheckBox.setChecked(taskItem.isCompleted());
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView levelTextView;
        public CheckBox completedCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
            levelTextView = itemView.findViewById(R.id.level_text_view);
            completedCheckBox = itemView.findViewById(R.id.completed_check_box);
        }
    }
}

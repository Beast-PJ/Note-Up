package com.beast.noteit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


// TaskListActivity.java

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvTasks);
        adapter = new TaskAdapter(taskItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add some sample data
        taskItems = new ArrayList<>();
        taskItems.add(new Task("Task 1", "Description 1", Level.EASY));
        taskItems.add(new Task("Task 2", "Description 2", Level.MEDIUM));
        taskItems.add(new Task("Task 3", "Description 3", Level.HARD));
        adapter.notifyDataSetChanged();
    }
}
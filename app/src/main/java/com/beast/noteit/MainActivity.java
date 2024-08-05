package com.beast.noteit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rvTasks;
    private TaskAdapter taskAdapter;
    private List<Task> tasks;
    private int level = 1;
    private int xp = 0;
    private TextView tvLevel;
    private TextView tvXP;
    private FloatingActionButton btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTasks = findViewById(R.id.rvTasks);
        tvLevel = findViewById(R.id.tvLevel);
        tvXP = findViewById(R.id.tvXP);
        btnAddTask = findViewById(R.id.btnAddTask);
        tasks = new ArrayList<>();

        taskAdapter = new TaskAdapter(tasks, position -> {
            Task task = tasks.get(position);
            if (!task.isCompleted()) {
                task.setCompleted(true);
                taskAdapter.notifyItemChanged(position);
                updateXP(20); // Assign XP per task
            }
        });

        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String task = data.getStringExtra("task");
            tasks.add(new Task(task));
            taskAdapter.notifyItemInserted(tasks.size() - 1);
        }
    }

    private void updateXP(int points) {
        xp += points;
        if (xp >= 100) {
            xp -= 100;
            level++;
            Toast.makeText(this, "Level Up!", Toast.LENGTH_SHORT).show();
        }
        tvLevel.setText("Level: " + level);
        tvXP.setText("XP: " + xp + "/100");
    }
}

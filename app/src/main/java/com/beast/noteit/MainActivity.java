package com.beast.noteit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ADD_TASK_REQUEST = 123;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView tvLevel, tvXP;
    private ProgressBar progressBarXP;
    private RecyclerView rvTasks;
    private FloatingActionButton btnAddTask;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    private int level = 1;
    private int currentXP = 0;
    private int xpForNextLevel = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvLevel = findViewById(R.id.tvLevel);
        tvXP = findViewById(R.id.tvXP);
        progressBarXP = findViewById(R.id.progressBarXP);
        rvTasks = findViewById(R.id.rvTasks);
        btnAddTask = findViewById(R.id.btnAddTask);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, this::completeTask);

        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, ADD_TASK_REQUEST);
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, findViewById(R.id.topAppBar), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        updateUI();
    }

    private void completeTask(int position) {
        Task task = taskList.get(position);
        if (!task.isCompleted()) {
            task.setCompleted(true);
            currentXP += task.getXp();
            checkLevelUp();
            taskAdapter.notifyItemChanged(position);
            updateUI();
        }
    }

    private void checkLevelUp() {
        if (currentXP >= xpForNextLevel) {
            level++;
            currentXP -= xpForNextLevel;
            xpForNextLevel += 50; // Increase the XP needed for next levels progressively
        }
    }

    private void updateUI() {
        tvLevel.setText("Level: " + level);
        tvXP.setText("XP: " + currentXP + "/" + xpForNextLevel);
        progressBarXP.setMax(xpForNextLevel);
        progressBarXP.setProgress(currentXP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK && data != null) {
            String task = data.getStringExtra("task");
            String details = data.getStringExtra("details");
            int difficulty = data.getIntExtra("difficulty", 1);
            int xp = data.getIntExtra("xp", 10);
            String reward = data.getStringExtra("reward");

            taskList.add(new Task(task, details, difficulty, xp, reward));
            taskAdapter.notifyItemInserted(taskList.size() - 1);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        CharSequence title = item.getTitle();
        if (title.equals("Tasks")) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (title == "Night Mode") {// Handle settings
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (title == "Settings") {
            toggleNightMode();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    private void toggleNightMode() {
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        recreate();
    }
}

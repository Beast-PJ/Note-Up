package com.beast.noteit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private int playerLevel = 1;
    private int playerXP = 0;
    private int xpToNextLevel = 100;
    private RecyclerView recyclerView;
    private final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        FloatingActionButton fab = findViewById(R.id.btnAddTask);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, position -> {
            Task completedTask = taskList.get(position);
            playerXP += completedTask.getXp();
            checkForLevelUp();
            taskList.remove(position);
            taskAdapter.notifyItemRemoved(position);
            updateUI();
        });

        // Set up RecyclerView
//        recyclerView = findViewById(R.id.rvTasks);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(taskAdapter);

        fab.setOnClickListener(v -> addNewTask());


        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new TasksFragment());
        }

        requestPermissions();

        // Update UI with current level and XP
        updateUI();
    }

    private void requestPermissions() {
        Dexter.withContext(this)
                .withPermissions(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (!report.areAllPermissionsGranted()) {
//                            showPermissionExplanationDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showPermissionExplanationDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Permissions Required")
                .setMessage("This app needs camera, location, and storage permissions to function correctly. Please enable them in settings.")
                .setPositiveButton("OK", (dialog, which) -> requestPermissions())
                .setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(MainActivity.this, "Permissions denied. Some features may not work.", Toast.LENGTH_SHORT).show())
                .create()
                .show();
    }

    private void addNewTask() {
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String taskName = data.getStringExtra("task");
            String details = data.getStringExtra("details");
            int difficulty = data.getIntExtra("difficulty", 1);
            int xp = data.getIntExtra("xp", 0);
            String reward = data.getStringExtra("reward");

            Task newTask = new Task(taskName, details, difficulty, xp, reward);
            taskList.add(newTask);
            taskAdapter.notifyItemInserted(taskList.size() - 1);
            updateUI();
        }
    }

    @SuppressLint("DefaultLocale")
    private void updateUI() {
        TextView tvLevel = findViewById(R.id.tvLevel);
        TextView tvXP = findViewById(R.id.tvXP);
        ProgressBar progressBarXP = findViewById(R.id.progressBarXP);

        tvLevel.setText(String.format("Level: %d", playerLevel));
        tvXP.setText(String.format("XP: %d/%d", playerXP, xpToNextLevel));
        progressBarXP.setMax(xpToNextLevel);
        progressBarXP.setProgress(playerXP);
    }

    private void checkForLevelUp() {
        if (playerXP >= xpToNextLevel) {
            playerLevel++;
            playerXP = playerXP - xpToNextLevel;
            xpToNextLevel *= 1.5; // Increase the XP requirement for next level
            showLevelUpDialog();
            updateUI();
        }
    }

    private void showLevelUpDialog() {
        LevelUpDialog dialog = new LevelUpDialog(this, playerLevel);
        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        CharSequence title = item.getTitle();
        assert title != null;
        if (title.equals("Tasks")) {
            fragment = new TasksFragment();
        } else if (title.equals("Calendar")) {
            fragment = new CalendarFragment();
        } else if (title.equals("Profile")) {
            fragment = new ProfileFragment();
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

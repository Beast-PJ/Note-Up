package com.beast.noteit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private int playerLevel = 1;
    private int playerXP = 0;
    private int xpToNextLevel = 100;
    private DrawerLayout drawerLayout;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, position -> {
            Task completedTask = taskList.get(position);
            playerXP += completedTask.getXp();
            checkForLevelUp();
            taskList.remove(position);
            taskAdapter.notifyItemRemoved(position);
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        setupViewPager(viewPager);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(viewPagerAdapter.getPageTitle(position))).attach();
    }

    private void setupViewPager(ViewPager2 viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerAdapter.addFragment(new AllTasksFragment(), "All");
        viewPagerAdapter.addFragment(new PersonalTasksFragment(), "Personal");
        viewPagerAdapter.addFragment(new WorkTasksFragment(), "Work");
        viewPagerAdapter.addFragment(new WishlistFragment(), "Wishlist");
        // Add more fragments as needed
        viewPager.setAdapter(viewPagerAdapter);
    }


    FloatingActionButton fab = findViewById(R.id.btnAddTask);
        fab.setOnClickListener(v -> addNewTask());

        updateUI();
    }

    private void addNewTask() {
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        startActivityForResult(intent, 1); // Request code 1 for adding task
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
        return false;
    }
}
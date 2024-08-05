package com.beast.noteit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTask, etDetails, etXP, etReward;
    private Spinner spDifficulty;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTask = findViewById(R.id.etTask);
        etDetails = findViewById(R.id.etDetails);
        etXP = findViewById(R.id.etXP);
        etReward = findViewById(R.id.etReward);
        spDifficulty = findViewById(R.id.spDifficulty);
        btnAdd = findViewById(R.id.btnAdd);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDifficulty.setAdapter(adapter);

        btnAdd.setOnClickListener(view -> {
            String task = etTask.getText().toString();
            String details = etDetails.getText().toString();
            int difficulty = spDifficulty.getSelectedItemPosition() + 1;
            int xp = Integer.parseInt(etXP.getText().toString());
            String reward = etReward.getText().toString();

            if (!task.isEmpty() && !details.isEmpty() && xp > 0 && !reward.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("task", task);
                resultIntent.putExtra("details", details);
                resultIntent.putExtra("difficulty", difficulty);
                resultIntent.putExtra("xp", xp);
                resultIntent.putExtra("reward", reward);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(AddTaskActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

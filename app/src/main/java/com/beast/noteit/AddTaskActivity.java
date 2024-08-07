package com.beast.noteit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
            String task,reward;
            int xp;
            task = etTask.getText().toString();
            reward = etReward.getText().toString();
            xp = Integer.parseInt(etXP.getText().toString());
            String details = etDetails.getText().toString();
            int difficulty = spDifficulty.getSelectedItemPosition() + 1;



            if (validate_data(task,reward,details, String.valueOf(xp))) {
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
    boolean validate_data(String task, String reward, String details, String xp){
        if (task.isEmpty()){
            etTask.setError("Invalid Email");
            return false;
        }
        if (reward.isEmpty()){
            etReward.setError("Password lenght is Invalid");
            return false;
        }
        if(xp.isEmpty()) {
            etXP.setError("Invalid Confirm Password");
            return false;
        }
        if(details.isEmpty()) {
            etDetails.setError("Invalid Confirm Password");
            return false;
        }
        return true;
    }
}

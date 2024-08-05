package com.beast.noteit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTask;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTask = findViewById(R.id.etTask);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(view -> {
            String task = etTask.getText().toString();
            if (!task.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("task", task);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(AddTaskActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


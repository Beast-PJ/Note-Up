package com.beast.noteit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTask;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTask = findViewById(R.id.etTask);

        findViewById(R.id.btnSaveTask).setOnClickListener(view -> {
            String task = etTask.getText().toString();
            if (!task.isEmpty()) {
                Intent intent = new Intent();
                intent.putExtra("task", task);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}

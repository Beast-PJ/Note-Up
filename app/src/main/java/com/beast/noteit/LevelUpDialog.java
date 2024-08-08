package com.beast.noteit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class LevelUpDialog extends Dialog {

    private int newLevel;

    public LevelUpDialog(@NonNull Context context, int newLevel) {
        super(context);
        this.newLevel = newLevel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_level_up);

        TextView tvNewLevel = findViewById(R.id.tvNewLevel);
        tvNewLevel.setText(String.format("You have reached Level %d", newLevel));

        Button btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> dismiss());
    }
}

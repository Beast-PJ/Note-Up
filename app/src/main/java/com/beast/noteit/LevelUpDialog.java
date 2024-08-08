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
        setContentView(R.layout.dialog_level_up);  // This refers to the XML layout

        // Finding views by their IDs
        TextView tvCongrats = findViewById(R.id.tvCongrats);
        TextView tvLevelUp = findViewById(R.id.tvLevelUp);
        TextView tvNewLevel = findViewById(R.id.tvNewLevel);
        Button btnClose = findViewById(R.id.btnClose);

        // Setting the text for each TextView
        tvCongrats.setText("CONGRATULATIONS!");
        tvLevelUp.setText("You've leveled up!");
        tvNewLevel.setText(String.format("New Level: %d", newLevel));

        // Setting click listener for the button to dismiss the dialog
        btnClose.setOnClickListener(v -> dismiss());
    }
}

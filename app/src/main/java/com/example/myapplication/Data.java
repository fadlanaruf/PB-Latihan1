package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Data extends AppCompatActivity {

    private EditText etTask;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        etTask = findViewById(R.id.etTask);
        btnSave = findViewById(R.id.btnSave);

        // Check if we're adding a new task
        if (getIntent().hasExtra("action") &&
                getIntent().getStringExtra("action").equals("add")) {
            etTask.setHint("Masukkan tugas baru");
        }

        btnSave.setOnClickListener(v -> {
            String taskText = etTask.getText().toString().trim();

            if (taskText.isEmpty()) {
                Toast.makeText(this, "Tugas tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create intent to send back the result
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newTask", taskText);
            setResult(RESULT_OK, resultIntent);

            finish(); // Return to HomeActivity
        });
    }
}
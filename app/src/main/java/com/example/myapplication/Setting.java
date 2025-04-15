package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Setting extends AppCompatActivity {

    private Button btnBackToHome, btnLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnBackToHome = findViewById(R.id.btnBackToHome);
        btnLogout = findViewById(R.id.btnLogout);

        btnBackToHome.setOnClickListener(v -> {
            // Kembali ke HomeActivity
            startActivity(new Intent(Setting.this, HomeActivity.class));
            finish(); // Optional: tutup activity saat ini
        });

        btnLogout.setOnClickListener(v -> {
            logoutUser();
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_settings);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, HomActivity.class));
                return true;
            } else if (id == R.id.nav_settings) {
                // Sudah di halaman settings
                return true;
            }
            return false;
        });
    }

    private void logoutUser() {
        mAuth.signOut();
        Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show();

        // Redirect ke MainActivity (login screen)
        Intent intent = new Intent(Setting.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Tutup activity saat ini
    }
}
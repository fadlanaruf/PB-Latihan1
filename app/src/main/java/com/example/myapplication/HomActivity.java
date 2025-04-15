package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom);

        // Initialize views
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvUsername = findViewById(R.id.tvUsername);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);

        // Set up Bottom Navigation
        bottomNav.setSelectedItemId(R.id.nav_profile);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(HomActivity.this, HomeActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                // Already on profile page
                return true;
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(HomActivity.this, Setting.class));
                finish();
                return true;
            }
            return false;
        });

        // Load and display user data
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Set email
            tvEmail.setText(user.getEmail() != null ? user.getEmail() : "Email not available");

            // Set username/display name
            String displayName = user.getDisplayName();
            tvUsername.setText(displayName != null && !displayName.isEmpty() ?
                    displayName : "User");
        } else {
            // If user is not logged in, redirect to login
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
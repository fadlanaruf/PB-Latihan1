package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomActivity extends AppCompatActivity {

    private TextView tvEmail, tvUsername;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom);

        // Inisialisasi komponen UI
        tvEmail = findViewById(R.id.tvEmail);
        tvUsername = findViewById(R.id.tvUsername);
        mAuth = FirebaseAuth.getInstance();

        // Tampilkan data user
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String username = email != null ? email.split("@")[0] : "User";

            tvEmail.setText("Email: " + email);
            tvUsername.setText("Username: " + username);
        }
    }
}
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private TextView tvTitle, tvDescription;
    private FirebaseAuth mAuth;
    private Button btnGoToProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Handling insets untuk menghindari overlap dengan status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi komponen UI
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        btnGoToProfile = findViewById(R.id.btnGoToProfile);

        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Ambil data user yang sedang login
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String username = email != null ? (email.contains("@") ? email.split("@")[0] : email) : "User";

            // Set teks selamat datang
            tvTitle.setText("Halo, " + username + "!");
            tvDescription.setText("Email: " + (email != null ? email : "Tidak tersedia"));
        } else {
            tvTitle.setText("Halo, Pengguna!");
            tvDescription.setText("Silakan login terlebih dahulu");
        }

        // Handle klik tombol profile
        btnGoToProfile.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, HomActivity.class); // Ganti HomActivity dengan ProfileActivity
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Cek jika user sudah logout dari activity lain
        if (mAuth.getCurrentUser() == null) {
            finish(); // Tutup activity jika user logout
        }
    }
}
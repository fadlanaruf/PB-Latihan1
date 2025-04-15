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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    private TextView tvTitle, tvDescription;
    private FirebaseAuth mAuth;
    private Button btnGoToProfile, btnGoToSetting, btnViewAllTodos;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView rvTodos;
    private TodoAdapter todoAdapter;

    private static final int ADD_TASK_REQUEST = 1;
    private ArrayList<Task> taskList = new ArrayList<>();

    public class Task {
        private String id;
        private String title;
        private boolean isCompleted;

        public Task(String title) {
            this.id = UUID.randomUUID().toString();
            this.title = title;
            this.isCompleted = false;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public boolean isCompleted() {
            return isCompleted;
        }

        public void setCompleted(boolean completed) {
            isCompleted = completed;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Initialize all UI components
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        btnGoToProfile = findViewById(R.id.btnGoToProfile);
        btnGoToSetting = findViewById(R.id.btnGoToSetting);
        btnViewAllTodos = findViewById(R.id.btnViewAllTodos);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        rvTodos = findViewById(R.id.rvTodos);

        // Setup RecyclerView
        rvTodos.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoAdapter(taskList);
        rvTodos.setAdapter(todoAdapter);

        // Window insets handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        updateUI(currentUser);

        // Button click handlers
        btnGoToProfile.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, HomActivity.class));
        });

        btnGoToSetting.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, Setting.class));
        });

        btnViewAllTodos.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, Data.class);
            intent.putExtra("action", "add");
            startActivityForResult(intent, ADD_TASK_REQUEST);
        });

        // Bottom Navigation setup
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                recreate();
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(HomeActivity.this, HomActivity.class));
                return true;
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(HomeActivity.this, Setting.class));
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String taskTitle = data.getStringExtra("newTask");
            Task newTask = new Task(taskTitle);
            taskList.add(newTask);
            todoAdapter.notifyItemInserted(taskList.size() - 1);
            Toast.makeText(this, "Tugas ditambahkan: " + taskTitle, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String email = user.getEmail();
            String username = email != null ?
                    (email.contains("@") ? email.split("@")[0] : email) : "User";

            tvTitle.setText("Halo, " + username + "!");
            tvDescription.setText("Email: " + (email != null ? email : "Tidak tersedia"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
package com.example.mevo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private String BASE_URL = "";
    private FirebaseAuth mAuth;

    EditText email,password;
    Button signin,signup;

    @Override
    public void onStart() {
        super.onStart();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this,RootActivity.class);
            intent.putExtra("username",currentUser.getDisplayName().toString());
            //Toast.makeText(this,""+currentUser.getDisplayName(),Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.username);
        password = findViewById(R.id.userpassword);
        signin = findViewById(R.id.SignInBtn);
        signup = findViewById(R.id.SignUpBtn);

        signin.setOnClickListener(v -> {
            String Login_email, Login_password;
            Login_email = email.getText().toString();
            Login_password = password.getText().toString();

            if (TextUtils.isEmpty(Login_email)) {
                Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(Login_password)) {
                Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(Login_email, Login_password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            //Intent intent = getIntent();
                            //finish();
                            //startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                        }
                    });

        });

        signup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        });

    }
}
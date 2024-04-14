package com.example.mevo;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mevo.APIs.API;
import com.example.mevo.DataModels.User;
import com.example.mevo.DataModels.UserModel;
import com.example.mevo.Utils.RetrofitConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    EditText email,password;
    Button signin,signup;
    ProgressBar progressBar;
    API retrofitAPI = new RetrofitConfig().getRerofitAPI();
    @Override
    public void onStart() {
        super.onStart();
        CheckLogin();
    }

    public void CheckLogin() {
        if (shp == null)
            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

        String userName = shp.getString("name", "");

        if (userName != null && !userName.equals("")) {
            Intent i = new Intent(MainActivity.this, RootActivity.class);
            i.putExtra("username",userName);
            startActivity(i);
            finish();
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

        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

        progressBar = findViewById(R.id.progressBarLogin);
        email = findViewById(R.id.username);
        password = findViewById(R.id.userpassword);
        signin = findViewById(R.id.SignInBtn);
        signup = findViewById(R.id.SignUpBtn);

        signin.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
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

            UserModel LoginModel = new UserModel(Login_email,"",Login_password);
            Call<UserModel> call = retrofitAPI.signIn(LoginModel);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    Log.e("------------", String.valueOf(response.code()));
                    if (response.code() == 200){
                        //Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        if (shp == null)
                            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

                        shpEditor = shp.edit();
                        shpEditor.putString("name", response.body().getName());
                        shpEditor.commit();

                        Intent intent = new Intent(MainActivity.this,RootActivity.class);
                        intent.putExtra("username",response.body().getName());
                        startActivity(intent);
                        finish();
                    } else if(response.code() == 205){
                        Toast.makeText(getApplicationContext(), "Invalid Credentials Response code 205", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.e("----------",t.getMessage());
                }
            });

        });

        signup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        });

    }
}
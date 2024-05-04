package com.example.mevo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.mevo.DataModels.UserModel;
import com.example.mevo.Utils.RetrofitConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {


    EditText name, email, password;
    private ProgressBar progressBar;
    Button signup;
    API retrofitAPI = new RetrofitConfig().getRerofitAPI();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBarRegister);
        name = findViewById(R.id.NameRegister);
        email = findViewById(R.id.EmailRegister);
        password = findViewById(R.id.userpasswordRegister);
        signup = findViewById(R.id.SignUpBtnRegister);

        signup.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);
            String Reg_name,Reg_email, Reg_password;
            Reg_name = name.getText().toString();
            Reg_email = email.getText().toString();
            Reg_password = password.getText().toString();

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (TextUtils.isEmpty(Reg_email)) {
                Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(Reg_password)) {
                Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
                return;
            }
            if(!Reg_email.matches(emailPattern)){
                Toast.makeText(getApplicationContext(), "Please enter valid email...", Toast.LENGTH_LONG).show();
                return;
            }


            UserModel modal = new UserModel(Reg_email,Reg_name,Reg_password);
            Call<JsonObject> call = retrofitAPI.signUp(modal);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    //Log.e("------->", response.body().get("message").toString());
                    progressBar.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.e("----------",t.getMessage());
                }
            });

        });
    }
}
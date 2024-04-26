package com.example.mevo;

import static com.example.mevo.Utils.RetrofitConfig.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mevo.Utils.OKHTTPConfig;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PanjikaranaActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_panjikarana);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String _id = intent.getStringExtra("_id");
        TextView id,name,age,gender;
        id = findViewById(R.id.AdmitPatientID);
        age = findViewById(R.id.AdmitPatientAge);
        name = findViewById(R.id.AdmitPatientName);
        gender = findViewById(R.id.AdmitPatientGender);
        EditText problem = findViewById(R.id.AdmitPatientProblem);
        Spinner doctors = findViewById(R.id.AdmitDoctors);

        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("_id",_id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String URL = BASE_URL;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonData.toString());
        Request request = new Request.Builder()
                .url(URL + "/GetPatientByID")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("-------->",e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               if(response.isSuccessful()){
                   String responseBody = response.body().string();
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(PanjikaranaActivity.this,responseBody,Toast.LENGTH_LONG).show();
                           try {
                               JSONObject jsonObject = new JSONObject(responseBody);
                               id.setText(jsonObject.get("_id").toString());
                               name.setText(jsonObject.get("PatientName").toString());
                               age.setText((jsonObject.get("PatientAge").toString()));
                               gender.setText(jsonObject.get("PatientGender").toString());

                           } catch (JSONException e) {
                               throw new RuntimeException(e);
                           }
                       }
                   });
               }
            }
        });


    }
}
package com.example.mevo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mevo.APIs.API;
import com.example.mevo.DataModels.PatientModel;
import com.example.mevo.DataModels.UserModel;
import com.example.mevo.Utils.ImageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatePatientActivity extends AppCompatActivity {

    private int CAMERA_PIC_REQUEST = 333;
    RadioButton genderSelection;
    private String BASE_URL = "https://good-rose-katydid-boot.cyclic.app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_patient);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText Name, Age, Address, Contact;
        ImageView PatientImage;
        RadioGroup Gender;
        Button saveBtn;

        Name = findViewById(R.id.patientName);
        Age = findViewById(R.id.patientAge);
        Contact = findViewById(R.id.patientContact);
        Address = findViewById(R.id.patientAddress);
        Gender = findViewById(R.id.patientGender);
        PatientImage = findViewById(R.id.patientImage);
        saveBtn = findViewById(R.id.patientSaveBtn);

        saveBtn.setOnClickListener(v -> {

            String base64String = ImageUtil.convert(((BitmapDrawable) PatientImage.getDrawable()).getBitmap());
            genderSelection = findViewById(Gender.getCheckedRadioButtonId());
            PatientModel patientModel = new PatientModel(Name.getText().toString(),Age.getText().toString(),Contact.getText().toString(),Address.getText().toString(),genderSelection.getText().toString(),base64String);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            API retrofitAPI = retrofit.create(API.class);
            Call<PatientModel> call = retrofitAPI.AddPatient(patientModel);
            call.enqueue(new Callback<PatientModel>() {
                @Override
                public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                    Toast.makeText(getApplicationContext(), "Patient Added Successfully", Toast.LENGTH_LONG).show();
                    //progressBar.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onFailure(Call<PatientModel> call, Throwable t) {
                    //progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Error Adding Patient", Toast.LENGTH_LONG).show();
                    Log.e("----------",t.getMessage());
                }
            });

        });

        PatientImage.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.patientImage);
            imageview.setImageBitmap(image);
        }

    }
}
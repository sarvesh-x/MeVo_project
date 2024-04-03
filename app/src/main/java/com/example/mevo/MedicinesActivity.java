package com.example.mevo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mevo.APIs.API;
import com.example.mevo.Adapters.DoctorsListAdapter;
import com.example.mevo.Adapters.MedicineAdapter;
import com.example.mevo.DataModels.DoctorModel;
import com.example.mevo.DataModels.MedicineModel;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Ref;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicinesActivity extends AppCompatActivity {

    private String BASE_URL = "https://good-rose-katydid-boot.cyclic.app";
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    API retrofitAPI = retrofit.create(API.class);
    Button addMedicine;
    RecyclerView MedicinesList;
    MedicineAdapter medicineAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medicines);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MedicinesList = findViewById(R.id.MedicinesList);
        ArrayList<MedicineModel> medicineModelArrayList = new ArrayList<>();
        Call<List<MedicineModel>> call = retrofitAPI.GetMedicines();
        call.enqueue(new Callback<List<MedicineModel>>() {
            @Override
            public void onResponse(Call<List<MedicineModel>> call, Response<List<MedicineModel>> response) {
                List<MedicineModel> medicines = response.body();
                for (int i = 0; i < medicines.size(); i++) {
                    MedicineModel tempMedicine = medicines.get(i);
                    medicineModelArrayList.add(tempMedicine);
                }
                medicineAdapter = new MedicineAdapter(MedicinesActivity.this,medicineModelArrayList);
                medicineAdapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MedicinesActivity.this, LinearLayoutManager.VERTICAL, false);
                MedicinesList.setLayoutManager(linearLayoutManager);
                MedicinesList.setAdapter(medicineAdapter);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(MedicinesActivity.this,"Error getting Medicine Details",Toast.LENGTH_SHORT).show();
            }
        });

        addMedicine = findViewById(R.id.addMedicine);
        addMedicine.setOnClickListener(v -> {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MedicinesActivity.this);
            final View layout = getLayoutInflater().inflate(R.layout.add_medicine_dialog,null);
            alertDialog.setView(layout);
            alertDialog.setTitle("Add Medicine");
            alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText MedicineID = layout.findViewById(R.id.addMedicineID);
                    EditText MedicineName = layout.findViewById(R.id.addMedicineName);
                    EditText MedicineQty = layout.findViewById(R.id.addMedicineQty);

                    MedicineModel medicineModel = new MedicineModel(MedicineID.getText().toString(),MedicineQty.getText().toString(),MedicineName.getText().toString());
                    Call<MedicineModel> call = retrofitAPI.AddMedicine(medicineModel);
                    call.enqueue(new Callback<MedicineModel>() {
                        @Override
                        public void onResponse(Call<MedicineModel> call, Response<MedicineModel> response) {
                            Toast.makeText(MedicinesActivity.this,"Added Successfully",Toast.LENGTH_SHORT).show();
                            MedicinesList.invalidate();
                            //medicineAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<MedicineModel> call, Throwable t) {
                            Toast.makeText(MedicinesActivity.this,"Error while Adding",Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Toast.makeText(MedicinesActivity.this,"3124324", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog dialog = alertDialog.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        });
    }
}
package com.example.mevo;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mevo.APIs.API;
import com.example.mevo.Adapters.DoctorsListAdapter;
import com.example.mevo.Adapters.PatientsAdapter;
import com.example.mevo.DataModels.DoctorModel;
import com.example.mevo.DataModels.PatientModel;
import com.example.mevo.Utils.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewPatientsActivity extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewPatientsActivity.this, LinearLayoutManager.VERTICAL, false);

    RecyclerView patientsList;
    API retrofitAPI = new RetrofitConfig().getRerofitAPI();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_patients);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        patientsList = findViewById(R.id.view_patients_list);
        Call<List<PatientModel>> call = retrofitAPI.GetPatients();
        call.enqueue(new Callback<List<PatientModel>>() {
            @Override
            public void onResponse(Call<List<PatientModel>> call, Response<List<PatientModel>> response) {
                //PatientsAdapter patientsAdapter = getPatientsAdapter(response);
                ArrayList<PatientModel> patientModelArrayList = new ArrayList<PatientModel>();
                List<PatientModel> patients = response.body();
                for (int i = 0; i < patients.size(); i++) {
                    PatientModel tempPatient = patients.get(i);
                    patientModelArrayList.add(tempPatient);
                }
                PatientsAdapter patientsAdapter = new PatientsAdapter(ViewPatientsActivity.this, patientModelArrayList, new PatientsAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(PatientModel patient) {

                    }
                });

                patientsList.setLayoutManager(linearLayoutManager);
                patientsList.setAdapter(patientsAdapter);
            }

            @Override
            public void onFailure(Call<List<PatientModel>> call, Throwable t) {
                Toast.makeText(ViewPatientsActivity.this,"Error getting Patient Details",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
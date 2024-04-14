package com.example.mevo.ui.patients;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mevo.APIs.API;
import com.example.mevo.Adapters.PatientsAdapter;
import com.example.mevo.CreatePatientActivity;
import com.example.mevo.DataModels.PatientModel;
import com.example.mevo.R;
import com.example.mevo.Utils.RetrofitConfig;
import com.example.mevo.ViewPatientsActivity;
import com.example.mevo.databinding.FragmentPatientsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatientsFragment extends Fragment {

private FragmentPatientsBinding binding;

    PatientsAdapter patientsAdapter;
    ArrayList<PatientModel> patientModelArrayList = new ArrayList<PatientModel>();
    API retrofitAPI = new RetrofitConfig().getRerofitAPI();
    RecyclerView patientsList;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        PatientsViewModel patientsViewModel = new ViewModelProvider(this).get(PatientsViewModel.class);

        binding = FragmentPatientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        patientsList = binding.getRoot().findViewById(R.id.view_patients_list);
        Call<List<PatientModel>> call = retrofitAPI.GetPatients();
        call.enqueue(new Callback<List<PatientModel>>() {
            @Override
            public void onResponse(Call<List<PatientModel>> call, Response<List<PatientModel>> response) {
                List<PatientModel> patients = response.body();
                for (int i = 0; i < patients.size(); i++) {
                    PatientModel tempPatient = patients.get(i);
                    patientModelArrayList.add(tempPatient);
                }
                patientsAdapter = new PatientsAdapter(binding.getRoot().getContext(), patientModelArrayList, new PatientsAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(PatientModel patient) {
                        PopupMenu popup = new PopupMenu(binding.getRoot().getContext(), patientsList.getChildAt(patientModelArrayList.indexOf(patient)));
                        popup.inflate(R.menu.patients_menu);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId() == R.id.patient_edit){
                                    EditPatient(patient);
                                } else if(item.getItemId() == R.id.patient_discharge){
                                    Toast.makeText(binding.getRoot().getContext(), "Discharge",Toast.LENGTH_SHORT).show();
                                } else if(item.getItemId() == R.id.patient_remove){
                                    DeletePatient(patient);
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                });

                patientsList.setLayoutManager(linearLayoutManager);
                patientsList.setAdapter(patientsAdapter);
            }

            @Override
            public void onFailure(Call<List<PatientModel>> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), "Error getting Patient Details",Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    private void EditPatient(PatientModel patientModel){
        AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
        final View layout = View.inflate(binding.getRoot().getContext(), R.layout.edit_patient_dialog,null);
        builder.setView(layout);
        builder.setTitle("Edit Patient Details");
        EditText PatientName = layout.findViewById(R.id.editPatientName);
        EditText PatientAge = layout.findViewById(R.id.editPatientAge);
        EditText PatientContact = layout.findViewById(R.id.editPatientContact);
        EditText PatientAddress = layout.findViewById(R.id.editPatientAddress);

        PatientName.setText(patientModel.getPatientName());
        PatientAge.setText(patientModel.getPatientAge());
        PatientContact.setText(patientModel.getPatientContact());
        PatientAddress.setText(patientModel.getPatientAddress());
        builder.setPositiveButton("Update",(dialog, which) -> {
            PatientModel updatedPatientModel = new PatientModel(patientModel.getPatientID(),PatientName.getText().toString(),PatientAge.getText().toString(),PatientContact.getText().toString(),PatientAddress.getText().toString(),patientModel.getPatientGender(),patientModel.getPatientImage());
            Call call = retrofitAPI.EditPatient(updatedPatientModel);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.code() == 200){
                        Toast.makeText(binding.getRoot().getContext(), "Updated",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(binding.getRoot().getContext(), "Error while Updating",Toast.LENGTH_SHORT).show();
                }
            });
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    private void DeletePatient(PatientModel patientModel){
        Map<String,Object> patient_id = new HashMap<>();
        patient_id.put("_id",patientModel.getPatientID());
        Call<PatientModel> call = retrofitAPI.DeletePatient(patient_id);
        call.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                Toast.makeText(binding.getRoot().getContext() ,"Deleted",Toast.LENGTH_SHORT).show();
                patientModelArrayList.remove(patientModelArrayList.indexOf(patientModel));
                patientsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), "Error while deleting",Toast.LENGTH_SHORT).show();
            }
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
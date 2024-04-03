package com.example.mevo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mevo.DataModels.MedicineModel;
import com.example.mevo.DataModels.PatientModel;
import com.example.mevo.R;

import java.util.ArrayList;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.Viewholder> {

    private final Context context;
    private final ArrayList<PatientModel> patientModelArrayList;

    public PatientsAdapter(Context context, ArrayList<PatientModel> patientModelArrayList){
        this.context = context;
        this.patientModelArrayList = patientModelArrayList;
    }

    @NonNull
    @Override
    public PatientsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_card, parent, false);
        return new PatientsAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientsAdapter.Viewholder holder, int position) {
        PatientModel patientModel = patientModelArrayList.get(position);
        holder.patient_name.setText(patientModel.getPatientName());
        holder.patient_gender.setText(patientModel.getPatientGender());
        holder.patient_age.setText(patientModel.getPatientAge());
        holder.patient_contact.setText(patientModel.getPatientContact());
        holder.patient_address.setText(patientModel.getPatientAddress());
    }

    @Override
    public int getItemCount() {
        return patientModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView patient_id;
        TextView patient_name;
        TextView patient_age;
        TextView patient_contact;
        TextView patient_gender;
        TextView patient_address;

        public Viewholder(@NonNull View itemView){
            super(itemView);

            patient_name = itemView.findViewById(R.id.patientListName);
            patient_age = itemView.findViewById(R.id.patientListAge);
            patient_address = itemView.findViewById(R.id.patientListAddress);
            patient_contact = itemView.findViewById(R.id.patientListContact);
            patient_gender = itemView.findViewById(R.id.patientListGender);

        }
    }
}

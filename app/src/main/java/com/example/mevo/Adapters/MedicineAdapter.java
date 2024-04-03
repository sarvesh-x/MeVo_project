package com.example.mevo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mevo.DataModels.DoctorModel;
import com.example.mevo.DataModels.MedicineModel;
import com.example.mevo.R;
import com.example.mevo.Utils.ImageUtil;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<MedicineModel> medicineModelArrayList;

    public MedicineAdapter(Context context, ArrayList<MedicineModel> medicineModelArrayList) {
        this.context = context;
        this.medicineModelArrayList = medicineModelArrayList;
    }
    @NonNull
    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.ViewHolder holder, int position) {
        MedicineModel model = medicineModelArrayList.get(position);
        holder.medicineID.setText(model.getMedicineID());
        holder.medicineName.setText(model.getMedicineName());
        holder.medicineQty.setText(model.getMedicineQty());
    }

    @Override
    public int getItemCount() {
        return medicineModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView medicineID;
        private final TextView medicineName;
        private final TextView medicineQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineID = itemView.findViewById(R.id.medicineID);
            medicineName = itemView.findViewById(R.id.medicineName);
            medicineQty = itemView.findViewById(R.id.medicineQty);
        }
    }
}

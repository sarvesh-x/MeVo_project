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
import com.example.mevo.R;

import java.util.ArrayList;

public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorsListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<DoctorModel> doctorModelArrayList;

    public DoctorsListAdapter(Context context, ArrayList<DoctorModel> doctorModelArrayList) {
        this.context = context;
        this.doctorModelArrayList = doctorModelArrayList;
    }

    @NonNull
    @Override
    public DoctorsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorsListAdapter.ViewHolder holder, int position) {
        DoctorModel model = doctorModelArrayList.get(position);
        holder.doctorName.setText(model.getDoctor_name());
        holder.available.setText("" + model.getAvailable());
        holder.doctorIV.setImageResource(model.getDoctor_image());
    }

    @Override
    public int getItemCount() {
        return doctorModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView doctorIV;
        private final TextView doctorName;
        private final TextView available;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorIV = itemView.findViewById(R.id.doctorsImage);
            doctorName = itemView.findViewById(R.id.doctorName);
            available = itemView.findViewById(R.id.doctorAvailable);
        }
    }
}

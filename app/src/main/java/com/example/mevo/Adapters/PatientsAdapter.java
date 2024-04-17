package com.example.mevo.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.mevo.APIs.API;
import com.example.mevo.DataModels.MedicineModel;
import com.example.mevo.DataModels.PatientModel;
import com.example.mevo.DataModels.Room;
import com.example.mevo.R;
import com.example.mevo.RootActivity;
import com.example.mevo.Utils.ImageUtil;

import org.json.JSONException;
import org.json.JSONObject;

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

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.Viewholder> {

    public interface OnItemLongClickListener{
        void onItemLongClick(PatientModel patient);
    }

    private final PatientsAdapter.OnItemLongClickListener listener;
    private final Context context;
    private final ArrayList<PatientModel> patientModelArrayList;

    public PatientsAdapter(Context context, ArrayList<PatientModel> patientModelArrayList,  PatientsAdapter.OnItemLongClickListener listener){
        this.context = context;
        this.patientModelArrayList = patientModelArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PatientsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_card, parent, false);
        return new PatientsAdapter.Viewholder(view);
    }
    public void loadImageWithGlide(String base64String, ImageView imageView) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Glide.with(context.getApplicationContext())
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientsAdapter.Viewholder holder, int position) {
        PatientModel patientModel = patientModelArrayList.get(position);
        holder.patient_id.setText(patientModel.getPatientID());
        holder.patient_name.setText(patientModel.getPatientName());
        holder.patient_gender.setText(patientModel.getPatientGender());
        holder.patient_age.setText(patientModel.getPatientAge());
        holder.patient_contact.setText(patientModel.getPatientContact());
        holder.patient_address.setText(patientModel.getPatientAddress());
        //holder.patient_image.setImageBitmap(ImageUtil.convert(patientModel.getPatientImage()));
        loadImageWithGlide(patientModel.getPatientImage(),holder.patient_image);
        holder.bind(patientModel,listener);
    }


    @Override
    public int getItemCount() {
        return patientModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public void bind(final PatientModel patient, final PatientsAdapter.OnItemLongClickListener listener){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(patient);
                    return true;
                }
            });
        }
        TextView patient_id;
        TextView patient_name;
        TextView patient_age;
        TextView patient_contact;
        TextView patient_gender;
        TextView patient_address;
        ImageView patient_image;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            patient_id = itemView.findViewById(R.id.patientListID);
            patient_name = itemView.findViewById(R.id.patientListName);
            patient_age = itemView.findViewById(R.id.patientListAge);
            patient_address = itemView.findViewById(R.id.patientListAddress);
            patient_contact = itemView.findViewById(R.id.patientListContact);
            patient_gender = itemView.findViewById(R.id.patientListGender);
            patient_image = itemView.findViewById(R.id.patientListImage);
        }

    }
}

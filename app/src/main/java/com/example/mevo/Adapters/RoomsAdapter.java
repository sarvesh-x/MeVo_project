package com.example.mevo.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mevo.APIs.API;
import com.example.mevo.DataModels.DoctorModel;
import com.example.mevo.DataModels.Room;
import com.example.mevo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder>{

    private final Context context;
    private String BASE_URL = "https://good-rose-katydid-boot.cyclic.app";
    RadioButton isAvailableRadioButton;
    private final ArrayList<Room> roomArrayList;
    public RoomsAdapter(Context context, ArrayList<Room> roomArrayList) {
        this.context = context;
        this.roomArrayList = roomArrayList;
    }

    @NonNull
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_card, parent, false);
        return new RoomsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsAdapter.ViewHolder holder, int position) {
        Room model = roomArrayList.get(position);
        holder.RoomNo.setText(model.getRoomNo());
        holder.RoomAvailable.setText("Availablity  "+model.getAvailable());
        holder.RoomName.setText("Room Name  "+(model.getRoomName()));
        holder.editButton.setOnClickListener(v -> {

            String EditroomNo = model.getRoomNo();


            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Update Room Details");
            final View customLayout = View.inflate(v.getContext(),R.layout.edit_room_dialog, null);
            TextView editRoomNo = customLayout.findViewById(R.id.editRoomNoDialog);
            RadioGroup isAvailable = customLayout.findViewById(R.id.editRoomAvailable);
            //Toast.makeText(v.getContext(),EditroomNo,Toast.LENGTH_LONG).show();
            editRoomNo.setText("Update Details for Room No. "+EditroomNo);
            builder.setView(customLayout);
            builder.setPositiveButton("Save", (dialog, which) -> {
                int availability = isAvailable.getCheckedRadioButtonId();
                isAvailableRadioButton = customLayout.findViewById(availability);
                Room roomModel = new Room(EditroomNo,isAvailableRadioButton.getText().toString(),"");
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                API retrofitAPI = retrofit.create(API.class);
                Call<Room> call = retrofitAPI.EditRoom(roomModel);
                call.enqueue(new Callback<Room>() {
                    @Override
                    public void onResponse(Call<Room> call, Response<Room> response) {
                        if (response.code() == 200){
                            Toast.makeText(v.getContext(), "Room Details Saved", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(v.getContext(), "Error Saving Room Details", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Room> call, Throwable t) {
                        //RoomprogressBar.setVisibility(View.INVISIBLE);
                        Log.e("----------",t.getMessage());
                    }
                });
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    public void showAlertDialogButtonClicked(View view) {

    }



    @Override
    public int getItemCount() {
        return  roomArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView RoomNo;
        private final ImageView editButton;
        private final TextView RoomName;
        private final TextView RoomAvailable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editButton = itemView.findViewById(R.id.edit_roomButton);
            RoomNo = itemView.findViewById(R.id.roomNo);
            RoomName = itemView.findViewById(R.id.roomName);
            RoomAvailable = itemView.findViewById(R.id.room_available);
        }
    }
}

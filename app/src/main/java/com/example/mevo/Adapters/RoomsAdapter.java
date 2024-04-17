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
    public interface OnItemLongClickListener{
        void onItemLongClick(Room room);
    }
    private final OnItemLongClickListener listener;
    private final Context context;
    RadioButton isAvailableRadioButton;
    private final ArrayList<Room> roomArrayList;
    public RoomsAdapter(Context context, ArrayList<Room> roomArrayList, OnItemLongClickListener listener) {
        this.context = context;
        this.roomArrayList = roomArrayList;
        this.listener = listener;
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
        holder.bind(roomArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return  roomArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public void bind(final Room room, final OnItemLongClickListener listener){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(room);
                    return true;
                }
            });
        }
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

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
import com.example.mevo.DataModels.Room;
import com.example.mevo.R;

import java.util.ArrayList;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder>{

    private final Context context;
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
        holder.RoomNo.setText(""+model.getRoomNo());
        holder.RoomAvailable.setText(""+model.getAvailable());
        holder.RoomName.setText((model.getRoomName()));
    }

    @Override
    public int getItemCount() {
        return  roomArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView RoomNo;
        private final TextView RoomName;
        private final TextView RoomAvailable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RoomNo = itemView.findViewById(R.id.roomNo);
            RoomName = itemView.findViewById(R.id.roomName);
            RoomAvailable = itemView.findViewById(R.id.room_available);
        }
    }
}

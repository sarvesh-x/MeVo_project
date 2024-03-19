package com.example.mevo.ui.Rooms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mevo.APIs.API;
import com.example.mevo.Adapters.NotificationAdapter;
import com.example.mevo.Adapters.RoomsAdapter;
import com.example.mevo.DataModels.NotificationModel;
import com.example.mevo.DataModels.Room;
import com.example.mevo.DataModels.UserModel;
import com.example.mevo.R;
import com.example.mevo.databinding.FragmentRoomsBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomsFragment extends Fragment {
    RadioButton isAvailableRadioButton;
    private String BASE_URL = "https://good-rose-katydid-boot.cyclic.app";
    Button add_room;
    ImageView edit_roomButton;
    RecyclerView roomsList;

private FragmentRoomsBinding binding;

void initRooms(){
    RecyclerView roomsList = binding.getRoot().findViewById(R.id.roomsList);
    ArrayList<Room> roomArrayList = new ArrayList<Room>();
    RoomsAdapter roomsAdapter = new RoomsAdapter(binding.getRoot().getContext(), roomArrayList);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
    roomsList.setLayoutManager(linearLayoutManager);

    roomsList.setAdapter(roomsAdapter);
    roomsAdapter.notifyDataSetChanged();
}

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        RoomsViewModel roomsViewModel = new ViewModelProvider(this).get(RoomsViewModel.class);

        binding = FragmentRoomsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        roomsList = binding.getRoot().findViewById(R.id.roomsList);
        add_room = binding.getRoot().findViewById(R.id.add_room);

        Retrofit getRoomsretrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        API getRoomsretrofitAPI = getRoomsretrofit.create(API.class);

        Call<List<Room>> getRoomscall = getRoomsretrofitAPI.GetRooms();
        getRoomscall.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.code() == 200){
                    RoomsAdapter roomsAdapter = getRoomsAdapter(response);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
                    roomsList.setLayoutManager(linearLayoutManager);
                    roomsList.setAdapter(roomsAdapter);
                } else {
                    Toast.makeText(binding.getRoot().getContext(), "Error Getting Rooms", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                //RoomprogressBar.setVisibility(View.INVISIBLE);
                Log.e("----------",t.getMessage());
            }
        });



        add_room.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
            builder.setTitle("Add Room");

            final View customLayout = getLayoutInflater().inflate(R.layout.add_room_dialog, null);
            builder.setView(customLayout);

            builder.setPositiveButton("Add", (dialog, which) -> {
                //ProgressBar RoomprogressBar = binding.getRoot().findViewById(R.id.addRoomProgressBar);
                //RoomprogressBar.setVisibility(View.VISIBLE);
                EditText addRoomNo = customLayout.findViewById(R.id.addroomNo);
                EditText addRoomName = customLayout.findViewById(R.id.addroomName);
                RadioGroup isAvailable = customLayout.findViewById(R.id.addRoomAvailable);
                int availability = isAvailable.getCheckedRadioButtonId();
                isAvailableRadioButton = customLayout.findViewById(availability);
                Room roomModel = new Room(addRoomNo.getText().toString(),isAvailableRadioButton.getText().toString(),addRoomName.getText().toString());

                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                API retrofitAPI = retrofit.create(API.class);
                Call<Room> call = retrofitAPI.AddRoom(roomModel);
                call.enqueue(new Callback<Room>() {
                    @Override
                    public void onResponse(Call<Room> call, Response<Room> response) {
                        if (response.code() == 200){
                            Toast.makeText(binding.getRoot().getContext(), "Room Added Successfully", Toast.LENGTH_LONG).show();

                           // initRooms();
                            //RoomprogressBar.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(binding.getRoot().getContext(), "Error Adding Room", Toast.LENGTH_LONG).show();
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
        return root;
    }

    @NonNull
    private RoomsAdapter getRoomsAdapter(Response<List<Room>> response) {
        ArrayList<Room> roomArrayList = new ArrayList<Room>();
        List<Room> rooms = response.body();
        for (int i = 0; i < rooms.size(); i++) {
            Room tempRoom = rooms.get(i);
            roomArrayList.add(tempRoom);
        }
        RoomsAdapter roomsAdapter = new RoomsAdapter(binding.getRoot().getContext(), roomArrayList);

        roomsAdapter.notifyDataSetChanged();
        return roomsAdapter;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.mevo.ui.Rooms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mevo.Adapters.NotificationAdapter;
import com.example.mevo.Adapters.RoomsAdapter;
import com.example.mevo.DataModels.NotificationModel;
import com.example.mevo.DataModels.Room;
import com.example.mevo.R;
import com.example.mevo.databinding.FragmentRoomsBinding;

import java.util.ArrayList;

public class RoomsFragment extends Fragment {

private FragmentRoomsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        RoomsViewModel roomsViewModel =
                new ViewModelProvider(this).get(RoomsViewModel.class);

    binding = FragmentRoomsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


        RecyclerView roomsList = binding.getRoot().findViewById(R.id.roomsList);
        ArrayList<Room> roomArrayList = new ArrayList<Room>();
        roomArrayList.add(new Room(1001,1,"OPD-1"));
        roomArrayList.add(new Room(1002,0,"OPD-2"));
        roomArrayList.add(new Room(1003,1,"OPD-3"));
        RoomsAdapter roomsAdapter = new RoomsAdapter(binding.getRoot().getContext(), roomArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        roomsList.setLayoutManager(linearLayoutManager);
        roomsList.setAdapter(roomsAdapter);

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
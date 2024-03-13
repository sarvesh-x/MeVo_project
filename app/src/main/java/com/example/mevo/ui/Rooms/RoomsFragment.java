package com.example.mevo.ui.Rooms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mevo.databinding.FragmentRoomsBinding;

public class RoomsFragment extends Fragment {

private FragmentRoomsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        RoomsViewModel roomsViewModel =
                new ViewModelProvider(this).get(RoomsViewModel.class);

    binding = FragmentRoomsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
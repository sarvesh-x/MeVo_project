package com.example.mevo.ui.home;

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

import com.example.mevo.Adapters.DoctorsListAdapter;
import com.example.mevo.DataModels.DoctorModel;
import com.example.mevo.R;
import com.example.mevo.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView doctorsList = binding.getRoot().findViewById(R.id.doctorsList);
        ArrayList<DoctorModel> courseModelArrayList = new ArrayList<DoctorModel>();
        courseModelArrayList.add(new DoctorModel("ABC", 4, R.drawable.ic_logo_background));
        courseModelArrayList.add(new DoctorModel("QWE", 3, R.drawable.ic_logo_background));
        courseModelArrayList.add(new DoctorModel("RTY", 4, R.drawable.ic_logo_background));
        courseModelArrayList.add(new DoctorModel("ZXC", 4, R.drawable.ic_logo_background));
        courseModelArrayList.add(new DoctorModel("JKL", 4, R.drawable.ic_logo_background));
        courseModelArrayList.add(new DoctorModel("WASD", 4, R.drawable.ic_logo_background));
        courseModelArrayList.add(new DoctorModel("BNM", 4, R.drawable.ic_logo_background));

        DoctorsListAdapter courseAdapter = new DoctorsListAdapter(binding.getRoot().getContext(), courseModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        doctorsList.setLayoutManager(linearLayoutManager);
        doctorsList.setAdapter(courseAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
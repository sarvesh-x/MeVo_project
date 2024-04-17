package com.example.mevo.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mevo.APIs.API;
import com.example.mevo.Adapters.DoctorsListAdapter;
import com.example.mevo.Adapters.RoomsAdapter;
import com.example.mevo.DataModels.DoctorModel;
import com.example.mevo.DataModels.Room;
import com.example.mevo.R;
import com.example.mevo.Utils.RetrofitConfig;
import com.example.mevo.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    DoctorsListAdapter doctorsListAdapter;
    API retrofitAPI = new RetrofitConfig().getRerofitAPI();
    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<DoctorModel>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView doctorsList = binding.getRoot().findViewById(R.id.doctorsList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false);
        Call<List<DoctorModel>> call = retrofitAPI.GetDoctors();
        call.enqueue(new Callback<List<DoctorModel>>() {
            @Override
            public void onResponse(Call<List<DoctorModel>> call, Response<List<DoctorModel>> response) {

                List<DoctorModel> doctors = response.body();
                for (int i = 0; i < doctors.size(); i++) {
                    DoctorModel tempDoctor = doctors.get(i);
                    doctorModelArrayList.add(tempDoctor);
                }
                doctorsListAdapter = new DoctorsListAdapter(binding.getRoot().getContext(), doctorModelArrayList);
                doctorsList.setLayoutManager(linearLayoutManager);
                doctorsList.setAdapter(doctorsListAdapter);
            }

            @Override
            public void onFailure(Call<List<DoctorModel>> call, Throwable t) {
                //Toast.makeText(binding.getRoot().getContext(), "Error Getting Doctors Details",Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
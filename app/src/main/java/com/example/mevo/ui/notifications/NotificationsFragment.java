package com.example.mevo.ui.notifications;

import android.os.Bundle;
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
import com.example.mevo.Adapters.NotificationAdapter;
import com.example.mevo.DataModels.DoctorModel;
import com.example.mevo.DataModels.NotificationModel;
import com.example.mevo.R;
import com.example.mevo.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationsFragment extends Fragment {

private FragmentNotificationsBinding binding;

    private String BASE_URL = "https://good-rose-katydid-boot.cyclic.app";

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView notificationsList = binding.getRoot().findViewById(R.id.notifications_list);
        ArrayList<NotificationModel> notificationModelArrayList = new ArrayList<NotificationModel>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        API retrofitAPI = retrofit.create(API.class);
        Call<List<NotificationModel>> call = retrofitAPI.GetNotifications();
        call.enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                NotificationAdapter notificationAdapter = getNotificationAdpater(response);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
                notificationsList.setLayoutManager(linearLayoutManager);
                notificationsList.setAdapter(notificationAdapter);
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(),"Error getting Notifications",Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    private NotificationAdapter getNotificationAdpater(Response<List<NotificationModel>> response) {
        ArrayList<NotificationModel> notificationModelArrayList = new ArrayList<NotificationModel>();
        List<NotificationModel> notifications = response.body();
        for (int i = 0; i < notifications.size(); i++) {
            NotificationModel tempNotification = notifications.get(i);
            notificationModelArrayList.add(tempNotification);
        }
        NotificationAdapter notificationAdapter = new NotificationAdapter(binding.getRoot().getContext(), notificationModelArrayList);
        notificationAdapter.notifyDataSetChanged();
        return notificationAdapter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
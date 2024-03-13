package com.example.mevo.ui.notifications;

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
import com.example.mevo.DataModels.NotificationModel;
import com.example.mevo.R;
import com.example.mevo.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView notificationsList = binding.getRoot().findViewById(R.id.notifications_list);
        ArrayList<NotificationModel> notificationModelArrayList = new ArrayList<NotificationModel>();
        notificationModelArrayList.add(new NotificationModel(R.mipmap.ic_logo,"Your go-to medical resource to get immediate clinical answers. With Medscape, you get free access to the latest news, expert commentary, clinical tools, and more."));
        notificationModelArrayList.add(new NotificationModel(R.mipmap.ic_logo,"Your go-to medical resource to get immediate clinical answers. With Medscape, you get free access to the latest news, expert commentary, clinical tools, and more."));
        notificationModelArrayList.add(new NotificationModel(R.mipmap.ic_logo,"Your go-to medical resource to get immediate clinical answers. With Medscape, you get free access to the latest news, expert commentary, clinical tools, and more."));
        NotificationAdapter notificationAdapter = new NotificationAdapter(binding.getRoot().getContext(), notificationModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        notificationsList.setLayoutManager(linearLayoutManager);
        notificationsList.setAdapter(notificationAdapter);

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
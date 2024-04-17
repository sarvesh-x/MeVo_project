package com.example.mevo.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.example.mevo.Utils.ImageUtil;
import com.example.mevo.Utils.RetrofitConfig;
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
    NotificationAdapter notificationAdapter;
    ArrayList<NotificationModel> notificationModelArrayList = new ArrayList<NotificationModel>();
    API retrofitAPI = new RetrofitConfig().getRerofitAPI();
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView notificationsList = binding.getRoot().findViewById(R.id.notifications_list);
        ArrayList<NotificationModel> notificationModelArrayList = new ArrayList<NotificationModel>();
        Call<List<NotificationModel>> call = retrofitAPI.GetNotifications();
        call.enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                notificationsList.setLayoutManager(linearLayoutManager);
                List<NotificationModel> notifications = response.body();
                for (int i = 0; i < notifications.size(); i++) {
                    NotificationModel tempNotification = notifications.get(i);
                    notificationModelArrayList.add(tempNotification);
                }
                notificationAdapter = new NotificationAdapter(binding.getRoot().getContext(), notificationModelArrayList, new NotificationAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //Toast.makeText(binding.getRoot().getContext(),""+notificationModelArrayList.get(position).getNotificationTitle(),Toast.LENGTH_SHORT).show();
                        final View customLayout = getLayoutInflater().inflate(R.layout.notification_dialog, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                        ImageView n_image = customLayout.findViewById(R.id.notification_header_image);
                        TextView n_head = customLayout.findViewById(R.id.notification_dialog_title);
                        TextView n_subhead = customLayout.findViewById(R.id.notification_dialog_subhead);
                        TextView n_body = customLayout.findViewById(R.id.notification_dialog_body);
                        NotificationModel notificationModel = notificationModelArrayList.get(position);
                        n_head.setText(notificationModel.getNotificationTitle());
                        n_body.setText(notificationModel.getNotificationContent());
                        n_subhead.setText(notificationModel.getNotificationSubhead());
                        n_image.setImageBitmap(ImageUtil.convert(notificationModel.getNotificationImage()));
                        builder.setPositiveButton("Read",(dialog, which) -> {});
                        builder.setView(customLayout);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
                notificationsList.setAdapter(notificationAdapter);
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(),"Error getting Notifications",Toast.LENGTH_SHORT).show();
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
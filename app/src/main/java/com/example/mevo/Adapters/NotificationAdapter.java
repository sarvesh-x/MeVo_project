package com.example.mevo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mevo.DataModels.NotificationModel;
import com.example.mevo.R;
import com.example.mevo.Utils.ImageUtil;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
private final Context context;
public interface OnItemClickListener{
    void onItemClick(int position);
}
private final OnItemClickListener listener;
private final ArrayList<NotificationModel> notificationModelArrayList;

public NotificationAdapter(Context context, ArrayList<NotificationModel> courseModelArrayList, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.notificationModelArrayList = courseModelArrayList;
        }

@NonNull
@Override
public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        NotificationModel model = notificationModelArrayList.get(position);
        holder.notificationTitle.setText(model.getNotificationTitle());
        holder.notificationSubhead.setText(model.getNotificationSubhead());
        holder.notificationImage.setImageBitmap(ImageUtil.convert(model.getNotificationImage()));
        holder.notificationContent.setText(model.getNotificationContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    int position = holder.getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            }
        });
        }

@Override
public int getItemCount() {
        return notificationModelArrayList.size();
        }

public static class ViewHolder extends RecyclerView.ViewHolder {
    private final ImageView notificationImage;
    private final TextView notificationTitle;
    private final TextView notificationSubhead;
    private final TextView notificationContent;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        notificationImage = itemView.findViewById(R.id.notification_image);
        notificationSubhead = itemView.findViewById(R.id.notification_subhead);
        notificationTitle = itemView.findViewById(R.id.notification_title);
        notificationContent = itemView.findViewById(R.id.notification_content);
    }
}
}
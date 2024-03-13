package com.example.mevo.DataModels;

import android.widget.ImageView;

public class NotificationModel {

    private int NotificationImage;
    private String NotificationTitle;
    public NotificationModel(int NotificationImage, String NotificationTitle){
        this.NotificationImage = NotificationImage;
        this.NotificationTitle = NotificationTitle;
    }

    public int getNotificationImage() {
        return NotificationImage;
    }

    public String getNotificationTitle() {
        return NotificationTitle;
    }

    public void setNotificationImage(int notificationImage) {
        NotificationImage = notificationImage;
    }

    public void setNotificationTitle(String notificationTitle) {
        NotificationTitle = notificationTitle;
    }
}

package com.example.mevo.DataModels;

import android.widget.ImageView;

public class NotificationModel {

    private String NotificationImage;
    private String NotificationContent;
    private String NotificationTitle;
    public NotificationModel(String NotificationImage, String NotificationTitle, String NotificationContent){
        this.NotificationImage = NotificationImage;
        this.NotificationContent = NotificationContent;
        this.NotificationTitle = NotificationTitle;
    }

    public String getNotificationContent() {
        return NotificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        NotificationContent = notificationContent;
    }

    public String getNotificationImage() {
        return NotificationImage;
    }

    public String getNotificationTitle() {
        return NotificationTitle;
    }

    public void setNotificationImage(String notificationImage) {
        NotificationImage = notificationImage;
    }

    public void setNotificationTitle(String notificationTitle) {
        NotificationTitle = notificationTitle;
    }
}

package com.example.mevo.DataModels;

import android.widget.ImageView;

public class NotificationModel {

    private String NotificationImage;
    private String NotificationContent;
    private String NotificationSubhead;
    private String NotificationTitle;
    public NotificationModel(String NotificationImage, String NotificationTitle, String NotificationContent, String NotificationSubhead){
        this.NotificationImage = NotificationImage;
        this.NotificationSubhead = NotificationSubhead;
        this.NotificationContent = NotificationContent;
        this.NotificationTitle = NotificationTitle;
    }

    public String getNotificationContent() {
        return NotificationContent;
    }

    public String getNotificationSubhead() {
        return NotificationSubhead;
    }

    public void setNotificationSubhead(String notificationSubhead) {
        NotificationSubhead = notificationSubhead;
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

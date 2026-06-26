package com.example.hirematch.models;

public class Notification {


    private String notificationId;
    private String userId;
    private String title;
    private String message;
    private String type;
    private boolean isRead;
    private String createdAt;

    public Notification() {
    }

    public Notification(
            String notificationId,
            String userId,
            String title,
            String message,
            String type,
            boolean isRead,
            String createdAt) {

        this.notificationId = notificationId;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getCreatedAt() {
        return createdAt;
    }


}

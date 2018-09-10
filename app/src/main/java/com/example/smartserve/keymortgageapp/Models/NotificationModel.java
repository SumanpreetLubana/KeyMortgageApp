package com.example.smartserve.keymortgageapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationModel implements Parcelable
{
    String notif_id,user_id,text,type,seen,status,image,isNotification,message;

    public NotificationModel(String notif_id, String user_id, String text, String type, String seen, String status, String image, String isNotification, String message) {
        this.notif_id = notif_id;
        this.user_id = user_id;
        this.text = text;
        this.type = type;
        this.seen = seen;
        this.status = status;
        this.image = image;
        this.isNotification = isNotification;
        this.message = message;
    }

    protected NotificationModel(Parcel in) {
        notif_id = in.readString();
        user_id = in.readString();
        text = in.readString();
        type = in.readString();
        seen = in.readString();
        status = in.readString();
        image = in.readString();
        isNotification = in.readString();
        message = in.readString();
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel in) {
            return new NotificationModel(in);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };

    public String getNotif_id() {
        return notif_id;
    }

    public void setNotif_id(String notif_id) {
        this.notif_id = notif_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsNotification() {
        return isNotification;
    }

    public void setIsNotification(String isNotification) {
        this.isNotification = isNotification;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(notif_id);
        parcel.writeString(user_id);
        parcel.writeString(text);
        parcel.writeString(type);
        parcel.writeString(seen);
        parcel.writeString(status);
        parcel.writeString(image);
        parcel.writeString(isNotification);
        parcel.writeString(message);
    }
}

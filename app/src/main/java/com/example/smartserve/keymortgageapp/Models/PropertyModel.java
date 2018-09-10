package com.example.smartserve.keymortgageapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class PropertyModel implements Parcelable {
    String pid,name,location,cat_name,cost,size,description,project_image;

    public PropertyModel(String pid, String name, String location, String cat_name, String cost, String size, String description, String project_image) {
        this.pid = pid;
        this.name = name;
        this.location = location;
        this.cat_name = cat_name;
        this.cost = cost;
        this.size = size;
        this.description = description;
        this.project_image = project_image;
    }

    protected PropertyModel(Parcel in) {
        pid = in.readString();
        name = in.readString();
        location = in.readString();
        cat_name = in.readString();
        cost = in.readString();
        size = in.readString();
        description = in.readString();
        project_image = in.readString();
    }

    public static final Creator<PropertyModel> CREATOR = new Creator<PropertyModel>() {
        @Override
        public PropertyModel createFromParcel(Parcel in) {
            return new PropertyModel(in);
        }

        @Override
        public PropertyModel[] newArray(int size) {
            return new PropertyModel[size];
        }
    };

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProject_image() {
        return project_image;
    }

    public void setProject_image(String project_image) {
        this.project_image = project_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pid);
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(cat_name);
        parcel.writeString(cost);
        parcel.writeString(size);
        parcel.writeString(description);
        parcel.writeString(project_image);
    }
}

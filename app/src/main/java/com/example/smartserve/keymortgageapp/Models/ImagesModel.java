package com.example.smartserve.keymortgageapp.Models;

import android.graphics.Bitmap;

import java.io.File;

public class ImagesModel {

    int iid;
    Bitmap bm;
    File filePath;

    public ImagesModel(int iid, Bitmap bm, File filePath) {
        this.iid = iid;
        this.bm = bm;
        this.filePath = filePath;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }
}

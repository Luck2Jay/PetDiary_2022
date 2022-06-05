package com.example.petdiary_2022;

import java.io.File;

public class PhotoBear {

    File filename;
    int resId;
    String photoname;


    public PhotoBear(File filename,int resId, String photoname){
        this.filename=filename;
        this.resId=resId;
        this.photoname=photoname;

    }


    public File getFilename() {
        return filename;
    }

    public void setFilename(File filename) {
        this.filename = filename;
    }

    public String getPhotoname() {
        return photoname;
    }
    public void setPhotoname(String photoname) {
        this.photoname = photoname;
    }

    public int getResId() {
        return resId;
    }
    public void setResId(int resId) {
        this.resId = resId;
    }
}



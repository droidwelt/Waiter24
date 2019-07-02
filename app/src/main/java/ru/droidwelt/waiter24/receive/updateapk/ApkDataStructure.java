package ru.droidwelt.waiter24.receive.updateapk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ApkDataStructure {

    @SerializedName("apk")
    @Expose
    private ArrayList<ApkDataClass> apk = new ArrayList<>();

    public ArrayList<ApkDataClass> getApk() {
        return apk;
    }

    public void setApk(ArrayList<ApkDataClass> apk) {
        this.apk = apk;
    }



}
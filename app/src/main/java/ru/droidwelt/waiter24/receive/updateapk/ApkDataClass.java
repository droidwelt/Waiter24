package ru.droidwelt.waiter24.receive.updateapk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApkDataClass {

    @SerializedName("NEWVERSION")
    @Expose
    public String NEWVERSION;

    @SerializedName("APK")
    @Expose
    public String APK;

}

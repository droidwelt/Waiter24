package ru.droidwelt.waiter24.receive.versionverver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class VersionServerDataStructure {

    @SerializedName("versionserver")
    @Expose
    private ArrayList<VersionServerDataClass> versionserver = new ArrayList<>();

    public ArrayList<VersionServerDataClass> getVersionServer() {
        return versionserver;
    }

    public void setVersionServer(ArrayList<VersionServerDataClass> versionserver) {
        this.versionserver = versionserver;
    }



}
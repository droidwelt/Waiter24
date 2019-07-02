package ru.droidwelt.waiter24.receive.versionverver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class VersionServerDataClass {

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @SerializedName("version")
    @Expose
    private String version;


}

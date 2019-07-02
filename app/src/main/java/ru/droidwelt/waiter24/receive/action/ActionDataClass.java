package ru.droidwelt.waiter24.receive.action;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ActionDataClass {

    public String getAction() {
        return res;
    }

    public void setAction(String res) {
        this.res = res;
    }

    @SerializedName("ACTION")
    @Expose
    private String res;

}

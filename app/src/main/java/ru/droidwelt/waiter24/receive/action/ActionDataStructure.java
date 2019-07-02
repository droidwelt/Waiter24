package ru.droidwelt.waiter24.receive.action;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ActionDataStructure {

    @SerializedName("ACTION")
    @Expose
    private ArrayList<ActionDataClass> v = new ArrayList<>();

    public ArrayList<ActionDataClass> getAction() {
        return v;
    }

    public void setAction(ArrayList<ActionDataClass> v) {
        this.v = v;
    }



}
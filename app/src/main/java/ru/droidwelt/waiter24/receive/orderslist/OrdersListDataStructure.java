package ru.droidwelt.waiter24.receive.orderslist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class OrdersListDataStructure {

    @SerializedName("ORDLIST")
    @Expose
    private ArrayList<OrdersListDataClass> v = new ArrayList<>();

    public ArrayList<OrdersListDataClass> getOrdersList() {
        return v;
    }

    public void setOrdersList(ArrayList<OrdersListDataClass> v) {
        this.v = v;
    }




    @SerializedName("POSLIST")
    @Expose
    private ArrayList<PosListDataClass> p = new ArrayList<>();

    public ArrayList<PosListDataClass> getPosList() {
        return p;
    }

    public void setPosList(ArrayList<PosListDataClass> p) {
        this.p = p;
    }



}
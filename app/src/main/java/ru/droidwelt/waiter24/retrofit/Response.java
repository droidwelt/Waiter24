package ru.droidwelt.waiter24.retrofit;

import com.google.gson.annotations.SerializedName;

//  https://stackoverflow.com/questions/20786593/how-should-i-handle-no-internet-connection-with-retrofit-on-android


public class Response {
    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @SuppressWarnings({"unused", "used by Retrofit"})
    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }
}

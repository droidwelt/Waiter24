package ru.droidwelt.waiter24.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.droidwelt.waiter24.receive.action.ActionDataStructure;
import ru.droidwelt.waiter24.receive.orderslist.OrdersListDataStructure;
import ru.droidwelt.waiter24.receive.updateapk.ApkDataStructure;
import ru.droidwelt.waiter24.receive.versionverver.VersionServerDataStructure;
import ru.droidwelt.waiter24.utils.PrefUtils;
import rx.Observable;


public class RetrofitClientRx {

    private static RetrofitClientRx instance;
    private static String oldRootURL = "";
    private RetrofitApiServiceRx apiService;

    private RetrofitClientRx() {

        //  Log.e("RetrofitClientRx", PrefUtils.getRootURL());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PrefUtils.getRootURL())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(RetrofitApiServiceRx.class);
    }

    public static RetrofitClientRx getInstance() {
        if ((instance == null) || !(PrefUtils.getRootURL().equals(oldRootURL))) {
            instance = new RetrofitClientRx();
            oldRootURL = PrefUtils.getRootURL();
        }
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }


    public Observable<ApkDataStructure> getApkJSON(String VERSION) {
        return apiService.getApkJSON(VERSION);
    }

    public Observable<VersionServerDataStructure> getVersionJSON() {
        return apiService.getVersionJSON();
    }

    public Observable<OrdersListDataStructure> getOrdersListJSON() {
        return apiService.getOrdersListJSON(new PrefUtils().getWaiterCode());
    }

    public Observable<OrdersListDataStructure> getPositionsListJSON() {
        return apiService.getPositionsListJSON(new PrefUtils().getWaiterCode());
    }

    public Observable<ActionDataStructure> getActionJSON(String action, String guid, int state) {
        return apiService.getActionJSON(action, guid, state);
    }


}

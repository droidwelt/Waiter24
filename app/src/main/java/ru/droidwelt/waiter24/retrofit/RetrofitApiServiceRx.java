package ru.droidwelt.waiter24.retrofit;


import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.droidwelt.waiter24.receive.action.ActionDataStructure;
import ru.droidwelt.waiter24.receive.orderslist.OrdersListDataStructure;
import ru.droidwelt.waiter24.receive.updateapk.ApkDataStructure;
import ru.droidwelt.waiter24.receive.versionverver.VersionServerDataStructure;
import rx.Observable;

public interface RetrofitApiServiceRx {


    @GET("GetVersionJSON.php")
    Observable<VersionServerDataStructure> getVersionJSON();

    @GET("WT_GetOrdersAndPosListJSON.php")
    Observable<OrdersListDataStructure> getOrdersListJSON(@Query("WAITER") String waiter);

    @GET("WT_GetPosListJSON.php")
    Observable<OrdersListDataStructure> getPositionsListJSON(@Query("WAITER") String waiter);

    @GET("WT_GetActionJSON.php")
    Observable<ActionDataStructure> getActionJSON(@Query("ACTION") String action,
                                                  @Query("GUID") String guid,
                                                  @Query("STATE") int state);

    @GET("GetApkWaiterJSON.php")
    Observable<ApkDataStructure> getApkJSON(@Query("VERSION")String VERSION);

}




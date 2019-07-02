package ru.droidwelt.waiter24.receive.orderslist;

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;
import ru.droidwelt.waiter24.retrofit.NetworkError;
import ru.droidwelt.waiter24.retrofit.RetrofitClientRx;
import ru.droidwelt.waiter24.utils.NetworkUtils;
import ru.droidwelt.waiter24.utils.ToastUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrdersListLoader {


    public void getOrdersList(final OrdersListPresenter cip) {

        if (new NetworkUtils().checkConnection(true)) {


         /*   Observer ob = new Observer<OrdersListDataStructure>() {
                @Override
                public void onCompleted() {
                    Log.e("OOOOOOOOO", "onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.DisplayToastError(Appl.getContext().getString(R.string.s_error_api) + "\n" + e.getMessage());
                }

                @Override
                public void onNext(OrdersListDataStructure ds) {
                    Log.e("OOOOOOOOO", "onNext");
                    ArrayList<OrdersListDataClass> ord = ds.getOrdersList();
                    ArrayList<PosListDataClass> pos = ds.getPosList();
                    cip.isReadyOrdersListData(ord, pos);
                }
            };*/

            RetrofitClientRx.getInstance()
                    .getOrdersListJSON()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                 //   .subscribe(ob);
                   .subscribe(new Observer<OrdersListDataStructure>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            String s = new  NetworkError(e).getAppErrorMessage();
                            Log.e("Error:",s);
                            ToastUtils.DisplayToastError(Appl.getContext().getString(R.string.s_error_api) + "\n" + s);
                        }

                        @Override
                        public void onNext(OrdersListDataStructure ds) {
                            ArrayList<OrdersListDataClass> ord = ds.getOrdersList();
                            ArrayList<PosListDataClass> pos = ds.getPosList();
                            cip.isReadyOrdersListData(ord, pos);
                        }
                    });
        }
    }


    public void getPositionList(final PositionListPresenter cip) {

        if (new NetworkUtils().checkConnection(true)) {

            RetrofitClientRx.getInstance()
                    .getPositionsListJSON()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<OrdersListDataStructure>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.DisplayToastError(Appl.getContext().getString(R.string.s_error_api) + "\n" + e.getMessage());
                        }

                        @Override
                        public void onNext(OrdersListDataStructure ds) {
                            ArrayList<PosListDataClass> pos = ds.getPosList();
                            cip.isReadyPositionListData(pos);
                        }

                    });
        }
    }




}

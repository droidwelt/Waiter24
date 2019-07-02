package ru.droidwelt.waiter24.receive.updateapk;

import android.util.Log;

import java.util.ArrayList;


import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;
import ru.droidwelt.waiter24.retrofit.RetrofitClientRx;
import ru.droidwelt.waiter24.utils.MainUtils;
import ru.droidwelt.waiter24.utils.NetworkUtils;
import ru.droidwelt.waiter24.utils.ToastUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApkLoader {


    public void getApk(final ApkPresenter cip) {

        if (new NetworkUtils().checkConnection(true)) {
            String sVersion = Integer.toString(new MainUtils().getVersionCode());

            RetrofitClientRx.getInstance()
                    .getApkJSON(sVersion)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApkDataStructure>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.DisplayToastError(Appl.getContext().getString(R.string.s_error_api) + "\n" + e.getMessage());
                            Log.e("ApkLoader", e.getMessage());
                        }

                        @Override
                        public void onNext(ApkDataStructure ds) {
                            ArrayList<ApkDataClass> res = ds.getApk();
                            cip.isReadyApkData(res);
                        }
                    });
        }
    }


}

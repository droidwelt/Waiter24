package ru.droidwelt.waiter24.receive.versionverver;

import java.util.ArrayList;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;
import ru.droidwelt.waiter24.retrofit.RetrofitClientRx;
import ru.droidwelt.waiter24.utils.NetworkUtils;
import ru.droidwelt.waiter24.utils.ToastUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VersionServerLoader {


    public void getVersion(final VersionServerMainPresenter cip) {

        if (new NetworkUtils().checkConnection(true)) {

            RetrofitClientRx.getInstance()
                    .getVersionJSON()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<VersionServerDataStructure>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.DisplayToastError(Appl.getContext().getString(R.string.s_error_api) + "\n" + e.getMessage());
                        }

                        @Override
                        public void onNext(VersionServerDataStructure ds) {
                            ArrayList<VersionServerDataClass> res = ds.getVersionServer();
                            if (res.size() > 0) {
                                VersionServerDataClass cic = res.get(0);
                                if (cip != null) {
                                    cip.isReadyVersionServerData(cic);                                }

                            }
                        }
                    });
        }
    }


}

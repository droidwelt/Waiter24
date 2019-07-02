package ru.droidwelt.waiter24.receive.action;

import java.util.ArrayList;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;
import ru.droidwelt.waiter24.retrofit.RetrofitClientRx;
import ru.droidwelt.waiter24.utils.NetworkUtils;
import ru.droidwelt.waiter24.utils.ToastUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActionLoader {


    public void getAction(final int frMode, final ActionPresenter cip, final String action, final String guid, final int state) {

        if (new NetworkUtils().checkConnection(true)) {

            RetrofitClientRx.getInstance()
                    .getActionJSON(action, guid, state)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ActionDataStructure>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.DisplayToastError(Appl.getContext().getString(R.string.s_error_api) + "\n" + e.getMessage());
                        }

                        @Override
                        public void onNext(ActionDataStructure ds) {
                            ArrayList<ActionDataClass> res = ds.getAction();
                            if (res.size() > 0) {
                                ActionDataClass cic = res.get(0);
                                if (cip != null) {
                                    if (frMode == 1) cip.isReadyActionDataFr1(cic);
                                    if (frMode == 2) cip.isReadyActionDataFr2(cic);
                                }

                            }
                        }
                    });
        }
    }


}

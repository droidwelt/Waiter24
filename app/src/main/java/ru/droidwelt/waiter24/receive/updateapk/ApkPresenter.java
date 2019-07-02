package ru.droidwelt.waiter24.receive.updateapk;


import java.util.ArrayList;

import ru.droidwelt.waiter24.main_UI.MainActivity;

public class ApkPresenter {

    private MainActivity view;

    public void attachView(MainActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }


    public void getActionData() {
        new ApkLoader().getApk( this);
    }


    public void isReadyApkData(ArrayList<ApkDataClass> cic) {
        if (view != null) {
            view.update_answer(cic);
       }
    }



}

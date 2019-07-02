package ru.droidwelt.waiter24.receive.versionverver;


import ru.droidwelt.waiter24.main_UI.MainActivity;

public class VersionServerMainPresenter {

    private MainActivity view;

    public void attachView(MainActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }


    public void getVersionServerData() {
        new VersionServerLoader().getVersion( this);
    }


    public void isReadyVersionServerData(VersionServerDataClass cic) {
        if (view != null) {
            view.displayVersionServer(cic);
        }
    }



}

package ru.droidwelt.waiter24.receive.orderslist;


import java.util.ArrayList;

import ru.droidwelt.waiter24.main_UI.FragmentTwo;

public class PositionListPresenter {

    private FragmentTwo view;

    public void attachView(FragmentTwo usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }

    public void getPositionListData() {
        new OrdersListLoader().getPositionList( this);
    }



    public void isReadyPositionListData(ArrayList<PosListDataClass> pos) {
        if (view != null) {
            view.position_refreshDataResult(pos);
        }
    }



}

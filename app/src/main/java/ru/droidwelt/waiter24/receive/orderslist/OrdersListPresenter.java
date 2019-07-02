package ru.droidwelt.waiter24.receive.orderslist;


import java.util.ArrayList;

import ru.droidwelt.waiter24.main_UI.FragmentOne;

public class OrdersListPresenter {

    private FragmentOne view;

    public void attachView(FragmentOne usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }


    public void getOrdersListData() {
        new OrdersListLoader().getOrdersList( this);
    }

 /*   public void getPositionListData() {
        new OrdersListLoader().getPositionList( this);
    }
*/

    public void isReadyOrdersListData(ArrayList<OrdersListDataClass> ord, ArrayList<PosListDataClass> pos) {
        if (view != null) {
            view.ord_refreshDataResult(ord, pos);
        }
    }


 /*   public void isReadyPositionListData(ArrayList<PosListDataClass> pos) {
        if (view != null) {
            view.position_refreshDataResult(pos);
        }
    }
*/


}

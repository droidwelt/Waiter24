package ru.droidwelt.waiter24.receive.action;


import ru.droidwelt.waiter24.common.Appl;
import ru.droidwelt.waiter24.main_UI.FragmentOne;
import ru.droidwelt.waiter24.main_UI.FragmentTwo;

public class ActionPresenter {

    private FragmentOne viewFr1;
    private FragmentTwo viewFr2;
    private String actionX;

    public void attachViewFr1(FragmentOne usersActivity) {
        viewFr1 = usersActivity;
    }

    public void attachViewFr2(FragmentTwo usersActivity) {
        viewFr2 = usersActivity;
    }

    public void detachViewFr1() {
        viewFr1 = null;
    }

    public void detachViewFr2() {
        viewFr2 = null;
    }


    public void getActionData(int ftMode,String action, String guid, int state) {
        actionX = action;
        new ActionLoader().getAction(ftMode,this, action, guid, state);
    }


    public void isReadyActionDataFr1(ActionDataClass cic) {
        if (viewFr1 != null) {
            if (actionX.equals("BILLPAYED")) {
                Appl.ORD_PAYMENT = 9;
            }
            viewFr1.displayAction();
        }
    }

    public void isReadyActionDataFr2(ActionDataClass cic) {
        if (viewFr2 != null) {
            viewFr2.displayAction();
        }
    }


}

package ru.droidwelt.waiter24.utils;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;

public class OrdersUtils {

    public int getStateColor(int state) {
        if (state == 0) return Appl.getContext().getResources().getColor(R.color.c_order_0);
        else if (state == 1) return Appl.getContext().getResources().getColor(R.color.c_order_1);
        else if (state == 2) return Appl.getContext().getResources().getColor(R.color.c_order_2);
        else if (state == 3) return Appl.getContext().getResources().getColor(R.color.c_order_3);
        else if (state == 4) return Appl.getContext().getResources().getColor(R.color.c_order_4);
        else if (state == 5) return Appl.getContext().getResources().getColor(R.color.c_order_5);
        else if (state == 6) return Appl.getContext().getResources().getColor(R.color.c_order_6);
        else if (state == 7) return Appl.getContext().getResources().getColor(R.color.c_order_7);
        else if (state == 8) return Appl.getContext().getResources().getColor(R.color.c_order_8);
        else if (state == 9) return Appl.getContext().getResources().getColor(R.color.c_order_9);
        else return 0;
    }


    public String getStateText(int state) {
        if (state == 0) return Appl.getContext().getResources().getString(R.string.s_order_0);
        else if (state == 1) return Appl.getContext().getResources().getString(R.string.s_order_1);
        else if (state == 2) return Appl.getContext().getResources().getString(R.string.s_order_2);
        else if (state == 3) return Appl.getContext().getResources().getString(R.string.s_order_3);
        else if (state == 4) return Appl.getContext().getResources().getString(R.string.s_order_4);
        else if (state == 5) return Appl.getContext().getResources().getString(R.string.s_order_5);
        else if (state == 6) return Appl.getContext().getResources().getString(R.string.s_order_6);
        else if (state == 7) return Appl.getContext().getResources().getString(R.string.s_order_7);
        else if (state == 8) return Appl.getContext().getResources().getString(R.string.s_order_8);
        else if (state == 9) return Appl.getContext().getResources().getString(R.string.s_order_9);
        else return "";
    }




    public String getPaymentText(int state) {
        if (state == 0) return Appl.getContext().getResources().getString(R.string.s_payment_0);
        else if (state == 1) return Appl.getContext().getResources().getString(R.string.s_payment_1);
        else if (state == 2) return Appl.getContext().getResources().getString(R.string.s_payment_2);
        else if (state == 3) return Appl.getContext().getResources().getString(R.string.s_payment_3);
        else if (state == 4) return Appl.getContext().getResources().getString(R.string.s_payment_4);
        else if (state == 5) return Appl.getContext().getResources().getString(R.string.s_payment_5);
        else if (state == 6) return Appl.getContext().getResources().getString(R.string.s_payment_6);
        else if (state == 7) return Appl.getContext().getResources().getString(R.string.s_payment_7);
        else if (state == 8) return Appl.getContext().getResources().getString(R.string.s_payment_8);
        else if (state == 9) return Appl.getContext().getResources().getString(R.string.s_payment_9);
        else return "";
    }

}

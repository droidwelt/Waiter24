package ru.droidwelt.waiter24.adapters;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;
import ru.droidwelt.waiter24.main_UI.FragmentOne;
import ru.droidwelt.waiter24.main_UI.MainActivity;
import ru.droidwelt.waiter24.receive.orderslist.OrdersListDataClass;
import ru.droidwelt.waiter24.utils.MainUtils;
import ru.droidwelt.waiter24.utils.OrdersUtils;


public class OrdRecyclerAdapter extends RecyclerView.Adapter<OrdRecyclerAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public int getItemCount() {
        return FragmentOne.ord_list.size();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final OrdersListDataClass ordListItem = FragmentOne.ord_list.get(position);
        viewHolder.order_table.setText(ordListItem.TABLENO + "." + MainUtils.intToStrEx(ordListItem.NOMER));
        if (ordListItem.CALL == 1) {
            viewHolder.order_call.setVisibility(View.VISIBLE);
        } else {
            viewHolder.order_call.setVisibility(View.INVISIBLE);
        }
        if (ordListItem.NEWORDER == 1) {
            viewHolder.order_neworder.setVisibility(View.VISIBLE);
        } else {
            viewHolder.order_neworder.setVisibility(View.INVISIBLE);
        }
        if (ordListItem.NEWPAYMENT == 1) {
            viewHolder.order_newpayment.setVisibility(View.VISIBLE);
        } else {
            viewHolder.order_newpayment.setVisibility(View.INVISIBLE);
        }
        if (Appl.ORD_POS == viewHolder.getAdapterPosition()) {
            viewHolder.ly_ord_main.setBackgroundColor(Appl.getContext().getResources().getColor(R.color.c_red));
        } else {
            viewHolder.ly_ord_main.setBackgroundColor(Appl.getContext().getResources().getColor(R.color.c_lightgray));
        }

        int st = ordListItem.STATE;
        if (ordListItem.PAYMENT > st) {
            st = ordListItem.PAYMENT;
        }
        viewHolder.ly_ord_state.setBackgroundColor(new OrdersUtils().getStateColor(st));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Appl.ORD_POS = position;
                Appl.ORD_STATE = ordListItem.STATE;
                Appl.ORD_PAYMENT = ordListItem.PAYMENT;
                Appl.ORD_ID = ordListItem.ORDERS_ID;
                Appl.ORD_TABLENO = ordListItem.TABLENO;
                Appl.ORD_NOMER = ordListItem.NOMER;
                notifyDataSetChanged();
                MainActivity.getInstance().fr1.pos_refreshData();
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Appl.ORD_POS = position;
                Appl.ORD_STATE = ordListItem.STATE;
                Appl.ORD_PAYMENT = ordListItem.PAYMENT;
                Appl.ORD_ID = ordListItem.ORDERS_ID;
                Appl.ORD_TABLENO = ordListItem.TABLENO;
                Appl.ORD_NOMER = ordListItem.NOMER;
                MainActivity.getInstance().fr1.clear_call();
                return false;// false
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView order_table;
        final ImageView order_call;
        final ImageView order_neworder;
        final ImageView order_newpayment;
        final LinearLayout ly_ord_main;
        final LinearLayout ly_ord_state;

        ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            order_table = itemLayoutView.findViewById(R.id.order_table);
            order_call = itemLayoutView.findViewById(R.id.order_call);
            order_neworder = itemLayoutView.findViewById(R.id.order_neworder);
            order_newpayment = itemLayoutView.findViewById(R.id.order_newpayment);
            ly_ord_main = itemLayoutView.findViewById(R.id.ly_ord_main);
            ly_ord_state = itemLayoutView.findViewById(R.id.ly_ord_state);

            if (getAdapterPosition() >= 0) {
                Appl.ORD_POS = getAdapterPosition();
            }
        }
    }
}
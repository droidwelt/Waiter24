package ru.droidwelt.waiter24.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;
import ru.droidwelt.waiter24.main_UI.FragmentTwo;
import ru.droidwelt.waiter24.main_UI.MainActivity;
import ru.droidwelt.waiter24.receive.action.ActionPresenter;
import ru.droidwelt.waiter24.receive.orderslist.PosListDataClass;


public class PositionRecyclerAdapter extends RecyclerView.Adapter<PositionRecyclerAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_position2, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public int getItemCount() {
        return FragmentTwo.position_list.size();
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final PosListDataClass posListItem = FragmentTwo.position_list.get(position);
        viewHolder.pos_name.setText(posListItem.NAME);
        if  (posListItem.CHANGED == 1) {
            viewHolder.pos_name.setTextColor(Appl.getContext().getResources().getColor(R.color.c_red));
        }  else {
            viewHolder.pos_name.setTextColor(Appl.getContext().getResources().getColor(R.color.c_text));
        }
        String s;
        if (posListItem.NUMBER > 1) {
            float sumpos = posListItem.PRICE * posListItem.NUMBER;
            s = "€ " + Appl.fmtM.format(posListItem.PRICE) + " x " + Appl.fmtN.format(posListItem.NUMBER) + " = " + "€ " + Appl.fmtM.format(sumpos);
        } else {
            s = "€ " + Appl.fmtM.format(posListItem.PRICE);
        }
        if (posListItem.STATE == 1) {
            s = s + "   ГОТОВО";
            viewHolder.ly_pos_main.setBackgroundColor(Appl.getContext().getResources().getColor(R.color.c_posstate_1));
        } else if (posListItem.STATE == 2) {
            s = s + "   ПОДАНО";
            viewHolder.ly_pos_main.setBackgroundColor(Appl.getContext().getResources().getColor(R.color.c_posstate_2));
        }  else {
            viewHolder.ly_pos_main.setBackgroundColor(Appl.getContext().getResources().getColor(R.color.c_posstate_0));
        }

        viewHolder.pos_price.setText(s);

        s = posListItem.TABLENO+'.'+posListItem.NOMER;
        if (posListItem.CALL == 0) {
            viewHolder.pos_tableno.setTextColor(Appl.getContext().getResources().getColor(R.color.c_text));
        }  else {
            viewHolder.pos_tableno.setTextColor(Appl.getContext().getResources().getColor(R.color.c_red));
        }
        viewHolder.pos_tableno.setText(s);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // что-нибудь
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Appl.POS_POS = position;
                Appl.POS_STATE = posListItem.STATE;
                Appl.POS_ID = posListItem.POSITIONS_ID;

                int newstate = 0;
                if (Appl.POS_STATE == 0) newstate = 1;
                if (Appl.POS_STATE == 1) newstate = 2;
                ActionPresenter ap = new ActionPresenter();
                ap.attachViewFr2( MainActivity.getInstance().fr2);
                ap.getActionData(2,"CHANGEPOSSTATE", Appl.POS_ID, newstate);
                return false;
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView pos_name;
        final TextView pos_price;
        final TextView pos_tableno;
        final LinearLayout ly_pos_main;

        ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            pos_name = itemLayoutView.findViewById(R.id.pos_name);
            pos_price = itemLayoutView.findViewById(R.id.pos_price);
            pos_tableno = itemLayoutView.findViewById(R.id.pos_tableno);
            ly_pos_main = itemLayoutView.findViewById(R.id.ly_pos_main);
        }
    }
}
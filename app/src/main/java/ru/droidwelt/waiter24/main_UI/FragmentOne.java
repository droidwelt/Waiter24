package ru.droidwelt.waiter24.main_UI;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.adapters.OrdRecyclerAdapter;
import ru.droidwelt.waiter24.adapters.PosRecyclerAdapter;
import ru.droidwelt.waiter24.common.Appl;
import ru.droidwelt.waiter24.ebus.Events;
import ru.droidwelt.waiter24.ebus.GlobalBus;
import ru.droidwelt.waiter24.receive.action.ActionPresenter;
import ru.droidwelt.waiter24.receive.orderslist.OrdersListDataClass;
import ru.droidwelt.waiter24.receive.orderslist.OrdersListPresenter;
import ru.droidwelt.waiter24.receive.orderslist.PosListDataClass;
import ru.droidwelt.waiter24.utils.OrdersUtils;
import ru.droidwelt.waiter24.utils.PrefUtils;
import ru.droidwelt.waiter24.utils.SoundUtils;


public class FragmentOne extends Fragment {

    private  View vx;

    public static List<OrdersListDataClass> ord_list;
    private RecyclerView ord_RecyclerView;
    private static RecyclerView.Adapter ord_Adapter;

    private static List<PosListDataClass> pos_listall;
    public static List<PosListDataClass> pos_list;
    private RecyclerView pos_RecyclerView;
    private static RecyclerView.Adapter pos_Adapter;

    private ImageButton ib_payed;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        GlobalBus.getBus().register(this);
        vx= v;
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ib_payed = view.findViewById(R.id.ib_payed);
        ib_payed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPayed();
            }
        });

        ord_RecyclerView = vx.findViewById(R.id.rv_orders);
        ord_RecyclerView.setHapticFeedbackEnabled(true);
        LinearLayoutManager ord_LayoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
        ord_RecyclerView.setLayoutManager(ord_LayoutManager);
        ord_list = new ArrayList<>();
        ord_Adapter = new OrdRecyclerAdapter();
        ord_RecyclerView.setAdapter(ord_Adapter);

        pos_RecyclerView = vx.findViewById(R.id.rv_pos);
        pos_RecyclerView.setHapticFeedbackEnabled(true);
        LinearLayoutManager pos_LayoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
        pos_RecyclerView.setLayoutManager(pos_LayoutManager);
        pos_listall = new ArrayList<>();
        pos_list = new ArrayList<>();
        pos_Adapter = new PosRecyclerAdapter();
        pos_RecyclerView.setAdapter(pos_Adapter);
        ord_refreshDataRequest();
    }

    //------------------- ЗАПРОС ПОЛУЧЕНИЯ СПИСКА ЗАКАЗОВ И ИХ ПОЗИЦИЙ -------------------------------------------
    public void ord_refreshDataRequest() {
        OrdersListPresenter presenter = new OrdersListPresenter();
        presenter.attachView(this);
        presenter.getOrdersListData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  //
    public void getMessage(Events.EventsMessage ev) {
       ;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }



    private void dialogPayed() {
        final Timer timer = new Timer();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity(), R.style.AlertDialogColorButton);
        builder.setTitle(R.string.s_billpayed);
        //   builder.setMessage("");

        builder.setNegativeButton(R.string.s_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.purge();
                timer.cancel();
            }
        });

        builder.setPositiveButton(R.string.s_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        timer.purge();
                        timer.cancel();
                        setBillPayed();
                    }
                });

        final AlertDialog dlg = builder.create();
        dlg.show();

        timer.schedule(new TimerTask() {
            public void run() {
                dlg.dismiss();
                timer.purge();
                timer.cancel();
            }
        }, 10000);
    }


    //--------------------- ЗАПРОС ПОДТВЕРЖДЕНИЯ ОПЛАТЫ ОФИЦИАНТОМ -------------------------------
    private void setBillPayed() {
        ActionPresenter ap = new ActionPresenter();
        ap.attachViewFr1(this);
        ap.getActionData(1,"BILLPAYED", Appl.ORD_ID, 0);
    }


    //------------------- ОБРАБОТКА ПОЛУЧЕННОГО СПИСКА ЗАКАЗОВ И ИХ ПОЗИЦИЙ -------------------------------------------
    public void ord_refreshDataResult(ArrayList<OrdersListDataClass> ord, ArrayList<PosListDataClass> pos) {
        int numbCall = 0;
        int iSaveWaiter =0;
        ord_list.clear();
        for (int i = 0; i < ord.size(); i = i + 1) {
            if (!(ord.get(i).ORDERS_ID.isEmpty())) {
                ord_list.add(ord.get(i));
                if (ord.get(i).CALL > 0) numbCall++;
                if (iSaveWaiter==0)  {
                    new PrefUtils().setWaiterName(ord.get(i).FIO_WAITER);
                    iSaveWaiter++;
                }
            }
        }

        if (numbCall > 0) {
            SoundUtils.playSoundCallWaiter();
            if (new PrefUtils().getUseVibrate()) {
                SoundUtils.vibrate();
            }
        }

        pos_list.clear();
        pos_listall.clear();
        for (int i = 0; i < pos.size(); i = i + 1) {
            if (!(pos.get(i).POSITIONS_ID.isEmpty())) {
                pos_listall.add(pos.get(i));
            }
        }

        ord_Adapter.notifyDataSetChanged();
        if ((ord_RecyclerView.getAdapter() != null) && (ord_RecyclerView.getAdapter().getItemCount() > 0)) {
            if (Appl.ORD_POS >= 0) {
                if (ord_RecyclerView.getAdapter().getItemCount() > Appl.ORD_POS) {
                    ord_RecyclerView.scrollToPosition(Appl.ORD_POS);
                } else
                    ord_RecyclerView.scrollToPosition(ord_RecyclerView.getAdapter().getItemCount() - 1);
            }
            pos_refreshData();
        }
    }


    //------------------- ПОКАЗ ДАННЫХ ПО ПОЗИЦИЯМ ЗАКАЗА ПРИ СМЕНЕ ЗАКАЗА  -------------------------------------------
    public void pos_refreshData() {

        pos_list.clear();
        for (int i = 0; i < pos_listall.size(); i = i + 1) {
            if (pos_listall.get(i).ORDERS_ID.equals(Appl.ORD_ID)) {
                pos_list.add(pos_listall.get(i));
            }
        }

        ord_displayTotal();

        pos_Adapter.notifyDataSetChanged();
        if ((pos_RecyclerView.getAdapter() != null) && (pos_RecyclerView.getAdapter().getItemCount() > 0)) {
            if (Appl.POS_POS >= 0) {
                if (pos_RecyclerView.getAdapter().getItemCount() > Appl.POS_POS)
                    pos_RecyclerView.scrollToPosition(Appl.POS_POS);
                else
                    pos_RecyclerView.scrollToPosition(pos_RecyclerView.getAdapter().getItemCount() - 1);
            }
        }
    }


    //--------------------- ПОКАЗ СОСТОЯНИЯ ЗАКАЗА, ЕГО НОМЕРА И СУММЫ -------------------------------
    private void ord_displayTotal() {
        TextView total_state = vx.findViewById(R.id.total_state);
        TextView total_summa = vx.findViewById(R.id.total_summa);
        String s = "";
        if (!(Appl.ORD_TABLENO.isEmpty())) {
            s = new OrdersUtils().getStateText(Appl.ORD_STATE) + ";  " + new OrdersUtils().getPaymentText(Appl.ORD_PAYMENT);
            total_state.setText(s);

            float sumpos = 0;
            for (int i = 0; i < pos_list.size(); i = i + 1) {
                sumpos = sumpos + pos_list.get(i).PRICE * pos_list.get(i).NUMBER;
            }

            s = "";
            if (!(Appl.ORD_TABLENO.isEmpty())) {
                s = "Заказ " + Appl.ORD_TABLENO + "." + Appl.ORD_NOMER;
                if (sumpos != 0) {
                    s = s + "  " + "€ " + Appl.fmtM.format(sumpos);
                }
            }
            total_summa.setText(s);

            if ((sumpos > 0) && (Appl.ORD_PAYMENT) != 9) {
                ib_payed.setVisibility(View.VISIBLE);
            } else {
                ib_payed.setVisibility(View.GONE);
            }
        } else {
            total_state.setText(s);
            total_summa.setText(s);
            ib_payed.setVisibility(View.GONE);
        }
    }


    //--------------------- ЗАПРОС СТИРАНИЯ ЗВОНКА ОФИЦИАНТУ -------------------------------
    public void clear_call() {
        ActionPresenter ap = new ActionPresenter();
        ap.attachViewFr1(this);
        ap.getActionData(1,"CLEARCALL", Appl.ORD_ID, 0);
    }

    //--------------------- ОТВЕТ ПОСЛЕ СТИРАНИЯ ЗВОНКА ОФИЦИАНТУ -------------------------------
    public void displayAction() {
        ord_refreshDataRequest();
        ord_displayTotal();
    }


}

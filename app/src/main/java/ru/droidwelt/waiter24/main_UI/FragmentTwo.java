package ru.droidwelt.waiter24.main_UI;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.adapters.PositionRecyclerAdapter;
import ru.droidwelt.waiter24.ebus.Events;
import ru.droidwelt.waiter24.ebus.GlobalBus;
import ru.droidwelt.waiter24.receive.orderslist.PosListDataClass;
import ru.droidwelt.waiter24.receive.orderslist.PositionListPresenter;
import ru.droidwelt.waiter24.utils.PrefUtils;


public class FragmentTwo extends Fragment {

    public static List<PosListDataClass> position_list;
    private static RecyclerView.Adapter position_Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        GlobalBus.getBus().register(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView position_RecyclerView = view.findViewById(R.id.rv_pos);
        position_RecyclerView.setHapticFeedbackEnabled(true);
        LinearLayoutManager pos_LayoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
        position_RecyclerView.setLayoutManager(pos_LayoutManager);
        position_list = new ArrayList<>();
        position_Adapter = new PositionRecyclerAdapter();
        position_RecyclerView.setAdapter(position_Adapter);
        position_refreshDataRequest();
    }

    //------------------- ЗАПРОС ПОЛУЧЕНИЯ СПИСКА  ПОЗИЦИЙ -------------------------------------------
    public void position_refreshDataRequest() {
        PositionListPresenter presenter = new PositionListPresenter();
        presenter.attachView(this);
        presenter.getPositionListData();
    }

    //------------------- ОБРАБОТКА ПОЛУЧЕННОГО СПИСКА  ПОЗИЦИЙ -------------------------------------------
    public void position_refreshDataResult(ArrayList<PosListDataClass> pos) {
        position_list.clear();
        int iSaveWaiter=0;
        for (int i = 0; i < pos.size(); i = i + 1) {
            if (!(pos.get(i).POSITIONS_ID.isEmpty())) {
                position_list.add(pos.get(i));
                if (iSaveWaiter==0)  {
                    new PrefUtils().setWaiterName(pos.get(i).FIO_WAITER);
                    iSaveWaiter++;
                }
            }
        }
        position_Adapter.notifyDataSetChanged();
    }

    //--------------------- ОТВЕТ ПОСЛЕ ИЗМЕНЕНИЯ СОСТОЯНИЯ ПОЗИЦИИ -------------------------------
    public void displayAction() {
        position_refreshDataRequest();
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



}

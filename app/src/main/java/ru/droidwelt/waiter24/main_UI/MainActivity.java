package ru.droidwelt.waiter24.main_UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;
import ru.droidwelt.waiter24.common.Const;
import ru.droidwelt.waiter24.ebus.Events;
import ru.droidwelt.waiter24.ebus.GlobalBus;
import ru.droidwelt.waiter24.receive.updateapk.ApkDataClass;
import ru.droidwelt.waiter24.receive.updateapk.ApkPresenter;
import ru.droidwelt.waiter24.receive.versionverver.VersionServerDataClass;
import ru.droidwelt.waiter24.receive.versionverver.VersionServerMainPresenter;
import ru.droidwelt.waiter24.utils.PrefUtils;
import ru.droidwelt.waiter24.utils.ToastUtils;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
import static ru.droidwelt.waiter24.common.Const.TimerRefreshInterval;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("StaticFieldLeak")
    private static MainActivity instance;

    private MenuItem mi = null;
    private MenuItem mMode = null;
    private Timer mTimer;

    public FragmentOne fr1;
    public FragmentTwo fr2;

    public static synchronized MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalBus.getBus().register(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        instance = this;
        Appl.setCsmOn();
        displayNotConnected();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                TextView waiter_name = findViewById(R.id.header_name);
                waiter_name.setText(new PrefUtils().getWaiterName());
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        selectFragment();
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
          //  Log.e("ERR", "MyTimerTask exec");
            if (Appl.isActiveConnection()) {
                try {
                    if (Appl.ORDERSMODE) {
                        fr1.ord_refreshDataRequest();
                    } else {
                        fr2.position_refreshDataRequest();
                    }
                   // SoundUtils.playSoundAddPosition();
                } catch (Exception ignored) {
                }
            }
        }
    }


    public void fr1_Add() {
        FragmentManager fm = getFragmentManager();
        if (fr1 == null) {
            fr1 = new FragmentOne();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.fragment_place, fr1, "FR1");
            fragmentTransaction.commit();
        }
    }


    public void fr2_Add() {
        FragmentManager fm = getFragmentManager();
        if (fr2 == null) {
            fr2 = new FragmentTwo();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.fragment_place, fr2, "FR2");
            fragmentTransaction.addToBackStack("FR2");
            fragmentTransaction.setTransition(TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    public void fr1_Remove() {
        FragmentManager fm = getFragmentManager();
        if (fr1 != null) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.remove(fr1);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.commit();
            fm.popBackStack();
            fr1 = null;
        }
    }

    public void fr2_Remove() {
        FragmentManager fm = getFragmentManager();
        if (fr2 != null) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.remove(fr2);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.commit();
            fm.popBackStack();
            fr2 = null;
        }
    }


    @Override
    public void onStart() {
        Appl.setCsmOn();
        super.onStart();
        try {
            mTimer = new Timer();
            MyTimerTask mMyTimerTask = new MyTimerTask();
            mTimer.schedule(mMyTimerTask, TimerRefreshInterval, TimerRefreshInterval);
        } catch (Exception e) {
          //  Log.e("ERR", "MyTimerTask onStart");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Appl.setCsmOff();
        try {
            mTimer.cancel();
            mTimer.purge();
        } catch (Exception e) {
          //  Log.e("ERR", "MyTimerTask onStop");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mi = menu.findItem(R.id.action_noconnection);
        mMode = menu.findItem(R.id.action_mode);
        displayNotConnected();
        displayMode();
        return true;
    }

    public void selectFragment() {
        if (Appl.ORDERSMODE) {
            fr2_Remove();
            fr1_Add();
        } else {
            fr1_Remove();
            fr2_Add();
        }
    }

    public void displayMode() {
        if (mMode != null) {
            if (Appl.ORDERSMODE) {
                mMode.setIcon(R.mipmap.ic_orders);
                mMode.setTitle(R.string.action_orders);
            } else {
                mMode.setIcon(R.mipmap.ic_positions);
                mMode.setTitle(R.string.action_positions);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            if (Appl.ORDERSMODE) {
                fr1.ord_refreshDataRequest();
            } else {
                fr2.position_refreshDataRequest();
            }
            return true;
        }

        if (id == R.id.action_mode) {
            Appl.ORDERSMODE = !Appl.ORDERSMODE;
            displayMode();
            selectFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_connectiontest) {
            requestVersionServer();
        } else if (id == R.id.nav_about) {
            Intent intentabout = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intentabout);
        } else if (id == R.id.nav_setting) {
            Intent intentpref = new Intent(MainActivity.this, PrefActivity.class);
            int REQUEST_CODE_PREF = 1003;
            startActivityForResult(intentpref, REQUEST_CODE_PREF);
        } else if (id == R.id.nav_update) {
            update_request();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //-----------------------------------------------------------------------------------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Appl.setCsmOff();
        GlobalBus.getBus().unregister(this);
    }


    //---------------------- ЗАПРОС ТЕСТА КОНЕНЕКТА ----------------------------------------------
    private void requestVersionServer() {
        VersionServerMainPresenter testpresenter = new VersionServerMainPresenter();
        testpresenter.attachView(this);
        testpresenter.getVersionServerData();
    }

    //---------------------- ПОКАЗ ОТВЕТА ТЕСТА КОНЕНЕКТА ----------------------------------------------
    public void displayVersionServer(VersionServerDataClass cic) {
        ToastUtils.DisplayToastOk(cic.getVersion());
    }


    public void displayNotConnected() {
        if (mi != null) {
            mi.setVisible(!(Appl.isActiveConnection()));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)  //
    public void getMessage(Events.EventsMessage ev) {
        if (ev.getMes_code().equals(Events.NETWORK_STATE)) {
            displayNotConnected();
        }
    }


    //--------------------- ЗАПРОС ОБНОВЛЕНИЯ -------------------------------
    public void update_request() {
        ApkPresenter ap = new ApkPresenter();
        ap.attachView(this);
        ap.getActionData();
    }

    //--------------------- ОТВЕТ ОБНОВЛЕНИЯ -------------------------------
    public void update_answer(ArrayList<ApkDataClass> ds) {
        if (ds.get(0).APK.length() > 100) {
            ToastUtils.DisplayToastOk(Appl.getContext().getResources().getString(R.string.s_loadmenu_updatestart));
            updateProcess(ds);
        } else {
            ToastUtils.DisplayToastOk(Appl.getContext().getResources().getString(R.string.s_loadmenu_uselastversion));
        }
    }

    public void updateProcess(ArrayList<ApkDataClass> ds) {
        String myApkPath;
        // 21 - 5.0 Lolipop
        if (Build.VERSION.SDK_INT >= 21) {
            myApkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        } else {
            myApkPath = Environment.getExternalStorageDirectory().toString() + "/Download/";
        }

        byte[] resall = Base64.decode(ds.get(0).APK, 0);

        OutputStream localDbStream = null;
        try {
            localDbStream = new FileOutputStream(myApkPath + Const.FILENAMETOAPDATE);
        } catch (FileNotFoundException e) {
            //  Log.i("'333333-1", myApkPath);
        }
        try {
            assert localDbStream != null;
            localDbStream.write(resall);
        } catch (IOException e) {
            //  Log.i("'333333-2", e.toString());
        }

        //   Uri u = Uri.fromFile(new File(myApkPath + Const.FILENAMETOAPDATE));
        Context c = Appl.getContext();
        File f = new File(myApkPath + Const.FILENAMETOAPDATE);
        Uri u = FileProvider.getUriForFile(c, "ru.droidwelt.waiter24.provider", f);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(u, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //  super.onBackPressed();
            dialogCloseApplication();
        }
    }


    public void dialogCloseApplication() {
        final Timer timer = new Timer();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogColorButton);
        builder.setTitle(R.string.s_exitappl_header);
        builder.setMessage(R.string.s_exitappl_body);

        builder.setNegativeButton(R.string.s_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                timer.purge();
                timer.cancel();
            }
        });

        builder.setPositiveButton(R.string.s_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                timer.purge();
                timer.cancel();
                finish();
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
        }, 5000);
    }

}

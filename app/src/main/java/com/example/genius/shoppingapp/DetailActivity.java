package com.example.genius.shoppingapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.genius.shoppingapp.models.Items;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;



public class DetailActivity extends AppCompatActivity {

    private Realm realm;
//    private Button view;
    private ListView lvItemNameList;
    private CustomArrayAdapter adapter;
    private Thread backgroundThread;
    private ImageView img;

    private int id=1;


    private final ArrayList<Items>item=new ArrayList<Items>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /////navi drawer

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//

///end navi drawer

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(DetailActivity.this, AddActivity.class);
//                DetailActivity.this.startActivity(intent);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


    }

    private void selectItem()
    {
        lvItemNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Object o=adapter.getItem(position);
                final Items it=(Items)o;
                int[] s=new int[5];
                s[0]=it.getId();

                lvItemNameList.isItemChecked(position);

                img=(ImageView) view.findViewById(R.id.imageview);

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DetailActivity.this, "You Clicked at "+ position, Toast.LENGTH_SHORT).show();
                        Log.v("position", String.valueOf(position));
                    }
                });



                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Items> delete=realm.where(Items.class).equalTo("id",it.getId()).findAll();
                        delete.deleteAllFromRealm();
                    }
                });
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();

                Log.v("position", String.valueOf(s[0]));
            }
        });

    }

    private void PopupMenu()
    {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }




//
//    private void getData()
//    {
//        backgroundThread=new Thread(){
////            item.clear();
//            @Override
//            public void run() {
//                            realm = Realm.getDefaultInstance();
//
//                            RealmResults<Items> results = realm.where(Items.class).findAll();
////                realm.beginTransaction();
//
//                            for (int i = 0; i < results.size(); i++) {
//                                item.add(new Items(results.get(i).getId(),results.get(i).getShope_name().toString(), (double) results.get(i).getAmount(),results.get(i).getCount(),results.get(i).getDate().toString()));
//                            }
//                            adapter = new CustomArrayAdapter(DetailActivity.this,item);
//                            lvItemNameList=(ListView)findViewById(R.id.items);
//                            lvItemNameList.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                        }
//
//        };
//        backgroundThread.start();
//    }
//
    private void getAll()
    {
        realm = Realm.getDefaultInstance();

        RealmResults<Items> results = realm.where(Items.class).findAll();
//                realm.beginTransaction();

        for (int i = 0; i < results.size(); i++) {
            item.add(new Items(results.get(i).getId(),results.get(i).getShope_name().toString(),results.get(i).getAmount(),results.get(i).getCount(),results.get(i).getDate()));
        }
        adapter = new CustomArrayAdapter(DetailActivity.this,item);
        lvItemNameList=(ListView)findViewById(R.id.items);
        lvItemNameList.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        item.clear();
//        getData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        item.clear();
        getAll();
        selectItem();
//        lvItemNameList.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        items.clear();
        realm.close();
    }

}

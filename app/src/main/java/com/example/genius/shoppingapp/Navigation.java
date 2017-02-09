package com.example.genius.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
//import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.widget.PopupMenu;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.genius.shoppingapp.models.Items;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Realm realm;
    //    private Button view;
    private ListView lvItemNameList;
    public CustomArrayAdapter adapter;
    private Thread backgroundThread;
    private ImageView img;


    private int id=1;


    private final ArrayList<Items> item=new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Navigation.this, AddActivity.class);
                Navigation.this.startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }


    private void selectItem()
    {
        lvItemNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Object o=adapter.getItem(position);
                final Items it=(Items)o;
                final String[] s= new String[4];
                s[0]= String.valueOf(it.getAmount());
                s[1]= String.valueOf(it.getCount());
                s[2]=it.getShope_name();
                s[3]= String.valueOf(it.getDate());

                lvItemNameList.isItemChecked(position);
                Intent intent = new Intent(Navigation.this, ViewActivity.class);
                intent.putExtra("string-array", s);
                Log.v("Activity","Data  "+intent);
                startActivity(intent);

            }
        });


    }

    public void getAll()
    {
        realm = Realm.getDefaultInstance();

        RealmResults<Items> results = realm.where(Items.class).findAll();
//                realm.beginTransaction();

        for (int i = 0; i < results.size(); i++) {
            item.add(new Items(results.get(i).getId(),results.get(i).getShope_name().toString(),results.get(i).getAmount(),results.get(i).getCount(),results.get(i).getDate()));
        }
        adapter = new CustomArrayAdapter(Navigation.this,item);
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

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        items.clear();
        realm.close();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(Navigation.this, WeeklyReport.class);
            Navigation.this.startActivity(intent);


        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(Navigation.this, MonthlyReport.class);
            Navigation.this.startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(Navigation.this, NotesActivity.class);
            Navigation.this.startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(Navigation.this, ProgressActivity.class);
            Navigation.this.startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Navigation.this, ContactActivity.class);
            Navigation.this.startActivity(intent);

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(Navigation.this, AboutActivity.class);
            Navigation.this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

package com.example.genius.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.genius.shoppingapp.models.Items;
import com.example.genius.shoppingapp.models.Notes;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class NotesActivity extends AppCompatActivity {

    private Realm realm;
    private GridView gvNotes;
    private GridViewAdapter adapter;
    private final ArrayList<Notes> note=new ArrayList<Notes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, AddNotesActivity.class);
                NotesActivity.this.startActivity(intent);
            }
        });
    }

    private void selectNote()
    {
        gvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=adapter.getItem(position);
                final Notes it=(Notes) o;
                final String[] s=new String[1];
                s[0]=it.getNotes();

                gvNotes.isItemChecked(position);
                Intent intent = new Intent(NotesActivity.this, ViewNotesActivity.class);
                intent.putExtra("string-array", s);
                Log.v("Activity","Data  "+intent);
                startActivity(intent);
            }
        });
    }


    public void getAllNotes()
    {
        realm = Realm.getDefaultInstance();

        RealmResults<Notes> results = realm.where(Notes.class).findAll();
//                realm.beginTransaction();

        for (int i = 0; i < results.size(); i++) {
            note.add(new Notes(results.get(i).getId(),results.get(i).getDate(),results.get(i).getNotes()));
        }
        adapter = new GridViewAdapter(NotesActivity.this,note);
        gvNotes=(GridView) findViewById(R.id.gridview);
        gvNotes.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        note.clear();
//        getData();

    }
    @Override
    protected void onResume() {
        super.onResume();
        note.clear();
        getAllNotes();
        selectNote();

    }


}


package com.example.genius.shoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.genius.shoppingapp.models.Items;
import com.example.genius.shoppingapp.models.Notes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class AddNotesActivity extends AppCompatActivity {

    private Button addNotes;
    private EditText textNotes;
    private Realm realm;
    private String[] editNoteStr=new String[2];
    private int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        textNotes=(EditText) findViewById(R.id.textNotes);
        addNotes=(Button)findViewById(R.id.addNotes);
        realm = Realm.getDefaultInstance();


        Intent intent =getIntent();
        editNoteStr = intent.getStringArrayExtra("string-array");

        if(editNoteStr==null)
        {
            textNotes.setText("");
        }
        else
        {
            textNotes.setText(editNoteStr[0]);
            id=Integer.parseInt(editNoteStr[1]);
        }



        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textNotes.getText().length()==0)
                {
                    Toast.makeText(AddNotesActivity.this,"Type a note",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(id==0)
                    {
                        save_notes(textNotes.getText().toString());
                        textNotes.setText("");
                        Toast.makeText(AddNotesActivity.this,"Note Added",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        update_notes(textNotes.getText().toString());
                        textNotes.setText("");
                        Toast.makeText(AddNotesActivity.this,"Note Updated",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    private void update_notes(String noteText)
    {
        Notes note= realm.where(Notes.class)
                .equalTo("id", id).findFirst();

        realm.beginTransaction();
        note.setNotes(noteText);
        realm.commitTransaction();

    }


    private void save_notes(final String notes)
    {
//        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        final Date date=new Date();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Notes note=realm.createObject(Notes.class);
                int nextID = realm.where(Notes.class).max("id").intValue()+1;
                note.setId(nextID);
                note.setNotes(notes);
                note.setDate(date);


            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("database","<<<<<<<<<<<<Stored ok>>>>>>>>>");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.v("database", String.valueOf(error));
            }
        });
    }
}

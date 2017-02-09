package com.example.genius.shoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Scroller;

public class ViewNotesActivity extends AppCompatActivity {

    private String[] noteStr=new String[1] ;
    private EditText viewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewNote=(EditText)findViewById(R.id.list_note_view);
        viewNote.setScroller(new Scroller(ViewNotesActivity.this));
        viewNote.setMaxLines(20);
        viewNote.setVerticalScrollBarEnabled(true);
        viewNote.setHorizontalScrollBarEnabled(true);
        viewNote.setMovementMethod(new ScrollingMovementMethod());

        Intent intent =getIntent();
        noteStr = intent.getStringArrayExtra("string-array");

        viewNote.setText(noteStr[0]);

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

}

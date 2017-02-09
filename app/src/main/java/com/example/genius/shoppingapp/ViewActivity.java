package com.example.genius.shoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    private String[] itemStr=new String[4];
    private TextView date;
    private TextView count;
    private TextView shop;
    private TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        date=(TextView)findViewById(R.id.list_item_date);
        count=(TextView)findViewById(R.id.list_item_count);
        shop=(TextView)findViewById(R.id.list_item_shop_name);
        amount=(TextView)findViewById(R.id.list_item_amount);

        Intent intent =getIntent();
        itemStr = intent.getStringArrayExtra("string-array");


        String str=itemStr[3];
        if (str.length() > 16) {
            str = str.substring(0, 10);
        }

        amount.setText("Amount : "+itemStr[0]);
        count.setText("Items : "+itemStr[1]);
        shop.setText("Name : "+itemStr[2]);
        date.setText("Date : "+str);

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

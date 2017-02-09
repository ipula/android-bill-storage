package com.example.genius.shoppingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.genius.shoppingapp.models.Items;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddActivity extends AppCompatActivity {

    private Realm realm;
    private Button button;
    private Button dateButton;
    private EditText shope_name;
    private EditText item_count;
    private EditText amount;
    private EditText date;
    private String[] editItemStr=new String[5];
    private int id=0;
    private  Date saveDate;
    private String mdy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button=(Button)findViewById(R.id.add);
        dateButton=(Button)findViewById(R.id.dateSelect);
        shope_name=(EditText) findViewById(R.id.shope);
        item_count=(EditText) findViewById(R.id.item_count);
        amount=(EditText) findViewById(R.id.total_amount);
        date=(EditText) findViewById(R.id.date);
//        xxx=(TextView) findViewById(R.id.xxx);
        realm = Realm.getDefaultInstance();



        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddActivity.this, dataB, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


        Intent intent =getIntent();
        editItemStr = intent.getStringArrayExtra("string-array");

        if(editItemStr==null)
        {
            shope_name.setText("");
            item_count.setText("");
            amount.setText("");
            date.setText("");
        }
        else
        {
            shope_name.setText(editItemStr[0]);
            item_count.setText(editItemStr[1]);
            amount.setText(editItemStr[2]);
            date.setText(editItemStr[3]);
            id=Integer.parseInt(editItemStr[4]);
        }





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shope_name.getText().length()==0)
                {
                    Toast.makeText(AddActivity.this,"Enter Shope Name",Toast.LENGTH_SHORT).show();
                }
                else if(item_count.getText().toString().length()==0)
                {
                    Toast.makeText(AddActivity.this,"Enter Item Count Number",Toast.LENGTH_SHORT).show();
                }
                else if (amount.getText().toString().length()==0)
                {
                    Toast.makeText(AddActivity.this,"Enter Amount Number",Toast.LENGTH_SHORT).show();
                }
                else if(date.getText().toString().length()==0)
                {
                    Toast.makeText(AddActivity.this,"Enter Date",Toast.LENGTH_SHORT).show();
                }
                else if(shope_name.getText().toString().length()!=0 && date.getText().toString().length()!=0 && amount.getText().toString().length()!=0 && item_count.getText().toString().length()!=0)
                {
                    if(id==0) {
                        save_to_database(shope_name.getText().toString(), Integer.parseInt(item_count.getText().toString().trim()), Double.parseDouble(amount.getText().toString().trim()),date.getText().toString());
                    }
                    else
                    {
                        try {
                            update(id,shope_name.getText().toString(), Integer.parseInt(item_count.getText().toString().trim()), Double.parseDouble(amount.getText().toString().trim()), date.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    shope_name.setText("");
                    item_count.setText("");
                    amount.setText("");
                    date.setText("");
                    Toast.makeText(AddActivity.this,"Item Added ok",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void update(final int id,final String name, final int count, final double amount, final String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            saveDate=df.parse(date);
            mdy = df.format(saveDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

                Items item= realm.where(Items.class)
                        .equalTo("id", id).findFirst();

                realm.beginTransaction();
                item.setAmount(amount);
                item.setCount(count);
                item.setDate(df.parse(mdy));
                item.setShope_name(name);
                realm.commitTransaction();

    }

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dataB= new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private void updateLabel() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
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

    private void save_to_database(final String name, final int count, final double amount, final String date)
    {
        final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            saveDate=df.parse(date);
            mdy = df.format(saveDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Items item=realm.createObject(Items.class);
                    int nextID = realm.where(Items.class).max("id").intValue()+1;
                    item.setId(nextID);
                    item.setAmount(amount);
                    item.setCount(count);
                try {
                    item.setDate(df.parse(mdy));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                item.setShope_name(name);


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

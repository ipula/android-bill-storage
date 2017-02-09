package com.example.genius.shoppingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.genius.shoppingapp.models.Items;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProgressActivity extends AppCompatActivity {

    private Realm realm;
    private double weeklySum=0;
    private double monthlySum=0;
    private TextView weeklyProgress;
    private TextView monthlyProgress;


    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    Date dateobj = new Date();



    //    Date date; // your date
    Calendar cal = Calendar.getInstance();
//    int year = cal.get(Calendar.YEAR);
//    int month = cal.get(Calendar.MONTH);
//    int day = cal.get(Calendar.DAY_OF_MONTH);
//    // etc.
//
//    String lastMonthDate=month+"/"+day+"/"+year;
//
//    int tMonth= cal.get(Calendar.MONTH)+1;
//    String thisMonth=tMonth+"/"+day+"/"+year;
    private Date endDate,startDate;
//
//    public ProgressActivity() throws ParseException {
//        this.endDate = df.parse(lastMonthDate);
//        this.startDate = df.parse(thisMonth);
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        weeklyProgress=(TextView)findViewById(R.id.editText_progress);
        monthlyProgress=(TextView)findViewById(R.id.editText2_progress);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void monthlyProgress() throws ParseException {
        int tmonth=cal.get(Calendar.MONTH);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String thisMonth=tmonth+"/"+day+"/"+year;
        String lastMonth=month+"/"+day+"/"+year;
        this.startDate = df.parse(thisMonth);
        this.endDate = df.parse(lastMonth);

        realm = Realm.getDefaultInstance();

        RealmResults<Items> results1 = realm.where(Items.class).between("date",startDate,endDate).findAll();
        for(int i=0; i<results1.size(); i++)
        {
            monthlySum=monthlySum + results1.get(i).getAmount();
        }
        monthlyProgress.setText("Last Month Expense is : "+monthlySum);
    }

    private void weeklyProgress() throws ParseException {
        int tWeek= cal.get(Calendar.DAY_OF_MONTH)-7;
        int tmonth=cal.get(Calendar.MONTH)+1;

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String thisMonth=tmonth+"/"+tWeek+"/"+year;
        String lastMonth=month+"/"+day+"/"+year;
        this.startDate = df.parse(thisMonth);
        this.endDate = df.parse(lastMonth);

        realm = Realm.getDefaultInstance();

        RealmResults<Items> results1 = realm.where(Items.class).between("date",startDate,endDate).findAll();

        for(int i=0; i<results1.size(); i++)
        {
            weeklySum=weeklySum + results1.get(i).getAmount();
        }
        weeklyProgress.setText("Last Week Expense is : "+weeklySum);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            weeklyProgress();
            monthlyProgress();
        } catch (ParseException e) {
            e.printStackTrace();
        }


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

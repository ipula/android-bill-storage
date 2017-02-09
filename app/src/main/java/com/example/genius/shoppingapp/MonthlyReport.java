package com.example.genius.shoppingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.genius.shoppingapp.models.Items;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MonthlyReport extends AppCompatActivity {
    private Realm realm;
    LineGraphSeries<DataPoint> series;


    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    Date dateobj = new Date();



//    Date date; // your date
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    // etc.

    String lastMonthDate=month+"/"+day+"/"+year;

    int tMonth= cal.get(Calendar.MONTH)+1;
    String thisMonth=tMonth+"/"+day+"/"+year;
    private Date endDate,startDate;

    public MonthlyReport() throws ParseException {
        this.endDate = df.parse(lastMonthDate);
        this.startDate = df.parse(thisMonth);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        double y;
        int x=0;

        realm = Realm.getDefaultInstance();

        RealmResults<Items> results1 = realm.where(Items.class).between("date",endDate,startDate).findAll();
        Log.v("Activity","Data  "+results1);
        Log.v("Activity","startDate  "+startDate);
        Log.v("Activity","endDate  "+endDate);


        GraphView g=(GraphView)findViewById(R.id.monthlyGraph);
        series=new LineGraphSeries<DataPoint>();

        for(int i=0; i<results1.size(); i++)
        {
            x+=i;
            y=results1.get(i).getAmount();
            series.appendData(new DataPoint(Double.parseDouble(String.valueOf(x)),y),true,results1.size());

        }
        g.addSeries(series);
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

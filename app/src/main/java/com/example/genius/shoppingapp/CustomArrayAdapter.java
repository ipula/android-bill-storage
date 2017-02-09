package com.example.genius.shoppingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ContextMenu;


import com.example.genius.shoppingapp.models.Items;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by genius on 12/5/2016.
 */

public class CustomArrayAdapter extends ArrayAdapter<Items>{
    private Realm realm;
    private ImageView img;
    public ArrayList<Items> items;
    private Context context;

    public CustomArrayAdapter(Activity context, ArrayList<Items> androidFlavors) {
        super(context, 0, androidFlavors);
        this.items=androidFlavors;
        this.context=context;
    }

//    public void updateItem(ArrayList<Items> receiptlist) {
//        this.receiptlist= receiptlist;
//    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        realm = Realm.getDefaultInstance();

        Items album = getItem(position);

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Items currentAndroidFlavor = getItem(position);

            TextView date = (TextView) listItemView.findViewById(R.id.list_date);

            String str=currentAndroidFlavor.getDate().toString();
            if (str.length() > 16) {
                str = str.substring(0, 10);
            }

            date.setText(str);
             Log.v("date",currentAndroidFlavor.getDate().toString());

            TextView shope = (TextView) listItemView.findViewById(R.id.list_shop);
            shope.setText(currentAndroidFlavor.getShope_name());


            TextView list_total = (TextView) listItemView.findViewById(R.id.list_total);
            list_total.setText((String.valueOf(currentAndroidFlavor.getAmount())), TextView.BufferType.EDITABLE);


        img=(ImageView) listItemView.findViewById(R.id.imageview);
        img.setTag(getItem(position));



        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v, position);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.show();

                Object o=getItem(position);
                final Items it=(Items)o;

                final int itemid=it.getId();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();

                        if(id==R.id.edit)
                        {
                            Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                            RealmResults<Items> editItem=realm.where(Items.class).equalTo("id",itemid).findAll();
                            String[] edit=new String[5];

                            edit[0]= editItem.get(0).getShope_name();
                            edit[1]= String.valueOf(editItem.get(0).getCount());
                            edit[2]= String.valueOf(editItem.get(0).getAmount());
                            edit[3]= String.valueOf(editItem.get(0).getDate());
                            edit[4]= String.valueOf(editItem.get(0).getId());


                            Intent intent = new Intent(getContext(), AddActivity.class);
                            intent.putExtra("string-array", edit);
                            context.startActivity(intent);
                            Log.v("Activity","Data  "+intent);


                            return true;
                        }
                        else if(id==R.id.delete)
                        {
                            Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    RealmResults<Items> delete=realm.where(Items.class).equalTo("id",itemid).findAll();
                                    delete.deleteAllFromRealm();
                                }
                            });
                            items.remove(position);
                            notifyDataSetChanged();
                            return true;
                        }
                        return onMenuItemClick(item);

                    }
                });
            }
        });


        return listItemView;

    }




}

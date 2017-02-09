package com.example.genius.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.genius.shoppingapp.models.Items;
import com.example.genius.shoppingapp.models.Notes;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by genius on 12/30/2016.
 */

public class GridViewAdapter extends ArrayAdapter<Notes> {
    private Realm realm;
    public ArrayList<Notes> notes;
    private Context context;
    private ImageView noteImage;

    public GridViewAdapter(Context context,ArrayList<Notes> notes) {
        super(context, 0, notes);
        this.notes=notes;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        realm = Realm.getDefaultInstance();
        View gridNoteView = convertView;

        if(gridNoteView == null) {
            gridNoteView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_notes, parent, false);
        }

        Notes getNotes = getItem(position);

        TextView date=(TextView) gridNoteView.findViewById(R.id.list_note_date);

        String str=getNotes.getDate().toString();
        if (str.length() > 16) {
            str = str.substring(0, 10);
        }
        date.setText(str);
        Log.v("date",getNotes.getDate().toString());

        noteImage=(ImageView) gridNoteView.findViewById(R.id.noteImageView);


        noteImage.setTag(getItem(position));



        noteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v, position);
                popup.getMenuInflater().inflate(R.menu.popup_note_menu, popup.getMenu());
                popup.show();

                Object o=getItem(position);
                final Notes it=(Notes) o;

                final int itemid=it.getId();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();

                        if(id==R.id.edit_note)
                        {
                            Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                            RealmResults<Notes> editItem=realm.where(Notes.class).equalTo("id",itemid).findAll();
                            String[] edit=new String[5];

                            edit[0]= editItem.get(0).getNotes();
                            edit[1]= String.valueOf(editItem.get(0).getId());


                            Intent intent = new Intent(getContext(), AddNotesActivity.class);
                            intent.putExtra("string-array", edit);
                            context.startActivity(intent);
                            Log.v("Activity","Data  "+intent);


                            return true;
                        }
                        else if(id==R.id.delete_note)
                        {
                            Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    RealmResults<Notes> delete=realm.where(Notes.class).equalTo("id",itemid).findAll();
                                    delete.deleteAllFromRealm();
                                }
                            });
                            notes.remove(position);
                            notifyDataSetChanged();
                            return true;
                        }
                        return onMenuItemClick(item);

                    }
                });
            }
        });


        return gridNoteView;

    }
}

package com.example.kirill.p8;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements TypesFragment.onItemClickListener,NotesFragment.onItemClickListener,
TypesInfoFragment.onTypesInfoItemClickListener {
    private int position;
    boolean withDetails = true,itSelected;
    FragmentTransaction fMan;
    Fragment fragNotes, fragTypes,fragTypesInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itSelected=false;
        setContentView(R.layout.activity_main);
        withDetails = (findViewById(R.id.typeinfo) != null);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
            itSelected=savedInstanceState.getBoolean("itSelected");
        }
        else position=1;
        if (withDetails) {
            ShowTypeInfo(position);
        }
        else {
            fragNotes=NotesFragment.newInstance();
            fragTypes=TypesFragment.newInstance();
            fMan=getSupportFragmentManager().beginTransaction();
            fMan.add(R.id.fragment_notes, fragNotes);
            fMan.add(R.id.fragment_types, fragTypes);
            fMan.commit();

            if (savedInstanceState!=null & itSelected==true) {
                ShowTypeInfo(position);
            }
        }

    }

    void ShowTypeInfo(int typeID) {
        String[] typeinfo=getTypeInfo(typeID);
        fragTypesInfo=getSupportFragmentManager().findFragmentById(R.id.typeinfo);
        fragTypesInfo=TypesInfoFragment.newInstance(typeinfo);
        if (!withDetails) {
            fMan=getSupportFragmentManager().beginTransaction();
            fMan.remove(fragNotes);
            fMan.remove(fragTypes);
            fMan.add(android.R.id.content, fragTypesInfo);
            fMan.addToBackStack(null);
            fMan.commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.typeinfo, fragTypesInfo).commit();
        }
    }

    public String[] getTypeInfo(int typeID) {
        String[] typeinfo = null;
        Cursor c = getContentResolver().query(
                ContractClass.TypeInfo.CONTENT_URI,
                ContractClass.TypeInfo.DEFAULT_PROJECTION,
                ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID + "=?",
                new String[]{"" + typeID},
                null);
        if (c != null) {
            if (c.moveToFirst()) {
                typeinfo = new String[c.getCount()];
                int i = 0;
                do {
                    String numsort = c.getString(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_NUMSORT));
                    String item_name = c.getString(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_ITEM_NAME));
                    Integer type_id = c.getInt(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID));
                    double mass_per_item = c.getDouble(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_MASS_PER_ITEM));
                    typeinfo[i] = numsort + " " + item_name + " | " + mass_per_item + "|" + type_id;
                    i++;
                } while (c.moveToNext());
            }
            c.close();
        }
        return typeinfo;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!withDetails) {
            getSupportFragmentManager().popBackStack();
        }
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
        outState.putBoolean("itSelected",itSelected);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!withDetails) {
            itSelected=false;
        }

    }

    @Override
    public void itemClick(int typeID, long id)
    {
        position=typeID;
        itSelected=true;
        ShowTypeInfo(typeID);
    }

    @Override
    public void NoteItemClick(int typeID, long id){
        Toast.makeText(this,"Note activated "+String.valueOf(typeID)+" "+String.valueOf(id),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTypesInfoItemClick(int position, long id) {
        Toast.makeText(this,"TypeInfo activated "+String.valueOf(position)+" "+String.valueOf(id),Toast.LENGTH_SHORT).show();
    }
}

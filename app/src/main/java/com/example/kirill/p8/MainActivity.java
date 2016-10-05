package com.example.kirill.p8;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
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
TypesInfoFragment.onTypesInfoItemClickListener, EnterInfoFragment.onEnterInfoItemClickListener {
    private int position;
    boolean withDetails = true,itSelected;
    FragmentTransaction fMan;
    Fragment fragNotes, fragTypes,fragTypesInfo,fragTypeInfoEnter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itSelected=false;
        setContentView(R.layout.activity_main);
        withDetails = (findViewById(R.id.typeinfo) != null);
//        UNKNOWN ERROR!!!
//        withDetails = getResources().getBoolean(R.bool.portrait_layout);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
            itSelected=savedInstanceState.getBoolean("itSelected");
        }
        else position=2;
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
        fragTypesInfo=TypesInfoFragment.newInstance(typeID);
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
                    Integer typeinfo_id = c.getInt(c.getColumnIndex(ContractClass.TypeInfo._ID));
                    double mass_per_item = c.getDouble(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_MASS_PER_ITEM));
                    typeinfo[i] = numsort + " " + item_name + " | " + mass_per_item + "|" + type_id+"|"+typeinfo_id;
                    i++;
                } while (c.moveToNext());
            }
            c.close();
        }
        return typeinfo;
    }

    public String[] getEnterInfo(int typeinfoID) {
        Integer type_id=-1,typeinfo_id; Double mass_per_item;
        String numsort, item_name, name, gost;
        String[] typeinfoEnter = new String[7];
        //TypeInfo table query
            Cursor c = getContentResolver().query(
                    ContractClass.TypeInfo.CONTENT_URI,
                    ContractClass.TypeInfo.DEFAULT_PROJECTION,
                    ContractClass.TypeInfo._ID + "=?",
                    new String[]{String.valueOf(typeinfoID)}, //Ha-ha-ha
                    null);
            if (c != null) {
                if (c.moveToFirst()) {
                    numsort = c.getString(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_NUMSORT));
                    item_name = c.getString(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_ITEM_NAME));
                    type_id = c.getInt(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID));
                    typeinfo_id = c.getInt(c.getColumnIndex(ContractClass.TypeInfo._ID));
                    mass_per_item = c.getDouble(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_MASS_PER_ITEM));
                    typeinfoEnter[0] = numsort;typeinfoEnter[1] =item_name;
                    typeinfoEnter[2] = String.valueOf(type_id);typeinfoEnter[3] = String.valueOf(typeinfo_id);
                    typeinfoEnter[4] = String.valueOf(mass_per_item);
                }
                c.close();
            } else {
                typeinfoEnter[0] = "nothing";typeinfoEnter[1] ="nothing";
                typeinfoEnter[2] = "nothing";typeinfoEnter[3] = "nothing";
                typeinfoEnter[4] = "nothing";
            }
        //Type table query
        c = getContentResolver().query(
        ContractClass.Types.CONTENT_URI,
        ContractClass.Types.DEFAULT_PROJECTION,
        ContractClass.Types._ID + "=?",
        new String[]{String.valueOf(type_id)}, //Ho-ho-ho
        null);
        if (c != null) {
            if (c.moveToFirst()) {
                name = c.getString(c.getColumnIndex(ContractClass.Types.COLUMN_NAME_NAME));
                gost = c.getString(c.getColumnIndex(ContractClass.Types.COLUMN_NAME_GOST));
                typeinfoEnter[5] = name;typeinfoEnter[6] =gost;
            }
            c.close();
        } else {
            typeinfoEnter[5] = "nothing";typeinfoEnter[6] = "nothing";
        }

        return typeinfoEnter;
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
        Toast.makeText(this,"Type activated "+String.valueOf(typeID)+" "+String.valueOf(id),Toast.LENGTH_SHORT).show();
        position=(int)(long) id;//(int)(long) id;
        itSelected=true;
        ShowTypeInfo((int)(long) id);//((int)(long) id);
    }

    @Override
    public void NoteItemClick(int typeID, long id){
        Toast.makeText(this,"Note activated "+String.valueOf(typeID)+" "+String.valueOf(id),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTypesInfoItemClick(int position, long id) {
        Toast.makeText(this,"TypeInfo activated "+String.valueOf(position)+" "+String.valueOf(id),Toast.LENGTH_SHORT).show();
        DialogFragment fragTypeInfoEnter=EnterInfoFragment.newInstance(getEnterInfo((int)(long) id));
        fragTypeInfoEnter.show(getSupportFragmentManager(), "fragTypeInfoEnter");
    }

    @Override
    public void onEnterInfoItemClick(int position, long id) {
        Toast.makeText(this,"EnterInfo activated "+String.valueOf(position)+" "+String.valueOf(id),Toast.LENGTH_SHORT).show();
    }
}

package com.example.kirill.p8;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Main2Activity extends FragmentActivity implements TypesFragment.onItemClickListener {
    private int position;
    boolean withDetails = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        withDetails = (findViewById(R.id.typeinfo) != null);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
        }
        else position=1;

        if (withDetails) {
            ShowTypeInfo(position);
        }
        else {
            if (savedInstanceState!=null) {
                ShowTypeInfo(position);
            }
        }
    }

    @Override
    public void itemClick(int typeID, long id)
    {
        position=typeID;
        ShowTypeInfo(typeID);
    }

    void ShowTypeInfo(int typeID) {
        String[] typeinfo=getTypeInfo(typeID);
        if (!withDetails) {
            startActivity(new Intent(this, TypesInfoActivity.class).putExtra("typeinfo", typeinfo));
        }
        else {
            TypesInfoFragment ti=(TypesInfoFragment) getSupportFragmentManager().findFragmentById(R.id.typeinfo);
            ti=TypesInfoFragment.newInstance(typeinfo);
            getSupportFragmentManager().beginTransaction().replace(R.id.typeinfo, ti).commit();
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }
}

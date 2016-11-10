package com.example.kirill.p8;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements TypesFragment.onItemClickListener, NotesFragment.onItemClickListener,
        TypesInfoFragment.onTypesInfoItemClickListener, EnterInfoFragment.onEnterInfoItemClickListener {
    private int position = 1;
    boolean withDetails = true, itSelected, withNotes = false;
    FragmentTransaction fMan;
    Fragment fragNotes, fragTypes, fragTypesInfo;
    MenuItem menuDelete;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itSelected = false;
        setContentView(R.layout.activity_main);
        withDetails = (findViewById(R.id.fragment_typeinfo) != null);
//        UNKNOWN ERROR!!!
//        withDetails = getResources().getBoolean(R.bool.portrait_layout);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
            itSelected = savedInstanceState.getBoolean("itSelected");
        }

        if (withDetails) {
            fragTypes = TypesFragment.newInstance();
            fMan = getSupportFragmentManager().beginTransaction();
            fMan.add(R.id.fragment_types, fragTypes);
            fMan.commit();
            ShowTypeInfo(position);
        } else {
            fragTypes = TypesFragment.newInstance();
            fMan = getSupportFragmentManager().beginTransaction();
            fMan.add(R.id.fragment_types, fragTypes);
            fMan.commit();
            if (savedInstanceState != null & itSelected == true) {
                ShowTypeInfo(position);
            }
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        menuDelete = (MenuItem) menu.findItem(R.id.miDelete);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose:
                fragNotes = NotesFragment.newInstance();
                if (!withDetails) {
                    fMan = getSupportFragmentManager().beginTransaction();
                    if (fragTypes.isVisible()) {
                        fMan.remove(fragTypes);
                    } else {
                        fMan.remove(fragTypesInfo);
                    }
                    fMan.add(R.id.fragment_canvas, fragNotes, "notes");
                    fMan.addToBackStack(null);
                    fMan.commit();
                } else {
                    fragTypes = (TypesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_types);
                    fragTypesInfo = (TypesInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_typeinfo);
                    fMan = getSupportFragmentManager().beginTransaction();
                    fMan.remove(fragTypes);
                    fMan.remove(fragTypesInfo);
                    fMan.add(R.id.fragment_canvas_l, fragNotes, "notes");
                    fMan.addToBackStack(null);
                    fMan.commit();
                }
                // User chose the "Settings" item, show the app settings UI...
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    void ShowTypeInfo(int typeID) {
//        Toast.makeText(this, "ShowTypeInfo. typeID="+String.valueOf(typeID), Toast.LENGTH_SHORT).show();
        fragTypesInfo = TypesInfoFragment.newInstance(typeID);
        if (!withDetails) {
            fMan = getSupportFragmentManager().beginTransaction();
            fMan.replace(R.id.fragment_types, fragTypesInfo);
            fMan.addToBackStack(null);
            fMan.commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_typeinfo, fragTypesInfo).commit();
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
                    typeinfo[i] = numsort + " " + item_name + " | " + mass_per_item + "|" + type_id + "|" + typeinfo_id;
                    i++;
                } while (c.moveToNext());
            }
            c.close();
        }
        return typeinfo;
    }

    public String[] getEnterInfo(int typeinfoID) {
        Integer type_id = -1, typeinfo_id;
        Double mass_per_item;
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
                typeinfoEnter[0] = numsort;
                typeinfoEnter[1] = item_name;
                typeinfoEnter[2] = String.valueOf(type_id);
                typeinfoEnter[3] = String.valueOf(typeinfo_id);
                typeinfoEnter[4] = String.valueOf(mass_per_item);
            }
            c.close();
        } else {
            typeinfoEnter[0] = "nothing";
            typeinfoEnter[1] = "nothing";
            typeinfoEnter[2] = "nothing";
            typeinfoEnter[3] = "nothing";
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
                typeinfoEnter[5] = name;
                typeinfoEnter[6] = gost;
            }
            c.close();
        } else {
            typeinfoEnter[5] = "nothing";
            typeinfoEnter[6] = "nothing";
        }

        return typeinfoEnter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", position);
        outState.putBoolean("itSelected", itSelected);
        if (!withDetails) {
            getSupportFragmentManager().popBackStack();
        }
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!withDetails) {
            itSelected = false;
        }

    }

    @Override
    public void itemClick(int pos, long id, long classID) {
        Toast.makeText(this, "Type activated. Position=" + String.valueOf(pos) + ". ClassID=" + String.valueOf(classID), Toast.LENGTH_SHORT).show();
        position = (int) (long) classID;//(int)(long) id;
        itSelected = true;
        ShowTypeInfo((int) (long) classID);//((int)(long) id);
    }

    @Override
    public void NoteItemClick(int position, long id, long noteID) {
        Toast.makeText(this, "Note activated. Position=" + String.valueOf(position) + ". NoteID=" + String.valueOf(noteID), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTypesInfoItemClick(int position, long id, long classID) {
//        Toast.makeText(this,"TypeInfo activated. Position="+String.valueOf(position)+". ClassID="+String.valueOf(classID),Toast.LENGTH_SHORT).show();
        DialogFragment fragTypeInfoEnter = EnterInfoFragment.newInstance(getEnterInfo((int) (long) classID));
        fragTypeInfoEnter.show(getSupportFragmentManager(), "fragTypeInfoEnter");
    }


    @Override
    public void onDialogPositiveClick(EnterInfoFragment dialog, Bundle args) {
        String strTemp=args.getString("numsort");
        strTemp=args.getString("item_name");
        final String strItemName=strTemp;
        String type_id=String.valueOf(args.getInt("type_id"));
        String typeinfo_id=String.valueOf(args.getInt("typeinfo_id"));
        strTemp=String.valueOf(args.getDouble("mass_per_item"));
        strTemp=String.valueOf(args.getString("name"));
        strTemp=String.valueOf(args.getString("gost"));
        String quantity1=String.valueOf(args.getDouble("ItemQuantity"));
        String quantity2=String.valueOf(args.getDouble("MassQuantity"));


        ContentValues values = new ContentValues();
        values.put("type_id",type_id);
        values.put("typeinfo_id", typeinfo_id);
        values.put("quantity1", quantity1);
        values.put("quantity2",quantity2);
        Uri notesURI = Uri.parse("content://"
                + ContractClass.AUTHORITY + "/" + "notes");
        getContentResolver().insert(notesURI,values);
//        getContentResolver().insert(ContractClass.Notes.CONTENT_URI,values);

        Toast.makeText(this, "Positive activated. dblTotal=" + String.valueOf(dialog.dblTotalMassPerItem) + ". From Bundle strTemp=" + strTemp, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDialogNegativeClick(EnterInfoFragment dialog) {
        Toast.makeText(this, "Negative activated. dblTotal=" + String.valueOf(dialog.dblTotalMassPerItem), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNeutralClick(EnterInfoFragment dialog) {
        Toast.makeText(this, "Neutral activated. dblTotal=" + String.valueOf(dialog.dblTotalMassPerItem), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.kirill.p8/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.kirill.p8/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void setMenuDeleteVisible(Boolean flagVisible){
        menuDelete.setVisible(flagVisible);
    }
}

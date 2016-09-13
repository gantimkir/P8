package com.example.kirill.p8;

import java.util.Arrays;

import com.example.kirill.p8.DataAdapter.ViewHolder;
import com.example.kirill.p8.ContractClass;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, OnItemClickListener{
	
	private ListView lvItems;
	private DataAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		if(savedInstanceState != null) {
//			return;
//		}
		lvItems = (ListView)findViewById(R.id.lvItems);
		mAdapter = new DataAdapter(this, null, 0);
		lvItems.setAdapter(mAdapter);
		lvItems.setOnItemClickListener(this);
		getSupportLoaderManager().initLoader(0, null, this);
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
        		MainActivity.this,
                android.R.layout.simple_list_item_1);
		ViewHolder holder = (ViewHolder) v.getTag();
		if(holder != null) {
			String[] students = getStudents(holder.classID);
			if(students != null && students.length > 0) {
				arrayAdapter.addAll(students);
			}
			else {
				arrayAdapter.add("No students");
			}
		} else {
			return;
		}
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
				MainActivity.this);
        builderSingle.setTitle("TypeInfo");
        builderSingle.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        builderSingle.show();
	}

	private String[] getStudents(long typeID) {
		String[] typeinfo = null;
		Cursor c = getContentResolver().query(
				ContractClass.TypeInfo.CONTENT_URI,
				ContractClass.TypeInfo.DEFAULT_PROJECTION,
				ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID+"=?",
				new String[] {""+typeID},
				null);
		if(c != null) {
			if(c.moveToFirst()) {
				typeinfo = new String[c.getCount()];
				int i=0;
				do {
					String numsort = c.getString(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_NUMSORT));
					String item_name = c.getString(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_ITEM_NAME));
					Integer type_id=c.getInt(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID));
					double mass_per_item = c.getDouble(c.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_MASS_PER_ITEM));
					typeinfo[i] = numsort+" "+item_name+" | "+mass_per_item+"|"+type_id;
					i++;
				} while (c.moveToNext());
			}
			c.close();
		}
		return typeinfo;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(
				this, 
				ContractClass.Types.CONTENT_URI,
				ContractClass.Types.DEFAULT_PROJECTION,
				null, 
				null, 
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor newData) {
		mAdapter.swapCursor(newData);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	

}

package com.example.kirill.p8;

import com.example.kirill.p8.ContractClass;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DataAdapter extends CursorAdapter {
	
	private LayoutInflater mInflater;

	public DataAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context ctx, Cursor cur) {
		long id = cur.getLong(cur.getColumnIndex(ContractClass.Types._ID));
		String typeNumber = cur.getString(cur.getColumnIndex(ContractClass.Types.COLUMN_NAME_NAME));
		String typeGOST = cur.getString(cur.getColumnIndex(ContractClass.Types.COLUMN_NAME_GOST));
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder != null) {
			holder.tvTypeName.setText(String.valueOf(id)+"|||"+typeNumber+"\""+typeGOST+"\"");
			holder.classID = id-1;
		}
	}

	@Override
	public View newView(Context ctx, Cursor cur, ViewGroup parent) {
		View root = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		ViewHolder holder = new ViewHolder();
		TextView tvTypeName = (TextView)root.findViewById(android.R.id.text1);
		holder.tvTypeName = tvTypeName;
		root.setTag(holder);
		return root;
	}
	
	public static class ViewHolder {
		public TextView tvTypeName;
		public long classID;
	}

}

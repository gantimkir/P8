package com.example.kirill.p8;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TypesInfoAdapter extends CursorAdapter {

	private LayoutInflater mInflater;

	public TypesInfoAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context ctx, Cursor cur) {
		long id = cur.getLong(cur.getColumnIndex(ContractClass.TypeInfo._ID));
		String typeinfoNumsort = cur.getString(cur.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_NUMSORT));
		String typeinfoItemName = cur.getString(cur.getColumnIndex(ContractClass.TypeInfo.COLUMN_ITEM_NAME));
		String typeinfoMassPerItem = cur.getString(cur.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_MASS_PER_ITEM));
		String typeinfoTypeID = cur.getString(cur.getColumnIndex(ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID));
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder != null) {
			holder.tvNumsort.setText(typeinfoNumsort);
			holder.tvItemName.setText(typeinfoItemName);
			holder.tvMassPerItem.setText(String.valueOf(typeinfoMassPerItem));
			holder.tvTypeID.setText(String.valueOf(typeinfoTypeID));
			holder.classID = id-1;
		}
	}

	@Override
	public View newView(Context ctx, Cursor cur, ViewGroup parent) {
		View root = mInflater.inflate(R.layout.type_text, parent, false);
		ViewHolder holder = new ViewHolder();
		TextView tvNumsort = (TextView)root.findViewById(R.id.tvNumsort);
		TextView tvItemName = (TextView)root.findViewById(R.id.tvItemName);
		TextView tvMassPerItem = (TextView)root.findViewById(R.id.tvMassPerItem);
		TextView tvTypeID = (TextView)root.findViewById(R.id.tvTypeID);
		holder.tvNumsort = tvNumsort;
		holder.tvItemName = tvItemName;
		holder.tvMassPerItem = tvMassPerItem;
		holder.tvTypeID = tvTypeID;
		root.setTag(holder);
		return root;
	}
	
	public static class ViewHolder {
		public TextView tvNumsort;
		public TextView tvItemName;
		public TextView tvMassPerItem;
		public TextView tvTypeID;
		public long classID;
	}

}

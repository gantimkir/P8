package com.example.kirill.p8;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteAdapter extends CursorAdapter {

	private LayoutInflater mInflater;


	public NoteAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context ctx, Cursor cur) {
		long id = cur.getLong(cur.getColumnIndex(ContractClass.Notes._ID));
		String noteTitle = cur.getString(cur.getColumnIndex(ContractClass.Notes.COLUMN_TITLE));
		String noteCreated = cur.getString(cur.getColumnIndex(ContractClass.Notes.COLUMN_CREATED_DATE));
		ViewNote vNote = (ViewNote) view.getTag();
		if(vNote != null) {
			vNote.tvTitle.setText(noteTitle);
			vNote.tvCreated.setText(noteCreated);
			vNote.NoteID = id-1;
		}
	}

	@Override
	public View newView(Context ctx, Cursor cur, ViewGroup parent) {
		View root = mInflater.inflate(R.layout.notes, parent, false);
		ViewNote vNote = new ViewNote();
		TextView tvTitle = (TextView) root.findViewById(R.id.tvTitle);
		TextView tvCreated = (TextView) root.findViewById(R.id.tvCreated);
		vNote.tvTitle = tvTitle;
		vNote.tvCreated = tvCreated;
		root.setTag(vNote);
		return root;
	}



	public static class ViewNote {
		public TextView tvTitle;
		public TextView tvCreated;
		public long NoteID;
	}

}

package com.example.kirill.p8;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;


public class NotesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lvNotes;
    private NoteAdapter mAdapter;
//    private View previous;

    public interface onItemClickListener {
        public void NoteItemClick(int position, long id);
    }
    onItemClickListener listener;

    public static NotesFragment newInstance() {
        NotesFragment frmtNotes = new NotesFragment();
//        Bundle args = new Bundle();
//        args.putStringArray("typeinfo", typeInfo);
//        ti.setArguments(args);
        return frmtNotes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new NoteAdapter(getContext(), null, 0);
        setListAdapter(mAdapter);
        getActivity().getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        listener.NoteItemClick(position, id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (onItemClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(
                getContext(),
                ContractClass.Notes.CONTENT_URI,
                ContractClass.Notes.DEFAULT_PROJECTION,
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


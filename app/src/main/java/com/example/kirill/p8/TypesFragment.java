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


public class TypesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

//    private ListView lvItems;
    private TypesAdapter mAdapter;

    public interface onItemClickListener {
        public void itemClick(int position,long id);
    }
    onItemClickListener listener;

    public static TypesFragment newInstance() {
        TypesFragment frmtTypes = new TypesFragment();
//        Bundle args = new Bundle();
//        args.putStringArray("typeinfo", typeInfo);
//        ti.setArguments(args);
        return frmtTypes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TypesAdapter(getContext(), null, 0);
        setListAdapter(mAdapter);
        getActivity().getSupportLoaderManager().initLoader(1, null, this);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_types1, container, false);
//    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        listener.itemClick(position, id);


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


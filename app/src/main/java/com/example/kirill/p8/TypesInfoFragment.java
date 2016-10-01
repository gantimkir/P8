package com.example.kirill.p8;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class TypesInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private TypesInfoAdapter mAdapter;

    public interface onTypesInfoItemClickListener {
        public void onTypesInfoItemClick(int position, long id);
    }
    onTypesInfoItemClickListener listener;

//    public static TypesInfoFragment newInstance(String[] typeInfo) {
//        TypesInfoFragment ti = new TypesInfoFragment();
//        Bundle args = new Bundle();
//        args.putStringArray("typeinfo", typeInfo);
//        ti.setArguments(args);
//        return ti;
//    }

    public static TypesInfoFragment newInstance(int typeID) {
        TypesInfoFragment frmtTypesInfo = new TypesInfoFragment();
        Bundle args = new Bundle();
        args.putInt("typeID", typeID);
        frmtTypesInfo.setArguments(args);
        return frmtTypesInfo;
    }

    Integer getTypeID() {
        return getArguments().getInt("typeID");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mAdapter = new TypesInfoAdapter(getContext(), null, 0);
//        setListAdapter(mAdapter);
//        getActivity().getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAdapter = new TypesInfoAdapter(getContext(), null, 0);
        View v = inflater.inflate(R.layout.typeinfo_listview, container, false);
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        ListView lvTypeInfo=(ListView) v.findViewById(R.id.lvName1);
        lvTypeInfo.setAdapter(mAdapter);
        lvTypeInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onTypesInfoItemClick(position,id);
            }
        });
//        lvTypeInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                listener.onTypesInfoItemClick(position,id);
//            }
//        setListAdapter(mAdapter);
//        getActivity().getSupportLoaderManager().initLoader(0, null, this);


//        View v = inflater.inflate(R.layout.type_text_frag, container, false);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
//                android.R.layout.simple_list_item_1);
//        if(getTypeInfo() != null && getTypeInfo().length > 0) {
//            arrayAdapter.addAll(getTypeInfo());
//        }
//        else {
//            arrayAdapter.add("No typeinfo");
//        }
//        ListView lvTypeInfo=(ListView) v.findViewById(R.id.lvName1);
//        lvTypeInfo.setAdapter(arrayAdapter);
//        lvTypeInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                listener.onTypesInfoItemClick(position,id);
//            }
//        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (onTypesInfoItemClickListener) context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(
                getContext(),
                ContractClass.TypeInfo.CONTENT_URI,
                ContractClass.TypeInfo.DEFAULT_PROJECTION,
                "type_id=?",
                new String[] {getTypeID().toString()},
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



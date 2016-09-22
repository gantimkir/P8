package com.example.kirill.p8;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class TypesInfoActivity extends FragmentActivity implements TypesInfoFragment.onTypesInfoItemClickListener {
    TypesInfoActivityListener listener;
    public interface TypesInfoActivityListener {
        public void onTypesInfoActivityItemClick(int position, long id);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        setContentView(R.layout.typeinfo_activity);
        if (savedInstanceState == null) {
            TypesInfoFragment details = TypesInfoFragment.newInstance(getIntent().getStringArrayExtra("typeinfo"));
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
      this.listener=null;
    }

    @Override
    public void onTypesInfoItemClick(int position, long id) {
//        listener.onTypesInfoActivityItemClick(position,id);
        Toast.makeText(this,"TypeInfo activated from TypesInfoActivity "+String.valueOf(position)+" "+String.valueOf(id),Toast.LENGTH_SHORT).show();

    }
}

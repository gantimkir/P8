package com.example.kirill.p8;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class TypesInfoActivity extends FragmentActivity {
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
    }
}

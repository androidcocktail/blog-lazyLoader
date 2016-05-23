package com.androidcocktail.lazyloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private static final int BATCH_SIZE = 50;

    private ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create the ListView
        ListView listView = new ListView(this);
        listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // add ProgressBar at ListView's end to indicate data load
        listView.addFooterView(new ProgressBar(this));

        // set ListView as the activity's only content
        setContentView(listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);

        // Load the initial set of items into the adapter.
        loadItems();


    }

    private void loadItems() {

    }
}

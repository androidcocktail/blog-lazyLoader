package com.androidcocktail.lazyloader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create the ListView
        ListView listView = new ListView(this);
        listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // add ProgressBar as ListView's footer to indicate data load
        listView.addFooterView(new ProgressBar(this));

        // set ListView as the activity's only content
        setContentView(listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new LazyLoader() {
            @Override
            public void loadMore(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                loadItems();
            }
        });
        // Load the initial set of items into the adapter.
        loadItems();


    }

    private void loadItems() {
        int startIndex = adapter.getCount();
        new FetchItemsTask(startIndex, fetchItemsTaskResponseListener).execute();
    }

    private ResponseListener fetchItemsTaskResponseListener = new ResponseListener() {
        @Override
        public void onResponse(List<String> newItems) {
            adapter.addAll(newItems);
        }
    };



}

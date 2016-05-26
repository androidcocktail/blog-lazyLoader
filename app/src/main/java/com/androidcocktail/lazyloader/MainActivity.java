package com.androidcocktail.lazyloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidcocktail.lazyloader.tasks.FetchItemsTask;
import com.androidcocktail.lazyloader.utils.LazyLoader;
import com.androidcocktail.lazyloader.utils.ResponseListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter ;

    // The FetchItemsTask delivers the new data to this listener in the main thread.
    private ResponseListener<List<String>> responseListener = new ResponseListener<List<String>>() {
        @Override
        public void onResponse(List<String> newItems) {

            // append the frash data to the ListView.
            adapter.addAll(newItems);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create the ListView
        ListView listView = new ListView(this);

        // add a ProgressBar as ListView's footer to indicate data load
        listView.addFooterView(new ProgressBar(this));

        // create an adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        // plug the adapter to the ListView
        listView.setAdapter(adapter);

        // set the ListView as the activity's content
        setContentView(listView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // CRUCIAL PART !! Add the LazyLoader as the onScrollListener for the ListView.
        listView.setOnScrollListener(new LazyLoader() {

            // This method is called when the user is nearing the end of the ListView
            // and the ListView is ready to add more items.
            @Override
            public void loadMore(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                // The ListView needs more data. So Fetch !!
                loadItems();
            }
        });
    }

    // Called by the LazyLoader when the ListView is ready for fresh data.
    private void loadItems() {

        // Index is required to fetch the next set of items
        int startIndex = adapter.getCount();

        // Fetch more items Asynchronously.
        new FetchItemsTask(startIndex, responseListener).execute();
    }
}

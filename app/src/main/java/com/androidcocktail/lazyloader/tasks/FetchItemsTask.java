package com.androidcocktail.lazyloader.tasks;

import android.os.AsyncTask;

import com.androidcocktail.lazyloader.utils.ResponseListener;

import java.util.ArrayList;
import java.util.List;

public class FetchItemsTask extends AsyncTask<Void, Void, List<String>> {

    private int startIndex;
    private ResponseListener<List<String>> responseListener;

    public FetchItemsTask(int startIndex, ResponseListener<List<String>> listener) {
        this.startIndex = startIndex;
        this.responseListener = listener;
    }

    @Override
    protected List<String> doInBackground(Void... params) {

        try {
            //In this example, we are fetching dummy data locally;
            // but in a real world app its usually a network or database request which takes time.
            // So FAKE that time delay here.
            Thread.sleep(3000);

        } catch (InterruptedException e) {

        }

        // Fetch a maximum of 50 new items.
        int end = startIndex + 50 ;

        // Get the data.
        List<String> dummyItems = new ArrayList<>();
        for(int i = startIndex ; i < end ; i++) {
            String item = "Item " + i ;
            dummyItems.add(item);
        }
        return dummyItems;
    }

    @Override
    protected void onPostExecute(List<String> newItems) {
        // give the new items back to the activity
        responseListener.onResponse(newItems);
    }
}
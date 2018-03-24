package com.example.shivam_pc.googlebookssearch;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Shivam-PC on 20-01-2018.
 */

class booksLoader extends AsyncTaskLoader<List<books>> {

    /** Query URL */
    private String mUrl;

    /**

     */
    public booksLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<books> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books
        List<books> booklist = QueryUtils.fetchbooksData(mUrl);
        return booklist;
    }
}


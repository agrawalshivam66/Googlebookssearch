package com.example.shivam_pc.googlebookssearch;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shivam-PC on 20-01-2018.
 */

public class BooksActivity2 extends AppCompatActivity implements LoaderCallbacks<List<books>> {


    String tosearch = "maze runner";
    private  String google_api_url = "https://www.googleapis.com/books/v1/volumes?q={"+tosearch+"}";

    private booksAdapter mAdapter;

    private TextView empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);


        google_api_url=getIntent().getStringExtra("URL");

        ListView books_list_view = (ListView) findViewById(R.id.list);

    mAdapter = new booksAdapter(this, new ArrayList<books>());

        books_list_view.setAdapter(mAdapter);

    empty = (TextView) findViewById(R.id.empty_view);
        books_list_view.setEmptyView(empty);


        books_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            books currentbooks = mAdapter.getItem(position);
            String bookurl = currentbooks.geturl().toString();
            Intent webint = new Intent(getBaseContext(),webpage_opener.class);
            webint.putExtra("url",bookurl);
            startActivity(webint);
        }

    });



    // Get a reference to the ConnectivityManager to check state of network connectivity
    ConnectivityManager connMgr = (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);


    // Get details on the currently active default data network
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

    // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected())

    {
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(1, null, this);
    }

    else {
        // Otherwise, display error
        // First, hide loading indicator so error message will be visible
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        // Update empty state with no connection error message
        empty.setText("No Internet Connection");
    }
}




    @Override
    public Loader<List<books>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new booksLoader(this, google_api_url);
    }

    @Override
    public void onLoadFinished(Loader<List<books>> loader, List<books> booksList) {
        // Set empty state text to display "No bookss found."
        empty.setText("No Books Found");
        // Clear the adapter of previous books data
        mAdapter.clear();

        // If there is a valid list of {@link books}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (booksList != null && !booksList.isEmpty()) {
            mAdapter.addAll(booksList);

            ProgressBar p = (ProgressBar)findViewById(R.id.loading_spinner);
            assert p != null;

            p.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<books>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}

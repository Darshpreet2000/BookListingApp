package com.example.booklistingapk;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookClass>> {
    private BookAdapter adapter;
    CoordinatorLayout coordinatorLayout;
    SearchView searchView;
    private static final int BOOK_LOADER_ID = 1;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String murl="";
    ProgressBar progressBar;
    boolean isConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout=findViewById(R.id.Linear);
        Snackbar snackbar = Snackbar
                .make(linearLayout, "Welcome To Book Listing app", Snackbar.LENGTH_LONG);
        snackbar.show();
        // Declaration and initialization ConnectivityManager for checking internet connection
        final ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        checkConnection(cm);
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        //Link code with api
        final ListView BooksList=(ListView) findViewById(R.id.list);
        searchView=(SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ImageView img=(ImageView) findViewById(R.id.back);
                img.setVisibility(View.GONE);
                // Base URI for the Books API.
                // Check connection status
                checkConnection(cm);

                if (isConnected) {
                    updateQueryUrl(query);
                    restartLoader();
                    Log.i(LOG_TAG, "Search value: " + query);
                } else {
                    // Clear the adapter of previous book data
                    adapter.clear();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //Setting an Adapter on ListView
        adapter=new BookAdapter(this,new ArrayList<BookClass>());
        BooksList.setAdapter(adapter);
        BooksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookClass currentbook=adapter.getItem(position);
                Uri Booksuri=Uri.parse(currentbook.getLink());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Booksuri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    private String updateQueryUrl(String searchValue) {

        if (searchValue.contains(" ")) {
            searchValue = searchValue.replace(" ", "+");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("https://www.googleapis.com/books/v1/volumes?q=").append(searchValue).append("&filter=paid-ebooks&maxResults=10");
        murl = sb.toString();
        return murl;
    }
    @Override
    public Loader<List<BookClass>> onCreateLoader(int i, Bundle bundle) {
      // murl= updateQueryUrl(searchView.getQuery().toString());
        return new BookLoader(this,murl);
    }

    @Override
    public void onLoadFinished(Loader<List<BookClass>> loader, List<BookClass> data) {
        adapter.clear();
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
     }
    @Override
    public void onLoaderReset(Loader<List<BookClass>> loader) {
        adapter.clear();
    }
    public void checkConnection(ConnectivityManager connectivityManager) {
        // Status of internet connection
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {
            isConnected = true;

            Log.i(LOG_TAG, "INTERNET connection status: " + String.valueOf(isConnected) + ". It's time to play with LoaderManager :)");

        } else {
            isConnected = false;

        }
    }
    public void restartLoader() {
      //  mEmptyStateTextView.setVisibility(View.GONE);
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
    }
}
package com.example.booklistingapk;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    private static List<BookClass> extractFeatureFromJson(String BooksJSON) {
        List<BookClass> Books = new ArrayList<BookClass>();

        try {
            JSONObject baseJsonResponse = new JSONObject(BooksJSON);
            JSONArray ItemsArray = baseJsonResponse.getJSONArray("items");
            for (int i = 0; i < ItemsArray.length(); i++) {
                JSONObject firstFeature = ItemsArray.getJSONObject(i);
                JSONObject VolumeInfo = firstFeature.getJSONObject("volumeInfo");
                String title = null;
                title = VolumeInfo.getString("title");
                String publisher = null;
                if (VolumeInfo.has("publisher"))
                    publisher = VolumeInfo.getString("publisher");
                else
                    publisher = "**Unknown Publisher**";
                // String description = VolumeInfo.getString("description");
                String date = null;
                if (VolumeInfo.has("publishedDate"))
                    date = VolumeInfo.getString("publishedDate");
                else
                    date = "**Unknown Date**";
                String Link = VolumeInfo.getString("infoLink");
                JSONObject ImageInfo = VolumeInfo.getJSONObject("imageLinks");
                String Thumbnail = ImageInfo.getString("smallThumbnail");
                Log.v("MAIN", Thumbnail);
                Books.add(new BookClass(title, publisher, date, Link, Thumbnail));
            }
            return Books;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in extarcting json", e);
            return Books;
        }
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    //To make Http Request
    //TO read from input stream
    private  static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    static List<BookClass> fetchBookData(String requestUrl) {

        final int SLEEP_TIME_MILLIS = 2000;

        // This action with sleeping is required for displaying circle progress bar
        try {
            Thread.sleep(SLEEP_TIME_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
            Log.i(LOG_TAG, "HTTP request: OK");
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Book}s
        List<BookClass> listBooks = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Book}s
        return listBooks;
    }
}
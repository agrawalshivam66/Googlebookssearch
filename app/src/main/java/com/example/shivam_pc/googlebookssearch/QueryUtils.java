package com.example.shivam_pc.googlebookssearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
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

/**
 * Created by Shivam-PC on 19-01-2018.
 */

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){
    }

    public static List<books> fetchbooksData(String requestUrl)
    {
        URL url = createUrl(requestUrl);

        String jsonResponse=null;

        try {
            jsonResponse =makeHttpRequest(url);
        }
        catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<books> bookslist1= extractFeatureFromJson(jsonResponse);

        return bookslist1;
    }



    private static URL createUrl(String stringUrl)
    {
        URL url=null;

        try{
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
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
            Log.e(LOG_TAG, "Problem retrieving the books JSON results.", e);
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



    private static Bitmap image(String src) {
        if (src==null)
        {
            src="https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fi5.walmartimages.com%2Fasr%2Ff752abb3-1b49-4f99-b68a-7c4d77b45b40_1.39d6c524f6033c7c58bd073db1b99786.jpeg%3FodnHeight%3D450%26odnWidth%3D450%26odnBg%3DFFFFFF&imgrefurl=https%3A%2F%2Fwww.walmart.com%2Fip%2FScotch-Multipurpose-Scissors-8in-Pointed-Gray-black%2F19675467&docid=_jQrOId75pO15M&tbnid=-pCCN7e53SUTOM%3A&vet=10ahUKEwiJ95jRpO7YAhXImJQKHVQGDS4QMwgxKAAwAA..i&w=275&h=275&bih=662&biw=1366&q=no%20image%20found&ved=0ahUKEwiJ95jRpO7YAhXImJQKHVQGDS4QMwgxKAAwAA&iact=mrc&uact=8";
        }
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
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


    private static List<books> extractFeatureFromJson(String booksjson) {
        if (TextUtils.isEmpty(booksjson)) {
            return null;
        }

        List<books> books_list = new ArrayList<>();

        int i = 0;
        try {
            JSONObject basejsonResponse = new JSONObject(booksjson);

            JSONArray booksarray = basejsonResponse.getJSONArray("items");

            for (i = 0; i < booksarray.length(); i++) {

                String title = "Not Available";
                String publisher = "Not Available";
                String url = "Not Available";
                String author = "Not Available";
                String lang = "Not Available";

                JSONObject currentbook = booksarray.getJSONObject(i);

                JSONObject volumeinfo = currentbook.getJSONObject("volumeInfo");

                title = volumeinfo.optString("title");

                publisher =  volumeinfo.optString("description");

                lang = volumeinfo.optString("language");

                url = volumeinfo.optString("infoLink");

                JSONArray authors = volumeinfo.getJSONArray("authors");

                author = authors.optString(0);

                JSONObject imglink=volumeinfo.getJSONObject("imageLinks");
                String imgurl=imglink.optString("smallThumbnail");

                if(publisher==null)
                {
                    publisher="Not Available";

                }

                books book = new books(author, title, publisher, lang, url,imgurl);

                books_list.add(book);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the Books JSON results", e);
            Log.e("value", Integer.toString(i));
        }

        return books_list;
    }
}

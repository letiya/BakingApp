package com.example.android.bakingapp.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
//    private static final String RECIPE_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String RECIPE_BASE_URL = "http://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     * This method builds the URL used to talk to movie db server.
     * @return a URL to query the movie server.
     */
    public static URL buildRecipeUrl(Context context) {
        Uri uri = Uri.parse(RECIPE_BASE_URL)
                .buildUpon()
                .build();

        if (uri == null) {
            return null;
        }
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Invalid uri: " + uri);
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     */
    public static String getResponseFromHttpUrl(URL url) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection = (HttpsURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); // Regular expression - Beginning of string.

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                Log.e(TAG, "No result from HTTP!");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "No internet connection!");
            return null;
        }  finally {
            urlConnection.disconnect();
        }
    }
}

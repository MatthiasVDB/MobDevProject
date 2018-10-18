package be.pxl.kartingapp.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtilities {
    final static String BASE_URL =
            //"https://maps.googleapis.com/maps/api/place/textsearch/json?query=Indoor+Karting+near+Belgium&key=AIzaSyA1826-KmQvMtxjEdOg1ucJbciu1ZPWI5s";
            "https://maps.googleapis.com/maps/api/place/textsearch/json";

    final static String PARAM_QUERY = "query";
    final static String paramQuery = "Indoor Karting near Belgium";

    final static String PARAM_KEY = "key";
    final static String key = "AIzaSyA1826-KmQvMtxjEdOg1ucJbciu1ZPWI5s";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, paramQuery)
                .appendQueryParameter(PARAM_KEY, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl() throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) buildUrl().openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("Foutmelding " + ex);
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }
}

package be.pxl.kartingapp;


import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import be.pxl.kartingapp.utilities.NetworkUtilities;

public class GetAllKartingsTask extends AsyncTask<URL, Void, String> {

    @Override
    protected String doInBackground(URL... urls) {
        String searchResults = null;

        try{
            searchResults = NetworkUtilities.getResponseFromHttpUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if(jsonString != null && !jsonString.equals("")){

            try {
                JSONObject json = new JSONObject(jsonString);
                JSONObject results = json.getJSONObject("results");
                //mSearchResults.setText(results.getString("name"));

                System.out.println(results);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

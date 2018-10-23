package be.pxl.kartingapp;


import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
                System.out.println(json);
                JSONArray results = json.getJSONArray("results");
                List<String> names = new ArrayList<>();
                for(int i=0;i<results.length();i++){
                    JSONObject json_obj = results.getJSONObject(i);

                    //name = json_obj.getString("name");
                    names.add(json_obj.getString("name"));

                }

                //mSearchResults.setText(results.getString("name"));

                System.out.println(results);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

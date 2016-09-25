package com.example.rachitagarwal.fashionapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rachit on 7/22/2016.
 */
public class RankingFragment extends Fragment {


    String myJSON;

    JSONParser jsonParser = new JSONParser();
    private static final String URL = "http://192.168.43.47/isro/collection.php";

    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;


    String result = "null";
    private static final String TAG_RESULT="result";




    static final String TAG_ID = "id";
    static final String TAG_LIKES = "likes";
    static final String TAG_PRICE = "price";
    static final String TAG_FLAG = "flag";
    JSONArray js;


    SharedPreferences shared;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ranking_main, container, false);

        listview = (ListView) rootView.findViewById(R.id.listView);
        shared = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
//        getData();

        new DownloadJSON().execute();

        return rootView;
    }


    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("Collection Images");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arraylist = new ArrayList<HashMap<String, String>>();
            JSONObject json = jsonParser.getJSONFromUrl(URL);
            result = json.toString();

            Log.d("result",result);
            try {
                JSONObject jsonObject=new JSONObject(result);
                js=jsonObject.getJSONArray(TAG_RESULT);


                for (int i = 0; i < js.length(); i++) {

                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject e = js.getJSONObject(i);

                    map.put("id", e.getString("photo_id"));
                    map.put("likes", e.getString("likes"));
                    map.put("price", e.getString("price"));


                    // Set the JSON Objects into the array

                    arraylist.add(map);

                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(getContext(), arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}

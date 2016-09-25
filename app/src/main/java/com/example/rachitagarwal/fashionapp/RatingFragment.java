package com.example.rachitagarwal.fashionapp;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rachit on 7/22/2016.
 */
@SuppressWarnings("ALL")
public class RatingFragment extends android.support.v4.app.Fragment {

    ImageButton first, second;
    ImageLoader imageLoader;

    String r1, r2;

    private static final String TAG_SUCCESS = "success";


    SharedPreferences shared;
    //ContentResolver contentResolver;

    String url = "http://192.168.43.47/isro/like.php";
    JSONParser jParser = new JSONParser();


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rating_main, container, false);

        shared = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
//        user_id = (shared.getString("user_id", "")).toString();
        //      user_name = (shared.getString("user_name", "")).toString();


        first = (ImageButton) rootView.findViewById(R.id.first);
        second = (ImageButton) rootView.findViewById(R.id.second);

        // final ContentValues values = new ContentValues();
        //final Account account = new Account(user_name, "com.example.rachitagarwal.fashionista");


        //contentResolver = getContext().getContentResolver();


        imageLoader = new ImageLoader(getContext());


        int intes[];

        intes = randomNumber();

        r1 = String.valueOf(intes[0]);
        r2 = String.valueOf(intes[1]);

        getImageFirst(r1);
        getImageSecond(r2);

        first.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.animator.animator_main));

                /*values.put(SyncProvider.USER,
                        user_id);

                values.put(SyncProvider.MODEL,
                        r1);

                values.put(SyncProvider.CREATE_AT, getCurrentTimeStamp());



                Uri uri =  getActivity().getContentResolver().insert(SyncProvider.CONTENT_URI, values);

                Toast.makeText(getContext(), uri.toString(), Toast.LENGTH_LONG).show();

                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(
                        ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(
                        ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

                contentResolver.requestSync(account,"com.example.rachitagarwal.fashionista",settingsBundle);
                */

                new likeAsyncTask(r1).execute();


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        int intes1[];
                        intes1 = randomNumber();
                        r1 = String.valueOf(intes1[0]);
                        r2 = String.valueOf(intes1[1]);

                        getImageFirst(r1);
                        getImageSecond(r2);


                    }
                }, 1000);


            }
        });


        second.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.animator.animator_main));

                /*



                values.put(SyncProvider.USER,
                        user_id);

                values.put(SyncProvider.MODEL,
                        r2);

                values.put(SyncProvider.CREATE_AT, getCurrentTimeStamp());

                Uri uri =  getActivity().getContentResolver().insert(SyncProvider.CONTENT_URI, values);

                Toast.makeText(getContext(),uri.toString(), Toast.LENGTH_LONG).show();


                contentResolver.requestSync(account, "com.example.rachitagarwal.fashionista", Bundle.EMPTY);
                */
                new likeAsyncTask(r2).execute();



                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        int intes2[];
                        intes2 = randomNumber();
                        r1 = String.valueOf(intes2[0]);
                        r2 = String.valueOf(intes2[1]);

                        getImageFirst(r1);
                        getImageSecond(r2);

                    }
                }, 1000);


            }
        });


        return rootView;
    }

    private class likeAsyncTask extends AsyncTask<Void, Void, String> {
        // private ProgressDialog progressDialog;
        String photo;

        likeAsyncTask(String urlPass) {
            photo = urlPass;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            // progressDialog = new ProgressDialog(MapsActivity.this);
            //  progressDialog.setMessage("Fetching route, Please wait...");
            // progressDialog.setIndeterminate(true);
            // progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("id", photo));
                Log.d("request!", "starting");

                JSONObject json = jParser.makeHttpRequest(url, "POST", params1);

                Log.d("like attempt", json.toString());

                // success tag for json
                int success = json.getInt(TAG_SUCCESS);


                if (success == 1) {
                    Log.d("Successfully Liked!", json.toString());

                    return "liked";
                } else {

                    return "your net is slow";

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //progressDialog.hide();

      //      Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();


        }
    }

    public int[] randomNumber() {
        boolean b1 = true;

        int ran[];
        ran = new int[2];
        while (b1) {
            Double s = Math.random() * 30;
            Double s1 = Math.random() * 30;
            ran[0] = s.intValue() + 1;
            ran[1] = s1.intValue() + 1;

            if (ran[0] == ran[1]) {
                b1 = true;
            } else {
                b1 = false;
            }
        }
        return ran;

    }


    public void getImageFirst(String id) {
        StringBuffer str = new StringBuffer("http://192.168.43.47/isro/collection/");
        String urls = str.append(id + ".jpg").toString();

        System.out.println("String is " + urls + "  " + id);

        int loader = R.mipmap.ic_launcher;


        imageLoader.DisplayImage(urls, loader, first);
    }

    public void getImageSecond(String id) {
        StringBuffer str = new StringBuffer("http://192.168.43.47/isro/collection/");
        String urls = str.append(id + ".jpg").toString();

        System.out.println("String is " + urls + "  " + id);

        int loader = R.mipmap.ic_launcher;


        imageLoader.DisplayImage(urls, loader, second);
    }


}
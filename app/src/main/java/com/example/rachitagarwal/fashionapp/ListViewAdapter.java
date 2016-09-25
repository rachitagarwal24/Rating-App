package com.example.rachitagarwal.fashionapp;

/**
 * Created by Rachit agarwal on 6/27/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();




    public ListViewAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        //TextView rank;
        TextView name;
        TextView desc;
        ImageView flag;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.custom_main, parent, false);

        // Get the position

        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        //rank = (TextView) itemView.findViewById(R.id.rank);

        name = (TextView) itemView.findViewById(R.id.textView1);
        desc = (TextView) itemView.findViewById(R.id.textView2);

        // Locate the ImageView in listview_item.xml
        flag = (ImageView) itemView.findViewById(R.id.imageview);

        // Capture position and set results to the TextViews
       // rank.setText(resultp.get(MainActivity.RANK));

        String id = resultp.get(RankingFragment.TAG_ID);

        name.setText(resultp.get(RankingFragment.TAG_LIKES));
        desc.setText(resultp.get(RankingFragment.TAG_PRICE));

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class

        if(id!=null) {

            StringBuffer str = new StringBuffer("http://192.168.43.47/isro/collection/");
            String urls = str.append(id + ".jpg").toString();

            System.out.println("String is " + urls + "  " + id);

            int loader = R.mipmap.ic_launcher;


            imageLoader.DisplayImage(urls,loader, flag);
        }


        /*
        // Capture ListView item click
       itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, GraphActivity.class);
                // Pass all data rank
                intent.putExtra("pk", resultp.get(RankingFragment.TAG_ID));
                // Pass all data country
                intent.putExtra("name", resultp.get(RankingFragment.TAG_NAME));
                // Pass all data population
                intent.putExtra("likes",resultp.get(RankingFragment.TAG_LI));
                // Pass all data flag
                intent.putExtra("flag", resultp.get(RankingFragment.TAG_FLAG));
                // Start SingleItemView Class
                context.startActivity(intent);

            }
        });
        */
        return itemView;
    }

}
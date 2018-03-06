package com.example.ghost.newsapi;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ghost on 3/3/2018.
 */
class ListNewsViewHolder {
    ImageView galleryImage;
    TextView author, title, sdetails, time;
}
class ListNewsAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    public ListNewsAdapter(MainActivity mainActivity, ArrayList<HashMap<String, String>> dataList) {
        this.activity=mainActivity;
        this.data=dataList;



    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {Log.v("errror","error");
        ListNewsViewHolder holder = null;
        if(view==null)

        {
          holder= new ListNewsViewHolder();
            view = LayoutInflater.from(activity).inflate(
                    R.layout.list_row, viewGroup, false);
            holder.galleryImage = (ImageView) view.findViewById(R.id.galleryImage);
            holder.author = (TextView) view.findViewById(R.id.author);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.sdetails = (TextView) view.findViewById(R.id.sdetails);
            holder.time = (TextView) view.findViewById(R.id.time);
            view.setTag(holder);
        }
        else
            holder = (ListNewsViewHolder) view.getTag();
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        try{
            holder.author.setText(song.get(MainActivity.KEY_AUTHOR));
            holder.title.setText(song.get(MainActivity.KEY_TITLE));
            holder.time.setText(song.get(MainActivity.KEY_PUBLISHEDAT));
            holder.sdetails.setText(song.get(MainActivity.KEY_DESCRIPTION));

            if(song.get(MainActivity.KEY_URLTOIMAGE).toString().length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }else{
                Picasso.with(activity)
                        .load(song.get(MainActivity.KEY_URLTOIMAGE).toString())
                        .resize(300, 200)
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {}
        return view;

    }
}

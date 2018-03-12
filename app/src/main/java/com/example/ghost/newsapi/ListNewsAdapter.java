package com.example.ghost.newsapi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
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
                    R.layout.lr, viewGroup, false);
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
            if(song.get(MainActivity.KEY_AUTHOR)!="null") {

                holder.author.setText(song.get(MainActivity.KEY_AUTHOR));
            } else
                holder.author.setText(" ");
            holder.title.setText(song.get(MainActivity.KEY_TITLE));
            holder.time.setText(song.get(MainActivity.KEY_PUBLISHEDAT));
            holder.sdetails.setText(song.get(MainActivity.KEY_DESCRIPTION));

           holder.galleryImage.setImageResource(R.mipmap.iload);
            new DownloadImageTask(holder.galleryImage)
                    .execute(song.get(MainActivity.KEY_URLTOIMAGE));



	/*An async task for processing the image url and to prevent the UI thread from getting blocked*/




//            if(song.get(MainActivity.KEY_URLTOIMAGE).toString().length() < 5)
//            {
//                holder.galleryImage.setVisibility(View.GONE);
//            }else{
//
//                Picasso.with(activity)
//                        .load(song.get(MainActivity.KEY_URLTOIMAGE).toString())
//                        .placeholder(R.mipmap.iload)
//                        .resize(300, 300)
//                        .into(holder.galleryImage);
//
//
//            }
        }catch(Exception e) {}
        return view;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView ImgView;

        public DownloadImageTask(ImageView ImgView) {
            this.ImgView = ImgView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap Img = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                Img = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return Img;
        }

        protected void onPostExecute(Bitmap Img) {
            ImgView.setImageBitmap(Img);
        }
    }

    }


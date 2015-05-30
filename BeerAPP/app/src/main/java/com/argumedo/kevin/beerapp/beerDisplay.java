package com.argumedo.kevin.beerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class BeerDisplay extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beerdisplay);
        startLoadTask(this);
    }

    public void startLoadTask(Context c){
        if (isOnline()) {
            loadItems task = new loadItems();
            task.execute();

        } else {
            Toast.makeText(c, "Not online", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    private class loadItems extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection = null;
        ImageView image = (ImageView) findViewById(R.id.Pic);
        Bitmap bmImage = null;

        Beer beer = (Beer) getIntent().getSerializableExtra("vBeer");
        String b = beer.getName();

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.d("DATA", b);
                InputStream in = new java.net.URL(beer.getPic()).openStream();
                bmImage = BitmapFactory.decodeStream(in);


            }  catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();

                }
            }
            return "";
        }

        protected void onPostExecute(String result) {
            TextView Name;
            TextView Desc;
            TextView ABV;

            Name = (TextView) findViewById(R.id.Name);
            Desc = (TextView) findViewById(R.id.Desc);
            ABV = (TextView)  findViewById(R.id.ABV);

            Name.setText(beer.getName());
            ABV.setText("ABV(" + beer.getAbv() + "%)");

            if(beer.getDescription().length()>0)
            {
                Desc.setText(beer.getDescription());
            }
            else
            {
                Desc.setText("Description is Unavailable");
            }
            if(bmImage == null)
            {
                image.setImageDrawable(getResources().getDrawable(R.drawable.unavailable));
            }
            else
            {
                image.setImageBitmap(bmImage);
            }

        }
    }
}

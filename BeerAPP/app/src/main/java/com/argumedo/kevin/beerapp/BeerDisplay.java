package com.argumedo.kevin.beerapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.ArrayList;import android.app.ListFragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;


public class BeerDisplay extends ActionBarActivity
{
    Button favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beerdisplay);
        startLoadTask(this);
        favorite = (Button) findViewById(R.id.favAdd);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "status " + "hello sucker Just checking on you ");
                Beer beer = (Beer) getIntent().getSerializableExtra("vBeer");
                beer.setType("1");

                DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
                dbHelper.clearTable();
                dbHelper.addRow(beer);
                dbHelper.close();
                Log.d("TAG", "status " + "was it added yes I think ");

                // showList();


            }
        });
    }
    /*                                          this is a maybe still not sure what I need it for...
    public void showList() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new FlickerFragment());
        ft.commit();
    }
    */
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
            TextView Recommended;

            Name = (TextView) findViewById(R.id.Name);
            Desc = (TextView) findViewById(R.id.Desc);
            ABV = (TextView)  findViewById(R.id.ABV);
            Recommended = (TextView) findViewById(R.id.recommended);

            Name.setText(beer.getName());
            ABV.setText("ABV(" + beer.getAbv() + "%)");
            Recommended.setText("View Recommended Beers");


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

            final TextView rec = (TextView) findViewById(R.id.recommended);

            rec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String query = beer.getStyleId();

                    Intent intent = new Intent(BeerDisplay.this, Recommended.class);
                    intent.putExtra("query", query);
                    startActivity(intent);
                }
            });




        }
    }
}

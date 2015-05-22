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


public class SurpriseMe extends ActionBarActivity
{

    private String PhotoURL, imageString;
    private ImageView featuredImage;
    private Bitmap featuredBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surpriseme);
        startLoadTask(SurpriseMe.this);
    }

    public void startLoadTask(Context c){
        if (isOnline()) {
            CallAPI task = new CallAPI();
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
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    private class CallAPI extends AsyncTask<String, String, String> {
        ArrayList<Surprise> randomBeer;
        HttpURLConnection urlConnection = null;

        @Override
        protected String doInBackground(String... params) {
            String startURL = "https://api.brewerydb.com/v2/";
            String endURL = "key=e1afe81e104ba290bb7507cd693ead92&format=json";
            String dataString = startURL + "beer/random?hasLabels=y&" + endURL;

            try {
                URL dataURL = new URL(dataString);

                urlConnection = (HttpURLConnection) dataURL.openConnection();
                urlConnection.connect();
                int status = urlConnection.getResponseCode();
                Log.i(status + "", status + "");

                InputStream inStream = urlConnection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));

                String response;
                StringBuilder sb = new StringBuilder();

                while ((response = bReader.readLine()) != null) {
                    sb = sb.append(response);
                }
                String fData = sb.toString();
                Log.d("data", fData);

                randomBeer = Surprise.getRandomBeer(fData);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return "";
        }

        protected void onPostExecute(String result) {
            if(result != null)
            {
                TextView rName = (TextView) findViewById(R.id.RandomName);
                TextView rDesc = (TextView) findViewById(R.id.RandomDesc);
                TextView rABV = (TextView)  findViewById(R.id.RandomABV);

                if(randomBeer != null)
                {
                    rName.setText(randomBeer.get(0).getName());
                    rABV.setText("ABV(" + randomBeer.get(0).getAbv());

                    if(randomBeer.get(0).getDescription().length()>0)
                    {
                        rDesc.setText(randomBeer.get(0).getDescription());
                    }
                    else
                    {
                        rDesc.setText("Description is Unavailable");

                    }

                    if(randomBeer.get(0).getPic().length() > 0)
                    {
                        new DownloadImageTask((ImageView) findViewById(R.id.RandomPic))
                                .execute(randomBeer.get(0).getPic());
                    }
                    else
                    {
                        ImageView img = (ImageView) findViewById(R.id.RandomPic);
                        img.setImageDrawable(getResources().getDrawable(R.drawable.unavailable));
                    }
                }
                else
                {
                    rName.setText("Error");
                }
            }

        }
    }
}


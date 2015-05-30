package com.argumedo.kevin.beerapp;

/**
 * Created by zero on 5/29/15.
 */
import android.app.ListFragment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FramentMainMenu extends Fragment implements AdapterView.OnItemClickListener{
    Cursor cursor;
    Button beerWeek, surpriseMe, search;
    EditText searchQ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        final MainActivity activity = (MainActivity)this.getActivity();

        beerWeek = (Button) view.findViewById(R.id.beerOfTheWeek);
        beerWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beerOfTheWeek();
            }
        });

        surpriseMe = (Button) view.findViewById(R.id.surpiseMe);
        surpriseMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surpriseMe();
            }
        });

        searchQ = (EditText) view.findViewById(R.id.searchQ);

        search = (Button) view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(v);
            }
        });

        return view;
    }

    public void search(View view)
    {
        EditText editText = (EditText)view.findViewById(R.id.searchQ);
        String query = editText.getText().toString();

        Intent intent = new Intent(getActivity(), Search.class);
        intent.putExtra("query", query);
        startActivity(intent);

    }


    public void surpriseMe()
    {
        Intent intent = new Intent(getActivity(),SurpriseMe.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }


    public void beerOfTheWeek()
    {
        Intent intent = new Intent(getActivity(),BeerOfTheWeek.class);
        intent.putExtra("taco","taco");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    //(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, Cursor cursor
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(getActivity(), mTitles[i], Toast.LENGTH_LONG).show();
    }


}

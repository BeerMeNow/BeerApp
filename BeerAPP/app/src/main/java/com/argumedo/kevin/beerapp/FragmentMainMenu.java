package com.argumedo.kevin.beerapp;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class FragmentMainMenu extends Fragment implements AdapterView.OnItemClickListener{
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

                String query = searchQ.getText().toString();

                Intent intent = new Intent(getActivity(), Search.class);
                intent.putExtra("query", query);
                startActivity(intent);
            }
        });

        return view;
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    //(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, Cursor cursor
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(getActivity(), mTitles[i], Toast.LENGTH_LONG).show();
    }


}
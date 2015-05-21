package com.argumedo.kevin.beerapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Beer {
    String id, name, abv;


    public Beer(JSONObject jsonBeer) throws JSONException
    {
        this.id = (String) jsonBeer.optString("id");
        this.name = (String) jsonBeer.optString("name");
        this.abv = (String) jsonBeer.optString("abv");
    }

    public static ArrayList<Beer>  makeBeerList (String beerData) throws JSONException
    {
        ArrayList<Beer> beerList = new ArrayList<Beer>();
        JSONObject data = new JSONObject(beerData);
        JSONArray  beerArray = data.optJSONArray("data");

        for(int i = 0; i < beerArray.length(); i++)
        {
            JSONObject beer = (JSONObject) beerArray.get(i);
            Beer currentBeer = new Beer(beer);
            beerList.add(currentBeer);
        }

        return beerList;
    }


}

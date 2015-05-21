package com.argumedo.kevin.beerapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Surprise {
    private String beerId, name, description, abv, pic;
    public Surprise(JSONObject jsonSurprise, String Pic) throws JSONException
    {
        this.beerId = (String) jsonSurprise.optString("id");
        this.name = (String) jsonSurprise.optString("name");
        this.description = (String) jsonSurprise.optString("description");
        this.abv = (String) jsonSurprise.optString("abv");
        this.pic = Pic;

    }

    public static ArrayList<Surprise> getRandomBeer(String featuredData) throws JSONException
    {
        ArrayList<Surprise> randomBeer = new ArrayList<>();
        JSONObject results = new JSONObject(featuredData);
        JSONObject data = results.optJSONObject("data");
        Log.d("DATADATA", data.toString());
        try
        {
            JSONObject labels = data.optJSONObject("labels");
            Log.d("PICTURE", labels +"");
            String pic = labels.optString("large");
            Log.d("PICTURE", pic);
            Surprise Random = new Surprise(data, pic);
            randomBeer.add(Random);
        }
        catch(NullPointerException E)
        {
            String pic = "";
            Surprise Random = new Surprise(data, pic);
            randomBeer.add(Random);
        }

        return randomBeer;

    }

    public String getName() {
        return name;
    }

    public String getBeerId() {
        return beerId;
    }

    public String getAbv() {
        return abv;
    }

    public String getDescription() {

        return description;
    }

    public String getPic() {
        return pic;
    }
}

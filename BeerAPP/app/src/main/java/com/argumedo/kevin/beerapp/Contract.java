package com.argumedo.kevin.beerapp;

import android.provider.BaseColumns;

public class Contract{
    public static final String DATABASE_NAME = "favorites.db";
    favEntry pe = new favEntry();

    public static final class favEntry implements BaseColumns{
        public int test = 7;

        public static final String TABLE_NAME = "favorites";
        public static final String BEER_NAME = "beerName";
        public static final String BEER_ID = "beerID";
        public static final String BEER_DESC = "beerDesc";
        public static final String BEER_ABV = "beerABV";

    }

}
package com.argumedo.kevin.beerapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;


public class favoritesAdapter extends CursorAdapter {
    private ImageView mImageView;
    TextView myABV;
    String ABV,name,ID;
    Button delete;
    private Cursor cur;
    private Context mContext;
    public favoritesAdapter(Context c, Cursor cursor){

        super(c, cursor, 0);
        this.cur=cursor;
        this.mContext=c;



    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {


//Handle buttons and add onClickListeners
        delete = (Button) view.findViewById(R.id.deleteMe);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Deleted",
                        Toast.LENGTH_SHORT).show();
                Log.d("TAG", "status " + "hello sucker Just checking on you ");
                cur.getColumnIndex(Contract.beerEntry._ID);
                ID=cur.getString(cur.getColumnIndex(Contract.beerEntry._ID));
                name=cur.getString(cur.getColumnIndexOrThrow(Contract.beerEntry.NAME));
                DataBaseHelper dbHelper = new DataBaseHelper(mContext);
                //dbHelper.clearTable();
                dbHelper.deleteRow(ID);

                dbHelper.close();

                Log.d("ADAPTER", "BEER that was Deleted: " + name + "ID:" + ID);
                Log.i("index:", "the index is:" + ID);

                //noinspection deprecation
                cur.requery();
               notifyDataSetChanged();
            }
        });


        TextView title = (TextView) view.findViewById(R.id.title_text);
        String beerName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.beerEntry.NAME));
        if(beerName.length()>=29)
        {
           beerName=beerName.substring(0,29)+"...";

        }
        title.setText(beerName);

        myABV=(TextView) view.findViewById(R.id.favABV);
        ABV=cursor.getString(cursor.getColumnIndexOrThrow(Contract.beerEntry.ABV));
        Log.i("FLICKR PHOTO ADAPT",ABV);
        String myString="(ABV:"+ABV+"%)";
        if(ABV.length()<=0)
        {
            myString="(ABV:N/A)";

        }
        myABV.setText(myString);

        //this is where you want to put the image at...............................................

    }

}
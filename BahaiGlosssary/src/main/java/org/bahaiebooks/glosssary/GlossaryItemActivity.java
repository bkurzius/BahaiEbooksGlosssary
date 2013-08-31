package org.bahaiebooks.glosssary;

/**
 * Created by briankurzius on 8/30/13.
 */

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GlossaryItemActivity extends Activity{
    private final String TAG = "GlossaryItemActivity";
    private MediaPlayer mp;
    private List<GlossaryItem> glossaryItems;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private GlossaryItem currentItem;
    private TextView itemTitle;
    private TextView itemDefinition;
    private String title = "";
    private String mp3 = "";
    private String definition = "";
    private int itemNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary_item);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            title = extras.getString(GlossaryListActivity.EXTRA_TITLE);
            mp3 = extras.getString(GlossaryListActivity.EXTRA_MP3);
            definition = extras.getString(GlossaryListActivity.EXTRA_DEFINITION);
            itemNumber = extras.getInt(GlossaryListActivity.EXTRA_ITEM_NUMBER);
            itemTitle = (TextView) findViewById(R.id.tvTitle);
            itemDefinition = (TextView) findViewById(R.id.tvDefinition);
            // reparse the json here -- or perhaps store that in the appliction class
            // this way we can got forward and back from here since we kow where we are in the set.
            try {
                InputStream is = this.getAssets().open("glossary.json");
                int ch;
                StringBuffer str = new StringBuffer();
                while ((ch = is.read()) != -1)
                    str.append((char) ch);
                String result = str.toString();
                Gson gson = new Gson();
                GlossaryList gi = gson.fromJson(result, GlossaryList.class);
                glossaryItems = gi.glossaryItems;

                setGlossaryItem();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("test", "the json did not parse");
            }


        }
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

        btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"clicked the button");

                if(itemNumber<glossaryItems.size()-1){
                    itemNumber++;
                    setGlossaryItem();
                }else{
                    Toast.makeText(GlossaryItemActivity.this,"no more items",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnPrevious = (ImageButton) findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"clicked the button");

                if(itemNumber>0){
                    itemNumber--;
                    setGlossaryItem();
                }else{
                    Toast.makeText(GlossaryItemActivity.this,"no more items",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setGlossaryItem(){
        currentItem = glossaryItems.get(itemNumber);
        title = currentItem.getTitle();
        mp3 = currentItem.getMp3();
        definition = currentItem.getDefinition();

        // set text
        itemTitle.setText(Html.fromHtml(title));
        if(definition.length()<1){
            itemDefinition.setVisibility(View.GONE);
        }else{
            itemDefinition.setText(Html.fromHtml(definition));
        }
        int identifier2 = getResources().getIdentifier("raw/" + mp3, null, this.getPackageName());
        mp = MediaPlayer.create(GlossaryItemActivity.this, identifier2);
    }

    private void openList(){
        Intent intent = new Intent(this, GlossaryListActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}

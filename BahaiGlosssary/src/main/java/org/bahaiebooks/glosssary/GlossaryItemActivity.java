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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class GlossaryItemActivity extends Activity{
    private final String TAG = "GlossaryItemActivity";
    private MediaPlayer mp;
    public List<GlossaryItem> glossaryItems;
    private TextView itemTitle;
    private TextView itemDefinition;
    String title = "";
    String mp3 = "";
    String definition = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary_item);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            title = extras.getString(GlossaryListActivity.EXTRA_TITLE);
            mp3 = extras.getString(GlossaryListActivity.EXTRA_MP3);
            definition = extras.getString(GlossaryListActivity.EXTRA_DEFINITION);
            Log.d(TAG,"mp3:" + mp3);
            int identifier = getResources().getIdentifier("raw/" + mp3, null, this.getPackageName());
            Log.d(TAG,"identifier:" + identifier);
            mp = MediaPlayer.create(GlossaryItemActivity.this, identifier);
            itemTitle = (TextView) findViewById(R.id.tvTitle);
            itemTitle.setText(Html.fromHtml(title));
            itemDefinition = (TextView) findViewById(R.id.tvDefinition);
            if(definition.length()<1){
                itemDefinition.setVisibility(View.GONE);
            }else{
                itemDefinition.setText(Html.fromHtml(definition));
            }
        }
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

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

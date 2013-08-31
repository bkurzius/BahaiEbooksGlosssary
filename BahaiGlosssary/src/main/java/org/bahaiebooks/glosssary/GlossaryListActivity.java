package org.bahaiebooks.glosssary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GlossaryListActivity extends Activity {
    public static final String EXTRA_MP3 = "extras_mp3";
    public static final String EXTRA_ITEM_NUMBER = "extras_item_number";
    public static final String EXTRA_TITLE = "extras_title";
    public static final String EXTRA_DEFINITION = "extras_definition";
    public List<GlossaryItem> glossaryItems;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary_list);
        lv = (ListView)this.findViewById(R.id.lvGlossary);
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

            for(GlossaryItem g:glossaryItems){
                Log.d("test", "the title is" + g.getTitle());
            }
            Log.d("test", "the size of the list is: " + glossaryItems.size());

           // List
            String[] values = new String[] { "a", "b", "c", "d", "e", "f", "g",
                    "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                    "t", "u", "w", "x", "y", "z" };

            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values);
            ListAdapter adapter = new ListAdapter(this, R.id.lvGlossary, glossaryItems);


            // Assign adapter to List
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("item clicked", "item clicked was: " + glossaryItems.get(i).getTitle());
                    Intent glossaryItemIntent = new Intent(GlossaryListActivity.this,GlossaryItemActivity.class);
                    glossaryItemIntent.putExtra(EXTRA_TITLE,glossaryItems.get(i).getTitle());
                    glossaryItemIntent.putExtra(EXTRA_MP3,glossaryItems.get(i).getMp3());
                    glossaryItemIntent.putExtra(EXTRA_DEFINITION,glossaryItems.get(i).getDefinition());
                    glossaryItemIntent.putExtra(EXTRA_ITEM_NUMBER,i);
                    startActivity(glossaryItemIntent);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
            Log.d("test", "the json did not parse");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.glossary_list, menu);
        return true;
    }

    public class ListAdapter extends ArrayAdapter<GlossaryItem> {

        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            // TODO Auto-generated constructor stub
        }

        private List<GlossaryItem> items;

        public ListAdapter(Context context, int resource, List<GlossaryItem> items) {

            super(context, resource, items);

            this.items = items;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.item_glossary_list_item, null);
            }

            GlossaryItem p = items.get(position);

            if (p != null) {
                TextView tt = (TextView) v.findViewById(R.id.tvTitle);
                if (tt != null) {
                   tt.setText(Html.fromHtml(p.getTitle()));
                }
            }
            return v;
        }
    }




}

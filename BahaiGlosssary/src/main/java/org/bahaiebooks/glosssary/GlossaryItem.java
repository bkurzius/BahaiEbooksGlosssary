package org.bahaiebooks.glosssary;

/**
 * Created by briankurzius on 8/24/13.
 */

import com.google.gson.annotations.SerializedName;


public class GlossaryItem {

    @SerializedName("title")
    public String title;
    @SerializedName("mp3")
    private String mp3;
    @SerializedName("definition")
    private String definition;

    public String getTitle(){
        return this.title;
    }
    public String getMp3(){
        return this.mp3;
    }
    public String getDefinition(){
        return this.definition;
    }

    public String toString(){
        return this.title;
    }


}

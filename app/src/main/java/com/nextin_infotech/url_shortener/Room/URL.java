package com.nextin_infotech.url_shortener.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "URLTable")
public class URL {

    @PrimaryKey(autoGenerate = true)
    int ID;
    String fullURL;
    String shortURL;

    public URL(String fullURL, String shortURL) {
        this.fullURL = fullURL;
        this.shortURL = shortURL;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFullURL() {
        return fullURL;
    }

    public void setFullURL(String fullURL) {
        this.fullURL = fullURL;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }
}

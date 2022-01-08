package com.nextin_infotech.url_shortener.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {URL.class}, version = 1, exportSchema = false)
public abstract class URLDatabase extends RoomDatabase {

    public abstract URLDao urlDao();

    public static URLDatabase INSTANCE;

    public static URLDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), URLDatabase.class, "URLTable").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}

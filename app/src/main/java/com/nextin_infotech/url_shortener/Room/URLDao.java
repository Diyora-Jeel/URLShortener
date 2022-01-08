package com.nextin_infotech.url_shortener.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface URLDao {

    @Query("select * from URLTable ORDER BY id DESC")
    LiveData<List<URL>> getAllURL();

    @Insert
    public void insertURL(URL url);

    @Query("Delete from URLTable where ID = :id")
    public void deleteURL(int id);

}

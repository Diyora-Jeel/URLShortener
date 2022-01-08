package com.nextin_infotech.url_shortener.Room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class URLRepository {

    public URLDao urlDao;

    public LiveData<List<URL>> getAllURL;

    public URLRepository(Application application) {
        URLDatabase database = URLDatabase.getDatabaseInstance(application);
        urlDao = database.urlDao();
        getAllURL = urlDao.getAllURL();
    }

    public void insertURL(URL url) {
        urlDao.insertURL(url);
    }

    public void deleteURL(int id) {
        urlDao.deleteURL(id);
    }
}
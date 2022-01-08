package com.nextin_infotech.url_shortener.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class URLViewModel extends AndroidViewModel {

    public URLRepository repository;
    public LiveData<List<URL>> getAllURL;

    public URLViewModel(@NonNull Application application) {
        super(application);

        repository = new URLRepository(application);
        getAllURL = repository.getAllURL;
    }

    public void InsertURL(URL url) {
        repository.insertURL(url);
    }

    public void DeleteURL(int id) {
        repository.deleteURL(id);
    }
}

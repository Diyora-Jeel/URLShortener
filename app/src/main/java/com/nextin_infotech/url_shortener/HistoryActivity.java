package com.nextin_infotech.url_shortener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.nextin_infotech.url_shortener.Room.URLViewModel;
import com.nextin_infotech.url_shortener.URLAdapter.URLAdapter;
import com.nextin_infotech.url_shortener.databinding.ActivityHistoryBinding;

public class HistoryActivity extends AppCompatActivity {

    ActivityHistoryBinding binding;

    URLViewModel urlViewModel;

    URLAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        urlViewModel = new ViewModelProvider(this).get(URLViewModel.class);

        urlViewModel.getAllURL.observe(HistoryActivity.this, urls -> {

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
            adapter = new URLAdapter(urls, HistoryActivity.this);
            binding.recyclerView.setAdapter(adapter);
        });
    }
}
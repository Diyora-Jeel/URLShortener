package com.nextin_infotech.url_shortener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nextin_infotech.url_shortener.Network.NetworkChangeReceiver;
import com.nextin_infotech.url_shortener.Room.URL;
import com.nextin_infotech.url_shortener.Room.URLViewModel;
import com.nextin_infotech.url_shortener.URLAdapter.URLAdapter;
import com.nextin_infotech.url_shortener.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    URLViewModel urlViewModel;
    String fullLink, shortLink;

    URLAdapter adapter;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastReceiver();

        urlViewModel = new ViewModelProvider(this).get(URLViewModel.class);

        urlViewModel.getAllURL.observe(MainActivity.this, urls -> {

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            adapter = new URLAdapter(urls, MainActivity.this);
            binding.recyclerView.setAdapter(adapter);
        });

        binding.HistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        binding.PasteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData pasteData = manager.getPrimaryClip();
                if (pasteData != null && pasteData.getItemCount() > 0) {
                    ClipData.Item item = pasteData.getItemAt(0);
                    binding.urlEditText.setText(item.getText().toString());
                } else {
                    binding.urlEditText.setError("empty");
                }
            }
        });

        binding.createShortURLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = binding.urlEditText.getText().toString().trim();

                if (Patterns.WEB_URL.matcher(url).matches()) {

                    binding.spinKit.setVisibility(View.VISIBLE);

                    RequestQueue requestQueue;
                    requestQueue = Volley.newRequestQueue(MainActivity.this);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://cutt.ly/api/api.php?key=8ef55569c632e84381639c6ce1b666cd55d0e&short=" + url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                fullLink = response.getJSONObject("url").getString("fullLink");
                                shortLink = response.getJSONObject("url").getString("shortLink");

                                Insert();

                                binding.spinKit.setVisibility(View.GONE);
                                binding.urlEditText.setText("");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                } else {
                    binding.urlEditText.setError("Please Enter URL");
                }
            }
        });
    }

    protected void registerNetworkBroadcastReceiver() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterBroadcastReceiver()
    {
        try {
            unregisterReceiver(broadcastReceiver);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    private void Insert() {

        URL url = new URL(fullLink, shortLink);
        urlViewModel.InsertURL(url);
    }
}
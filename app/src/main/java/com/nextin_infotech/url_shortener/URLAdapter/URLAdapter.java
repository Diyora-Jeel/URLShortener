package com.nextin_infotech.url_shortener.URLAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.nextin_infotech.url_shortener.DetailsActivity;
import com.nextin_infotech.url_shortener.R;
import com.nextin_infotech.url_shortener.Room.URL;
import com.nextin_infotech.url_shortener.Room.URLViewModel;

import java.util.List;


public class URLAdapter extends RecyclerView.Adapter<URLAdapter.URLViewHolder> {

    List<URL> listURL;
    Context context;
    URLViewModel urlViewModel;

    public URLAdapter(List<URL> listURL, Context context) {
        this.listURL = listURL;
        this.context = context;
    }

    @NonNull
    @Override
    public URLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.url_layout, parent, false);
        return new URLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull URLViewHolder holder, int position) {

        urlViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(URLViewModel.class);

        holder.shortLink.setText(listURL.get(position).getShortURL());
        holder.fullLink.setText(listURL.get(position).getFullURL());

        String shortLink = listURL.get(position).getShortURL();
        String fullLink = listURL.get(position).getFullURL();

        int id = listURL.get(position).getID();


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                urlViewModel.DeleteURL(id);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("shortLink", shortLink);
                intent.putExtra("fullLink", fullLink);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listURL.size();
    }

    public class URLViewHolder extends RecyclerView.ViewHolder {

        TextView shortLink, fullLink;
        ImageView delete;
        CardView cardView;

        public URLViewHolder(@NonNull View itemView) {
            super(itemView);

            shortLink = itemView.findViewById(R.id.shortLink);
            fullLink = itemView.findViewById(R.id.fullLink);
            delete = itemView.findViewById(R.id.delete);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}

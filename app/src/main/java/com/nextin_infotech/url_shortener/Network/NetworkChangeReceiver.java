package com.nextin_infotech.url_shortener.Network;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.nextin_infotech.url_shortener.R;

public class NetworkChangeReceiver extends BroadcastReceiver
{

    Dialog dialog;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if (isOnline(context)) {
                dialog.dismiss();
            } else {
                Dialog(context);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public boolean isOnline(Context context) {
        try {

            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    void Dialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.customdialog, null);
        Button CloseBtn = view.findViewById(R.id.CloseBtn);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        CloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                System.exit(0);
            }
        });

        dialog.show();
    }
}
package com.nextin_infotech.url_shortener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.nextin_infotech.url_shortener.databinding.ActivityDetailsBinding;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    String shortLink, fullLink;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        shortLink = getIntent().getStringExtra("shortLink");
        fullLink = getIntent().getStringExtra("fullLink");

        binding.shortLink.setText(shortLink);
        binding.fullLink.setText(fullLink);

        createQRCode();

        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plane");
                share.putExtra(Intent.EXTRA_TEXT,shortLink);
                startActivity(Intent.createChooser(share,"Share URL"));
            }
        });

        binding.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text",shortLink);
                manager.setPrimaryClip(clipData);

                Toast.makeText(DetailsActivity.this, "URl Copied!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createQRCode() {

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();

        Point point = new Point();
        display.getSize(point);

        int width = point.x;
        int height = point.y;

        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        qrgEncoder = new QRGEncoder(binding.shortLink.getText().toString(), null, QRGContents.Type.TEXT, dimen);
        try {

            bitmap = qrgEncoder.encodeAsBitmap();
            binding.imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {

        }
    }
}

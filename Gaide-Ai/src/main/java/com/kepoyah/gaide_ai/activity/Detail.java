package com.kepoyah.gaide_ai.activity;

import static maes.tech.intentanim.CustomIntent.customType;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kepoyah.gaide_ai.R;
import com.kepoyah.gaide_ai.ads.admob.AdBannerAi;
import com.zayviusdigital.artificialintelligence.listener.OnListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Detail extends AppCompatActivity implements Html.ImageGetter{
    private TextView contentDescription;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ai);

        TextView titleTv = findViewById(R.id.titleTv);
        contentDescription = findViewById(R.id.contentDesc);
        contentDescription.setMovementMethod(LinkMovementMethod.getInstance());
        titleTv.setText(getIntent().getStringExtra("title"));
        Spanned spanned = Html.fromHtml(getIntent().getStringExtra("content"), Detail.this, null);
        contentDescription.setText(spanned);
        RelativeLayout banner_ly = findViewById(R.id.banner);
        AdBannerAi.GET(Detail.this, banner_ly, new OnListener() {
            @Override
            public void succeed() {
                super.succeed();
            }

            @Override
            public void failed() {
                super.failed();
            }
        });

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable empty = getResources().getDrawable(R.drawable.ic_baseline_image_24);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);

        return d;
    }


    class LoadImage extends AsyncTask<Object, Void, Bitmap> {
        private LevelListDrawable mDrawable;
        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                CharSequence t = contentDescription.getText();
                contentDescription.setText(t);
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        customType(Detail.this,"fadein-to-fadeout");
    }

}
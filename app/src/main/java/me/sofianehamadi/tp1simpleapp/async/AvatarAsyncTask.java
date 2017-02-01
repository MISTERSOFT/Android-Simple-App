package me.sofianehamadi.tp1simpleapp.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by MISTERSOFT on 31/01/2017.
 */

public class AvatarAsyncTask extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... params) {
        String imageUrl = params[0];
        Bitmap image = null;
        if (!imageUrl.equals("")) {
            URL url = null;
            try {
                url = new URL(imageUrl);
                image = BitmapFactory.decodeStream((InputStream) url.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }
}

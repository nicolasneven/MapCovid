package com.example.mapcovid;

import android.os.AsyncTask;
import java.io.IOException;

public class DataScraper extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(TestActivity.this);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Connect to the website
            Document document = Jsoup.connect(url).get();

            //Get the logo source of the website
            Element img = document.select("img").first();
            // Locate the src attribute
            String imgSrc = img.absUrl("src");
            // Download image from URL
            InputStream input = new java.net.URL(imgSrc).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);

            //Get the title of the website
            title = document.title();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        imageView.setImageBitmap(bitmap);
        textView.setText(title);
        progressDialog.dismiss();
    }

}

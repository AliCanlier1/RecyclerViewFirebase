package com.example.recyclerviewfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    Button btnStart;
    Button btnThread;
    ImageView threadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        btnThread = findViewById(R.id.btnThread);
        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadImage downloadImage = new DownloadImage();
                Bitmap bitmap = null;
                try {
                    bitmap = downloadImage.execute("https://upload.wikimedia.org/wikipedia/commons/e/ed/016vallesmarineris_reduced0.5.jpg").get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadImage.setImageBitmap(bitmap);

            }
        });

    }
    private class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        HttpURLConnection httpURLConnection;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url =new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                Bitmap threadPhoto = BitmapFactory.decodeStream(inputStream);
                return threadPhoto;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                httpURLConnection.disconnect();
            }
            return null;
        }
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            if(bitmap !=null){
                threadImage.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(),"Download Successful!",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Download finished with failure!",Toast.LENGTH_SHORT).show();

            }
        }
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
    }

}
package com.example.RecyclerViewFirebase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button btnSignIn, btnSignUp,btnThread;
    ImageView imageView;
    EditText emailText, passwordText;
    ImageView threadImage;
    String url = "https://upload.wikimedia.org/wikipedia/tr/8/86/Fenerbah%C3%A7e_SK.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnThread = findViewById(R.id.btnThread);
        imageView = findViewById(R.id.imageView);
        emailText = findViewById(R.id.txtUserName);
        passwordText = findViewById(R.id.txtPassword);
        auth = FirebaseAuth.getInstance();

        }
    public void signInClick(View view){
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        if(email.equals("")||password.equals(""))
            Toast.makeText(this,"Please enter email and password!",Toast.LENGTH_SHORT).show();
        else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intentOfSignIn = new Intent(MainActivity.this, UserLocationActivity.class);
                    startActivity(intentOfSignIn);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void signUpClick(View view){
        Intent intentOfSignUp = new Intent(MainActivity.this, SignUp.class);
        startActivity(intentOfSignUp);
    }
    public void threadButton(View view){
        ImageDownloader imageDownloader = (ImageDownloader) new ImageDownloader().execute(url);
    }
    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        HttpURLConnection urlConnection;
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                Bitmap temp = BitmapFactory.decodeStream(inputStream);
                return temp;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }
        protected void onPostExecute(Bitmap bitmap){
            if(bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}
package com.example.RecyclerViewFirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.RecyclerViewFirebase.User.User;
import com.example.RecyclerViewFirebase.User.UserAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements IRestaurantInterface{
    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    UserAdapter userAdapter;
    FirebaseFirestore db;
    String reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db =FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<User>();
        userAdapter = new UserAdapter(UserActivity.this,userArrayList,this);
        EventChangeListener();
        recyclerView.setAdapter(userAdapter);
    }
    private void EventChangeListener() {
        db.collection("RecyclerViewExample").orderBy("age")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange dc :value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                userArrayList.add(dc.getDocument().toObject(User.class));
                            }
                            userAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(UserActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
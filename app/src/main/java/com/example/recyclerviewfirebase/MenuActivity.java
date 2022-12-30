package com.example.recyclerviewfirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.recyclerviewfirebase.RestaurantMenu.Menu;
import com.example.recyclerviewfirebase.RestaurantMenu.MenuAdapter;
import com.example.recyclerviewfirebase.User.User;
import com.example.recyclerviewfirebase.User.UserAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Menu> menuArrayList;
    MenuAdapter menuAdapter;
    FirebaseFirestore db;
    Intent intent;
    String referenceID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        recyclerView = findViewById(R.id.menusRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db =FirebaseFirestore.getInstance();
        menuArrayList = new ArrayList<Menu>();
        menuAdapter = new MenuAdapter(MenuActivity.this,menuArrayList);
        recyclerView.setAdapter(menuAdapter);
        intent = getIntent();
        referenceID = intent.getStringExtra("DocumentId");
        EventChangeListener();
    }
    private void EventChangeListener() {
        db.collection("RecyclerViewExample").document(referenceID).collection("Menus")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange dc :value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                menuArrayList.add(dc.getDocument().toObject(Menu.class));
                            }
                            menuAdapter.notifyDataSetChanged();
                    }}
                });
    }
}
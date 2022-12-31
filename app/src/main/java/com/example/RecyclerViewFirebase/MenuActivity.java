package com.example.RecyclerViewFirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.RecyclerViewFirebase.RestaurantMenu.Menu;
import com.example.RecyclerViewFirebase.RestaurantMenu.MenuAdapter;
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
    Button location;
    Button reservation;
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
        EventChangeListener();
        location = findViewById(R.id.btnLocation);
        reservation = findViewById(R.id.btnReservation);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
    private void EventChangeListener() {
        db.collection("RestaurantMenus")
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
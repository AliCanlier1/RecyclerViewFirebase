package com.example.RecyclerViewFirebase;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recyclerviewfirebase.R;
import com.example.recyclerviewfirebase.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.RecyclerViewFirebase.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    LatLng userLatlng;
    Button saveLocation;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        saveLocation = findViewById(R.id.btnSaveLocation);
        userLatlng = new LatLng(37.161496313430575,28.37597874644133);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(userLatlng).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatlng,18));
        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                Map<String, Object> userLocation = new HashMap<>();
                userLocation.put("Latitude",37.161496313430575);
                userLocation.put("Longitude",28.37597874644133);

                db.collection("User Locations").document()
                        .set(userLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(UserLocationActivity.this,"Locations informations are added!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                Intent intent = new Intent(UserLocationActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }
}
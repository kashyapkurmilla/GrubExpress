package com.example.grubexpress.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grubexpress.Adapter.BestFoodsAdapter;
import com.example.grubexpress.Adapter.CategoryAdapater;
import com.example.grubexpress.Domain.Category;
import com.example.grubexpress.Domain.Foods;
import com.example.grubexpress.Domain.Location;
import com.example.grubexpress.Domain.Price;
import com.example.grubexpress.Domain.Time;
import com.example.grubexpress.Domain.User;
import com.example.grubexpress.R;
import com.example.grubexpress.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
private ActivityMainBinding binding ;
private FirebaseAuth Auth ;

TextView grubcoins , welcomeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView b1 = binding.logoutbtn;

        initUserData();
        initLocation();
        initTime();
        initPrice();
        initBestFood();
        initCategory();

        Button chatBotbtn = binding.button4;

        chatBotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initUserData() {
        welcomeName = binding.textView13;
        grubcoins = binding.textView10;

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            DatabaseReference userRef = database.getReference("Users").child(currentUser.getUid());

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String userName = snapshot.child("userName").getValue(String.class);
                        Long grubCoins = snapshot.child("grubCoins").getValue(Long.class);

                        if (userName != null) {
                            welcomeName.setText(userName);
                        }

                        if (grubCoins != null) {
                            grubcoins.setText(String.valueOf(grubCoins));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        } else {
            // Handle the case where currentUser is null
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent);
        }
    }



    private void initBestFood() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBarBestFood.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query = myRef.orderByChild("BestFood").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Foods.class));
                    }
                    if (!list.isEmpty()) {
                        binding.BestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter<BestFoodsAdapter.ViewHolder> adapter = new BestFoodsAdapter(list);
                        binding.BestFoodView.setAdapter(adapter);
                    }
                }
                binding.progressBarBestFood.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBar5.setVisibility(View.VISIBLE);
        ArrayList<Category> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Category.class));
                    }
                    if (!list.isEmpty()) {
                        binding.ChooseCategory.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                        RecyclerView.Adapter<CategoryAdapater.ViewHolder> adapter = new CategoryAdapater(list);
                        binding.ChooseCategory.setAdapter(adapter);
                    }
                }
                binding.progressBar5.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    private void initLocation() {
        DatabaseReference myRef =database.getReference("Location");
        ArrayList<Location> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Location.class));
                    }
                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this,R.layout.sp_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.locationSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
    private void initTime() {
        DatabaseReference myRef =database.getReference("Time");
        ArrayList<Time> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Time.class));
                    }
                    ArrayAdapter<Time> adapter = new ArrayAdapter<>(MainActivity.this,R.layout.sp_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.timeSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
    private void initPrice() {
        DatabaseReference myRef =database.getReference("Price");
        ArrayList<Price> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Price.class));
                    }
                    ArrayAdapter<Price> adapter = new ArrayAdapter<>(MainActivity.this,R.layout.sp_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.priceSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}
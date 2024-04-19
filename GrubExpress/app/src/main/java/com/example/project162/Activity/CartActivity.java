package com.example.project162.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.project162.Adapter.CartAdapter;
import com.example.project162.Domain.Order;
import com.example.project162.Helper.ChangeNumberItemsListener;
import com.example.project162.Helper.ManagmentCart;
import com.example.project162.R;
import com.example.project162.databinding.ActivityCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;
    Button PlaceOdr ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        PlaceOdr = binding.button2;

        setVariable();
        double total = calculateCart();
        initList();

        PlaceOdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intiPlaceorder(total);
                Intent intent = new Intent(CartActivity.this, ByeActivity.class);
                startActivity(intent);
            }
        });

    }


    private void initList() {
        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, () -> calculateCart());
        binding.cardView.setAdapter(adapter);
    }

    private double calculateCart() {
        double percentTax = 0.02; //percent 2% tax
        double delivery = 10; // 10 Dollar

        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        binding.totalFeeTxt.setText("Rs " + itemTotal);
        binding.taxTxt.setText("Rs " + tax);
        binding.deliveryTxt.setText("Rs " + delivery);
        binding.totalTxt.setText("Rs " + total);

        return total;

    }

    private void intiPlaceorder(double total) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            DatabaseReference userRef = database.getReference("Users").child(currentUser.getUid());

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String userName = snapshot.child("userName").getValue(String.class);
                        Long grubCoins = snapshot.child("grubCoins").getValue(Long.class);

                        if (userName != null && grubCoins != null) {
                            // Add 20 extra grub coins
                            long newGrubCoins = grubCoins + 20;

                            // Create order object
                            Order order = new Order();
                            order.setCustomerName(userName);
                            // Assuming order date is the current date
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            String currentDate = dateFormat.format(new Date());
                            order.setOrderDate(currentDate);
                            order.setOrderTotal(total);

                            // Push order to Firebase
                            DatabaseReference ordersRef = database.getReference("Orders").child(currentUser.getUid()).push();
                            ordersRef.setValue(order);

                            // Clear cart
                            managmentCart.clearCart();

                            // Update grub coins
                            userRef.child("grubCoins").setValue(newGrubCoins);

                            // Navigate to ByeActivity

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        } else {
            Intent intent = new Intent(CartActivity.this, IntroActivity.class);
            startActivity(intent);
        }
    }



    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }
}
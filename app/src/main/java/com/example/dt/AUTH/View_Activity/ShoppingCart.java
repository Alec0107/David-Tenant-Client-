package com.example.dt.AUTH.View_Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dt.AUTH.Models.ProductOrder;
import com.example.dt.AUTH.Network.SocketManager;
import com.example.dt.AUTH.View_Model.CartSingleton;
import com.example.dt.AUTH.View_Model.RecyclerAdapterCart;
import com.example.dt.R;

public class ShoppingCart extends AppCompatActivity implements RecyclerAdapterCart.OnQtyChangeListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CartSingleton cartSingleton;
    private TextView total;
    private Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_cart);

        cartSingleton = CartSingleton.getCartProductInstance();

        populateCart();

        checkout = findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SocketManager s = SocketManager.getSocketInstance();
            }
        });


    }



    void populateCart(){

        if(cartSingleton.getProducts() != null) {

            recyclerView = findViewById(R.id.CartRecyclerview);
            layoutManager = new LinearLayoutManager(ShoppingCart.this);
            recyclerView.setLayoutManager(layoutManager);

            RecyclerAdapterCart recyclerAdapterCart = new RecyclerAdapterCart(ShoppingCart.this, cartSingleton.getProducts(),ShoppingCart.this);
            recyclerView.setAdapter(recyclerAdapterCart);

            setTotalPriceUI();

        }else{
            System.out.println("Your cart is empty!");
        }


    }


    void setTotalPriceUI(){
        total = findViewById(R.id.totalAmount);

        double totalAmount = cartSingleton.getTotalAmount();
        // Format the totalAmount to two decimal places
        String formattedAmount = String.format("%.2f", totalAmount);
        total.setText("$" + formattedAmount);

    }


    @Override
    public void onIncreaseQty(ProductOrder productOrder) {
        setTotalPriceUI();  // Update total price when quantity increases
    }

    @Override
    public void onDecreaseQty(ProductOrder productOrder) {
        setTotalPriceUI();  // Update total price when quantity increases
    }
}
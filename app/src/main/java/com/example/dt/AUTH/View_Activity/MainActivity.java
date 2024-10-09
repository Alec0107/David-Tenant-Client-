package com.example.dt.AUTH.View_Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dt.AUTH.Network.SocketManager;
import com.example.dt.AUTH.View_Fragment.CategoryFragment;
import com.example.dt.AUTH.View_Fragment.ProductFragment;
import com.example.dt.AUTH.Models.ProductOrder;
import com.example.dt.AUTH.View_Model.CartSingleton;
import com.example.dt.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ProductOrder> productOrderList;
    private TextView storeName;
    private ImageView bagView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        storeName = findViewById(R.id.Storename);
        bagView   = findViewById(R.id.bag);
        bagView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingCart = new Intent(MainActivity.this, ShoppingCart.class);
                startActivity(shoppingCart);

            }
        });

 
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.Fragment_container, ProductFragment.class, null)
                    .commit();

        }

        CartSingleton cartSingleton = CartSingleton.getCartProductInstance();
        productOrderList = cartSingleton.getProducts();

        NavBarButton();
        productCartQty();

    }





    public void NavBarButton(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.Botton_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                int id = item.getItemId();

                if(R.id.Home == id){

                    // Setting the Store Name
                    storeName.setText("DAVID TENANT");
                    Typeface typeface = Typeface.create("sans-serif-light", Typeface.BOLD);
                    storeName.setTypeface(typeface);

                    // adding or display the productHomepage
                    selectedFragment = new ProductFragment();
                    System.out.println("HOMEPAGE");

                }else if(R.id.Category == id){

                    // Changing the store name into ( Categories)
                    storeName.setText("Categories");
                    Typeface typeface = Typeface.create("serif", Typeface.NORMAL);
                    storeName.setTypeface(typeface);

                    // adding or display the category fragment
                    selectedFragment = new CategoryFragment();
                    System.out.println("Category");

                }else if(R.id.WishList == id){
                    System.out.println("WISH LIST");

                }else if(R.id.Profile == id){
                    System.out.println("PROFILE");

                }

                // Check if a fragment is selected and replace it in the FragmentManager
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.Fragment_container, selectedFragment, null)
                            .addToBackStack(null)
                            .commit();
                }

                return true;  // Return true to reflect the navigation item selection
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        productCartQty();
    }

    public void productCartQty(){

        View bagQty = findViewById(R.id.BagQty);
        int size = 0;


        if(productOrderList != null) {
            size = productOrderList.size();
        }

            // Show the bag quantity view only if there's at least one product
            if (size > 0) {
                bagQty.setVisibility(View.VISIBLE);
                TextView number = bagQty.findViewById(R.id.qtyNumber);

                String qty;

                if(size < 9){
                    qty = String.format("%02d", size);
                }else{
                    qty = String.valueOf(size);
                }
                number.setText(qty);

            }else{
                 bagQty.setVisibility(View.GONE);
            }

    } // productCartQty()

    @Override
    public void onBackPressed() {
        // Check if the current fragment is a CategoryFragment
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // If the current fragment is CategoryFragment, pop back stack
            getSupportFragmentManager().popBackStack();


        } else {
            // If no fragments are in the back stack, call the default back pressed behavior
            super.onBackPressed();
        }
    }
}

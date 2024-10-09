package com.example.dt.AUTH.View_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dt.AUTH.View_Model.CartSingleton;
import com.example.dt.AUTH.Models.ProductOrder;
import com.example.dt.R;

public class ProductDetail extends AppCompatActivity  {

    ImageView image, cart, plus, minus;
    TextView name, description, price, note;
    Button small, medium, large, xlarge, xxlarge, bag;
    String productName, productPrice, productImage, productDescription, productEditorNote;
    private CartSingleton cartSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        image       = findViewById(R.id.ProductImage);
        name        = findViewById(R.id.ProductName);
        description = findViewById(R.id.ProductDescription);
        price       = findViewById(R.id.ProductPrice);
        note        = findViewById(R.id.EditorsNote);

        //Buttons
        small   = findViewById(R.id.Small);
        medium  = findViewById(R.id.Medium);
        large   = findViewById(R.id.Large);
        xlarge  = findViewById(R.id.XLarge);
        xxlarge = findViewById(R.id.XXLarge);
        bag = findViewById(R.id.BagButton);
        cart = findViewById(R.id.bag);


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(ProductDetail.this, ShoppingCart.class);
                startActivity(cartIntent);
            }
        });


        handleButton(); // handling buttons

        // Retrieve th required data from Product Activity
        Intent intent = getIntent();

        productName        = intent.getStringExtra("PName");
        productDescription = intent.getStringExtra("PDescription");
        productPrice       = intent.getStringExtra("PPrice");
        productImage       = intent.getStringExtra("PImage");
        productEditorNote  = intent.getStringExtra("PEditorsNote");

        showProduct(productName, productDescription, productPrice, productImage, productEditorNote);

    }

    private void showProduct(String producteName, String productDescription, String productPrice, String productImage, String productEditorNote){

        name.setText(producteName);
        description.setText(productDescription);
        price.setText("$" + productPrice + "0");
        note.setText(productEditorNote);


        Glide.with(ProductDetail.this).load(productImage).into(image);

    }


    private void handleButton(){

        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClicked(view);
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClicked(view);
            }
        });

        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClicked(view);
            }
        });

        xlarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClicked(view);
            }
        });

        xxlarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClicked(view);
            }
        });

    }


    private void handleButtonClicked(View view){

        clearSelection();
        int id = view.getId();

        if(id == R.id.Small){
            // Set the background resource and text color for the clicked button
            changetextcolor(view);
            // Adding product to cart instance and setting the size to its respective size
            addProductCart("S");

        }else if(id == R.id.Medium){
            // Set the background resource and text color for the clicked button
            changetextcolor(view);
            // Adding product to cart instance and setting the size to its respective size
            addProductCart("M");

        }else if(id == R.id.Large){
            // Set the background resource and text color for the clicked button
            changetextcolor(view);
            // Adding product to cart instance and setting the size to its respective size
            addProductCart("L");

        }else if(id == R.id.XLarge){
            // Set the background resource and text color for the clicked button
            changetextcolor(view);
            // Adding product to cart instance and setting the size to its respective size
            addProductCart("XL");

        }else if(id == R.id.XXLarge){
            // Set the background resource and text color for the clicked button
            changetextcolor(view);
            // Adding product to cart instance and setting the size to its respective size
            addProductCart("XXL");

        }

    }


    // a method to add product to the cart
    void addProductCart(String size){

            bag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                            cartSingleton = CartSingleton.getCartProductInstance();

                            // Flag to check if the product is already in the cart
                            boolean isExist = false;
                            // Check if the cart already contains the product
                            for (ProductOrder product : cartSingleton.getProducts()) {
                                if (product.getBrandName().equals(productName) && product.getSize().equals(size)) {
                                 // Product exists, increase the quantity
                                   int totalqty = product.getQty() + 1;
                                   product.setQty(totalqty);
                                   isExist = true;
                                   break;
                                }
                            }

                            // Check if product is not exists then add to cartSingleton
                            if (!isExist) {
                                 cartSingleton.addProduct(new ProductOrder(productName, productDescription, productPrice, productImage, size, 1));
                                 System.out.println("\n\nProduct hasnt been added yet, adding it now\n\n");
                             }

                             System.out.println("Product Qty Increased\n");
                            for (ProductOrder productOrder : cartSingleton.getProducts()) {
                                  System.out.println(productOrder.toString());
                            }

                    }catch(NullPointerException e){
                         e.printStackTrace();
                         Toast.makeText(ProductDetail.this, "Please select size to add!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }



    public void setQty(){

        plus  = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartSingleton.getProducts();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }






    // a method to change the text color when selected
    void changetextcolor(View view){

        view.setBackgroundResource(R.drawable.button_selected);

        Button Btn = (Button) view;
        Btn.setTextColor(getResources().getColor(R.color.white));

    }

    // a method to change non clicked button to normal state
    void clearSelection(){

        small.setBackgroundResource(R.drawable.button_normal);
        medium.setBackgroundResource(R.drawable.button_normal);
        large.setBackgroundResource(R.drawable.button_normal);
        xlarge.setBackgroundResource(R.drawable.button_normal);
        xxlarge.setBackgroundResource(R.drawable.button_normal);


        // call this method to changeTextColor() on any button
        changeTextColor(small);  // Changes text color of small button
        changeTextColor(medium); // Changes text color of medium button
        changeTextColor(large); // Changes text color of large button
        changeTextColor(xlarge); // Changes text color of xlarge button
        changeTextColor(xxlarge); // Changes text color of xxlarge button
    }

    // FOR CHANGING THE TEXT WHEN CLICKED AND NOT CLICKED
    void changeTextColor(Button b) {
        b.setTextColor(getResources().getColor(R.color.black));
    }

















    // FOR DEBUGGING PURPOSE
    void show(){

        CartSingleton cartSingleton = CartSingleton.getCartProductInstance();

        for (ProductOrder order: cartSingleton.getProducts()){
            System.out.println("lists order: \n" + order.toString());
        }

    }


}
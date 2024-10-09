package com.example.dt.AUTH.View_Model;

import com.example.dt.AUTH.Models.ProductOrder;

import java.util.ArrayList;
import java.util.List;

public class CartSingleton {

    private static CartSingleton instance;
    private List<ProductOrder> productOrders;

    private CartSingleton(){
        productOrders = new ArrayList<>();
    }

    public static CartSingleton getCartProductInstance(){
        if(instance == null){
            instance = new CartSingleton();
        }
        return instance;
    }

    public void addProduct(ProductOrder product){
        productOrders.add(product);
    }

    public List<ProductOrder> getProducts() {
        return productOrders;
    }

    public double getTotalAmount(){
        double total = 0.00;

        for(ProductOrder productOrder: productOrders){
            total += productOrder.getQty() * Double.valueOf(productOrder.getPrice());
        }

        return total;
    }

}

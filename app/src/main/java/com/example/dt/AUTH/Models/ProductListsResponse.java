package com.example.dt.AUTH.Models;

import java.util.ArrayList;
import java.util.List;

public class ProductListsResponse  {


    private ArrayList<CategoryProduct> categoryProducts;

    public ArrayList<CategoryProduct> getCategoryProducts() {
        return categoryProducts;
    }

    @Override
    public String toString() {
        return "ProductListsResponse{" +
                "categoryProducts=" + categoryProducts +
                '}';
    }
}

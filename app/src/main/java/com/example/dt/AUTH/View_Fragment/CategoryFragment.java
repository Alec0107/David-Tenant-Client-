package com.example.dt.AUTH.View_Fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dt.AUTH.Models.CategoryProduct;
import com.example.dt.AUTH.Models.Executor;
import com.example.dt.AUTH.Models.ProductListsResponse;
import com.example.dt.AUTH.Requests.ProductRequest;
import com.example.dt.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class CategoryFragment extends Fragment {

    CardView tshirtCard, jeanCard, jacketCard, shortCard;

    private Future<ProductListsResponse> future;
    private ExecutorService executorService;
    private HashMap<String, ArrayList<CategoryProduct>> categoryProductMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_category, container, false);
        executorService = Executor.getExecutorService();


        tshirtCard = rootView.findViewById(R.id.TshirtCard);
        jeanCard   = rootView.findViewById(R.id.JeansCard);
        jacketCard = rootView.findViewById(R.id.JacketCard);
        shortCard  = rootView.findViewById(R.id.ShortCard);

        try {

            getCategoryProduct();

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        openFragment();

        return rootView;
    }

    public void getCategoryProduct() throws ExecutionException, InterruptedException {

        // Submit the task to retrieve category products
        future = executorService.submit(new ProductRequest());

        // Wait for the result
        ProductListsResponse productListsResponse = future.get();

        // Initialize the category-product map with ArrayList to hold multiple products per category
        categoryProductMap = new HashMap<>();

        // Loop through the product list and group them by category
        for(int i = 0; i < productListsResponse.getCategoryProducts().size(); i++){

            String catName = productListsResponse.getCategoryProducts().get(i).getCategory_name();
            CategoryProduct categoryProduct = productListsResponse.getCategoryProducts().get(i);

            // Check if the category already exists in the map
            if(!categoryProductMap.containsKey(catName)){

                // If it doesn't exist, create a new list for this category
                categoryProductMap.put(catName, new ArrayList<>());
            }

            // Add the product to the corresponding category's list
            categoryProductMap.get(catName).add(categoryProduct);


        }

        // Print out the products grouped by category
        for (String key : categoryProductMap.keySet()) {
            System.out.println("Category: " + key);
            for(CategoryProduct product: categoryProductMap.get(key)){
                System.out.println(" - Product: " + product); // Adjust based on your product's toString() method
            }
        }

    }


















    public void openFragment(){


        tshirtCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new instance of your fragment
                TshirtFragment tshirtFragment = new TshirtFragment();

                // Create a Bundle to hold the data
                Bundle bundle = new Bundle();

                if(categoryProductMap.containsKey("T-Shirts")){
                    ArrayList<CategoryProduct> tshirt = categoryProductMap.get("T-Shirts");
                    bundle.putSerializable("tshirtProducts", tshirt);
                }

                // Set the arguments for the fragment
                tshirtFragment.setArguments(bundle);

                startFragmentCategory(tshirtFragment);
            }
        });

        jacketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new instance of your fragment
                JacketFragment jacketFragment = new JacketFragment();

                // Create a Bundle to hold the data
                Bundle bundle = new Bundle();

                if(categoryProductMap.containsKey("Jackets")){
                    ArrayList<CategoryProduct> jacket = categoryProductMap.get("Jackets");
                    bundle.putSerializable("jacketProducts", jacket);
                }

                // Set the arguments for the fragment
                jacketFragment.setArguments(bundle);
                startFragmentCategory(jacketFragment);
            }
        });


        jeanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new instance of your fragment
               JeansFragment jeansFragment = new JeansFragment();

                // Create a Bundle to hold the data
                Bundle bundle = new Bundle();

                if(categoryProductMap.containsKey("Jeans")){
                    ArrayList<CategoryProduct> jeans = categoryProductMap.get("Jeans");
                    bundle.putSerializable("jeanProducts", jeans);
                }

                // Set the arguments for the fragment
                jeansFragment.setArguments(bundle);
                startFragmentCategory(jeansFragment);
            }
        });

        shortCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new instance of your fragment
                ShortFragment shortFragment = new ShortFragment();

                // Create a Bundle to hold the data
                Bundle bundle = new Bundle();

                if(categoryProductMap.containsKey("Shorts")){
                    ArrayList<CategoryProduct> shorts = categoryProductMap.get("Shorts");
                    bundle.putSerializable("shortProducts", shorts);
                }

                // Set the arguments for the fragment
                shortFragment.setArguments(bundle);
                startFragmentCategory(shortFragment);
            }
        });

    }

    public void startFragmentCategory(Fragment selectedFragment){

        if (selectedFragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.Fragment_container, selectedFragment, null)
                    .addToBackStack(null)
                    .commit();

        }
    }





}
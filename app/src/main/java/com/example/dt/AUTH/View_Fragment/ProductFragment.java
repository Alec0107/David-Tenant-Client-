package com.example.dt.AUTH.View_Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dt.AUTH.Models.CategoryProduct;
import com.example.dt.AUTH.Models.Executor;
import com.example.dt.AUTH.Models.ProductListsResponse;
import com.example.dt.AUTH.Requests.ProductRequest;
import com.example.dt.AUTH.View_Activity.ProductDetail;
import com.example.dt.AUTH.View_Model.RecyclerAdapter;
import com.example.dt.R;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ProductFragment extends Fragment implements RecyclerAdapter.OnItemClickListener{

    private View rootView;
    private RecyclerView recyclerView;
    private ProductListsResponse productListsResponse;
    private ExecutorService executorService;
    private Future<ProductListsResponse> future;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_product, container, false);

        executorService =  Executor.getExecutorService(); // Initialize ExecutorService

        getProducts(); // Fetch data when the view is created

        return rootView;
    }



    public void getProducts(){

        recyclerView = rootView.findViewById(R.id.RecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        future = executorService.submit(new ProductRequest());

        class ProductClassGet implements Runnable{

            @Override
            public void run() {

                try {
                    productListsResponse = future.get();

                    if(getActivity() != null){

                       getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(productListsResponse != null){
                                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(productListsResponse.getCategoryProducts(), getContext(), ProductFragment.this);
                                    recyclerView.setAdapter(recyclerAdapter);
                                }else{
                                    System.out.println("Product Lists is empty");
                                }
                            }
                        });
                    }


                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }



        } executorService.execute(new ProductClassGet());
    } // get products


    @Override
    public void setOnClickItem(CategoryProduct categoryProduct) {

        Intent productDetail = new Intent(getContext(), ProductDetail.class);

        productDetail.putExtra("PName", categoryProduct.getBrand_name());
        productDetail.putExtra("PDescription", categoryProduct.getDescription());
        productDetail.putExtra("PPrice", String.valueOf(categoryProduct.getPrice()));
        productDetail.putExtra("PEditorsNote", categoryProduct.getEditor_note());
        productDetail.putExtra("PImage", categoryProduct.getImage_url());

        startActivity(productDetail);
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        Executor.makeShutdown();
    }

}
package com.example.dt.AUTH.View_Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dt.AUTH.Models.CategoryProduct;
import com.example.dt.AUTH.View_Activity.ProductDetail;
import com.example.dt.AUTH.View_Model.RecyclerAdapter;
import com.example.dt.R;

import java.util.ArrayList;


public class TshirtFragment extends Fragment implements RecyclerAdapter.OnItemClickListener{

    private View rootView;
    private TextView header1, header2;
    private RecyclerView recyclerView;
    private ArrayList<CategoryProduct> tshirtProducts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            // type casting and get the data from host fragment to this fragment (Tshirt Fragment)
            tshirtProducts = (ArrayList<CategoryProduct>) getArguments().getSerializable("tshirtProducts");

            for(int i = 0; i < tshirtProducts.size(); i ++){
                Log.d("TSHIRT FRAGMENT", tshirtProducts.get(i).toString());
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product, container, false);

        header1 = rootView.findViewById(R.id.header1);
        header1.setText("CATEGORIES / T-SHIRT");

        displayProducts();

        // Inflate the layout for this fragment
        return rootView;
    }


    public void displayProducts(){

        recyclerView = rootView.findViewById(R.id.RecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(tshirtProducts != null){

                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(tshirtProducts, getContext(),TshirtFragment.this);
                    recyclerView.setAdapter(recyclerAdapter);
                }else{
                    Log.d("Tshirt Product", "Tshirt Category lists is empty");
                }
            }
        });

    }


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
}
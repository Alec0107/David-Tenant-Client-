package com.example.dt.AUTH.View_Fragment;

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
import com.example.dt.AUTH.View_Model.RecyclerAdapter;
import com.example.dt.R;

import java.util.ArrayList;


public class JeansFragment extends Fragment implements RecyclerAdapter.OnItemClickListener {

    private View rootView;
    private RecyclerView recyclerView;
    private ArrayList<CategoryProduct> jeanProducts;
    private TextView header1, header2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // type casting and get the data from host fragment to this fragment (Tshirt Fragment)
            jeanProducts = (ArrayList<CategoryProduct>) getArguments().getSerializable("jeanProducts");

            for(int i = 0; i < jeanProducts.size(); i ++){
                Log.d("JEANS FRAGMENT", jeanProducts.get(i).toString());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.fragment_product, container, false);

         header1 = rootView.findViewById(R.id.header1);
         header1.setText("CATEGORIES / JEANS");

         displayProducts();


         return rootView;
    }



    public void displayProducts(){

        recyclerView = rootView.findViewById(R.id.RecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(jeanProducts, getContext(), JeansFragment.this);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });


    }


    @Override
    public void setOnClickItem(CategoryProduct categoryProduct) {

    }
}
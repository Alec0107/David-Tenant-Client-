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

public class JacketFragment extends Fragment implements RecyclerAdapter.OnItemClickListener {

    private View rootView;
    private RecyclerView recyclerView;
    private ArrayList<CategoryProduct> jacketProducts;
    private TextView header1, header2;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // type casting and get the data from host fragment to this fragment (Tshirt Fragment)
           jacketProducts = (ArrayList<CategoryProduct>) getArguments().getSerializable("jacketProducts");

            for(int i = 0; i < jacketProducts.size(); i ++){
                Log.d("JACKET FRAGMENT", jacketProducts.get(i).toString());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product, container, false);

        header1 = rootView.findViewById(R.id.header1);
        header1.setText("CATEGORIES / JACKETS");

        displayProduct();

        return rootView;
    }




    public void displayProduct(){

        recyclerView = rootView.findViewById(R.id.RecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(jacketProducts, getContext(), JacketFragment.this);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });


    }


    @Override
    public void setOnClickItem(CategoryProduct categoryProduct) {

    }
}
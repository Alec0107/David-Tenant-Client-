package com.example.dt.AUTH.View_Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dt.AUTH.Models.CategoryProduct;
import com.example.dt.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private List<CategoryProduct> categoryProducts;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecyclerAdapter(List<CategoryProduct> categoryProducts, Context context, OnItemClickListener onItemClickListener) {
        this.categoryProducts = categoryProducts;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_one_line, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

        CategoryProduct categoryProduct = categoryProducts.get(position);

        String name      = categoryProduct.getBrand_name();
        String price     = String.valueOf(categoryProduct.getPrice());
        String image_url = categoryProduct.getImage_url();

        holder.name.setText(name);
        holder.price.setText("$" + price + "0");
        Glide.with(context).load(image_url).into(holder.imag_url);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.setOnClickItem(categoryProduct);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryProducts.size();
    }

    // ViewHolder class to hold item views
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, price;
        ImageView imag_url;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name     = itemView.findViewById(R.id.NameProduct);
            price    = itemView.findViewById(R.id.PriceProduct);
            imag_url = itemView.findViewById(R.id.ImageProduct);

        }
    }

    public interface OnItemClickListener{

        void setOnClickItem(CategoryProduct categoryProduct);

    }

}

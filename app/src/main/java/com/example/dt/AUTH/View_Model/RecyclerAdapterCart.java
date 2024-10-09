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
import com.example.dt.AUTH.Models.ProductOrder;
import com.example.dt.R;

import java.util.List;

public class RecyclerAdapterCart extends RecyclerView.Adapter<RecyclerAdapterCart.ViewHolder> {

    private Context context;
    private List<ProductOrder> productOrderList;
    private OnQtyChangeListener onQtyChangeListener;

    public RecyclerAdapterCart(Context context, List<ProductOrder> productOrderList, OnQtyChangeListener onQtyChangeListener){
        this.context = context;
        this.productOrderList = productOrderList;
        this.onQtyChangeListener = onQtyChangeListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(context).inflate(R.layout.cart_one_line, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductOrder productOrder = productOrderList.get(position);

        String name  = productOrder.getBrandName();
        String price = productOrder.getPrice();
        String size  = productOrder.getSize();
        String qty   = String.valueOf(productOrder.getQty());


        holder.name.setText(name);
        holder.price.setText("$" + price + "0");
        holder.size.setText("Size: " + size);
        holder.qty.setText("0" + qty);
        Glide.with(context).load(productOrder.getImage_url()).into(holder.image);

        // click listener for increasing the product qty in cart
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(productOrder.getQty() < 9){
                    productOrder.setQty(productOrder.getQty() + 1);
                    holder.qty.setText(String.valueOf( "0" + productOrder.getQty()));
                    onQtyChangeListener.onIncreaseQty(productOrder);
                }else{
                    productOrder.setQty(productOrder.getQty() + 1);
                    holder.qty.setText(String.valueOf( productOrder.getQty()));
                    onQtyChangeListener.onIncreaseQty(productOrder);
                }

            }
        });

        // click listener for decreasing the product qty in cart
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 if(productOrder.getQty() > 1) {

                     if (productOrder.getQty() > 10 ) {
                            productOrder.setQty(productOrder.getQty() - 1);
                            holder.qty.setText(String.valueOf(productOrder.getQty()));
                            onQtyChangeListener.onDecreaseQty(productOrder);
                        }else{
                            productOrder.setQty(productOrder.getQty() - 1);
                            holder.qty.setText(String.valueOf(("0" + productOrder.getQty())));
                            onQtyChangeListener.onDecreaseQty(productOrder);
                        }

                    }

            }
        });

        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartSingleton cartSingleton = CartSingleton.getCartProductInstance();
                for(ProductOrder p: cartSingleton.getProducts()){
                    System.out.println(p);
                }

            }
        });


    }

    @Override
    public int getItemCount() {

        return productOrderList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image, plus, minus, trash;
        private TextView name, price, size, qty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name  = itemView.findViewById(R.id.NameProduct);
            price = itemView.findViewById(R.id.PriceProduct);
            size  = itemView.findViewById(R.id.SizeProduct);
            qty   = itemView.findViewById(R.id.QtyTxt);
            image = itemView.findViewById(R.id.ImageProduct);
            plus  = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            trash = itemView.findViewById(R.id.Trash);

        }

    }

    public interface OnQtyChangeListener{

        void onIncreaseQty(ProductOrder productOrder);
        void onDecreaseQty(ProductOrder productOrder);

    }


}

package com.example.milkteastore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteastore.R;
import com.example.milkteastore.controller.Home.DetailsActivity;
import com.example.milkteastore.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private final Context context;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products != null ? products : new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        if (product != null) {
            holder.tvName.setText(product.getName() != null ? product.getName() : "Unnamed Product");
            holder.tvPrice.setText(String.format("₱ %.2f", product.getPrice()));

            // Ánh xạ tên file ảnh thành resource ID
            int imageResId = R.drawable.milktea_1; // Fallback tạm thời
            String imageName = product.getImage();
            if (imageName != null && !imageName.isEmpty()) {
                int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                imageResId = resId != 0 ? resId : R.drawable.milktea_1;
            }
            holder.imgProduct.setImageResource(imageResId);

            // Sử dụng final local variable trong lambda
            final int finalImageResId = imageResId;
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("ITEM_ID", product.getId());
                intent.putExtra("ITEM_NAME", product.getName() != null ? product.getName() : "");
                intent.putExtra("ITEM_PRICE", String.valueOf(product.getPrice()));
                intent.putExtra("ITEM_IMAGE", finalImageResId);
                intent.putExtra("ITEM_DESCRIPTION", product.getDescription() != null ? product.getDescription() : "");
                intent.putExtra("ITEM_SIZE", product.getSize() != null ? product.getSize() : "");
                intent.putExtra("ITEM_QUANTITY", product.getQuantity()); // Giả định int, không cần null check
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public void updateData(List<Product> newProducts) {
        products = newProducts != null ? new ArrayList<>(newProducts) : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}
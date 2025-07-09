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

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private Context context;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        this.context = parent.getContext(); // Lưu context từ parent
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format("₱ %.2f", product.getPrice()));

        // Ánh xạ tên file ảnh thành resource ID
        String imageName = product.getImage();
        if (!imageName.isEmpty()) {
            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.imgProduct.setImageResource(resId);
            } else {
                holder.imgProduct.setImageResource(R.drawable.milktea_1); // Fallback nếu không tìm thấy
            }
        } else {
            holder.imgProduct.setImageResource(R.drawable.milktea_1); // Fallback nếu image rỗng
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailsActivity.class);
            intent.putExtra("ITEM_ID", product.getId());
            intent.putExtra("ITEM_NAME", product.getName());
            intent.putExtra("ITEM_PRICE", String.valueOf(product.getPrice()));
            intent.putExtra("ITEM_IMAGE", !imageName.isEmpty() ? context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()) : R.drawable.milktea_1);
            intent.putExtra("ITEM_DESCRIPTION", product.getDescription());
            intent.putExtra("ITEM_SIZE", product.getSize());
            intent.putExtra("ITEM_QUANTITY", product.getQuantity());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateData(List<Product> newProducts) {
        products.clear();
        products.addAll(newProducts);
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
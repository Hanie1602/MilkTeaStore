package com.example.milkteastore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteastore.R;
import com.example.milkteastore.controller.Cart.ChooseToppingActivity;
import com.example.milkteastore.model.CartItem;
import com.example.milkteastore.model.Topping;
import com.example.milkteastore.utils.CartManager;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<CartItem> cartItems;
    private final Context context;
    private final Runnable updateSubtotalCallback;

    private final Runnable updateCartBadgeCallback;

    public CartAdapter(Context context, List<CartItem> cartItems, Runnable updateSubtotalCallback, Runnable updateCartBadgeCallback) {
        this.context = context;
        this.cartItems = cartItems;
        this.updateSubtotalCallback = updateSubtotalCallback;
        this.updateCartBadgeCallback = updateCartBadgeCallback;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        int imageResId = item.getImage(); // Nếu ảnh là resource ID
        holder.imgProduct.setImageResource(imageResId);

        holder.tvName.setText(item.getName());
        holder.tvDesc.setText("Size: " + item.getSize());

        // ✅ Tính tổng tiền sản phẩm + topping
        double basePrice = Double.parseDouble(item.getPrice());
        double toppingTotal = 0.0;
        StringBuilder toppingNames = new StringBuilder();

        if (item.getToppings() != null && !item.getToppings().isEmpty()) {
            for (Topping topping : item.getToppings()) {
                toppingTotal += topping.getPrice();
                toppingNames.append(topping.getName()).append(", ");
            }

            // Xóa dấu phẩy cuối
            if (toppingNames.length() > 2) {
                toppingNames.setLength(toppingNames.length() - 2);
            }

            holder.tvToppings.setText("Topping: " + toppingNames);
        } else {
            holder.tvToppings.setText("No toppings");
        }

// ✅ Hiển thị giá tổng của 1 sản phẩm (base + topping)
        double totalPerUnit = basePrice + toppingTotal;
        holder.tvPrice.setText(String.format("$%.2f", totalPerUnit));

        holder.tvPrice.setText("$" + item.getPrice());
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        holder.btnIncrease.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
            updateSubtotalCallback.run();
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
            } else {
                CartManager.getInstance().removeItem(item);
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
                updateCartBadgeCallback.run();
            }
            updateSubtotalCallback.run();

        });

        holder.btnRemove.setOnClickListener(v -> {
            CartManager.getInstance().removeItem(item);
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            updateSubtotalCallback.run();
            updateCartBadgeCallback.run();
        });

        // Xử lý nút Add Topping
        holder.btnAddTopping.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChooseToppingActivity.class);
            intent.putExtra("productId", item.getProductId());
            intent.putExtra("cartIndex", position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvDesc, tvPrice, tvQuantity;
        Button btnIncrease, btnDecrease;
        ImageButton btnRemove, btnAddTopping;
        TextView tvToppings;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnAddTopping = itemView.findViewById(R.id.btnAddTopping);

            tvToppings = itemView.findViewById(R.id.tvToppings);
        }
    }
}

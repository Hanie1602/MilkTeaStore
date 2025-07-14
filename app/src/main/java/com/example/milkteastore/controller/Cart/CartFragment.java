package com.example.milkteastore.controller.Cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.milkteastore.Adapter.CartAdapter;
import com.example.milkteastore.databinding.FragmentCartBinding;
import com.example.milkteastore.model.CartItem;
import com.example.milkteastore.model.Topping;
import com.example.milkteastore.utils.CartManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartAdapter adapter;
    private List<CartItem> cartItems;
    private int currentUserId;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUserId = requireContext()
                .getSharedPreferences("USER_SESSION", getContext().MODE_PRIVATE)
                .getInt("USER_ID", -1);

        cartItems = CartManager.getInstance()
                .getCartItems()
                .stream()
                .filter(item -> item.getUserId() == currentUserId)
                .collect(Collectors.toList());

        adapter = new CartAdapter(getContext(), cartItems, this::updateSubtotal);
        binding.rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCart.setAdapter(adapter);

        updateSubtotal();

        binding.btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CheckoutActivity.class);
            intent.putExtra("TOTAL_AMOUNT", CartManager.getInstance().calculateTotal(currentUserId));
            intent.putExtra("CART_LIST", new ArrayList<>(cartItems));
            startActivity(intent);
        });
    }

    private void updateSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : cartItems) {
            double basePrice = Double.parseDouble(item.getPrice());
            double toppingTotal = 0.0;
            if (item.getToppings() != null) {
                for (Topping topping : item.getToppings()) {
                    toppingTotal += topping.getPrice();
                }
            }
            subtotal += (basePrice + toppingTotal) * item.getQuantity();
        }
        binding.tvSubtotal.setText(String.format("$%.2f", subtotal));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) adapter.notifyDataSetChanged();
        updateSubtotal();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

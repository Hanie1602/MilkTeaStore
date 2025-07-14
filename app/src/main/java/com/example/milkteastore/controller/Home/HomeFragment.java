package com.example.milkteastore.controller.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteastore.Adapter.ProductAdapter;
import com.example.milkteastore.Adapter.RecommendationAdapter;
import com.example.milkteastore.R;
import com.example.milkteastore.controller.Cart.CartFragment;
import com.example.milkteastore.controller.Profile.ProfileFragment;
import com.example.milkteastore.dao.CategoryDAO;
import com.example.milkteastore.dao.ProductDAO;
import com.example.milkteastore.databinding.FragmentHomeBinding;
import com.example.milkteastore.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProductDAO productRepository;
    private CategoryDAO categoryDAO;
    private List<Product> allProducts;
    private List<Product> filteredProducts;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productRepository = new ProductDAO(requireContext());
        categoryDAO = new CategoryDAO(requireContext());

        allProducts = productRepository.getAllProducts();
        if (allProducts == null) allProducts = new ArrayList<>();
        filteredProducts = new ArrayList<>(allProducts);

        // Most Popular
        RecyclerView rvMostPopular = binding.rvMostPopular;
        rvMostPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ProductAdapter mostPopularAdapter = new ProductAdapter(getContext(), filteredProducts);
        rvMostPopular.setAdapter(mostPopularAdapter);

        // Recommendation
        RecyclerView rvRecommendation = binding.rvRecommendation;
        rvRecommendation.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(getTopProducts(5));
        rvRecommendation.setAdapter(recommendationAdapter);

        // Search
        EditText etSearch = binding.etSearch;
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        // Shop Icon
        if (binding.ivShopIcon != null) {
            binding.ivShopIcon.setOnClickListener(v -> {
                double latitude = 10.8506;
                double longitude = 106.7544;
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(Cửa hàng MilkTea)"));
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude)));
                }
            });
        }
        // Chat
        ImageView ivChat = binding.ivChat;
        ivChat.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đã bấm chat", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), com.example.milkteastore.controller.Chat.ChatActivity.class);
            startActivity(intent);
        });
    }

    private void filterProducts(String query) {
        filteredProducts.clear();
        if (query.isEmpty()) {
            filteredProducts.addAll(allProducts);
        } else {
            String searchQuery = query.toLowerCase();
            for (Product product : allProducts) {
                if (product != null && product.getName() != null && product.getName().toLowerCase().contains(searchQuery)) {
                    filteredProducts.add(product);
                }
            }
        }
        ProductAdapter adapter = (ProductAdapter) binding.rvMostPopular.getAdapter();
        if (adapter != null) {
            adapter.updateData(filteredProducts);
        }
    }

    private List<Product> getTopProducts(int count) {
        List<Product> topProducts = new ArrayList<>(allProducts);
        Collections.sort(topProducts, (p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
        return topProducts.size() > count ? topProducts.subList(0, count) : topProducts;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

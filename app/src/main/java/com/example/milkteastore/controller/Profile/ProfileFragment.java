package com.example.milkteastore.controller.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.milkteastore.R;
import com.example.milkteastore.controller.LoginRegister.LoginActivity;

public class ProfileFragment extends Fragment {

    private ImageView imgAvatar;
    private TextView tvName, tvEmail;
    private LinearLayout rowProfile, rowOrderProduct, rowLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imgAvatar = view.findViewById(R.id.imgAvatar);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        rowProfile = view.findViewById(R.id.rowProfile);
        rowOrderProduct = view.findViewById(R.id.rowOrderProduct);
        rowLogout = view.findViewById(R.id.rowLogout);

        ((TextView) rowProfile.findViewById(R.id.tvTitle)).setText("Profile");
        ((TextView) rowOrderProduct.findViewById(R.id.tvTitle)).setText("History Order");
        ((TextView) rowLogout.findViewById(R.id.tvTitle)).setText("Logout");

        ((ImageView) rowProfile.findViewById(R.id.imgIcon)).setImageResource(R.drawable.ic_profile);
        ((ImageView) rowOrderProduct.findViewById(R.id.imgIcon)).setImageResource(R.drawable.ic_profile);
        ((ImageView) rowLogout.findViewById(R.id.imgIcon)).setImageResource(R.drawable.ic_logout);

        SharedPreferences prefs = requireContext().getSharedPreferences("USER_SESSION", getContext().MODE_PRIVATE);
        String name = prefs.getString("USER_NAME", "Unknown User");
        String email = prefs.getString("USER_EMAIL", "unknown@example.com");

        tvName.setText(name);
        tvEmail.setText(email);

        rowProfile.setOnClickListener(view1 -> {
            // Mở chi tiết Profile nếu có
        });

        rowOrderProduct.setOnClickListener(view1 -> {
            startActivity(new Intent(requireContext(), OrderHistoryActivity.class));
        });

        rowLogout.setOnClickListener(view1 -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}

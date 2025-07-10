package com.example.milkteastore.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.*;
import android.widget.*;

import com.example.milkteastore.R;
import com.example.milkteastore.model.Order;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> list) {
        super(context, 0, list);
        this.context = context;
        this.orderList = list;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = orderList.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_order_row, parent, false);

        TextView tvCode = convertView.findViewById(R.id.tvOrderCode);
        TextView tvDate = convertView.findViewById(R.id.tvOrderDate);
        TextView tvTotal = convertView.findViewById(R.id.tvOrderTotal);

        tvCode.setText("Code: " + order.code);
        tvDate.setText("Date: " + order.orderDate);
        tvTotal.setText("Total: $" + order.totalPrice);

        return convertView;
    }
}

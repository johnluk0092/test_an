package vn.edu.hutech.computerstore.ui.home.order.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.model.order.OrderUnit;
import vn.edu.hutech.computerstore.utils.DrawableUtils;

public class OrderListDetailAdapter extends RecyclerView.Adapter<OrderListDetailItemViewHolder> {

    private final Context context;
    private List<OrderUnit> cartUnits;

    public OrderListDetailAdapter(Context context) {
        this.context = context;
        cartUnits = new ArrayList<>();
    }

    public void setOrderUnit(List<OrderUnit> cartUnits) {
        this.cartUnits = cartUnits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderListDetailItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.layout_order_list_detail_item, parent, false);
        return new OrderListDetailItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListDetailItemViewHolder holder, int position) {
        final OrderUnit orderUnit = cartUnits.get(position);
        Glide.with(holder.itemView)
             .load(orderUnit.getProductImagePath())
             .placeholder(DrawableUtils.getCircularProgress(context))
             .into(holder.getIvProductImage());

        holder.getTvProductName().setText(orderUnit.getProductName());
        holder.getTvPrice().setText(String.format(Locale.US, "%,d", orderUnit.getPrice()));
        holder.getTvQuantity().setText(String.format(Locale.US, "%,d", orderUnit.getQuantity()));
        holder.itemView.setTag(orderUnit);
    }

    @Override
    public int getItemCount() {
        return cartUnits.size();
    }

}

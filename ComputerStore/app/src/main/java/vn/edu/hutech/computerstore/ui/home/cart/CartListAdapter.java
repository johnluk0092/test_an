package vn.edu.hutech.computerstore.ui.home.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.model.cart.CartUnit;
import vn.edu.hutech.computerstore.model.product.Product;
import vn.edu.hutech.computerstore.utils.DrawableUtils;
import vn.edu.hutech.computerstore.utils.ProductNumberUtils;

public class CartListAdapter extends RecyclerView.Adapter<CartListItemViewHolder> {

    private final Context context;
    private List<CartUnit> cartUnits;

    private View.OnClickListener onRemoveProductClickListener;
    private View.OnClickListener onIncreaseQuantityClickListener;
    private View.OnClickListener onDecreaseQuantityClickListener;

    public CartListAdapter(Context context) {
        this.context = context;
        cartUnits = new ArrayList<>();
    }

    public void setOnRemoveProductClickListener(View.OnClickListener onRemoveProductClickListener) {
        this.onRemoveProductClickListener = onRemoveProductClickListener;
    }

    public void setOnIncreaseQuantityClickListener(View.OnClickListener onIncreaseQuantityClickListener) {
        this.onIncreaseQuantityClickListener = onIncreaseQuantityClickListener;
    }

    public void setOnDecreaseQuantityClickListener(View.OnClickListener onDecreaseQuantityClickListener) {
        this.onDecreaseQuantityClickListener = onDecreaseQuantityClickListener;
    }

    public void setCartUnits(List<CartUnit> cartUnits) {
        this.cartUnits = cartUnits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.layout_cart_list_item, parent, false);
        return new CartListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListItemViewHolder holder, int position) {
        final CartUnit cartUnit = cartUnits.get(position);
        final Product product = Objects.isNull(cartUnit.getProduct()) ? new Product() : cartUnit.getProduct();
        Glide.with(holder.itemView)
             .load(product.getImagePath())
             .placeholder(DrawableUtils.getCircularProgress(context))
             .into(holder.getIvProductImage());

        holder.getTvProductName().setText(product.getName());
        holder.getTvPrice().setText(ProductNumberUtils.toCurrencyVND(product.getPrice()));
        holder.getTvQuantity().setText(ProductNumberUtils.toNumberWithThousandSplit(cartUnit.getQuantity()));
        holder.getBtnMinus().setOnClickListener(v -> {
            cartUnit.decreaseQuantity(1);
            holder.getTvQuantity().setText(ProductNumberUtils.toNumberWithThousandSplit(cartUnit.getQuantity()));
            onDecreaseQuantityClickListener.onClick(holder.itemView);
        });
        holder.getBtnAdd().setOnClickListener(v -> {
            cartUnit.increaseQuantity(1);
            holder.getTvQuantity().setText(ProductNumberUtils.toNumberWithThousandSplit(cartUnit.getQuantity()));
            onIncreaseQuantityClickListener.onClick(holder.itemView);
        });
        holder.getBtnRemove().setOnClickListener(v -> {
            onRemoveProductClickListener.onClick(holder.itemView);
        });
        holder.itemView.setTag(cartUnit);
    }

    @Override
    public int getItemCount() {
        return cartUnits.size();
    }

}

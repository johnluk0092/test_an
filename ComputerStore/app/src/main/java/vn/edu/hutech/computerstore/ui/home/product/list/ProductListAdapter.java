package vn.edu.hutech.computerstore.ui.home.product.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.model.product.Product;
import vn.edu.hutech.computerstore.utils.DrawableUtils;
import vn.edu.hutech.computerstore.utils.ProductNumberUtils;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListViewHolder> {

    private final Context context;
    private List<Product> products;

    @Setter
    private View.OnClickListener onItemClickListener;

    @Setter
    private View.OnClickListener onCartClickListener;

    public ProductListAdapter(Context context) {
        this.context = context;
        this.products = new ArrayList<>();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.layout_product_list_item,
                                              parent,
                                              false);
        return new ProductListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        final Product product = products.get(position);
        Glide.with(context)
             .load(product.getImagePath())
             .placeholder(DrawableUtils.getCircularProgress(context))
             .into(holder.getIvProductImage());

        holder.getTvProductName().setText(product.getName());
        holder.getTvPrice().setText(ProductNumberUtils.toCurrencyVND(product.getPrice()));

        holder.itemView.setTag(product);
        holder.itemView.setOnClickListener(onItemClickListener);
        holder.getIbAddCart().setOnClickListener(v -> onCartClickListener.onClick(holder.itemView));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}

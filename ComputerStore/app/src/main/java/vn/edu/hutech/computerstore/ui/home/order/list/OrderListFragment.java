package vn.edu.hutech.computerstore.ui.home.order.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.databinding.FragmentOrderListBinding;
import vn.edu.hutech.computerstore.model.order.OrderDetail;
import vn.edu.hutech.computerstore.ui.base.LoginAccountManager;

@AndroidEntryPoint
public class OrderListFragment extends Fragment {

    private FragmentOrderListBinding binding;

    private OrderListViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(OrderListViewModel.class);

        final OrderListAdapter orderListAdapter = new OrderListAdapter(getContext());
        orderListAdapter.setOnClickItemListener(v -> {
            final OrderDetail orderDetail = (OrderDetail) v.getTag();
            final Bundle bundle = new Bundle();
            bundle.putSerializable("orderItem", orderDetail);
            Navigation.findNavController(view)
                      .navigate(R.id.action_nav_home_order_list_to_order_list_detail_fragment, bundle);
        });

        binding.orderListRvList.setAdapter(orderListAdapter);

        viewModel.getOrderState().observe(getViewLifecycleOwner(), order -> {
            if (Objects.isNull(order)) {
                return;
            }
            orderListAdapter.setOrderItems(order.getOrderDetails());
            orderListAdapter.notifyDataSetChanged();
        });

        viewModel.fetchOrders(LoginAccountManager.getInstance().getUserId());
    }

}
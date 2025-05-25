package vn.edu.hutech.computerstore.ui.home.order.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.hutech.computerstore.databinding.FragmentOrderListDetailBinding;
import vn.edu.hutech.computerstore.model.order.OrderDetail;

public class OrderListDetailFragment extends Fragment {

    private FragmentOrderListDetailBinding binding;

    private OrderListDetailViewModel viewModel;

    private OrderListDetailAdapter orderListDetailAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderListDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderListDetailAdapter = new OrderListDetailAdapter(getContext());
        binding.orderListDetailRvList.setAdapter(orderListDetailAdapter);

        viewModel = new ViewModelProvider(this).get(OrderListDetailViewModel.class);
        viewModel.getOrderItemState().observe(getViewLifecycleOwner(), orderItem -> {
            orderListDetailAdapter.setOrderUnit(orderItem.getOrderUnits());
        });

        viewModel.setOrderItemState((OrderDetail) getArguments().getSerializable("orderItem"));
    }

}
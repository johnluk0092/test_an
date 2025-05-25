package vn.edu.hutech.computerstore.ui.home.cart;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.databinding.FragmentCartListBinding;
import vn.edu.hutech.computerstore.model.cart.Cart;
import vn.edu.hutech.computerstore.model.cart.CartUnit;
import vn.edu.hutech.computerstore.ui.base.LoginAccountManager;
import vn.edu.hutech.computerstore.ui.home.MainActivityViewModel;
import vn.edu.hutech.computerstore.utils.ProductNumberUtils;

@AndroidEntryPoint
public class CartListFragment extends Fragment {

    private FragmentCartListBinding binding;
    private CartListViewModel viewModel;
    private CartListAdapter cartListAdapter;

    private MainActivityViewModel mainActivityViewModel;

    private void showRemoveCartDialog(Runnable removeCallback, Runnable cancelCallBack) {
        new AlertDialog.Builder(getContext())
                .setMessage(R.string.cart_list_alert_msg_remove_item)
                .setPositiveButton(R.string.all_yes, (dialog, which) -> removeCallback.run())
                .setNegativeButton(R.string.all_no, (dialog, which) -> cancelCallBack.run())
                .show();
    }

    private void bindingComponents() {
        cartListAdapter.setOnRemoveProductClickListener(v -> {
            final CartUnit cartUnit = (CartUnit) v.getTag();
            showRemoveCartDialog(() -> viewModel.removeProduct(LoginAccountManager.getInstance().getUserId(),
                                                               cartUnit.getProductId()),
                                 () -> {});
        });

        cartListAdapter.setOnIncreaseQuantityClickListener(v -> {
            final CartUnit cartUnit = (CartUnit) v.getTag();
            viewModel.updateCart(LoginAccountManager.getInstance().getUserId(), cartUnit);
        });

        cartListAdapter.setOnDecreaseQuantityClickListener(v -> {
            final CartUnit cartUnit = (CartUnit) v.getTag();
            if (cartUnit.getQuantity() <= 0) {
                showRemoveCartDialog(() -> {
                                         viewModel.removeProduct(LoginAccountManager.getInstance()
                                                                                    .getUserId(),
                                                                 cartUnit.getProductId());
                                         viewModel.updateCart(LoginAccountManager.getInstance().getUserId(), cartUnit);
                                     },
                                     () -> {
                                         v.findViewById(R.id.cart_list_item_btn_add).callOnClick();
                                         viewModel.updateCart(LoginAccountManager.getInstance().getUserId(), cartUnit);
                                     });
            } else {
                viewModel.updateCart(LoginAccountManager.getInstance().getUserId(), cartUnit);
            }
        });

        binding.cartListRvList.setAdapter(cartListAdapter);

        binding.cartListBtnCheckout.setOnClickListener(v -> {
            final EditText etAddress = new EditText(getContext());
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.cart_list_alert_address_title)
                    .setMessage(R.string.cart_list_alert_address_message)
                    .setView(etAddress)
                    .setPositiveButton(R.string.cart_list_checkout, (dialog, which) -> {
                        final Cart cart = viewModel.getCartState().getValue();
                        cart.setShipAddress(etAddress.getText().toString());
                        viewModel.order(LoginAccountManager.getInstance().getUserId(), viewModel.getCartState().getValue());
                    })
                    .setNegativeButton(R.string.all_cancel, null)
                    .show();
        });
    }

    private void observeStates() {
        viewModel.getCartState().observe(getViewLifecycleOwner(), cart -> {
            cartListAdapter.setCartUnits(cart.getCartUnits());
            binding.cartListItemTotalPrice.setText(ProductNumberUtils.toCurrencyVND(cart.getTotalPrice()));
            binding.cartListPaymentArea.setVisibility(cart.getCartUnits().isEmpty() ? View.INVISIBLE : View.VISIBLE);
        });

        viewModel.getCheckoutState().observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                mainActivityViewModel.setBottomTabIdState(R.id.nav_nested_graph_home_order_list);
            }

            Toast.makeText(getContext(),
                           result.isSuccess() ? R.string.cart_list_msg_checkout_success : R.string.cart_list_msg_checkout_fail,
                           Toast.LENGTH_SHORT)
                 .show();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        viewModel = new ViewModelProvider(this).get(CartListViewModel.class);
        cartListAdapter = new CartListAdapter(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeStates();
        bindingComponents();
        viewModel.fetchCart(LoginAccountManager.getInstance().getUserId());
    }
}
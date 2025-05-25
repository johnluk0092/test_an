package vn.edu.hutech.computerstore.ui.home.order.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.hutech.computerstore.model.order.OrderDetail;
import vn.edu.hutech.computerstore.ui.base.BaseViewModel;

@HiltViewModel
public class OrderListDetailViewModel extends BaseViewModel {
    private static final String LOG_TAG = OrderListDetailViewModel.class.getSimpleName();

    private final MutableLiveData<OrderDetail> orderItemState = new MutableLiveData<>();

    @Inject
    public OrderListDetailViewModel() {}

    public LiveData<OrderDetail> getOrderItemState() {
        return orderItemState;
    }

    public void setOrderItemState(OrderDetail orderDetail) {
        orderItemState.setValue(orderDetail);
    }

}
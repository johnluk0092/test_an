package vn.edu.hutech.computerstore.ui.home.order.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.hutech.computerstore.model.order.Order;
import vn.edu.hutech.computerstore.service.OrderService;
import vn.edu.hutech.computerstore.ui.base.BaseViewModel;

@HiltViewModel
public class OrderListViewModel extends BaseViewModel {

    private final MutableLiveData<Order> orderState = new MutableLiveData<>();

    private final OrderService orderService;

    @Inject
    public OrderListViewModel(OrderService orderService) {
        this.orderService = orderService;
    }

    public LiveData<Order> getOrderState() {
        return orderState;
    }

    public void fetchOrders(String userId) {
        orderService.fetchOrders(userId)
                    .addOnSuccessListener(orderState::setValue);
    }

}

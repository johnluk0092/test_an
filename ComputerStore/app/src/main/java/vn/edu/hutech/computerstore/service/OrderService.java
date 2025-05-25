package vn.edu.hutech.computerstore.service;

import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hutech.computerstore.common.ComputerStoreConstant;
import vn.edu.hutech.computerstore.model.cart.Cart;
import vn.edu.hutech.computerstore.model.order.Order;
import vn.edu.hutech.computerstore.model.order.OrderDetail;
import vn.edu.hutech.computerstore.model.order.OrderUnit;
import vn.edu.hutech.computerstore.repository.remote.CartRepository;
import vn.edu.hutech.computerstore.repository.remote.OrderRepository;

@Singleton
public class OrderService extends BaseService {

    private final DateFormat dateFormat = new SimpleDateFormat(ComputerStoreConstant.DATE_TIME_FORMAT, Locale.US);

    private OrderRepository orderRepository;
    private CartRepository cartRepository;

    @Inject
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public Task<Order> fetchOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Task<Void> add(String userId, Cart cart) {
        final List<OrderUnit> orderUnits = cart.getCartUnits()
                                               .stream()
                                               .map(c -> new OrderUnit(c.getProduct().getId(),
                                                                       c.getProduct().getImagePath(),
                                                                       c.getProduct().getName(),
                                                                       c.getQuantity(),
                                                                       c.getProduct().getPrice()))
                                               .collect(Collectors.toList());
        final String orderItemId = String.format("%s-%s", userId, UUID.randomUUID().toString().replace("-", ""));
        return orderRepository
                .save(userId, new OrderDetail(orderItemId, dateFormat.format(new Date()), orderUnits, cart.getShipAddress()))
                .onSuccessTask(c -> cartRepository.remove(userId));
    }

}

package vn.edu.hutech.computerstore.repository.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hutech.computerstore.model.order.Order;
import vn.edu.hutech.computerstore.model.order.OrderDetail;

@Singleton
public class OrderRepository {

    private static final String COLLECTION_ORDER = "order";
    private UUID uuid;

    private FirebaseFirestore db;

    @Inject
    public OrderRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> save(String userId, OrderDetail orderDetail) {
        return db.collection(COLLECTION_ORDER).document(userId).get().onSuccessTask(orderResult -> {
            final Order order = Optional.ofNullable(orderResult.toObject(Order.class)).orElse(new Order());
            order.getOrderDetails().add(0, orderDetail);
            return db.collection(COLLECTION_ORDER).document(userId).set(order);
        });
    }

    public Task<Order> findByUserId(String userId) {
        return db.collection(COLLECTION_ORDER)
                 .document(userId)
                 .get()
                 .continueWith(task -> task.getResult().toObject(Order.class));
    }

}

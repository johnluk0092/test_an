package vn.edu.hutech.computerstore.repository.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hutech.computerstore.model.product.Product;
import vn.edu.hutech.computerstore.repository.parameter.ProductQuery;

@Singleton
public class ProductRepository {
    private static final String COLLECTION_PRODUCT = "product";
    final FirebaseFirestore db;

    @Inject
    public ProductRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<List<Product>> findAll() {
        return db.collection(COLLECTION_PRODUCT)
                 .get()
                 .continueWith(task -> task.getResult().toObjects(Product.class));
    }

    public Task<List<Product>> findByProductIdIn(List<String> productIds) {
        return db.collection(COLLECTION_PRODUCT)
                 .whereIn(FieldPath.documentId(), productIds)
                 .get()
                 .continueWith(task -> task.getResult().toObjects(Product.class));
    }

    public Task<List<Product>> findByCategoryPath(String categoryPath) {
        return db.collection(COLLECTION_PRODUCT)
                 .whereArrayContains("categoryPaths", categoryPath)
                 .get()
                 .continueWith(task -> task.getResult().toObjects(Product.class));
    }

    public Task<List<Product>> findByCriteria(ProductQuery productQuery) {
        return db.collection(COLLECTION_PRODUCT)
                 .whereGreaterThanOrEqualTo("price", productQuery.getPriceFrom())
                 .whereLessThanOrEqualTo("price", productQuery.getPriceTo())
                 .get().continueWith(result -> {
                    final List<Product> products = result.getResult().toObjects(Product.class);

                    final List<Predicate<Product>> predicates = new ArrayList<>();
                    if (StringUtils.isNoneBlank(productQuery.getBrandName())) {
                        final String queryBrandName = StringUtils.stripAccents(productQuery.getBrandName())
                                                                 .toLowerCase();
                        predicates.add(p -> StringUtils.stripAccents(p.getBrand())
                                                       .toLowerCase()
                                                       .contains(queryBrandName));
                    }

                    if (StringUtils.isNoneBlank(productQuery.getProductName())) {
                        final String queryProductName = StringUtils.stripAccents(productQuery.getProductName())
                                                                   .toLowerCase();
                        predicates.add(p -> StringUtils.stripAccents(p.getName())
                                                       .toLowerCase()
                                                       .contains(queryProductName));
                    }

                    final List<Product> resultProducts;
                    if (!predicates.isEmpty()) {
                        Stream<Product> streamProduct = products.stream();
                        for (Predicate<Product> predicate : predicates) {
                            streamProduct = streamProduct.filter(predicate);
                        }
                        resultProducts = streamProduct.collect(Collectors.toList());
                    } else {
                        resultProducts = products;
                    }

                    final boolean isSortByProductName = productQuery.getOrderField() == ProductQuery.OrderField.PRODUCT_NAME;
                    final boolean isSortDirectionASC = productQuery.getOrderDirection() == Query.Direction.ASCENDING;

                    final Comparator<Product> comparator = isSortByProductName ?
                                                           Comparator.comparing(Product::getName) :
                                                           Comparator.comparingLong(Product::getPrice);

                    return resultProducts.stream()
                                         .sorted(isSortDirectionASC ? comparator : comparator.reversed())
                                         .collect(Collectors.toList());
                });

    }

}

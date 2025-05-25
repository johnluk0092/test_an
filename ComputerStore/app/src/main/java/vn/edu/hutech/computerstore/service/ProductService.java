package vn.edu.hutech.computerstore.service;

import com.google.android.gms.tasks.Task;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hutech.computerstore.model.product.Category;
import vn.edu.hutech.computerstore.model.product.Product;
import vn.edu.hutech.computerstore.repository.parameter.ProductQuery;
import vn.edu.hutech.computerstore.repository.remote.CategoryRepository;
import vn.edu.hutech.computerstore.repository.remote.ProductRepository;

@Singleton
public class ProductService extends BaseService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Inject
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Task<List<Product>> fetchProductsByCategoryPath(String categoryPath) {
        return productRepository.findByCategoryPath(categoryPath);
    }

    public Task<List<Product>> fetchProducts() {
        return productRepository.findAll();
    }

    public Task<List<Category>> fetchCategories() {
        return categoryRepository.findAll();
    }

    public Task<List<Product>> fetchProductsByCriteria(ProductQuery productQuery) {
        return productRepository.findByCriteria(productQuery);
    }

}

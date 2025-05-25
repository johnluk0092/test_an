package vn.edu.hutech.computerstore.repository.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hutech.computerstore.model.product.Category;

@Singleton
public class CategoryRepository {

    @Inject
    public CategoryRepository() {}

    public Task<List<Category>> findAll() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("category")
                 .get()
                 .continueWith(task -> task.getResult().toObjects(Category.class));
    }

}

package vn.edu.hutech.computerstore.repository.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hutech.computerstore.repository.local.UserSettingRepository;
import vn.edu.hutech.computerstore.model.user.User;

@Singleton
public class UserAuthRepository {

    private static final String COLLECTION_USER = "user";

    private FirebaseAuth auth;
    private FirebaseFirestore db;



    @Inject
    public UserAuthRepository(UserSettingRepository userSettingRepository) {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private Task<User> findByEmail(String email) {
        return db.collection(COLLECTION_USER).document(email)
                 .get()
                 .continueWith(task -> task.getResult().toObject(User.class));
    }

    public Task<User> createUser(User user, String password) {
        return auth.createUserWithEmailAndPassword(user.getUserId(), password)
                   .continueWithTask(task -> db.collection(COLLECTION_USER)
                                               .document(task.getResult().getUser().getEmail())
                                               .set(user)
                                               .onSuccessTask(t -> findByEmail(task.getResult().getUser().getEmail())));
    }

    public Task<User> login(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password)
                   .continueWithTask(task -> findByEmail(task.getResult().getUser().getEmail()));
    }

}

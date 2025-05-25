package vn.edu.hutech.computerstore.service;

import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hutech.computerstore.model.user.User;
import vn.edu.hutech.computerstore.model.user.UserSetting;
import vn.edu.hutech.computerstore.repository.local.UserSettingRepository;
import vn.edu.hutech.computerstore.repository.remote.UserAuthRepository;
import vn.edu.hutech.computerstore.ui.base.LoginAccountManager;

@Singleton
public class UserService extends BaseService {

    private final int DEFAULT_MAX_REMEMBER = 30;

    private final UserAuthRepository userAuthRepository;

    private final UserSettingRepository userSettingRepository;

    @Inject
    public UserService(UserAuthRepository userAuthRepository, UserSettingRepository userSettingRepository) {
        this.userAuthRepository = userAuthRepository;
        this.userSettingRepository = userSettingRepository;
    }

    public Task<User> register(User user, String password) {
        return userAuthRepository.createUser(user, password)
                                 .addOnSuccessListener(u -> {
                                     LoginAccountManager.getInstance().setUser(user);
                                     saveRememberUser(user.getUserId());
                                 });
    }

    public Task<User> login(String email, String password) {
        return userAuthRepository.login(email, password).addOnSuccessListener(user -> {
            saveRememberUser(email);
            LoginAccountManager.getInstance().setUser(user);
        });
    }

    private void saveRememberUser(String email) {
        deleteUserSetting();
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, DEFAULT_MAX_REMEMBER);
        userSettingRepository.save(new UserSetting(email, true, calendar.getTime().toString()));
    }

    public List<UserSetting> findAllRememberAccount() {
        return userSettingRepository.findAll();
    }

    public void deleteUserSetting() {
        userSettingRepository.deleteAll();
    }

}

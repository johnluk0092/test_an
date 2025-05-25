package vn.edu.hutech.computerstore.ui.base;

import lombok.Getter;
import lombok.Setter;
import vn.edu.hutech.computerstore.model.user.User;

public final class LoginAccountManager {
    private static LoginAccountManager loginAccountManager;

    @Setter
    @Getter
    private User user;

    private LoginAccountManager() {
        user = new User();
    }

    public String getUserId() {
        return user.getUserId();
    }

    public static LoginAccountManager getInstance() {
        if (loginAccountManager == null) {
            loginAccountManager = new LoginAccountManager();
        }
        return loginAccountManager;
    }

}

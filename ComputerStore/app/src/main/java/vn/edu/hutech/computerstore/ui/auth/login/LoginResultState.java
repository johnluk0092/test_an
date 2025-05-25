package vn.edu.hutech.computerstore.ui.auth.login;

import androidx.annotation.Nullable;

import lombok.Data;
import vn.edu.hutech.computerstore.model.user.User;

@Data
class LoginResultState {

    @Nullable
    private User user;

    @Nullable
    private Integer error;

    LoginResultState(@Nullable Integer error) {
        this.error = error;
    }

    LoginResultState(@Nullable User user) {
        this.user = user;
    }

}
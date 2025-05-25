package vn.edu.hutech.computerstore.ui.home.profile;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.hutech.computerstore.service.UserService;
import vn.edu.hutech.computerstore.ui.base.BaseViewModel;

@HiltViewModel
public class ProfileViewModel extends BaseViewModel {

    private final UserService userService;

    @Inject
    public ProfileViewModel(UserService userService) {
        this.userService = userService;
    }

}

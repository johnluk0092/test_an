package vn.edu.hutech.computerstore.ui.auth.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hutech.computerstore.model.user.User;

public class RegisterState {

    @Data
    @Builder
    public static class Result {
        private User user;
        private Integer error;

        public static Result success(User user) {
            return Result.builder().user(user).build();
        }

        public static Result fail(int error) {
            return Result.builder().error(error).build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Form {

        @Builder.Default
        private int errorName = -1;

        @Builder.Default
        private int errorPhone = -1;

        @Builder.Default
        private int errorBirthday = -1;

        @Builder.Default
        private int errorEmail = -1;

        @Builder.Default
        private int errorPassword = -1;

        public Form clone() {
            return new Form(errorName, errorPhone, errorBirthday, errorEmail, errorPassword);
        }

        public boolean isValid() {
            return errorName == -1 && errorPhone == -1 && errorBirthday == -1 && errorEmail == -1 && errorPassword == -1;
        }
    }

}

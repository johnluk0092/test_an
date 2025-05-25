package vn.edu.hutech.computerstore.ui.base;

import lombok.Data;

@Data
public class ActionResultState {
    private boolean isSuccess;

    private ActionResultState(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public static ActionResultState success() {
        return new ActionResultState(true);
    }

    public static ActionResultState error() {
        return new ActionResultState(false);
    }

}

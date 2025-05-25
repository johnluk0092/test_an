package vn.edu.hutech.computerstore.model.user;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "user_setting")
public class UserSetting {

    @NonNull
    @PrimaryKey
    private String email;

    @ColumnInfo(name = "remember_sign_in")
    private boolean rememberSignIn;

    @ColumnInfo(name = "expired_date")
    private String expireDate;

}

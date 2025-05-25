package vn.edu.hutech.computerstore.repository.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vn.edu.hutech.computerstore.model.user.UserSetting;

@Dao
public interface UserSettingRepository {

    @Insert
    void save(UserSetting userSetting);

    @Update
    void update(UserSetting userSetting);

    @Query("SELECT * FROM user_setting")
    List<UserSetting> findAll();

    @Query("SELECT * FROM user_setting where email = :email")
    UserSetting findByEmail(String email);

    @Query("DELETE FROM user_setting")
    void deleteAll();

}

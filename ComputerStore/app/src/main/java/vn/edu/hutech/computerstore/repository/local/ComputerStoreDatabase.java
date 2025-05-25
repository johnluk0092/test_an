package vn.edu.hutech.computerstore.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import vn.edu.hutech.computerstore.model.user.UserSetting;

@Database(entities = UserSetting.class, version = 1, exportSchema = false)
public abstract class ComputerStoreDatabase extends RoomDatabase {
    public abstract UserSettingRepository getUserSettingDAO();
}

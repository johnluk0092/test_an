package vn.edu.hutech.computerstore.repository.local;

import android.content.Context;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    UserSettingRepository provideUserSettingDao(ComputerStoreDatabase computerStoreDatabase) {
        return computerStoreDatabase.getUserSettingDAO();
    }

    @Provides
    ComputerStoreDatabase provideComputerStoreDatabase(@ApplicationContext Context appContext) {
        return Room.databaseBuilder(appContext, ComputerStoreDatabase.class, "computer_store_db")
            .allowMainThreadQueries()
            .build();
    }
}

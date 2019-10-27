package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.persistence;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.model.KeyResult;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.model.Objective;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.model.Project;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.model.User;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.persistence.dao.UserDao;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.TimestampConverter;


@androidx.room.Database(
        entities = {
                User.class,
                Project.class,
                Objective.class,
                KeyResult.class
        },
        version = 0)
@TypeConverters({TimestampConverter.class,})
public abstract class AppDatabase extends RoomDatabase {

    // DAO
    public abstract UserDao userDao();

    public static final String DB_NAME = "app_db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getDatabase(final Context context) {
        if(instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(),
                                     AppDatabase.class,
                                     DB_NAME)
                    .build();
        }
        return instance;
    }
}

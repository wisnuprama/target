package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.KeyResult;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Objective;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Project;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.User;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao.ObjectiveDao;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao.ProjectDao;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao.UserDao;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.TimestampConverter;


@androidx.room.Database(
        entities = {
                User.class,
                Project.class,
                Objective.class,
                KeyResult.class
        },
        version = 1,
        exportSchema = false)
@TypeConverters({TimestampConverter.class,})
public abstract class AppDatabase extends RoomDatabase {

    // DAO
    public abstract UserDao userDao();

    public abstract ProjectDao projectDao();

    public abstract ObjectiveDao objectiveDao();

    public static final String DB_NAME = "app_db";
    private static AppDatabase instance;

    /**
     * Singleton to access DB.
     * @param context
     * @return
     */
    public static synchronized AppDatabase getInstance(final Context context) {
        if(instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(),
                                     AppDatabase.class,
                                     DB_NAME)
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    public static void seedUser(@NonNull Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("user.json");
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String json = new String(buffer, StandardCharsets.UTF_8);
        User user = new Gson().fromJson(json, new TypeToken<User>(){}.getType());
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        appDatabase.userDao().insertAll(user);

        // set active user id
        PreferenceRepository.setActiveUserId(context, user.getId());
    }

    public static void seedProject(@NonNull Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("projects.json");
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String json = new String(buffer, StandardCharsets.UTF_8);
        List<Project> projects = new Gson().fromJson(json, new TypeToken<List<Project>>(){}.getType());
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        appDatabase.projectDao().insertAll(projects.toArray(new Project[0]));

        // activate project
        int activeProjectId = projects.get(projects.size() - 1).getId();
        PreferenceRepository.setActiveProjectId(context, activeProjectId);
    }
}

package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.BuildConfig;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Project;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.User;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.AppDatabase;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;

/**
 * Class for export data to JSON file and share using @{@link FileProvider} and {@link Intent.ACTION_SEND}.
 * This class use GSON to serialize object to JSON format.
 * This class also load data from db, so run the operation in non UI-thread (AsyncTask).
 * This class only export data from current user saved on {@link PreferenceRepository}.
 */
public class DataExporter {

    private Context mContext;

    // flexibility
    private AppDatabase mAppDatabase;

    private Serializer mSerializer ;

    private Intent mShareFileIntent;

    private File mFile;

    public DataExporter(Context context) {
        mContext = context;
        mAppDatabase = AppDatabase.getInstance(context);
    }

    /**
     * Load all the data needed from current user to be serialized.
     */
    private void load() {
        String userId = PreferenceRepository.getActiveUserId(mContext);
        User user = mAppDatabase.userDao().getUserById(userId);
        List<Project> projects = mAppDatabase.projectDao()
                .getProjectsByUserId(user.getId());
        List<ObjectiveWithKeyResults> objectives = mAppDatabase
                .objectiveDao()
                .getObjectiveWithKeyResultsByUserId(user.getId());
        mSerializer = new Serializer(user, projects, objectives);
    }

    /**
     * Write serialized data to file.
     * @return
     */
    public DataExporter write(){
        load();
        // create file in files directory
        File filesPath = new File(mContext.getFilesDir(), "files");
        mFile = new File(filesPath, "exported.json");

        // open the file in mode private
        try (FileOutputStream outputStream = mContext.openFileOutput(mFile.getName(), Context.MODE_PRIVATE)){
            outputStream.write(mSerializer.serialize().getBytes()); // write
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Build the share intent {@link Intent.ACTION_SEND} and temporary read permission to the file.
     * @return
     */
    public DataExporter buildIntent() {
        mShareFileIntent = new Intent(Intent.ACTION_SEND);
        // set flag to give temporary permission to external app to use FileProvider
        mShareFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // generate URI, defined authority as the application ID in the Manifest,
        // the last param is file we want to open
        Uri uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID, mFile);
        mShareFileIntent.setDataAndType(uri, "application/json");
        // put file as attachment
        mShareFileIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return this;
    }

    /**
     * Start share intent activity. Use current activity.
     * @param activity
     * @return
     */
    public DataExporter share(@NonNull Activity activity) {
        // open share to other apps
        activity.startActivity(Intent.createChooser(mShareFileIntent, "Share via..."));
        return this;
    }

    /**
     * Serializer for GSON.
     */
    static class Serializer {
        @SerializedName("user")
        User mUser;

        @SerializedName("projects")
        List<Project> mProjectList;

        @SerializedName("objectives")
        List<ObjectiveWithKeyResults> mObjectiveList;

        Serializer(User user, List<Project> projectList, List<ObjectiveWithKeyResults> objectiveList) {
            mUser = user;
            mProjectList = projectList;
            mObjectiveList = objectiveList;
        }

        /**
         * Serialize to JSON string format.
         * @return String
         */
        String serialize() {
            return new Gson().toJson(this);
        }
    }
}

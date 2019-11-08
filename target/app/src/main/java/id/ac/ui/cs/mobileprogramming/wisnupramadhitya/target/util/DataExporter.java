package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.BuildConfig;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
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

    private File mFile;

    private String mFileName;

    public DataExporter(Context context) {
        mContext = context;
        mAppDatabase = AppDatabase.getInstance(context);
        mFileName = String.format("%s.json", mContext.getString(R.string.backup_title));
    }

    /**
     * Load all the data needed from current user to be serialized.
     */
    public DataExporter load() {
        // load all from the database for current user
        String userId = PreferenceRepository.getActiveUserId(mContext);
        User user = mAppDatabase.userDao().getUserById(userId);
        List<Project> projects = mAppDatabase.projectDao()
                .getProjectsByUserId(user.getId());
        List<ObjectiveWithKeyResults> objectives = mAppDatabase
                .objectiveDao()
                .getObjectiveWithKeyResultsByUserId(user.getId());
        // serialize
        mSerializer = new Serializer(user, projects, objectives);
        return this;
    }

    /**
     * Write serialized data to file.
     * @return
     */
    public DataExporter write(){
        // create file in files directory
        mFile = new File(mContext.getFilesDir(), mFileName);
        // openFileOutput for internal storage
        try (FileOutputStream outputStream = mContext.openFileOutput(mFile.getName(), Context.MODE_PRIVATE)){
            outputStream.write(mSerializer.serialize().getBytes()); // write
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Start share intent activity. Use current activity.
     * @param activity
     * @return
     */
    public DataExporter share(@NonNull Activity activity) {
        // open share to other apps
        // generate URI, defined authority as the application ID in the Manifest,
        // the last param is file we want to open
        Uri fileUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID, mFile);
        Intent shareFileIntent = ShareCompat.IntentBuilder.from(activity)
                .setStream(fileUri) // set file as attachment
                .setType("text/*") // set mime type
                .setSubject(mFileName) // for google drive, it used as file name. gmail use this as subject
                .getIntent()
                .setAction(Intent.ACTION_SEND) // send to other app, share
                .setDataAndType(fileUri, "text/*")
                // set flag to give temporary permission to external app to use FileProvider
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(shareFileIntent, "Share via..."));
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

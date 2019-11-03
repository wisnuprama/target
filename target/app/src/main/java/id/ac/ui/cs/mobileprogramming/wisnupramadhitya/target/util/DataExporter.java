package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;

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

public class DataExporter {

    private Context mContext;

    // flexibility
    private AppDatabase mAppDatabase;

    private Serializer mSerializer ;

    private Intent mIntent;

    private File mFile;

    public DataExporter(Context context) {
        mContext = context;
        mAppDatabase = AppDatabase.getInstance(context);
    }

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

    public DataExporter write(){
        load();
        File filesPath = new File(mContext.getFilesDir(), "files");
        mFile = new File(filesPath, "exported.json");
        try (FileOutputStream outputStream = mContext.openFileOutput(mFile.getName(), Context.MODE_PRIVATE)){
            outputStream.write(mSerializer.serialize().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public DataExporter buildIntent() {
        mIntent = new Intent(Intent.ACTION_SEND);
        // set flag to give temporary permission to external app to use your FileProvider
        mIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
        Uri uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID, mFile);
        mIntent.setDataAndType(uri, "application/json");
        mIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return this;
    }

    public DataExporter share(@NonNull Activity activity) {
        activity.startActivity(Intent.createChooser(mIntent, "Share via..."));
        return this;
    }

    static class Serializer {
        User mUser;
        List<Project> mProjectList;
        List<ObjectiveWithKeyResults> mObjectiveList;

        Serializer(User user, List<Project> projectList, List<ObjectiveWithKeyResults> objectiveList) {
            mUser = user;
            mProjectList = projectList;
            mObjectiveList = objectiveList;
        }

        String serialize() {
            return new Gson().toJson(this);
        }
    }
}

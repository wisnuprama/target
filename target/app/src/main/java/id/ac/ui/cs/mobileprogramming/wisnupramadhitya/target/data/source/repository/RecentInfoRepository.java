package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.RecentInfo;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.UpdateInfoDataSource;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.remote.RecentInfoRemoteDataSource;

public class RecentInfoRepository implements UpdateInfoDataSource {

    private static RecentInfoRepository instance;

    private RecentInfoRemoteDataSource mRecentInfoRemoteDataSource;

    public RecentInfoRepository(RecentInfoRemoteDataSource recentInfoRemoteDataSource) {
        mRecentInfoRemoteDataSource = recentInfoRemoteDataSource;
    }

    @Override
    public LiveData<List<RecentInfo>> getUpdateInfos() {
        return mRecentInfoRemoteDataSource.getUpdateInfos();
    }

    public static synchronized RecentInfoRepository getInstance(@NonNull RecentInfoRemoteDataSource recentInfoRemoteDataSource) {
        if(instance == null) instance = new RecentInfoRepository(recentInfoRemoteDataSource);
        return instance;
    }
}

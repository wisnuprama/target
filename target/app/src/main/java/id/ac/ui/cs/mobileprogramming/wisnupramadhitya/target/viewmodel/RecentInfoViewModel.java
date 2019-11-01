package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.RecentInfo;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.RecentInfoRepository;

public class RecentInfoViewModel extends ViewModel {

    private RecentInfoRepository mRecentInfoRepository;

    public MediatorLiveData<List<RecentInfo>> recentInfoLiveData = new MediatorLiveData<>();

    public RecentInfoViewModel(RecentInfoRepository recentInfoRepository) {
        mRecentInfoRepository = recentInfoRepository;
        LiveData<List<RecentInfo>> liveData = mRecentInfoRepository.getUpdateInfos();
        recentInfoLiveData.addSource(liveData, recentInfos -> recentInfoLiveData.setValue(recentInfos));
    }

    public MediatorLiveData<List<RecentInfo>> getRecentInfoLiveData() {
        return recentInfoLiveData;
    }
}

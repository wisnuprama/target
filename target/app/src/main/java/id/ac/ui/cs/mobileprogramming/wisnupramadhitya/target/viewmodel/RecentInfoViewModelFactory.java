package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.RecentInfoRepository;

public class RecentInfoViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private RecentInfoRepository mRecentInfoRepository;

    public RecentInfoViewModelFactory(RecentInfoRepository recentInfoRepository) {
        mRecentInfoRepository = recentInfoRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecentInfoViewModel(mRecentInfoRepository);
    }
}

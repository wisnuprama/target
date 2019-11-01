package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.RecentInfo;

public interface UpdateInfoDataSource {

    LiveData<List<RecentInfo>> getUpdateInfos();

}

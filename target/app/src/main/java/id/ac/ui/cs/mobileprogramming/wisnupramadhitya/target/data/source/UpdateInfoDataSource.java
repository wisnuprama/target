package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.UpdateInfo;

public interface UpdateInfoDataSource {

    LiveData<List<UpdateInfo>> getUpdateInfos();

}

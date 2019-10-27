package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository;

import androidx.lifecycle.LiveData;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.User;

public interface UserDataSource {
    LiveData<User> getUser(String userId);
}

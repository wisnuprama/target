package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.User;

public interface UserDataSource {
    LiveData<List<User>> getUsers();
    LiveData<User> getUser(String userId);
}

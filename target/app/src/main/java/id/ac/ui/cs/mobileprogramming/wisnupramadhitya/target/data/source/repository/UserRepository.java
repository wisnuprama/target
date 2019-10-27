package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.User;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao.UserDao;

public class UserRepository implements UserDataSource {

    private UserDao mUserDao;

    private static UserRepository instance;

    private UserRepository(UserDao userDao) {
        mUserDao = userDao;
    }

    @Override
    public LiveData<List<User>> getUsers() {
        return null;
    }

    @Override
    public LiveData<User> getUser(String userId) {
        return null;
    }

    public static synchronized UserRepository getInstance(UserDao userDao) {
        if(instance == null) {
            instance = new UserRepository(userDao);
        }
        return instance;
    }
}

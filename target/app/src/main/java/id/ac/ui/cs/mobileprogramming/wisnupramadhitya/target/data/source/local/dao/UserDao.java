package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.User;

@Dao
public interface UserDao {

    @Insert
    void insertAll(User... users);

    @Query("SELECT * FROM user WHERE id = :userId LIMIT 1")
    LiveData<User> findUserById(String userId);

    @Query("SELECT * FROM user WHERE id = :userId LIMIT 1")
    User getUserById(String userId);
}

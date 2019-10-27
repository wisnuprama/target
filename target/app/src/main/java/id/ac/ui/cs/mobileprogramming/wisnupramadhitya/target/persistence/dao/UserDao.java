package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.model.User;

@Dao
public interface UserDao {

    @Insert
    void insertAll(User... users);

    @Query("SELECT * FROM user LIMIT 1")
    User getCurrentUser();
}

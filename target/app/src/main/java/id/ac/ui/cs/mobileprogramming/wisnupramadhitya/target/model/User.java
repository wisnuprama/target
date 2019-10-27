package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;


@Entity
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;

    @ColumnInfo(name = "first_name")
    private String mUsername;

    public User() {
        mId = UUID.randomUUID().toString();
    }

    @Ignore
    public User(String username) {
        this();
        mUsername = username;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        mId = id;
    }

    @NonNull
    public String getUsername() {
        return mUsername;
    }

    public void setUsername(@NonNull String username) {
        this.mUsername = username;
    }
}

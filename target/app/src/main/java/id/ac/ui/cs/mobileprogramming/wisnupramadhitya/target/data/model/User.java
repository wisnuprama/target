package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
public class User {

    @NonNull
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;

    @SerializedName("username")
    @ColumnInfo(name = "username")
    private String mUsername;

    @SerializedName("dateCreated")
    @ColumnInfo(name = "date_created")
    private OffsetDateTime mDateCreated = OffsetDateTime.now();

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

    public OffsetDateTime getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        mDateCreated = dateCreated;
    }
}

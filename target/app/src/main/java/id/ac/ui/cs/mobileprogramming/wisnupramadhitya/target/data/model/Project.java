package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.gson.OffsetDateTimeConverter;


@Entity(
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "owner_id",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class Project {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer mId;

    @SerializedName("projectName")
    @ColumnInfo(name = "project_name")
    private String mProjectName;

    @SerializedName("ownerId")
    @ColumnInfo(name = "owner_id", index = true)
    private String mOwnerId;

    @SerializedName("isFavorite")
    @ColumnInfo(name = "is_favorite")
    private Boolean mIsFavorite;

    @SerializedName("dateCreated")
    @JsonAdapter(OffsetDateTimeConverter.class)
    @ColumnInfo(name = "date_created")
    private OffsetDateTime mDateCreated = OffsetDateTime.now();

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getProjectName() {
        return mProjectName;
    }

    public void setProjectName(String mProjectName) {
        this.mProjectName = mProjectName;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(String ownerId) {
        this.mOwnerId = ownerId;
    }

    public Boolean isFavorite() {
        return mIsFavorite;
    }

    public void setIsFavorite(Boolean mIsFavorite) {
        this.mIsFavorite = mIsFavorite;
    }

    public OffsetDateTime getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(OffsetDateTime mDateCreated) {
        this.mDateCreated = mDateCreated;
    }
}

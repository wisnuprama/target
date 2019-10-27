package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;


@Entity(
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "owner_id",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class Project {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer mId;

    @ColumnInfo(name = "project_name")
    private String mProjectName;

    @ColumnInfo(name = "owner_id")
    private String mOwnerId;

    @ColumnInfo(name = "is_favorite")
    private Boolean mIsFavorite;

    @ColumnInfo(name = "date_created")
    private OffsetDateTime mDateCreated;

    public Integer getId() {
        return mId;
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

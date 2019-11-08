package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.gson.OffsetDateTimeConverter;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = Project.class,
                        parentColumns = "id",
                        childColumns = "project_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "owner_id",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class Objective {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer mId;

    @SerializedName("projectId")
    @ColumnInfo(name = "project_id", index = true)
    private Integer mProjectId;

    @SerializedName("ownerId")
    @ColumnInfo(name = "owner_id", index = true)
    private String mOwnerId;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String mTitle;

    @SerializedName("rational")
    @ColumnInfo(name = "rational")
    private String mRational;

    @SerializedName("dateCreated")
    @JsonAdapter(OffsetDateTimeConverter.class)
    @ColumnInfo(name = "date_created")
    private OffsetDateTime mDateCreated = OffsetDateTime.now();

    @SerializedName("deadline")
    @ColumnInfo(name = "deadline")
    private OffsetDateTime mDeadline;

    public Objective(Integer projectId, String ownerId, String title, String rational, OffsetDateTime deadline) {
        mProjectId = projectId;
        mOwnerId = ownerId;
        mTitle = title;
        mRational = rational;
        mDeadline = deadline;
    }

    @Ignore
    public Objective(Integer id, Integer projectId, String ownerId, String title, String rational, OffsetDateTime deadline) {
        this(projectId, ownerId, title, rational, deadline);
        setId(id);
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Integer getProjectId() {
        return mProjectId;
    }

    public void setProjectId(Integer projectId) {
        mProjectId = projectId;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(String ownerId) {
        mOwnerId = ownerId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getRational() {
        return mRational;
    }

    public void setRational(String rational) {
        mRational = rational;
    }

    public OffsetDateTime getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        mDateCreated = dateCreated;
    }

    public OffsetDateTime getDeadline() {
        return mDeadline;
    }

    public void setDeadline(OffsetDateTime deadline) {
        mDeadline = deadline;
    }

    public Boolean isDue() {
        final OffsetDateTime currentTime = OffsetDateTime.now();
        return currentTime.isAfter(getDeadline());
    }

    public Long getRemainingDay() {
        if(getDeadline() == null) return -1L;
        final OffsetDateTime today = OffsetDateTime.now();
        long remaining = ChronoUnit.DAYS.between(today, getDeadline());
        // hacky2 somehow between returns negative for positive
        return remaining > 0 ? 0 : -remaining;
    }
}

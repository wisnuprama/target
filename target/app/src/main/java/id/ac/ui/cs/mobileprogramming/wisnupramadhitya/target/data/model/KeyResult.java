package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.gson.OffsetDateTimeConverter;

@Entity(
    foreignKeys = {
            @ForeignKey(entity = Objective.class,
                    parentColumns = "id",
                    childColumns = "objective_id",
                    onDelete = ForeignKey.CASCADE),
    }
)
public class KeyResult {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer mId;

    @SerializedName("objectiveId")
    @ColumnInfo(name = "objective_id", index = true)
    private Integer mObjectiveId;

    @SerializedName("statement")
    @ColumnInfo(name = "statement")
    private String mStatement;

    @SerializedName("measurement")
    @ColumnInfo(name = "measurement")
    private String mMeasurement;

    @SerializedName("currentValue")
    @ColumnInfo(name = "current_value")
    private Double mCurrentValue;

    @SerializedName("startValue")
    @ColumnInfo(name = "start_value")
    private Double mStartValue;

    @SerializedName("targetValue")
    @JsonAdapter(OffsetDateTimeConverter.class)
    @ColumnInfo(name = "target_value")
    private Double mTargetValue;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Integer getObjectiveId() {
        return mObjectiveId;
    }

    public void setObjectiveId(Integer objectiveId) {
        mObjectiveId = objectiveId;
    }

    public String getStatement() {
        return mStatement;
    }

    public void setStatement(String statement) {
        mStatement = statement;
    }

    public String getMeasurement() {
        return mMeasurement;
    }

    public void setMeasurement(String measurement) {
        mMeasurement = measurement;
    }

    public Double getCurrentValue() {
        return mCurrentValue;
    }

    public void setCurrentValue(Double currentValue) {
        mCurrentValue = currentValue;
    }

    public Double getStartValue() {
        return mStartValue;
    }

    public void setStartValue(Double startValue) {
        mStartValue = startValue;
    }

    public Double getTargetValue() {
        return mTargetValue;
    }

    public void setTargetValue(Double targetValue) {
        mTargetValue = targetValue;
    }

    public Boolean isComplete() {
        return getTargetValue().equals(getCurrentValue());
    }
}

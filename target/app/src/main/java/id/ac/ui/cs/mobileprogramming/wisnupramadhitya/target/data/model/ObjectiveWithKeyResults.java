package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.List;

public class ObjectiveWithKeyResults {

    @Ignore
    private Integer totalCompleted;

    @Embedded
    private Objective mObjective;

    @Relation(parentColumn = "id", entityColumn = "objective_id")
    private List<KeyResult> mKeyResults;

    public Objective getObjective() {
        return mObjective;
    }

    public List<KeyResult> getKeyResults() {
        return mKeyResults;
    }

    public void setObjective(Objective objective) {
        mObjective = objective;
    }

    public void setKeyResults(List<KeyResult> keyResults) {
        mKeyResults = keyResults;
    }

    public String getStats() {
        if(totalCompleted == null) {
            totalCompleted = 0;
            getKeyResults().forEach((keyResult -> {
                totalCompleted += keyResult.isComplete() ? 1 : 0;
            }));
        }

        return String.format("%s / %s completed", getKeyResults().size(), totalCompleted);
    }
}

package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Objective;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;

public interface ObjectiveDataSource {

    @Deprecated
    void createObjective(@NonNull String userId, @NonNull Integer projectId,
                         String title, String rational, OffsetDateTime deadline);

    void updateObjective(@NonNull final Objective objective);

    void deleteObjective(@NonNull Integer objectiveId);

    LiveData<ObjectiveWithKeyResults> getObjective(@NonNull Integer objectiveId);

    LiveData<List<ObjectiveWithKeyResults>> getProjectObjectives(Integer projectId);
}

package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;

public interface ObjectiveDataSource {

    void createObjective(@NonNull String userId, @NonNull Integer projectId,
                         @NonNull String title, String rational, OffsetDateTime deadline);

    LiveData<List<ObjectiveWithKeyResults>> getProjectObjectives(Integer projectId);
}

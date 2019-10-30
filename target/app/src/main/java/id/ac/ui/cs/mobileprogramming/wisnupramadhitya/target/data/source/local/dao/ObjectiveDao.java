package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Objective;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Project;

@Dao
public interface ObjectiveDao {

    @Insert
    void insertAll(Objective... objectives);

    @Query("SELECT * FROM objective")
    LiveData<List<Objective>> getObjectives();

    @Query("SELECT * FROM objective WHERE owner_id = :userId")
    LiveData<List<Objective>> getObjectivesByUserId(String userId);

    @Query("SELECT * FROM objective WHERE project_id = :projectId")
    LiveData<List<Objective>> getObjectivesByProjectId(Integer projectId);

    @Query("SELECT * FROM objective WHERE id = :objectiveId")
    LiveData<Project> getObjectiveById(Integer objectiveId);
}

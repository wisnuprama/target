package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Project;

@Dao
public interface ProjectDao {

    @Insert
    void insertAll(Project... projects);

    @Query("SELECT * FROM project WHERE owner_id = :userId")
    LiveData<List<Project>> getProjectsByUserId(String userId);

    @Query("SELECT * FROM project")
    LiveData<List<Project>> getProjects();

    @Query("SELECT * FROM project WHERE id = :projectId")
    LiveData<Project> getProjectById(Integer projectId);

}

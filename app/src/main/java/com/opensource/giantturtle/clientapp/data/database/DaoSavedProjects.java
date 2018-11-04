package com.opensource.giantturtle.clientapp.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoSavedProjects {

    @Insert
    void insert(ModelSavedGitHubProject baseGitHubRepo);

    @Update
    void update(ModelSavedGitHubProject baseGitHubRepo);

    @Delete
    void delete(ModelSavedGitHubProject baseGitHubRepo);

    @Query("DELETE FROM saved_gh_projects")
    void deleteAllSavedRepos();

    @Query("SELECT * FROM saved_gh_projects ORDER BY id DESC")
    LiveData<List<ModelSavedGitHubProject>> getAllSavedProjects();

}

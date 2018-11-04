package com.opensource.giantturtle.clientapp.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoCachedProjects {

    @Insert
    void insert(ModelCachedGitHubProject cachedGitHubRepo);

    @Insert
    void saveToCache(List<ModelCachedGitHubProject> listOfGitHubProjects);

    @Update
    void update(ModelCachedGitHubProject cachedGitHubRepo);

    @Delete
    void delete(ModelCachedGitHubProject cachedGitHubRepo);

    @Query("DELETE FROM cashed_gh_projects")
    void deleteAllCachedRepos();

    @Query("SELECT * FROM cashed_gh_projects")
    LiveData<List<ModelCachedGitHubProject>> getAllCachedFromDb();

    @Query("DELETE FROM cashed_gh_projects WHERE id NOT IN (SELECT id FROM cashed_gh_projects ORDER BY id LIMIT :cacheLimit)")
    void trimCacheTable(int cacheLimit);
}

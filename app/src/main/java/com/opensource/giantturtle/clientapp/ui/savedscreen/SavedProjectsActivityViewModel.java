package com.opensource.giantturtle.clientapp.ui.savedscreen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.opensource.giantturtle.clientapp.data.DataRepository;
import com.opensource.giantturtle.clientapp.data.database.ModelSavedGitHubProject;

import java.util.List;

public class SavedProjectsActivityViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<ModelSavedGitHubProject>> allSavedRepos;

    public SavedProjectsActivityViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance(application);
        allSavedRepos = repository.getAllSavedProjects();
    }

    public void delete(ModelSavedGitHubProject savedGitHubRepo) {
        repository.deleteSavedRepo(savedGitHubRepo);
    }

    public void deleteAllSavedRepos() {
        repository.deleteAllSavedRepos();
    }

    public LiveData<List<ModelSavedGitHubProject>> getAllSavedRepos() {
        return allSavedRepos;
    }
}

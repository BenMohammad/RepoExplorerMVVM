package com.opensource.giantturtle.clientapp.ui.detailsscreen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.opensource.giantturtle.clientapp.data.DataRepository;
import com.opensource.giantturtle.clientapp.data.database.ModelBaseGitHubProject;
import com.opensource.giantturtle.clientapp.data.database.ModelSavedGitHubProject;


public class DetailsActivityViewModel extends AndroidViewModel {
    private DataRepository repository;


    public DetailsActivityViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance(application);
    }


    void bookmarkProject(ModelBaseGitHubProject baseGitHubProject) {
        repository.bookmarkProject(baseGitHubProject);
    }

    void deleteBookmark(ModelSavedGitHubProject savedProject) {
        repository.deleteSavedRepo(savedProject);
    }



}

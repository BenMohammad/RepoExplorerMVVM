package com.opensource.giantturtle.clientapp.ui.mainscreen;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;

import com.opensource.giantturtle.clientapp.data.DataRepository;
import com.opensource.giantturtle.clientapp.data.database.ModelBaseGitHubProject;
import com.opensource.giantturtle.clientapp.data.database.ModelCachedGitHubProject;
import com.opensource.giantturtle.clientapp.data.webservice.GitHubClientService;
import com.opensource.giantturtle.clientapp.utils.WebServiceMessage;

import java.util.Date;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<ModelCachedGitHubProject>> allCachedProjects;

    //for pagination
    private int pageCounter = 1;
    private String searchTerm;
    private boolean isFirstRun;
    private MutableLiveData<WebServiceMessage> webServiceStatus;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance(application);
        allCachedProjects = repository.getAllCachedProjects();
        searchTerm = repository.getLastSavedSearchTerm();
        isFirstRun = repository.isFirstRun();
        if (!repository.isCacheFreshEnough(new Date())) {
            repository.updateFromWebService(this.searchTerm, pageCounter, GitHubClientService.RESULTS_PER_PAGE);
        }
        webServiceStatus = (MutableLiveData<WebServiceMessage>) Transformations.switchMap(repository.getWebServiceMessage(),
                new Function<WebServiceMessage, LiveData<WebServiceMessage>>() {
            @Override
            public LiveData<WebServiceMessage> apply(WebServiceMessage input) {
                switch (input) {
                    case UPDATING_STATUS:
                        webServiceStatus.postValue(WebServiceMessage.UPDATING_STATUS);
                        break;
                    case ON_FAILURE:
                        if (pageCounter > 1) pageCounter--;
                        webServiceStatus.postValue(WebServiceMessage.ON_FAILURE);
                        break;
                    case ON_RESPONSE_SUCCESS:
                        webServiceStatus.postValue(WebServiceMessage.ON_RESPONSE_SUCCESS);
                        break;
                    case ON_RESPONSE_NOTHING_FOUND:
                        webServiceStatus.postValue(WebServiceMessage.ON_RESPONSE_NOTHING_FOUND);
                        break;
                    case ON_RESPONSE_NO_MORE_RESULTS:
                        webServiceStatus.postValue(WebServiceMessage.ON_RESPONSE_NO_MORE_RESULTS);
                        break;
                    default:
                        break;
                }
                return null;
            }
        });
    }

    @Override
    protected void onCleared() {
        //Trim the cached table so it wouldn't grow indefinitely and user wouldn't uninstall it because
        //it takes too much memory on the phone
        repository.trimCacheTable();
        repository.clearWebServiceMessage();
        super.onCleared();
    }

    void setFirstRunFlagOff() {
        isFirstRun = false;
        repository.setFirstRunOff();
    }

    boolean isFirstRun() {
        return isFirstRun;
    }

    void bookmarkProject(ModelBaseGitHubProject baseGitHubProject) {
        repository.bookmarkProject(baseGitHubProject);
    }

    LiveData<List<ModelCachedGitHubProject>> getAllCachedProjects() {
        return allCachedProjects;
    }

    String getSearchTerm() {
        return searchTerm;
    }

    void searchGitHub(String searchTerm) {
        pageCounter = 1;
        this.searchTerm = searchTerm;
        repository.updateFromWebService(searchTerm, pageCounter, GitHubClientService.RESULTS_PER_PAGE);
    }

    void loadMore() {
        pageCounter++;
        repository.updateFromWebService(this.searchTerm, pageCounter, GitHubClientService.RESULTS_PER_PAGE);
    }

    MutableLiveData<WebServiceMessage> getWebServiceStatus() {
        return webServiceStatus;
    }
}
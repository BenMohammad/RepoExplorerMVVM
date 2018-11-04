package com.opensource.giantturtle.clientapp.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.opensource.giantturtle.clientapp.data.database.DaoCachedProjects;
import com.opensource.giantturtle.clientapp.data.database.DaoSavedProjects;
import com.opensource.giantturtle.clientapp.data.database.ModelBaseGitHubProject;
import com.opensource.giantturtle.clientapp.data.database.ModelCachedGitHubProject;
import com.opensource.giantturtle.clientapp.data.database.GitHubProjectsDatabase;
import com.opensource.giantturtle.clientapp.data.database.ModelSavedGitHubProject;
import com.opensource.giantturtle.clientapp.data.webservice.GitHubClientService;
import com.opensource.giantturtle.clientapp.data.webservice.apiresponse.GitHubRepo;
import com.opensource.giantturtle.clientapp.data.webservice.apiresponse.items.Item;
import com.opensource.giantturtle.clientapp.utils.Configuration;
import com.opensource.giantturtle.clientapp.utils.Utils;
import com.opensource.giantturtle.clientapp.utils.WebServiceMessage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository {
    private static DataRepository dataRepositoryInstance;
    private DaoSavedProjects savedProjectsDao;
    private DaoCachedProjects cachedProjectsDao;
    private Retrofit retrofit;
    private GitHubClientService gitHubClient;
    private SharedPreferences sharedPreferences;
    private static int FRESH_TIMEOUT_IN_MINUTES = Configuration.FRESH_TIMEOUT_IN_MINUTES;
    private MutableLiveData<WebServiceMessage> webServiceCallStatus = new MutableLiveData<>();

    public static synchronized DataRepository getInstance(Application application) {
        if (dataRepositoryInstance == null) {
            dataRepositoryInstance = new DataRepository(application);
        }
        return dataRepositoryInstance;
    }

    private DataRepository(Application application) {
        GitHubProjectsDatabase database = GitHubProjectsDatabase.getInstance(application);
        cachedProjectsDao = database.cachedProjectsDao();
        savedProjectsDao = database.savedProjectsDao();
        sharedPreferences = application.getSharedPreferences("repoExplorerSharedPref", Context.MODE_PRIVATE);
    }

    public boolean isFirstRun(){
        return sharedPreferences.getBoolean("isFirstRun",true);
    }

    public void setFirstRunOff () {
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putBoolean("isFirstRun",false);
        myEditor.apply();
    }

    public String getLastSavedSearchTerm() {
        return sharedPreferences.getString("lastSearchTerm", GitHubClientService.DEFAULT_SEARCH_TERM);
    }

    private void saveLastSearchTerm(String searchTerm){
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putString("lastSearchTerm",searchTerm);
        myEditor.apply();
    }

    private  void setLastRefreshedDate (Date date){
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putString("lastRefreshDate",date.toString());
        myEditor.apply();
    }

    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }

    public boolean isCacheFreshEnough (Date date){
        String lastRefreshedDate = sharedPreferences.getString("lastRefreshDate", null) ;
        if (lastRefreshedDate==null) return false; //not fresh if there isn't any
        else {
            return new Date(lastRefreshedDate).compareTo(getMaxRefreshTime(date))>0; //comparison
        }
    }

    public void updateFromWebService(final String searchTerm, final int pageNumber, int resultsPerPage) {
        webServiceCallStatus.setValue(WebServiceMessage.UPDATING_STATUS);
        final List<ModelCachedGitHubProject> gitHubProjectsList = new ArrayList<ModelCachedGitHubProject>();
        if (retrofit==null || gitHubClient==null){//because using only GitHubClientService
            retrofit = new Retrofit.Builder().baseUrl(GitHubClientService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            gitHubClient = retrofit.create(GitHubClientService.class);
        }
        Call<GitHubRepo> call = gitHubClient.getRepos(searchTerm, pageNumber, resultsPerPage);
        call.enqueue(new Callback<GitHubRepo>() {
            @Override
            public void onResponse(Call<GitHubRepo> call, Response<GitHubRepo> response) {
                //Log.d("respBody",response.body().toString());
                ArrayList<Item> itemList = response.body().getItems();
                for (int i = 0; i < itemList.size(); i++) {
                    ModelCachedGitHubProject current = new ModelCachedGitHubProject();
                    current.setOwnersName(itemList.get(i).getOwner().getLogin());
                    current.setRepoName(itemList.get(i).getName());
                    current.setRepoSize(itemList.get(i).getSize());
                    current.setHasWiki(itemList.get(i).isHas_wiki());
                    current.setCreatedAt(itemList.get(i).getCreated_at());
                    current.setPushedAt(itemList.get(i).getPushed_at());
                    current.setUpdatedAt(itemList.get(i).getUpdated_at());
                    current.setHtmlUrl(itemList.get(i).getHtml_url());
                    current.setAvatarUrl(itemList.get(i).getOwner().getAvatar_url());
                    current.setLanguage(itemList.get(i).getLanguage());
                    current.setForksCount(itemList.get(i).getForks_count());
                    current.setScore(itemList.get(i).getScore());
                    current.setDescription(itemList.get(i).getDescription());
                    gitHubProjectsList.add(current);
                }
                if (!gitHubProjectsList.isEmpty()) {
                    boolean clearPreviousCache;
                    if (pageNumber==1) {
                        clearPreviousCache =true;
                        saveLastSearchTerm(searchTerm);
                    }
                    else clearPreviousCache = false;
                    cacheProjectsList(gitHubProjectsList, clearPreviousCache);
                    setLastRefreshedDate(new Date());//set current date as last refreshed
                    webServiceCallStatus.postValue(WebServiceMessage.ON_RESPONSE_SUCCESS);
                } else  {
                    if (pageNumber==1)webServiceCallStatus.postValue(WebServiceMessage.ON_RESPONSE_NOTHING_FOUND);
                    else webServiceCallStatus.postValue(WebServiceMessage.ON_RESPONSE_NO_MORE_RESULTS);
                }
            }
            @Override
            public void onFailure(Call<GitHubRepo> call, Throwable t) {
                //Log.e("wentWrong", t.getMessage());
                webServiceCallStatus.postValue(WebServiceMessage.ON_FAILURE);
            }
        });
    }

    public LiveData<List<ModelCachedGitHubProject>> getAllCachedProjects() {
        return cachedProjectsDao.getAllCachedFromDb();
    }

    public LiveData<List<ModelSavedGitHubProject>> getAllSavedProjects() {
        return savedProjectsDao.getAllSavedProjects();
    }

    public MutableLiveData<WebServiceMessage> getWebServiceMessage() {
        return webServiceCallStatus;
    }

    public void clearWebServiceMessage() {
        //to get fresh webServiceCallStatus when opening app which is not cleared from androids cache memory
        webServiceCallStatus=new MutableLiveData<>();
    }

    private void cacheProjectsList(List<ModelCachedGitHubProject> cachedGitHubRepoList, boolean clearCache) {
        new SaveListToCacheAsyncTask(cachedProjectsDao, clearCache).execute(cachedGitHubRepoList);
    }

    public void trimCacheTable() {
        new TrimCacheAsyncTask(cachedProjectsDao).execute();
    }

    private static class SaveListToCacheAsyncTask extends AsyncTask<List<ModelCachedGitHubProject>, Void, Void> {
        private DaoCachedProjects cachedProjectsDao;
        boolean clearCacheBeforeAdding;

        private SaveListToCacheAsyncTask(DaoCachedProjects cachedProjectsDao, boolean clearCacheBeforeAdding) {
            this.cachedProjectsDao = cachedProjectsDao;
            this.clearCacheBeforeAdding = clearCacheBeforeAdding;
        }

        @Override
        protected Void doInBackground(List<ModelCachedGitHubProject>... cachedGitHubReposList) {
            if (clearCacheBeforeAdding) {
                cachedProjectsDao.deleteAllCachedRepos();
            }
            cachedProjectsDao.saveToCache(cachedGitHubReposList[0]);
            return null;
        }
    }

    private static class TrimCacheAsyncTask extends AsyncTask<ModelCachedGitHubProject, Void, Void> {
        private DaoCachedProjects cachedProjectsDao;

        private TrimCacheAsyncTask(DaoCachedProjects cachedProjectsDao) {
            this.cachedProjectsDao = cachedProjectsDao;
        }
        @Override
        protected Void doInBackground(ModelCachedGitHubProject... cachedGitHubRepos) {
            cachedProjectsDao.trimCacheTable(GitHubClientService.RESULTS_PER_PAGE);
            return null;
        }
    }

    private static class SavedTableAsyncTask extends AsyncTask<ModelSavedGitHubProject, Void, Void> {
        private DaoSavedProjects savedProjectsDao;
        private ActionTypeSaved actionType;

        private SavedTableAsyncTask(DaoSavedProjects savedProjectsDao, ActionTypeSaved actionType) {
            this.savedProjectsDao = savedProjectsDao;
            this.actionType = actionType;
        }
        @Override
        protected Void doInBackground(ModelSavedGitHubProject... savedGitHubRepos) {
            switch (actionType) {
                case DELETE_SAVED:
                    savedProjectsDao.delete(savedGitHubRepos[0]);
                    break;
                case INSERT_BOOKMARK:
                    savedProjectsDao.insert(savedGitHubRepos[0]);
                    break;
                case DELETE_ALL_SAVED:
                    savedProjectsDao.deleteAllSavedRepos();
                    break;
                case UPDATE_SAVED:
                    savedProjectsDao.update(savedGitHubRepos[0]);
                    break;
                default:
                    break;
            }
            return null;
        }
    }

    public void bookmarkProject(ModelBaseGitHubProject baseGitHubProject) {
        new SavedTableAsyncTask(savedProjectsDao, ActionTypeSaved.INSERT_BOOKMARK).execute(Utils.changeProjectType(baseGitHubProject));
    }

    public void deleteSavedRepo(ModelSavedGitHubProject savedGitHubRepo) {
        new SavedTableAsyncTask(savedProjectsDao, ActionTypeSaved.DELETE_SAVED).execute(savedGitHubRepo);
    }

    public void deleteAllSavedRepos() {
        new SavedTableAsyncTask(savedProjectsDao, ActionTypeSaved.DELETE_ALL_SAVED).execute();
    }

    private enum ActionTypeSaved {
        DELETE_SAVED,
        DELETE_ALL_SAVED,
        UPDATE_SAVED,
        INSERT_BOOKMARK
    }
}

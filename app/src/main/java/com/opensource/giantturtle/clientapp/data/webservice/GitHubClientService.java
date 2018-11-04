package com.opensource.giantturtle.clientapp.data.webservice;


import com.opensource.giantturtle.clientapp.data.webservice.apiresponse.GitHubRepo;
import com.opensource.giantturtle.clientapp.utils.Configuration;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface GitHubClientService {
    String BASE_URL = "https://api.github.com/";
    String DEFAULT_SEARCH_TERM = Configuration.DEFAULT_SEARCH_TERM;
    int RESULTS_PER_PAGE = Configuration.RESULTS_PER_PAGE;

    @Headers("User-Agent: giant2turtle@gmail.com")
    @GET("search/repositories")
    Call<GitHubRepo> getRepos(@Query("q") String searchParam, @Query("page") int page, @Query("per_page") int perPage);

    /*@Headers("User-Agent: giant2turtle@gmail.com")
    @GET
    Call<ModelBaseGitHubProject> getTenRepos(@Url String url);*/
}

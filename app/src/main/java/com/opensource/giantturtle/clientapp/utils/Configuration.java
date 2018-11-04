package com.opensource.giantturtle.clientapp.utils;

public interface Configuration {
    //utility interface for easier configuration of the app
    int FRESH_TIMEOUT_IN_MINUTES = 12 * 60;
    int RESULTS_PER_PAGE = 25;
    String DEFAULT_SEARCH_TERM = "tetris";
}

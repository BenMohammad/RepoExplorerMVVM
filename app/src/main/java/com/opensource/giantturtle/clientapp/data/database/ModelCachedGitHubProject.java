package com.opensource.giantturtle.clientapp.data.database;

import android.arch.persistence.room.Entity;

@Entity(tableName = "cashed_gh_projects")
public class ModelCachedGitHubProject extends ModelBaseGitHubProject {
    //need to have this sub class because ROOM supports only one Entity per class
}

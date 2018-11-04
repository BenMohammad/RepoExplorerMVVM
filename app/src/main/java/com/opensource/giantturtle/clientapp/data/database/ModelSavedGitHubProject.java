package com.opensource.giantturtle.clientapp.data.database;

import android.arch.persistence.room.Entity;

@Entity(tableName = "saved_gh_projects")
public class ModelSavedGitHubProject extends ModelBaseGitHubProject {
    //need to have this sub class because ROOM supports only one Entity per class
}

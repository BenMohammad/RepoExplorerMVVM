package com.opensource.giantturtle.clientapp.data.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

//Define each table here as entities
@Database(entities = {ModelSavedGitHubProject.class,ModelCachedGitHubProject.class}, version = 1)
public abstract class GitHubProjectsDatabase extends RoomDatabase {

    private static GitHubProjectsDatabase instance;

    public abstract DaoSavedProjects savedProjectsDao();

    public abstract DaoCachedProjects cachedProjectsDao();

    public static synchronized GitHubProjectsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GitHubProjectsDatabase.class, "projects_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };




}

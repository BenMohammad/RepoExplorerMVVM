package com.opensource.giantturtle.clientapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.opensource.giantturtle.clientapp.data.database.ModelBaseGitHubProject;
import com.opensource.giantturtle.clientapp.data.database.ModelSavedGitHubProject;
import com.opensource.giantturtle.clientapp.ui.detailsscreen.DetailsActivity;

public class Utils {

    public static Intent createDeatailsIntent(Activity parentActivity, ModelBaseGitHubProject clickedProject) {

        Intent myIntent = new Intent(parentActivity, DetailsActivity.class);
        myIntent.putExtra("parentActivity", parentActivity.getClass().getSimpleName());
        myIntent.putExtra("avatarUrl", clickedProject.getAvatarUrl());
        myIntent.putExtra("createdAt", clickedProject.getCreatedAt());
        myIntent.putExtra("pushedAt", clickedProject.getPushedAt());
        myIntent.putExtra("updatedAt", clickedProject.getUpdatedAt());
        myIntent.putExtra("description", clickedProject.getDescription());
        myIntent.putExtra("htmlUrl", clickedProject.getHtmlUrl());
        myIntent.putExtra("forksCount", Integer.toString(clickedProject.getForksCount()));
        myIntent.putExtra("ownersName", clickedProject.getOwnersName());
        myIntent.putExtra("language", clickedProject.getLanguage());
        myIntent.putExtra("score", Float.toString(clickedProject.getScore()));
        myIntent.putExtra("repoName", clickedProject.getRepoName());
        myIntent.putExtra("repoSize", Double.toString(clickedProject.getRepoSize()));
        myIntent.putExtra("id", Integer.toString(clickedProject.getId()));
        myIntent.putExtra("hasWiki", Boolean.toString(clickedProject.hasWiki()));
        myIntent.putExtra("prettyCreatedAt", clickedProject.getPrettyCreatedAt());
        myIntent.putExtra("prettyUpdatedAt", clickedProject.getPrettyUpdatedAt());
        myIntent.putExtra("prettyPushedAt", clickedProject.getPrettyPushedAt());
        return myIntent;
    }

    public static ModelSavedGitHubProject changeProjectType(ModelBaseGitHubProject baseGitHubProject) {
        //Convert project from cached to saved, because I need to have two class entities to save in two different tables
        ModelSavedGitHubProject savedGitHubProject = new ModelSavedGitHubProject();
        savedGitHubProject.setAvatarUrl(baseGitHubProject.getAvatarUrl());
        savedGitHubProject.setCreatedAt(baseGitHubProject.getCreatedAt());
        savedGitHubProject.setPushedAt(baseGitHubProject.getPushedAt());
        savedGitHubProject.setUpdatedAt(baseGitHubProject.getUpdatedAt());
        savedGitHubProject.setHtmlUrl(baseGitHubProject.getHtmlUrl());
        savedGitHubProject.setDescription(baseGitHubProject.getDescription());
        savedGitHubProject.setForksCount(baseGitHubProject.getForksCount());
        savedGitHubProject.setHasWiki(baseGitHubProject.hasWiki());
        savedGitHubProject.setLanguage(baseGitHubProject.getLanguage());
        savedGitHubProject.setOwnersName(baseGitHubProject.getOwnersName());
        savedGitHubProject.setRepoName(baseGitHubProject.getRepoName());
        savedGitHubProject.setRepoSize(baseGitHubProject.getRepoSize());
        savedGitHubProject.setScore(baseGitHubProject.getScore());
        return savedGitHubProject;
    }
}

package com.opensource.giantturtle.clientapp.data.database;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.opensource.giantturtle.clientapp.utils.Utils;

import java.text.ParseException;

/*
This class is not an Room entity, its a superclass for Room entities ModeCachedGitHubProject and
ModelSavedGitHubProject entities, because Room needsto have separate class for each entity in order
 to store it in separate tables
*/

public class ModelBaseGitHubProject {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String repoName;
    private String ownersName;
    private double repoSize;
    private boolean hasWiki;
    private String htmlUrl;
    private String createdAt;
    private String updatedAt;
    private String pushedAt;
    private String avatarUrl;
    private String language;
    private int forksCount;
    private float score;
    private String description;

    //pretty date and time, not to be stored in database
    @Ignore
    private String prettyCreatedAt;
    @Ignore
    private String prettyUpdatedAt;
    @Ignore
    private String prettyPushedAt;

    public String getPrettyPushedAt() {
        return prettyPushedAt;
    }

    public ModelBaseGitHubProject() {
    }
        public int getId() {
        return id;
    }

    public String getPrettyCreatedAt() {
        return prettyCreatedAt;
    }

    public String getPrettyUpdatedAt() {
        return prettyUpdatedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        //For pretty date format
        this.prettyCreatedAt = Utils.convertToPrettyTime(createdAt);
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        this.prettyUpdatedAt = Utils.convertToPrettyTime(updatedAt);
    }

    public String getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(String pushedAt) {
        this.pushedAt = pushedAt;
        this.prettyPushedAt = Utils.convertToPrettyTime(pushedAt);
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getOwnersName() {
        return ownersName;
    }

    public void setOwnersName(String ownersName) {
        this.ownersName = ownersName;
    }

    public double getRepoSize() {
        return repoSize;
    }

    public void setRepoSize(double repoSize) {
        this.repoSize = repoSize;
    }

    public boolean hasWiki() {
        return hasWiki;
    }

    public void setHasWiki(boolean hasWiki) {
        this.hasWiki = hasWiki;
    }

}

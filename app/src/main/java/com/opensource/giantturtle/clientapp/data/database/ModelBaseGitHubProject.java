package com.opensource.giantturtle.clientapp.data.database;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        try {
            this.prettyCreatedAt = convertPrettyFormat(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
            this.prettyCreatedAt = createdAt;
        }

    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        try {
            this.prettyUpdatedAt = convertPrettyFormat(updatedAt);
        } catch (ParseException e) {
            e.printStackTrace();
            this.prettyUpdatedAt = updatedAt;
        }
    }

    public String getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(String pushedAt) {
        this.pushedAt = pushedAt;

        try {
            this.prettyPushedAt = convertPrettyFormat(pushedAt);
        } catch (ParseException e) {
            e.printStackTrace();
            this.prettyPushedAt = pushedAt;
        }
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


    private static String convertPrettyFormat(String dateStr) throws ParseException {
        if (dateStr!=null){
            PrettyTime prettyTime = new PrettyTime();
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date convertedDate = sourceFormat.parse(dateStr);
            return prettyTime.format(convertedDate);
        }
        else return "N/A";

    }

}

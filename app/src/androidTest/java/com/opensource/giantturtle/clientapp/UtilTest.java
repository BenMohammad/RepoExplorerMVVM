package com.opensource.giantturtle.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;
import com.opensource.giantturtle.clientapp.data.database.ModelCachedGitHubProject;
import com.opensource.giantturtle.clientapp.data.database.ModelSavedGitHubProject;
import com.opensource.giantturtle.clientapp.ui.mainscreen.MainActivity;
import com.opensource.giantturtle.clientapp.ui.savedscreen.SavedProjectsActivity;
import com.opensource.giantturtle.clientapp.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class UtilTest {

    @Test
    public void containsCorectExtras()  {

        MainActivity mainActivity = mock(MainActivity.class);
        ModelCachedGitHubProject cachedGitHubProject = new ModelCachedGitHubProject();
        cachedGitHubProject.setDescription("test123");
        Intent intent = Utils.createDeatailsIntent(mainActivity, cachedGitHubProject);
        assertNotNull(intent);
        Bundle extras = intent.getExtras();
        assertNotNull(extras);
        assertEquals("test123", extras.getString("description"));

        SavedProjectsActivity savedProjectsActivity = mock (SavedProjectsActivity.class);
        ModelSavedGitHubProject savedGitHubProject = new ModelSavedGitHubProject();
        savedGitHubProject.setDescription("test321");
        Intent intent2 = Utils.createDeatailsIntent(savedProjectsActivity, savedGitHubProject);
        assertNotNull(intent2);
        Bundle extras2 = intent2.getExtras();
        assertNotNull(extras2);
        assertEquals("test321", extras2.getString("description"));
    }

    @Test
    public void createsCorrectProject(){
        Intent intent = new Intent();
        intent.putExtra("avatarUrl", "test123");
        intent.putExtra("createdAt", "test123");
        intent.putExtra("pushedAt", "test123");
        intent.putExtra("updatedAt", "test123");
        intent.putExtra("description", "test123");
        intent.putExtra("htmlUrl", "test123");
        intent.putExtra("forksCount", Integer.toString(123));
        intent.putExtra("ownersName", "test123");
        intent.putExtra("language", "test123");
        intent.putExtra("score", Float.toString(123F));
        intent.putExtra("repoName", "test123");
        intent.putExtra("repoSize", Double.toString(123));
        intent.putExtra("id", Integer.toString(123));
        intent.putExtra("hasWiki", Boolean.toString(true));
        ModelSavedGitHubProject savedGitHubProject = Utils.projectFromIntent(intent);

        assertEquals("test123", savedGitHubProject.getAvatarUrl());
        assertEquals("test123", savedGitHubProject.getCreatedAt());
        assertEquals("test123", savedGitHubProject.getPushedAt());
        assertEquals("test123", savedGitHubProject.getUpdatedAt());
        assertEquals("test123", savedGitHubProject.getDescription());
        assertEquals(123, savedGitHubProject.getForksCount());
        assertEquals("test123", savedGitHubProject.getOwnersName());
        assertEquals("test123", savedGitHubProject.getLanguage());
        assertEquals(123F, savedGitHubProject.getScore(),1e-8);
        assertEquals("test123", savedGitHubProject.getRepoName());
        assertEquals(123, savedGitHubProject.getRepoSize(),1e-8);
        assertEquals(123, savedGitHubProject.getId());
        assertEquals(true, savedGitHubProject.hasWiki());
    }
}

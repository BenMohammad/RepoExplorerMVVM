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
}

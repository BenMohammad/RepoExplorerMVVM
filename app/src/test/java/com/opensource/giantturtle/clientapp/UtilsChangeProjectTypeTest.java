package com.opensource.giantturtle.clientapp;

import com.opensource.giantturtle.clientapp.data.database.ModelBaseGitHubProject;
import com.opensource.giantturtle.clientapp.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsChangeProjectTypeTest {

    @Test
    public void changedProjectType() {
        ModelBaseGitHubProject baseGitHubProject = new ModelBaseGitHubProject();
        baseGitHubProject.setDescription("test123");
        assertEquals("test123", Utils.changeProjectType(baseGitHubProject).getDescription());
    }
}
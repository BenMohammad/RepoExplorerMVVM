package com.opensource.giantturtle.clientapp;

import com.opensource.giantturtle.clientapp.data.database.ModelBaseGitHubProject;
import com.opensource.giantturtle.clientapp.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsTest {

    @Test
    public void changedProjectType() {
        ModelBaseGitHubProject baseGitHubProject = new ModelBaseGitHubProject();
        baseGitHubProject.setDescription("test123");
        assertEquals("test123", Utils.changeProjectType(baseGitHubProject).getDescription());
    }

    @Test
    public void convertToPretyTime(){
            String malformedDate = "2016-12T12:26:11Z";
            String correctDate = "2016-12-20T12:26:11Z";
            String nullDate = null;
            assertEquals(malformedDate, Utils.convertToPrettyTime(malformedDate));
            assertEquals("N/A", Utils.convertToPrettyTime(nullDate));
            assertThat(correctDate, not(equalTo(Utils.convertToPrettyTime(correctDate))));

    }
}
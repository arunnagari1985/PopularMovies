package com.example.android.popularmovies;

import com.example.android.popularmovies.Utilities.NetworkUtils;

import org.junit.Test;

import java.net.URL;

import static com.example.android.popularmovies.Utilities.NetworkUtils.buildUrl;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private final int SORT_BY_POPULARITY = 1;

    private final int SORT_BY_TOP_RATING = 2;


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testBuildUrl()
    {
        URL testUrl = NetworkUtils.buildUrl(SORT_BY_POPULARITY);
        //URL expected = ""; This will include API KEY hence commenting this code.
        //assertEquals(testUrl,expected);
    }


}
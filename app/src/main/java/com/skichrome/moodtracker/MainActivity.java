package com.skichrome.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mood.possibilities.Mood;
import mood.possibilities.MoodReferences;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>The first activity of the app</b>
 * <p>
 *     The launcher activity, display moods and launch other activities
 * </p>
 *
 * @author skichrome
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity
{
    /**
     * This ArrayList is used to store all moods possibilities, and wil be used to change the user interface
     */
    private List<Mood> mMoodList = new ArrayList<>();

    /**
     * <b>the method called when we start the app</b>
     *
     * @param savedInstanceState
     *      used to save bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setMoodList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * <b>This method fill the ArrayList with the different possibilities.</b>
     * <p>
     *     I use a generic class to decrease the number of class files. The ArrayList is filled with
     *     the objects in the {@link MoodReferences} enumeration.
     * </p>
     */
    private void setMoodList()
    {
        for (MoodReferences m : MoodReferences.values())
        {
            mMoodList.add(new Mood<>(m));
        }
    }
}
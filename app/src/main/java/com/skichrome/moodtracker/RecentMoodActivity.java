package com.skichrome.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import mood.possibilities.*;

/**
 * <b>Display the list of recent moods</b>
 * <p>
 *     When launched she load the saved moods in the file and she display a RecyclerView with the recent moods
 * </p>
 *
 * @see RecentMoodAdapter
 */
public class RecentMoodActivity extends AppCompatActivity
{
    /**
     * used for debug tag
     */
    public static final String FILE_OPENING_ERROR = "OBJECT_IN_STREAM_ERROR";
    /**
     * Used to store the list saved in the file
     */
    private LinkedList<Mood> mRecentMoods = new LinkedList();

    /**
     * <b>Load recent mood list in file and create a RecyclerView</b>
     *
     * @param savedInstanceState
     *      Used to save some parameters
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_mood);

        getSavedMoods();

        final RecyclerView mRecentMoodAdapter = findViewById(R.id.list_items);
        //set the positioning of the cells of RecyclerView
        mRecentMoodAdapter.setLayoutManager(new LinearLayoutManager(this));
        //Create an adapter who set the content of cells
        mRecentMoodAdapter.setAdapter(new RecentMoodAdapter(mRecentMoods));


    }

    /**
     * <b>Get the saved moods in the file if exist</b>
     */
    private void getSavedMoods()
    {

        ObjectInputStream ois;
        String mFileName = getFilesDir().getAbsolutePath() + "/" +"SavedMoods.oms";

        try
        {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File(mFileName))));

            mRecentMoods = ((LinkedList<Mood>)ois.readObject());

            ois.close();
        }
        catch (FileNotFoundException e)
        {
            Log.i("OBJECT_OUTPUT_STREAM", "File doesn't exist...");
        }
        catch (IOException e)
        {
            Log.i("", "Error during opening the file...");
        }
        catch (ClassNotFoundException e)
        {
            Log.e(FILE_OPENING_ERROR, "Class not found");
        }
    }
}


package com.skichrome.moodtracker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.LinkedList;
import mood.possibilities.*;
import mood.save.ObjectInputClass;

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
     * Used to store the list saved in the file
     */
    private LinkedList<Mood> mRecentMoods = new LinkedList<>();
    /**
     * Used to store the titles of the recent mood TextViews
     */
    private ArrayList<Integer> mTitleMoods = new ArrayList<>();
    /**
     * used to pass the context to another object
     */
    private Context mContext;

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

        //create the list of possible moods and the list of cells title
        getSavedMoods();
        setTitleMoods();

        final RecyclerView mRecentMoodAdapter = findViewById(R.id.list_items);
        //set the positioning of the cells of RecyclerView
        mRecentMoodAdapter.setLayoutManager(new LinearLayoutManager(this));
        //Create an adapter who set the content of cells
        mRecentMoodAdapter.setAdapter(new RecentMoodAdapter(mRecentMoods, mTitleMoods, this));
    }

    /**
     * <b>Get the saved moods in the file</b>
     *
     * @see mood.save.ObjectStreamClass
     */
    private void getSavedMoods()
    {
        ObjectInputClass oic = new ObjectInputClass(this);
        oic.LoadFromFile();
        mRecentMoods.clear();
        mRecentMoods = oic.getRecentMoodList();
    }

    /**
     * <b>Set the list of possible cells title</b>
     */
    private void setTitleMoods()
    {
        mTitleMoods.add(R.string.today);
        mTitleMoods.add(R.string.yesterday);
        mTitleMoods.add(R.string.two_day_ago);
        mTitleMoods.add(R.string.three_day_ago);
        mTitleMoods.add(R.string.four_day_ago);
        mTitleMoods.add(R.string.five_day_ago);
        mTitleMoods.add(R.string.six_day_ago);
        mTitleMoods.add(R.string.one_week_ago);
    }
}


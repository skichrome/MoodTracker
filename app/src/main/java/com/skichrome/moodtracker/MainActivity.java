package com.skichrome.moodtracker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import mood.possibilities.BadMood;
import mood.possibilities.HappyMood;
import mood.possibilities.Mood;
import mood.possibilities.MoodReferences;
import mood.possibilities.NormalMood;
import mood.possibilities.VeryBadMood;
import mood.possibilities.VeryHappyMood;

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
     * used to link and update the smiley
     */
    private ImageView mMoodImage;
    /**
     * used to link and update the color font of the background of the smiley
     */
    private FrameLayout mFontLayout;
    /**
     * used to link to the layout and set an onClickListener to ask the user to add a commentary
     */
    private ImageButton mBtnComment;
    /**
     * used to link to the layout, set an onClickListener and to start a new activity with the recent moods
     */
    private ImageButton mBtnRecent;

    /**
     * <b>the method called when we start the app</b>
     *
     * @param savedInstanceState
     *      used to save bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //link to the layout file
        setContentView(R.layout.activity_main);

        //link different fields to the layout
        mMoodImage = findViewById(R.id.image_mood);
        mFontLayout = findViewById(R.id.font_layout);
        mBtnComment = findViewById(R.id.add_comment);
        mBtnRecent = findViewById(R.id.recent_mood);

        //create the list of objects
        setMoodList();
        //set the image and the color for the first time, debug for the start of the project, not definitive
        setImageAndColor();

        mBtnComment.setOnClickListener(new View.OnClickListener()
        {
            /**
             * <b>Override the onClick method, show an AlertDialog</b>
             * <p>
             *     When the user click on the button, the method will show an AlertDialog who ask the user to
             *     indicate a comment about his mood
             * </p>
             *
             * @param v
             *      contains the view
             */
            @Override
            public void onClick(View v)
            {
                //When we click the button we display an Alertdialog who ask user to enter a string and store it in the mood object


            }
        });

        mBtnRecent.setOnClickListener(new View.OnClickListener()
        {
            /**
             * <b>Override the onClick method, start a new activity</b>
             * <p>
             *     When the user click on the button, the method will launch a new activity who
             *     resume the recent moods of the user if available
             * </p>
             *
             * @param v
             *      contains the view
             */
            @Override
            public void onClick(View v)
            {
                //launch a new activity with recent moods
            }
        });
    }

    /**
     *
     */
    private void setImageAndColor()
    {
        int i = 3;
        mMoodImage.setImageResource(mMoodList.get(i).getMoodReferences());
        mFontLayout.setBackgroundResource(mMoodList.get(i).getColorAssociated());
    }

    /**
     * <b>This method fill the ArrayList with object that represent the different possibilities.</b>
     * <p>
     *     This method instantiate one object of each mood type and insert it in the ArrayList mMoodList
     * </p>
     */
    private void setMoodList()
    {
        Mood VBMood = new VeryBadMood();
        mMoodList.add(VBMood);
        Mood BMood = new BadMood();
        mMoodList.add(BMood);
        Mood NMood = new NormalMood();
        mMoodList.add(NMood);
        Mood HMood = new HappyMood();
        mMoodList.add(HMood);
        Mood VHMood = new VeryHappyMood();
        mMoodList.add(VHMood);
    }
}
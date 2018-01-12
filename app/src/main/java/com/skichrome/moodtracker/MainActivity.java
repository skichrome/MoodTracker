package com.skichrome.moodtracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import mood.possibilities.BadMood;
import mood.possibilities.HappyMood;
import mood.possibilities.Mood;
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
             *      contain the view
             */
            @Override
            public void onClick(View v)
            {
                //When we click the button we display an Alertdialog who ask user to enter a string and store it in the mood object
                final AlertDialog.Builder mComment = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = getLayoutInflater();

                mComment.setTitle(R.string.comment_alertdialog);
                //inflate and set the alertdialog
                mComment.setView(inflater.inflate(R.layout.alertdialog_res, null));
                mComment.setPositiveButton(R.string.AlertDialog_positive_btn, new DialogInterface.OnClickListener()
                {
                    /**
                     * <b>For AlertDialog positive button onClickListener</b>
                     * <p>
                     *     In this method I get back the String entered by the user and if it's null I simply cancel the alertDialog like the cancel button
                     * </p>
                     * @param dialog
                     *      the DialogInterface
                     * @param which
                     *      not used here
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        EditText mAlertDialogEditText = ((AlertDialog) dialog).findViewById(R.id.TextView_alertdialog);

                        if (mAlertDialogEditText != null)
                        {
                            String mUserText = mAlertDialogEditText.getText().toString();
                            Log.e("Value of AlertDialog", mUserText);
                            //Update in the current object the string associated to the user commentaries
                        }
                        else
                        {
                            dialog.dismiss();
                        }
                    }
                });

                mComment.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    /**
                     * <b>For AlertDialog negative button onClickListener</b>
                     * <p>
                     *     In this method I simply cancel the alertDialog when the user touch the cancel button
                     * </p>
                     * @param dialog
                     *      the DialogInterface
                     * @param which
                     *      not used here
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                mComment.create();
                mComment.show();
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
        int i = 4;
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
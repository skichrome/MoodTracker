package com.skichrome.moodtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import mood.possibilities.BadMood;
import mood.possibilities.HappyMood;
import mood.possibilities.Mood;
import mood.possibilities.NormalMood;
import mood.possibilities.VeryBadMood;
import mood.possibilities.VeryHappyMood;

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
    private final String LOG_TAG_INFO_LISTMOODS = "addToRecentMoods";

    /**
     * This ArrayList is used to store all moods possibilities, and wil be used to change the user interface
     */
    private List<Mood> mMoodList = new ArrayList<>();
    /**
     * <b>Contain the list of the moods of last days</b>
     */
    private List<Mood> mRecentMood = new LinkedList<>();
    /**
     * used to link and update the smiley
     */
    private ImageView mMoodImage;
    /**
     * used to link and update the color font of the background of the smiley
     */
    private View mFontLayout;
    /**
     * used to link to the layout and set an onClickListener to ask the user to add a commentary
     */
    private ImageButton mBtnComment;
    /**
     * used to link to the layout, set an onClickListener and to start a new activity with the recent moods
     */
    private ImageButton mBtnRecent;
    /**
     * field used to detect gestures
     */
    private GestureDetectorCompat mGestureDetector;
    /**
     * Used to display and store the current mood selected by the user
     */
    private int mCurrentMood = 3;
    /**
     * get the instance from Calendar, to get the current date when the user quit the app
     */
    Calendar myDate = Calendar.getInstance();

    /**
     * <b>the method called when we start the app</b>
     *
     * @param savedInstanceState used to save bundle
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

        //create the Gesture detector
        mGestureDetector = new GestureDetectorCompat(this, new MyGestureListener());

        //create the list of objects
        setMoodList();

        //set the image and the color for the first time, debug for the start of the project, not definitive
        setImageAndColor(mCurrentMood);

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
                            //Update in the current object the string associated to the user commentaries
                            mRecentMood.get(mRecentMood.size()-1).setUserComment(mUserText);
                            Log.d("ALERT_DIALOG", mRecentMood.get(mRecentMood.size()-1).getUserComment());
                        } else
                        {
                            dialog.dismiss();
                        }
                    }
                });

                mComment.setNegativeButton(R.string.cancel_alertdialog, new DialogInterface.OnClickListener()
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
                Intent intent = new Intent(MainActivity.this, RecentMoodActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     * <p>
     *     When the user or the system quit the app we have to save the ArrayList and the current mood index in a file to be able to display it later
     * </p>
     */
    @Override
    protected void onPause()
    {
        String mFileName;

        ObjectOutputStream oos;

        mFileName = getFilesDir().getAbsolutePath() + "/" +"SavedMoods.oms";
        Log.i("File Directory and name", "File Directory and name" + mFileName);

        try
        {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                       new FileOutputStream(
                          new File(mFileName))));

            if (mRecentMood.size() != 0)
            {
                oos.writeObject(mRecentMood);
                oos.writeInt(mCurrentMood);
                Log.i("FileOutputStream", "Objects succesfully writed");
            }

            oos.close();
        }
        catch (FileNotFoundException e1)
        {
            Log.e("ERR", "File not found ...");
            e1.printStackTrace();
        }
        catch (IOException e)
        {
            Log.e("ERR", "Error when opening the file...");
            e.printStackTrace();
        }

        super.onPause();
    }

    /**
     *  <b>Used to bind the correct parameters to the layout and save mood</b>
     *  <p>
     *      Called at the start of the app but also when a scroll is detected to update the layout
     *  </p>
     *  <p>
     *      In a second part we save the current mood in the LinkedList mRecentMood. First we have to get the current date (day only),
     *      secondly we get the saved date if exist (if not we set to -1), and finally two cases are possible :
     *      <ul>
     *          <li>The List size is less than 7, so the historic isn't full, we can add moods to the historic</li>
     *          <ul>
     *              <li>The list contains elements but the date saved doesn't match with the current date : we add new mood because
     *                  it means that we are in another day</li>
     *              <li>The list contains elements and the dates match : we have to delete the last element and add the new mood.</li>
     *          </ul>
     *          <li>the List is full : we have to delete the first mood saved and add to the end of the list a new one</li>
     *      </ul>
     *  </p>
     *
     *      @param i
     *          Integer, used to set the corresponding image and color to layout
     */
    private void setImageAndColor(int i)
    {
        //set the background  and the emote
        mMoodImage.setImageResource(mMoodList.get(i).getMoodReferences());
        mFontLayout.setBackgroundResource(mMoodList.get(i).getColorAssociated());

        //get the date of the system
        int currentDay = myDate.get(Calendar.DAY_OF_MONTH);
        Log.i(LOG_TAG_INFO_LISTMOODS, "current date" + currentDay);

        //get the saved date, if doesn't set it contains -1
        int savedDay;
        if (mRecentMood.size() == 0)
            savedDay = -1;
        else
            savedDay = mRecentMood.get(mRecentMood.size() - 1).getDay();

        //the list isn't full
        if (mRecentMood.size() <= 7)
        {
            if (savedDay != currentDay)
            {
                //add new mood to list
                addToRecentMoods(i, currentDay);
                Log.i(LOG_TAG_INFO_LISTMOODS, "List not full, add new only");
            }
            //if the date is the same that the saved date we delete the last object and add a new object with the new selected mood
            else
            {
                mRecentMood.remove(mRecentMood.size() - 1);
                //add new mood to list
                addToRecentMoods(i, currentDay);
                Log.i(LOG_TAG_INFO_LISTMOODS, "List not full, delete last and add new");
            }
        }
        else
        {
            mRecentMood.remove(0);
            addToRecentMoods(i, currentDay);
            Log.i(LOG_TAG_INFO_LISTMOODS, "List full, delete first and add new");

        }
    }

    /**
     * Just add a new mood to the LinkedList mRecentMood
     *
     * @see MainActivity#mRecentMood
     * @see MainActivity#setImageAndColor(int)
     *
     * @param i
     *      Integer, used to get the focused mood
     * @param mCurrentDay
     *      Integer, contains the current day
     */
    private void addToRecentMoods(int i, int mCurrentDay)
    {
        mRecentMood.add(mMoodList.get(i));
        mRecentMood.get(mRecentMood.size() - 1).setDay(mCurrentDay);
        Log.i(LOG_TAG_INFO_LISTMOODS, "Successfully added to mRecentMood");
    }

    /**
     * <b>This method fill the ArrayList with object that represent the different possibilities.</b>
     * <p>
     * This method instantiate one object of each mood type and insert it in the ArrayList mMoodList
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

    /**
     * <b>used to set the new mood when a scroll down is detected</b>
     * <p>
     *     The value of mCurrentMood is decreased and the method verify that mCurrentMood isn't out of bound. If it is out of bound
     *     the method reset mCurrentMood to a correct value, here this is the end of the index of the ArrayList {@link MainActivity#mMoodList} contains all the moods.
     * </p>
     *
     * @see MyGestureListener
     */
    public void decreaseCurrentModPosition()
    {
        mCurrentMood--;
        if (mCurrentMood < 0) mCurrentMood = (mMoodList.size()-1);
        setImageAndColor(mCurrentMood);
    }
    /**
     * <b>used to set the new mood when a scroll up is detected</b>
     * <p>
     *     The value of mCurrentMood is increased and the method verify that mCurrentMood isn't out of bound. If it is out of bound
     *     the method reset mCurrentMood to a correct value, here this is the start of the index of the ArrayList {@link MainActivity#mMoodList} contains all the moods.
     * </p>
     *
     * @see MyGestureListener
     */
    public void increaseCurrentModPosition()
    {
        mCurrentMood++;
        if (mCurrentMood >= mMoodList.size()) mCurrentMood = 0;
        setImageAndColor(mCurrentMood);
    }

    /**
     * Called when a touch screen event was not handled by any of the views
     * under it.  This is most useful to process touch events that happen
     * outside of your window bounds, where there is no view to receive it.
     *
     * @param event The touch screen event being processed.
     * @return Return true if you have consumed the event, false if you haven't.
     * The default implementation always returns false.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * <b>Used to detect a user scroll, up or down</b>
     *
     * @author skichrome
     * @version 1.0
     */
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {

        //FIELDS
        //private static final String DEBUG_TAG = "Gesture -------->";

        //METHODS

        /**
         * detect a down touch of the user (only when the user touch the screen, not then he take off his finger
         * @param e
         *      the event detected
         * @return
         *      boolean
         */
        @Override
        public boolean onDown(MotionEvent e)
        {
            //Log.d(DEBUG_TAG, "OnDown");
            return super.onDown(e);
        }

        /**
         *<b>Used to detect a up or down scroll in the app, to display the corresponding mood</b>
         * <p>
         *     When this method detect a scroll, she compare the e1 and e2 Y values and according to the result she modify
         *     the mood displayed by calling the corresponding method in the {@link MainActivity}
         * </p>
         * @param e1
         *      The first down motion event that started the fling.
         * @param e2
         *      MotionEvent: The move motion event that triggered the current onFling.
         * @param velocityX
         *      float: The velocity of this fling measured in pixels per second along the x axis.
         * @param velocityY
         *      float: The velocity of this fling measured in pixels per second along the y axis.
         * @return
         *      Boolean
         *
         * @see MainActivity#decreaseCurrentModPosition()
         * @see MainActivity#increaseCurrentModPosition()
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if (e1.getY() < e2.getY())
            {
                decreaseCurrentModPosition();
                //Log.d(DEBUG_TAG, "top to bottom, scrolled down");
            } else if (e1.getY() > e2.getY())
            {
                increaseCurrentModPosition();
                //Log.d(DEBUG_TAG, "bottom to top, scrolled up");
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
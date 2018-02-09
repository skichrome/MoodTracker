package com.skichrome.moodtracker;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import java.util.Calendar;
import java.util.LinkedList;

import mood.functionnality.MoodsFunctionality;
import mood.possibilities.Mood;
import mood.save.ObjectInputClass;
import mood.save.ObjectOutputClass;

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
     * Debug tag for the Logs
     */
    private final String LOG_TAG_INFO_LISTMOODS = "addToRecentMoods";
    /**
     * <b>Contain the list of the moods of last days</b>
     */
    private LinkedList<Mood> mRecentMood = new LinkedList<>();
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
     * Used to share mood
     */
    private ImageButton mBtnShare;
    /**
     * field used to detect gestures
     */
    private GestureDetectorCompat mGestureDetector;
    /**
     * Used to display and store the current mood selected by the user
     */
    private int mCurrentMood;
    /**
     * get the instance from Calendar, to get the current date when the user quit the app
     */
    Calendar myDate = Calendar.getInstance();
    /**
     * contains the list of available moods and functionality available for different moods
     */
    private MoodsFunctionality mMoodsFunc;
    /**
     * Boolean used to avoid override of comment saved in last recent mood after calling onResume method
     */
    private boolean stateOfOnResume = false;

    /**
     * <b>the method called when we start the app</b>
     *
     * <p>
     *     This method do ;
     *     <ul>
     *         <li>Link to the layout</li>
     *         <li>Detect a vertical swipe</li>
     *         <li>setup the mood possibilities and display the default or the saved mood</li>
     *         <li>Detect a click on commentary button and create and display an AlertDialog, who save a commentary in the current mood</li>
     *         <li>Detect a click on recent activity button and launch it</li>
     *     </ul>
     * </p>
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
        mBtnShare = findViewById(R.id.share_mood);

        //create the Gesture detector to detect a user scroll
        mGestureDetector = new GestureDetectorCompat(this, new MyGestureListener());

        //create an instance of MoodFunctionality class, who will set up all moods possibilities and play a sound when asked
        mMoodsFunc = new MoodsFunctionality(this);

        //define the AlertDialog when the user want to write a comment
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

                //define an AlertDialog title
                mComment.setTitle(R.string.comment_alertdialog);

                //inflate and set the alertdialog
                mComment.setView(inflater.inflate(R.layout.alertdialog_res, null));

                //define the positive button of the AlertDialog
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

                        if (mAlertDialogEditText != null || !(mAlertDialogEditText.toString().equals("")))
                        {
                            //get the user comment
                            String mUserText = mAlertDialogEditText.getText().toString();
                            //Update in the current object the string associated to the user commentaries
                            mRecentMood.getLast().setUserComment(mUserText);
                            Log.d("ALERT_DIALOG", mRecentMood.getLast().getUserComment());
                        } else
                        {
                            mRecentMood.getLast().setUserComment("");
                            dialog.dismiss();
                        }
                    }
                });

                //define the negative button of the AlertDialog
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

                //create and show the AlertDialog
                mComment.create();
                mComment.show();
            }
        });

        //
        mBtnShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                //create a new intent for share the mood
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                //
                String textToSend = mRecentMood.getLast().toString();
                if (!(textToSend.equals("")))
                    intent.putExtra(Intent.EXTRA_TEXT, textToSend);

                //be sure that the system have an app who can send something
                PackageManager manager = getPackageManager();
                ComponentName component = intent.resolveActivity(manager);

                if (component != null)
                    startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
                else
                    Toast.makeText(view.getContext(), R.string.error_during_sharing, Toast.LENGTH_SHORT).show();
            }
        });

        //Launch RecentMoodActivity when the user click on this button
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
        //create an instance of ObjectOutputClass and call the method who save to the file the list of recent moods and the index of current mood
        ObjectOutputClass ooc = new ObjectOutputClass(this);
        ooc.saveToFile(mRecentMood, mCurrentMood);

        Log.d("onPause Method", mRecentMood.toString());

        super.onPause();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     *
     * <p>
     *     When the user return on this activity we load the recent mood list saved (if exist)
     *     and the current mood index.
     *     After what the method update the layout view.
     * </p>
     */
    @Override
    protected void onResume()
    {
        //when the onResume method is called, change the value to true
        stateOfOnResume = true;

        //create an instance of ObjectInputClass and load the list of recent moods (and before that we clear the list to avoid adding some unwanted moods to the list
        ObjectInputClass oic = new ObjectInputClass(this);
        oic.LoadFromFile();
        mRecentMood.clear();
        mRecentMood = oic.getRecentMoodList();

        if (mRecentMood.size() != 0)
            Log.d("Com in Last Mood Saved", mRecentMood.getLast().getUserComment());

        //get the index of current mood
        mCurrentMood = oic.getCurrentMood();

        //set the correct smiley and color
        setImageAndColor(mCurrentMood);

        Log.d("onResume Method", mRecentMood.toString());

        super.onResume();
    }

    /**
     *  <b>Used to bind the correct parameters to the layout and save current mood in the list</b>
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
        //Used to save the comment in the last recent mood saved (avoid a bug of destroying the comment in last recent mood saved, when the date is the same)
        String tempLastComment = null;

        if (mRecentMood.size() != 0)
        {
            tempLastComment = mRecentMood.getLast().getUserComment();
            Log.d("SET_IMG_COL-mRecentMood", tempLastComment);
        }

        //set the background  and the emote
        mMoodImage.setImageResource(mMoodsFunc.getCurrentMood(i).getMoodReferences());
        mFontLayout.setBackgroundResource(mMoodsFunc.getCurrentMood(i).getColorAssociated());

        //play a sound from the MoodsFunctionality class
        mMoodsFunc.playCurrentSound(i);

        //get the date of the system
        int currentDay = myDate.get(Calendar.DAY_OF_MONTH);
        Log.i(LOG_TAG_INFO_LISTMOODS, "current date" + currentDay);

        //get the saved date, if doesn't set it contains -1
        int savedDay;
        if (mRecentMood.size() == 0) savedDay = -1;
        else savedDay = mRecentMood.getLast().getDay();

        //if the list is full (more than 8 moods saved) we delete the oldest saved mood
        while (mRecentMood.size() > 8)
        {
            mRecentMood.removeFirst();
            Log.i(LOG_TAG_INFO_LISTMOODS, "List full, delete first mood of recent mood ");
        }

        //if the date stored is the same we delete the most recent mood
        if (savedDay == currentDay)
        {
            mRecentMood.removeLast();

            Log.i(LOG_TAG_INFO_LISTMOODS, "List not full, same day, remove the most recent mood ");
        }

        //Add the displayed mood to the recent mood list
        mRecentMood.addLast(mMoodsFunc.getCurrentMood(i));
        mRecentMood.getLast().setDay(currentDay);

        //when the method is called in onResume method we have to reset the comment with the good String, and reset the value of stateOfOnResume to false
        if (stateOfOnResume && tempLastComment != null)
        {
            mRecentMood.getLast().setUserComment(tempLastComment);
            stateOfOnResume = false;
        }

        Log.d(LOG_TAG_INFO_LISTMOODS, "Successfully added to mRecentMood; size of recent mood list : " + mRecentMood.size());
    }

    /**
     * <b>used to set the new mood when a scroll down is detected</b>
     * <p>
     *     The value of mCurrentMood is decreased and the method verify that mCurrentMood isn't out of bound. If it is out of bound
     *     the method reset mCurrentMood to a correct value, here this is the end of the index of the ArrayList {@link MoodsFunctionality#mMoodList} contains all the moods.
     * </p>
     *
     * @see MyGestureListener
     */
    public void decreaseCurrentModPosition()
    {
        mCurrentMood--;
        if (mCurrentMood < 0) mCurrentMood = (mMoodsFunc.getListSize()-1);
        setImageAndColor(mCurrentMood);
    }
    /**
     * <b>used to set the new mood when a scroll up is detected</b>
     * <p>
     *     The value of mCurrentMood is increased and the method verify that mCurrentMood isn't out of bound. If it is out of bound
     *     the method reset mCurrentMood to a correct value, here this is the start of the index of the ArrayList {@link MoodsFunctionality#mMoodList} contains all the moods.
     * </p>
     *
     * @see MyGestureListener
     */
    public void increaseCurrentModPosition()
    {
        mCurrentMood++;
        if (mCurrentMood >= mMoodsFunc.getListSize()) mCurrentMood = 0;
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
        //FIELDS------------------------------------------------------------------------------------
        private static final String DEBUG_TAG = "USER_SCROLLING";
        //CONSTRUCTORS------------------------------------------------------------------------------
        //GETTERS/SETTERS---------------------------------------------------------------------------
        //METHODS-----------------------------------------------------------------------------------
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
                Log.d(DEBUG_TAG, "top to bottom, scrolled down");
            } else if (e1.getY() > e2.getY())
            {
                increaseCurrentModPosition();
                Log.d(DEBUG_TAG, "bottom to top, scrolled up");
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
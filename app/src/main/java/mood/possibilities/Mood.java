package mood.possibilities;

import java.io.Serializable;

/**
 * <b>class used to create a mood object</b>
 *
 * <p>
 *     This class contains a field with the type of mood.
 *     A field who will be updated with the user commentary.
 *     A field with the date, updated in {@link com.skichrome.moodtracker.MainActivity}
 * </p>
 * <p>
 *     The {@link Mood#toString} is used for the sharing functionality
 * </p>
 *
 * @author skichrome
 * @version 1.0
 */
public class Mood implements Serializable
{
    //FIELDS----------------------------------------------------------------------------------------
    /**
     * contains the comment added by user
     */
    private String userComment;
    /**
     * contains the day when the mood is saved
     */
    private int mDay;
    /**
     * contains the color associated to the smiley
     */
    int mColorAssociated;
    /**
     * contains the type of mood
     */
    int mMoodReferences;
    /**
     * used to set the dimension of the TextView in the adapter
     *
     * @see com.skichrome.moodtracker.RecentMoodAdapter
     */
    int mDimens;

    //CONSTRUCTORS----------------------------------------------------------------------------------
    /**
     * <b>Constructor of {@link Mood}</b>
     *
     * <p>
     *     Set the default user comment, an empty String
     * </p>
     *
     * @param usrCmt
     *      String, will contains only an empty String here.
     */
    public Mood(String usrCmt)
    {
        this.userComment = usrCmt;
    }

    //GETTERS/SETTERS-------------------------------------------------------------------------------
    /**
     * <b>Able to get the user comment</b>
     *
     * @return String, the user comment
     */
    public String getUserComment()
    {
        return this.userComment;
    }
    /**
     * <b>able to set a commentary to the mood.</b>
     */
    public void setUserComment(String mCom)
    {
        this.userComment = mCom;
    }
    /**
     * get the day saved
     *
     * @return an int contain the number of the day
     */
    public int getDay()
    {
        return mDay;
    }
    /**
     * set the day when the mood is saved (when the user quit the app)
     * @param day
     *      int contains the number of the day
     */
    public void setDay(int day)
    {
        mDay = day;
    }
    /**
     * <b>Able to get the smiley reference</b>
     *
     * @return integer, the reference to the smiley
     */
    public int getMoodReferences()
    {
        return this.mMoodReferences;
    }
    /**
     * <b>Able to get the color reference</b>
     *
     * @return integer, the reference to the color
     */
    public int getColorAssociated()
    {
        return this.mColorAssociated;
    }

    /**
     * get the id of the dimension to be setted to the TextView in {@link com.skichrome.moodtracker.RecentMoodAdapter}
     *
     * @return Integer, the id.
     */
    public int getDimens()
    {
        return mDimens;
    }
    //METHODS---------------------------------------------------------------------------------------
    /**
     * used mainly for debug, describe the object
     *
     * @return a string who contain the description
     */
    public String toString()
    {
        if (!(userComment.equals("")))
        {
            return "Here a comment of my today mood : " + userComment + ".";
        }
        else return "";
    }
}

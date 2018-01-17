package mood.possibilities;

import com.skichrome.moodtracker.MainActivity;

import java.io.Serializable;

/**
 * <b>Generic class used to create a mood object</b>
 *
 * <p>
 *     This generic class contains a field with the type of mood defined in {@link MainActivity#setMoodList()}.
 *     And a field who will be updated with the user commentary.
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
    protected int mColorAssociated;
    /**
     * contains the type of mood
     */
    protected int mMoodReferences;

    //CONSTRUCTORS----------------------------------------------------------------------------------

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
    //METHODS---------------------------------------------------------------------------------------
    /**
     * used mainly for debug, describe the object
     *
     * @return a string who contain the description
     */
    public String toString()
    {
        return "User comment : " + this.userComment + "\n" + "Mood reference" + this.mMoodReferences + "Color Reference" + this.mColorAssociated;
    }
}

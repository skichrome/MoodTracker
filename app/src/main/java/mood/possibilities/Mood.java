package mood.possibilities;

import com.skichrome.moodtracker.MainActivity;

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
public class Mood
{

    //FIELDS----------------------------------------------------------------------------------------
    /**
     * contains the comment added by user
     */
    private String userComment;
    /**
     * contains the color associated to the smiley
     */
    int mColorAssociated;
    /**
     * contains the type of mood
     */
    int mMoodReferences;

    //CONSTRUCTORS----------------------------------------------------------------------------------
    /**
     * <b>Add the attribute mood to the object</b>
     */
    Mood()
    {
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

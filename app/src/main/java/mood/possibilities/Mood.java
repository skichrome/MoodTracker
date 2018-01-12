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
public class Mood<T>
{

    //FIELDS----------------------------------------------------------------------------------------
    private String userComment;
    private T mMoodReferences;

    //CONSTRUCTORS----------------------------------------------------------------------------------
    public Mood()
    {
        //this.userComment = null;
    }

    public Mood(T mMoodRef)
    {
        this.mMoodReferences = mMoodRef;
    }

    //GETTERS/SETTERS-------------------------------------------------------------------------------
    public String getUserComment()
    {
        return userComment;
    }
    public T getMoodReferences()
    {
        return mMoodReferences;
    }
    //METHODS---------------------------------------------------------------------------------------

    public String toString()
    {
        return "User comment : " + this.userComment + "\n" + "Mood reference" + this.mMoodReferences;
    }
}

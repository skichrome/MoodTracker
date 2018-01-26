package mood.possibilities;

import com.skichrome.moodtracker.R;

/**
 * Specialized class of {@link Mood}, define personalized characteristics
 *
 * @author skichrome
 * @version 1.0
 */
public class NormalMood extends Mood
{
    //FIELDS------------------------------------------------------------------------------------
    /**
     * Contains a reference to the smiley for this mood
     */
    private int mMoodRef = R.drawable.smiley_normal;
    /**
     * Contains a reference to the color for this mood
     */
    private int mBackgroundColors = R.color.cornflower_blue_65;
    /**
     * Contains a reference to the dimension for this mood
     *
     * @see com.skichrome.moodtracker.RecentMoodAdapter.MyViewHolder#display(Mood)
     */
    private int mDimenRef = R.dimen.normal_mood;

    //CONSTRUCTORS------------------------------------------------------------------------------
    /**
     * Set the characteristics of the mood object
     */
    public NormalMood()
    {
        super("");

        this.mMoodReferences = mMoodRef;
        this.mColorAssociated = mBackgroundColors;
        this.mDimens = mDimenRef;
    }

    //GETTERS/SETTERS---------------------------------------------------------------------------
    //METHODS-----------------------------------------------------------------------------------
    /**
     * Used for the sharing function of the app
     *
     * @return a string who contain the description
     */
    public String toString()
    {
        return "I am neutral today :| " + "\n" + super.toString();
    }
}

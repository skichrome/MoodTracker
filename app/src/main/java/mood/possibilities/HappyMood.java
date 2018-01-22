package mood.possibilities;

import com.skichrome.moodtracker.R;

/**
 * Specialized class of {@link Mood}, define personalized characteristics
 *
 * @author skichrome
 * @version 1.0
 */
public class HappyMood extends Mood
{
    //FIELDS------------------------------------------------------------------------------------
    /**
     * Contains a reference to the smiley for this mood
     */
    private int mMoodRef = R.drawable.smiley_happy;
    /**
     * Contains a reference to the color for this mood
     */
    private int mBackgroundColors = R.color.light_sage;
    /**
     * Contains a reference to the dimension for this mood
     *
     * @see com.skichrome.moodtracker.RecentMoodAdapter.MyViewHolder#display(Mood)
     */
    private int mDimenRef = R.dimen.high_mood;

    //CONSTRUCTORS------------------------------------------------------------------------------
    /**
     * Set the characteristics of the mood object
     */
    public HappyMood()
    {
        super("");

        this.mMoodReferences = mMoodRef;
        this.mColorAssociated = mBackgroundColors;
        this.mDimens = mDimenRef;
    }
    //GETTERS/SETTERS---------------------------------------------------------------------------
    //METHODS-----------------------------------------------------------------------------------
}

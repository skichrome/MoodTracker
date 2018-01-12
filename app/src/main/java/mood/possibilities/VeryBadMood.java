package mood.possibilities;

import com.skichrome.moodtracker.R;

/**
 * @author skichrome
 * @version 1.0
 */

public class VeryBadMood extends Mood
{
    private int mMoodRef = R.drawable.smiley_sad;
    private int mBackgroundColors = R.color.faded_red;

    public VeryBadMood()
    {
        this.mMoodReferences = mMoodRef;
        this.mColorAssociated = mBackgroundColors;
    }
}

package mood.possibilities;

import com.skichrome.moodtracker.R;

/**
 * @author skichrome
 * @version 1.0
 */

public class VeryHappyMood extends Mood
{
    private int mMoodRef = R.drawable.smiley_super_happy;
    private int mBackgroundColors = R.color.banana_yellow;

    public VeryHappyMood()
    {
        this.mMoodReferences = mMoodRef;
        this.mColorAssociated = mBackgroundColors;
    }
}

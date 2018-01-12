package mood.possibilities;

import com.skichrome.moodtracker.R;

/**
 * @author skichrome
 * @version 1.0
 */

public class HappyMood extends Mood
{
    private int mMoodRef = R.drawable.smiley_happy;
    private int mBackgroundColors = R.color.light_sage;

    public HappyMood()
    {
        this.mMoodReferences = mMoodRef;
        this.mColorAssociated = mBackgroundColors;
    }
}

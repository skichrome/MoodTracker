package mood.possibilities;

import com.skichrome.moodtracker.R;

/**
 * @author skichrome
 * @version 1.0
 */

public class BadMood extends Mood
{
    private int mMoodRef = R.drawable.smiley_disappointed;
    private int mBackgroundColors = R.color.warm_grey;

    public BadMood()
    {
        this.mMoodReferences = mMoodRef;
        this.mColorAssociated = mBackgroundColors;
    }
}

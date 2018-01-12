package mood.possibilities;

import com.skichrome.moodtracker.R;

/**
 * @author skichrome
 * @version 1.0
 */
public class NormalMood extends Mood
{
    private int mMoodRef = R.drawable.smiley_normal;
    private int mBackgroundColors = R.color.cornflower_blue_65;

    public NormalMood()
    {
        this.mMoodReferences = mMoodRef;
        this.mColorAssociated = mBackgroundColors;
    }
}

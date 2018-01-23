package mood.functionnality;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.skichrome.moodtracker.R;

import java.util.ArrayList;

import mood.possibilities.BadMood;
import mood.possibilities.HappyMood;
import mood.possibilities.Mood;
import mood.possibilities.NormalMood;
import mood.possibilities.VeryBadMood;
import mood.possibilities.VeryHappyMood;

/**
 * <b>This class is dedicated to functionality available for different moods</b>
 *
 * <p>
 *     <ul>
 *         <li>List of available moods</li>
 *         <li>Play a sound when requested for a specific mood</li>
 *     </ul>
 * </p>
 *
 * @author skichrome
 * @version 1.0
 */

public class MoodsFunctionality
{
    //FIELDS----------------------------------------------------------------------------------------
    /**
     * This ArrayList is used to store all moods possibilities, and wil be used to change the user interface. It can't be modified.
     */
    private static final ArrayList<Mood> mMoodList = new ArrayList<>();
    /**
     * This ArrayList is used to store all sound possibilities. It can't be modified.
     */
    private static final ArrayList<Integer> mSoundAvailable = new ArrayList<>();
    /**
     * Context, used in SoundPool loading method
     */
    private final Context mCt;
    /**
     * Instance of SoundPool, used to play sound when the user change the mood in the main actiivity
     */
    private static final SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    /**
     * Used to be sure that all the sounds is loaded, before to play any sound.
     */
    private boolean stateOfLoading = false;

    //CONSTRUCTORS----------------------------------------------------------------------------------
    /**
     * <b>Constructor of {@link MoodsFunctionality}. </b>
     * <p>
     *     Set the list of available moods and set the sound available.
     * </p>
     *
     * @see MoodsFunctionality#setMoodList()
     * @see MoodsFunctionality#setSoundAvailable()
     *
     * @param ct
     *      Context
     */
    public MoodsFunctionality (Context ct)
    {
        this.mCt = ct;
        setMoodList();
        setSoundAvailable();
    }
    //GETTERS/SETTERS-------------------------------------------------------------------------------

    /**
     * Used to get the mood at the index in parameter
     *
     * @param index
     *      Integer, the position of the mood requested
     * @return
     *      The mood requested
     */
    public Mood getCurrentMood(int index)
    {
        if (index < getListSize())
            return mMoodList.get(index);
        else return mMoodList.get(3);
    }

    /**
     * Get the size of the list mMoodList
     *
     * @return
     *      Integer, The size of the list
     */
    public int getListSize ()
    {
        return mMoodList.size();
    }
    //METHODS---------------------------------------------------------------------------------------
    /**
     * set the list of available moods
     */
    private void setMoodList ()
    {
        mMoodList.add(new VeryBadMood());
        mMoodList.add(new BadMood());
        mMoodList.add(new NormalMood());
        mMoodList.add(new HappyMood());
        mMoodList.add(new VeryHappyMood());
    }

    /**
     * <b>Set the index of sound to be played. </b>
     *
     * <p>
     *     Firstly, this method create a temporary ArrayList with the sound id in raw folder,
     *     Secondly she create an id for the SoundPool instance.
     *     Thirdly the override method onLoadComplete change the boolean {@link MoodsFunctionality#stateOfLoading}
     *     to true to be sure that all sounds are loaded.
     * </p>
     */
    private void setSoundAvailable()
    {
        //Temporary ArrayList to load id for SoundPool class
        ArrayList<Integer> mSound = new ArrayList<>();
        mSound.add(R.raw.very_bad_mood);
        mSound.add(R.raw.bad_mood);
        mSound.add(R.raw.normal_mood);
        mSound.add(R.raw.happy_mood);
        mSound.add(R.raw.very_happy_mood);

        //loading the id in a loop and wait the end of loading to change the value of stateOfLoading
        for (int Sound : mSound)
        {
            mSoundAvailable.add(sp.load(mCt.getApplicationContext(), Sound, 1));
        }
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
        {
            @Override
            public void onLoadComplete (SoundPool soundPool, int i, int i1)
            {
                stateOfLoading = true;
            }
        });
    }
    /**
     * Used to play the sound at the index in parameter, if all the sound is loaded
     *
     * @param index
     *      Integer, the position of the sound requested
     */
    public void playCurrentSound(int index)
    {
        if (stateOfLoading) sp.play(mSoundAvailable.get(index), 1, 1,1, 0, 1);
    }
}

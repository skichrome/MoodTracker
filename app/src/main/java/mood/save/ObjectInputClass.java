package mood.save;

import android.content.Context;
import android.util.Log;

import com.skichrome.moodtracker.MainActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import mood.possibilities.*;

/**
 * <b>Used to load from file</b>
 *
 * <p>
 *     this class only load from the file defined in superclass the recent moods and the current mood index
 * </p>
 *
 * @see ObjectStreamClass
 *
 * @author skichrome
 * @version 1.0
 */

public class ObjectInputClass extends ObjectStreamClass
{
    //FIELDS----------------------------------------------------------------------------------------
    /**
     * Declaration of the input Stream outside of the try ... catch block.
     */
    private ObjectInputStream ois = null;
    /**
     * Used as a temporary field who contains the recent mood list, must be get back by the getter getRecentMoodList to be reusable
     *
     * @see ObjectInputClass#getRecentMoodList()
     */
    private LinkedList<Mood> mRecentMoodList = new LinkedList<>();
    /**
     * Used as a temporary field who contains the recent mood index, must be get back by the getter getCurrentMood to be reusable
     *
     * @see ObjectInputClass#getCurrentMood()
     */
    private int currentMood = 3;

    //CONSTRUCTORS----------------------------------------------------------------------------------
    /**
     * <b>Constructor of {@link ObjectInputClass}</b>
     *
     * <p>
     *     Set the context by calling the superclass' constructor
     * </p>
     *
     * @see ObjectStreamClass
     *
     * @param mCt
     *      Context, contains the context
     */
    public ObjectInputClass(Context mCt)
    {
        super(mCt);
    }

    //GETTERS/SETTERS-------------------------------------------------------------------------------

    /**
     * Used to get the recent mood list.
     *
     * @see MainActivity#onResume()
     *
     * @return
     *      LinkedList<Mood>, the list of recent moods loaded from file
     */
    public LinkedList<Mood> getRecentMoodList()
    {
        return mRecentMoodList;
    }
    /**
     * Used to get the current mood index.
     *
     * @see MainActivity#onResume()
     *
     * @return
     *      Integer, the index of current moods loaded from file, the default value is 3
     */
    public int getCurrentMood()
    {
        return currentMood;
    }

    //METHODS---------------------------------------------------------------------------------------
    /**
     * <b>Load from a file the recent moods</b>
     * <p>
     *    The main method of this class, She load the recent mood list and the current mood index in the file
     *    defined in superclass.
     * </p>
     * <p>
     *     This methods doesn't return any variables but update the fields defined in this class, the values of updated
     *     parameters are accessible by calling the getters.
     * </p>
     *
     * @see ObjectStreamClass
     * @see ObjectInputClass#getCurrentMood()
     * @see ObjectInputClass#getRecentMoodList()
     */
    public void LoadFromFile()
    {
        try
        {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(mFileName)));

            mRecentMoodList = ((LinkedList<Mood>) ois.readObject());
            currentMood = ois.readInt();

        }
        catch (FileNotFoundException e)
        {
            Log.e(ERR_TAG, "File not found...");
        }
        catch (NullPointerException e)
        {
            Log.e(ERR_TAG, "Error, null pointer");
        }
        catch (ClassNotFoundException e)
        {
            Log.e(ERR_TAG, "Error, class not found");
        }
        catch (IOException e)
        {
            Log.e(ERR_TAG, "Error Input stream");
        }
        finally
        {
            try
            {
                if (ois != null)
                {
                    ois.close();

                    boolean stateOfDelete = mFileName.createNewFile();
                    Log.d(ERR_TAG, "read and delete Successful (InputStream) : " + stateOfDelete);
                }

            } catch (IOException e)
            {
                Log.e(ERR_TAG, "not able to close the stream");
            }
        }
    }
}

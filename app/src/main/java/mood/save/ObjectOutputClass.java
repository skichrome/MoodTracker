package mood.save;

import android.content.Context;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import mood.possibilities.Mood;

/**
 * <b>Used to save to file</b>
 *
 * <p>
 *     this class only save to the file defined in superclass the recent moods and the current mood index
 * </p>
 *
 * @see ObjectStreamClass
 *
 * @author skichrome
 * @version 1.0
 */

public class ObjectOutputClass extends ObjectStreamClass
{
    //FIELDS----------------------------------------------------------------------------------------
    /**
     * Declaration of the Output Stream outside of the try ... catch block.
     */
    private ObjectOutputStream oos = null;

    //CONSTRUCTORS----------------------------------------------------------------------------------
    /**
     * <b>Constructor of {@link ObjectOutputClass}</b>
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
    public ObjectOutputClass(Context mCt)
    {
        super(mCt);
    }

    //GETTERS/SETTERS-------------------------------------------------------------------------------

    //METHODS---------------------------------------------------------------------------------------
    /**
     * <b>Save to a file the recent moods</b>
     * <p>
     *    The main method of this class, She save the recent mood list and the current mood index in the file
     *    defined in superclass.
     * </p>
     *
     * @see ObjectStreamClass
     *
     * @param mRecMood
     *      LinkedList<Mood>, Contains the recent moods list
     * @param mCurrentMoodIndex
     *      Integer, contains the current mood index
     */
    public void saveToFile(LinkedList<Mood> mRecMood, int mCurrentMoodIndex)
    {
        try
        {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(mFileName)));

            oos.reset();

            Log.d(ERR_TAG, "Comment saved : " + mRecMood.getLast().getUserComment());

            oos.writeObject(mRecMood);
            oos.writeInt(mCurrentMoodIndex);
        }
        catch (NullPointerException e)
        {
            Log.e(ERR_TAG, "Error, Null pointer");
        }
        catch (IOException e)
        {
            Log.e(ERR_TAG, "Error Output stream");
        }
        finally
        {
            try
            {
                if (oos != null)
                    oos.close();
            } catch (IOException e)
            {
                Log.e(ERR_TAG, "not able to close the stream");
            }
        }
    }
}

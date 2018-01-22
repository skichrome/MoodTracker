package mood.save;

import android.content.Context;
import java.io.File;

/**
 * <b>Abstract class used to define the common stream parameters</b>
 *
 * <p>
 *     This class define the file path were will be saved the moods, and she use the context
 *     to set the correct path
 * </p>
 *
 * @author skichrome
 * @version 1.0
 */

public abstract class ObjectStreamClass
{
    //FIELDS----------------------------------------------------------------------------------------
    /**
     * Error tag for this package
     */
    static final String ERR_TAG = "OBJECT-STREAM-CLASS";
    /**
     * This field contains the abstract path of the file
     */
    protected File mFileName;


    //CONSTRUCTORS----------------------------------------------------------------------------------
    /**
     * <b>Constructor of {@link ObjectStreamClass}</b>
     *
     * <p>
     *     Set the abstract file path
     * </p>
     *
     * @param mCt
     *      contains the context for the file path
     */
    public ObjectStreamClass(Context mCt)
    {
        mFileName = new File (mCt.getFilesDir().getAbsolutePath() + File.separatorChar + "savedmoods.md");

        //mFileName = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.pathSeparator + "Android" + File.separator + "data" + File.pathSeparator + mCt.getPackageName() + "SavedMoods.oms");
    }

    //GETTERS/SETTERS-------------------------------------------------------------------------------

    //METHODS---------------------------------------------------------------------------------------
}

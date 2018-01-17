package com.skichrome.moodtracker;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import mood.possibilities.Mood;

/**
 * Use this adapter to setup a viewHolder, bind a view to an viewHolder and display it
 *
 * @author skichrome
 * @version 1.0
 */
public class RecentMoodAdapter extends RecyclerView.Adapter<RecentMoodAdapter.MyViewHolder>
{
    /**
     * contain the recent moods
     */
    private List<Mood> mRecMoods = new LinkedList<>();

    /**
     * <b>set the list with the recent moods in parameter</b>
     * @param recentMoods
     *      LinkedList with the recent moods if exist
     */
    public RecentMoodAdapter(LinkedList<Mood> recentMoods)
    {
        this.mRecMoods = recentMoods;
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount()
    {
        return mRecMoods.size();
    }


    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recent_moods_cells, parent, false);

        return new MyViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Mood item = mRecMoods.get(position);
        holder.display(item);
    }


    /**
     * <b>Can display the data</b>
     */
    public class MyViewHolder extends ViewHolder
    {
        /**
         * Used to fill the space, display some informations to be sure that the view exist (debug)
         */
        private final TextView myMood;
        /**
         * This will be coloured according to the mood type
         */
        private final LinearLayout myLinearLayout;
        /**
         * used to set a dynamic space
         */
        private final TextView mySpace;
        /**
         * the field reserved to the comment icon
         */
        private final ImageView myImage;

        /**
         * <b>Constructor of MyViewHolder</b>
         * <p>
         *     Link to the different fields in the layout, here because to decrease performance consumption,
         *     this steps will be done once.
         * </p>
         *
         * @param itemView
         *      the view
         */
        public MyViewHolder(View itemView)
        {
            super(itemView);

            myMood = itemView.findViewById(R.id.content_cells);
            myLinearLayout = itemView.findViewById(R.id.LinearLayout_content_cells);
            mySpace = itemView.findViewById(R.id.space_content_cells);
            myImage = itemView.findViewById(R.id.image_content_cells);
        }

        /**
         * Display the content on the RecyclerView
         * @param mMood
         *      Mood, contains the mood to be displayed
         */
        public void display(Mood mMood)
        {
            //display the commment icon if a comment is stored
            if (mMood.getUserComment() != null)
            {
                myImage.setImageResource(R.drawable.ic_comment_black_48px);
            }
            //hide the icon if no comment available
            else
            {
                myImage.setImageResource(0);
            }

            myLinearLayout.setBackgroundResource(mMood.getColorAssociated());
            myMood.setText(mMood.getColorAssociated());

        }
    }
}

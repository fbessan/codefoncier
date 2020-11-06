package com.besco.innova.codefoncier.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.besco.innova.codefoncier.R;
import com.besco.innova.codefoncier.models.Chapitre;
import com.besco.innova.codefoncier.models.Titre;


import java.util.ArrayList;

/**
 * Created by lenovo on 2/23/2016.
 */
public class SectionedExpandableGridAdapter extends RecyclerView.Adapter<SectionedExpandableGridAdapter.ViewHolder> {

    //data array
    private ArrayList<Object> mDataArrayList;

    //context
    private final Context mContext;

    //listeners
    private final ItemClickListener mItemClickListener;
    private final SectionStateChangeListener mSectionStateChangeListener;

    //view type
    private static final int VIEW_TYPE_SECTION = R.layout.layout_titre;
    private static final int VIEW_TYPE_ITEM = R.layout.layout_chapitre; //TODO : change this

    public SectionedExpandableGridAdapter(Context context, ArrayList<Object> dataArrayList,
                                          final GridLayoutManager gridLayoutManager, ItemClickListener itemClickListener,
                                          SectionStateChangeListener sectionStateChangeListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
        mSectionStateChangeListener = sectionStateChangeListener;
        mDataArrayList = dataArrayList;

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return isSection(position)?gridLayoutManager.getSpanCount():1;
            }
        });
    }

    private boolean isSection(int position) {
        return mDataArrayList.get(position) instanceof Titre;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false), viewType);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.viewType) {
            case VIEW_TYPE_ITEM :
                final Chapitre chapitre = (Chapitre) mDataArrayList.get(position);
                holder.itemTextView.setText(chapitre.getDescription());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.itemClicked(chapitre);
                    }
                });
                break;
            case VIEW_TYPE_SECTION :
                final Titre titre = (Titre) mDataArrayList.get(position);
                holder.sectionTextView.setText(titre.getDescription());
                holder.sectionTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.itemClicked(titre);
                    }
                });
                holder.sectionToggleButton.setChecked(titre.isExpanded);
                holder.sectionToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mSectionStateChangeListener.onSectionStateChanged(titre, isChecked);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isSection(position))
            return VIEW_TYPE_SECTION;
        else return VIEW_TYPE_ITEM;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        //common
        View view;
        int viewType;

        //for section
        TextView sectionTextView;
        ToggleButton sectionToggleButton;

        //for item
        TextView itemTextView;
        ImageView text_itemswipe;

        public ViewHolder(View view, int viewType) {
            super(view);
            final Boolean[] bool = {true};
            this.viewType = viewType;
            this.view = view;
            if (viewType == VIEW_TYPE_ITEM) {
                itemTextView = (TextView) view.findViewById(R.id.text_item);
                text_itemswipe = (ImageView) view.findViewById(R.id.text_itemswipe);

                text_itemswipe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(bool[0]) {
                            itemTextView.setSelected(true);
                            bool[0] = false;
                        }else{
                            itemTextView.setSelected(false);
                            bool[0] = true;
                        }
                    }
                });
                //itemTextView.setSelected(true);

            } else {
                sectionTextView = (TextView) view.findViewById(R.id.text_section);
                sectionTextView.setSelected(true);
                sectionToggleButton = (ToggleButton) view.findViewById(R.id.toggle_button_section);
            }
        }
    }
}

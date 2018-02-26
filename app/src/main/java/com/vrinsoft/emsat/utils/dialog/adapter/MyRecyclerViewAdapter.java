package com.vrinsoft.emsat.utils.dialog.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;


import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.utils.dialog.model.BinItems;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewFavLocationAdapter";
    private ArrayList<BinItems> mDataset;

    public MyRecyclerViewAdapter(ArrayList<BinItems> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_row_dialog_single_choice, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.mtxt_name.setChecked(getItem(position).isSelected());
        holder.mtxt_name.setText(getItem(position).getName());
    }

    BinItems getItem(int position) {
        return mDataset.get(position);
    }

    public void selectItem(int position) {
        for (BinItems binItemses : mDataset) {
            binItemses.setSelected(false);
        }
        mDataset.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
       CheckedTextView mtxt_name;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mtxt_name = (CheckedTextView) itemView.findViewById(R.id.ctItems);
        }
    }
}
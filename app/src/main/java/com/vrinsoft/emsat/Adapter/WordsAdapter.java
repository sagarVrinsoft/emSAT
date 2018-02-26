package com.vrinsoft.emsat.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.databinding.ItemWordsBinding;

import java.util.ArrayList;


/**
 * Created by dorji on 26/12/17.
 */


public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.MyViewHolder> {

    private Activity mActivity;
    private ArrayList<String> mArrayList;

    public WordsAdapter(Activity mActivity, ArrayList<String> mArrayList) {
        this.mActivity = mActivity;
        this.mArrayList = mArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_words, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.mBinding.txtParent.setText(mArrayList.get(position));
        //set touch listeners
        holder.mBinding.txtParent.setOnTouchListener(new ChoiceTouchListener());

    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemWordsBinding mBinding;

        public MyViewHolder(final View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

    }

    /**
     * ChoiceTouchListener will handle touch events on draggable views
     */
    public final class ChoiceTouchListener implements View.OnTouchListener {
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            /*
             * Drag details: we only need default behavior
             * - clip data could be set to pass data as part of drag
             * - shadow can be tailored
             */
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }


}

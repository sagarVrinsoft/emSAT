package com.vrinsoft.emsat.activity.mytest;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.apis.test_list.Result;
import com.vrinsoft.emsat.databinding.CustomRowMytestBinding;

import java.util.ArrayList;


public class MyTestListAdapter extends RecyclerView.Adapter<MyTestListAdapter.ViewHolder> {

    Activity mActivity;
    ArrayList<Result> mArrayList;
    OnClickable onClickable;
    public interface OnClickable
    {
        public void getPosition(int position);
    }
    public MyTestListAdapter(Activity mActivity, ArrayList<Result> mArrayList, OnClickable onClickable) {
        this.mActivity = mActivity;
        this.mArrayList = mArrayList;
        this.onClickable = onClickable;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomRowMytestBinding mBinding = DataBindingUtil.inflate
                (LayoutInflater.from(mActivity),
                        R.layout.custom_row_mytest, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.mBinding.swipe.setClickToClose(true);
        holder.mBinding.txtProgress.setText(""+Math.round(holder.mBinding.progressScore.getPercent())+"/"+100);
        holder.mBinding.txtCategory.setText(getArrayList().get(position).getCategoryName());
        holder.mBinding.txtSubCategory.setText(getArrayList().get(position).getSubCategoryName());
        holder.mBinding.txtEndDate.setText(getArrayList().get(position).getDateTime());
        holder.mBinding.swipe.setTag(position);
        holder.mBinding.llMain.setTag(holder.mBinding.swipe);
        holder.mBinding.llRetakeQuiz.setTag(holder.mBinding.swipe);
        holder.mBinding.llRetakeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeLayout swipeLayout = ((SwipeLayout) v.getTag());
                onClickable.getPosition((Integer) swipeLayout.getTag());
                swipeLayout.toggle(true);
            }
        });

        holder.mBinding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeLayout swipeLayout = ((SwipeLayout) v.getTag());
                swipeLayout.toggle(true);
            }
        });
    }

    public ArrayList<Result> getArrayList() {
        return mArrayList;
    }

    public void add(Result item) {
        mArrayList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomRowMytestBinding mBinding;
        public ViewHolder(CustomRowMytestBinding itemNotificationListBinding) {
            super(itemNotificationListBinding.getRoot());
            mBinding = itemNotificationListBinding;
        }
    }
}

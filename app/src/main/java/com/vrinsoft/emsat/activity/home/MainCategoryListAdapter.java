package com.vrinsoft.emsat.activity.home;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.home.model.category.Result;
import com.vrinsoft.emsat.databinding.CustomRowCategoryGridBinding;

import java.util.ArrayList;


public class MainCategoryListAdapter extends RecyclerView.Adapter<MainCategoryListAdapter.ViewHolder> {

    Activity mActivity;
    ArrayList<Result> mArrayList;
    OnClickable onClickable;
    public interface OnClickable
    {
        public void getPosition(int position);
    }
    public MainCategoryListAdapter(Activity mActivity, ArrayList<Result> mArrayList, OnClickable onClickable) {
        this.mActivity = mActivity;
        this.mArrayList = mArrayList;
        this.onClickable = onClickable;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomRowCategoryGridBinding mBinding = DataBindingUtil.inflate
                (LayoutInflater.from(mActivity),
                        R.layout.custom_row_category_grid, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.mBinding.llRightVerticalSeperator.setVisibility
                (position%2!=0?View.INVISIBLE:View.VISIBLE);

        if(position == (mArrayList.size()-1))
        {
            holder.mBinding.llBottomSeparator.setVisibility(View.INVISIBLE);
        }

        if(mArrayList.size() > 2 && position == (mArrayList.size()-2) && mArrayList.size()%2==0)
        {
            holder.mBinding.llBottomSeparator.setVisibility(View.INVISIBLE);
        }
        holder.mBinding.rlRootView.setTag(position);
        holder.mBinding.txtCategory.setText(getArrayList().get(position).getBroadcastMsg());
        holder.mBinding.rlRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickable.getPosition((Integer) v.getTag());
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
        CustomRowCategoryGridBinding mBinding;
        public ViewHolder(CustomRowCategoryGridBinding itemNotificationListBinding) {
            super(itemNotificationListBinding.getRoot());
            mBinding = itemNotificationListBinding;
        }
    }
}

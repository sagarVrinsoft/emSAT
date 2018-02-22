package com.vrinsoft.emsat.activity.subcategory;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.subcategory.model.Result;
import com.vrinsoft.emsat.databinding.CustomRowSubcategoryBinding;

import java.util.ArrayList;


public class SubCategoryListAdapter extends RecyclerView.Adapter<SubCategoryListAdapter.ViewHolder> {

    Activity mActivity;
    ArrayList<Result> mArrayList;
    OnClickable onClickable;
    public interface OnClickable
    {
        public void getPosition(int position);
    }
    public SubCategoryListAdapter(Activity mActivity, ArrayList<Result> mArrayList, OnClickable onClickable) {
        this.mActivity = mActivity;
        this.mArrayList = mArrayList;
        this.onClickable = onClickable;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomRowSubcategoryBinding mBinding = DataBindingUtil.inflate
                (LayoutInflater.from(mActivity),
                        R.layout.custom_row_subcategory, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.mBinding.txtName.setTag(position);
        holder.mBinding.txtName.setText(getArrayList().get(position).getBroadcastMsg());
        holder.mBinding.txtName.setOnClickListener(new View.OnClickListener() {
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
        CustomRowSubcategoryBinding mBinding;
        public ViewHolder(CustomRowSubcategoryBinding itemNotificationListBinding) {
            super(itemNotificationListBinding.getRoot());
            mBinding = itemNotificationListBinding;
        }
    }
}

package com.vrinsoft.emsat.activity.cms.faq.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.apis.model.faqs.BeanFaqs;

import java.util.ArrayList;

public class FAQExListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private ArrayList<BeanFaqs.Result> mCatagoryArrayList;
    Spanned answer;

    public FAQExListAdapter(Activity context, ArrayList<BeanFaqs.Result> mCatagoryArrayList) {
        this.context = context;
        this.mCatagoryArrayList = mCatagoryArrayList;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return mCatagoryArrayList.get(groupPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Nullable
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, @Nullable View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.faq_new_user_child, null);
        }

        TextView txtChild = (TextView) convertView.findViewById(R.id.txtChild);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            answer = Html.fromHtml(mCatagoryArrayList.get(groupPosition).getAnswer(), Html.FROM_HTML_MODE_COMPACT);
        }
        else
        {
            answer = Html.fromHtml(mCatagoryArrayList.get(groupPosition).getAnswer());
        }
        txtChild.setText(answer);

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    public Object getGroup(int groupPosition) {
        return mCatagoryArrayList.get(groupPosition);
    }

    public int getGroupCount() {
        return mCatagoryArrayList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Nullable
    public View getGroupView(int groupPosition, boolean isExpanded,
                             @Nullable View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.faq_new_user_group, null);
        }

        TextView txtParent = (TextView) convertView.findViewById(R.id.txtParent);
        TextView txtIndex = (TextView) convertView.findViewById(R.id.txtIndex);
        ImageView mImgIndicator = (ImageView) convertView.findViewById(R.id.imgIndicator);

        txtParent.setText(mCatagoryArrayList.get(groupPosition).getQuestion());

//        if (!mCatagoryArrayList.get(groupPosition).getQuestion().equals("") && mCatagoryArrayList.get(groupPosition).getQuestion() != null) {
//            txtIndex.setText("" + txtParent.getText().toString().charAt(0));
//        }

        mImgIndicator.setSelected(isExpanded);
        return convertView;
    }

    public ArrayList<BeanFaqs.Result> getArrayList(){
        return mCatagoryArrayList;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

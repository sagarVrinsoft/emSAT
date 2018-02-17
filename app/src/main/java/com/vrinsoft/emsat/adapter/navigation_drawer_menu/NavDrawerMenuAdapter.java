package com.vrinsoft.emsat.adapter.navigation_drawer_menu;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.databinding.ItemNavMenuBinding;
import com.vrinsoft.emsat.interfaces.OnItemClickListener;
import com.vrinsoft.emsat.model.drawer_menu.BinDrawerMenu;

import java.util.ArrayList;

public class NavDrawerMenuAdapter extends RecyclerView.Adapter<NavDrawerMenuAdapter.MenuItemHolder> {

    private Activity mActivity;
    private ArrayList<BinDrawerMenu> menuItemList;
    private ItemNavMenuBinding mItemNavMenuBinding;
    private OnItemClickListener clickListener;

    public NavDrawerMenuAdapter(Activity mActivity, ArrayList<BinDrawerMenu> menuItemList) {
        this.mActivity = mActivity;
        this.menuItemList = menuItemList;
    }

    @Override
    public MenuItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mItemNavMenuBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.item_nav_menu, parent, false);
        return new MenuItemHolder(mItemNavMenuBinding);
    }

    @Override
    public void onBindViewHolder(MenuItemHolder holder, final int position) {

        holder.mBinding.imgMenuItem.setImageResource(menuItemList.get(position).getMenuImg());
        holder.mBinding.txtMenuItem.setText(menuItemList.get(position).getMenuTitle());

        holder.mBinding.llItemRow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.getPosition(position,v);
            }
        });
    }

    public void setItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    public class MenuItemHolder extends RecyclerView.ViewHolder {

        ItemNavMenuBinding mBinding;

        public MenuItemHolder(ItemNavMenuBinding mItemNavMenuBinding) {
            super(mItemNavMenuBinding.getRoot());
            mBinding = mItemNavMenuBinding;
        }
    }
}

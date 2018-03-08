package com.vrinsoft.emsat.navigation_drawer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.adapter.navigation_drawer_menu.NavDrawerMenuAdapter;
import com.vrinsoft.emsat.databinding.NavMenuDrawerBinding;
import com.vrinsoft.emsat.interfaces.OnItemClickListener;
import com.vrinsoft.emsat.model.drawer_menu.BinDrawerMenu;

import java.util.ArrayList;

public class FragmentDrawer extends Fragment implements View.OnClickListener, OnItemClickListener {

    private static String TAG = FragmentDrawer.class.getSimpleName();
    View v;
    NavMenuDrawerBinding menuDrawerBinding;
    private MasterActivity mActivity;
    private FragmentDrawerListener drawerListener;
    private ArrayList<BinDrawerMenu> mArrayList;
    private NavDrawerMenuAdapter menuAdapter;

    public FragmentDrawer() {
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MasterActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        menuDrawerBinding = DataBindingUtil.inflate(inflater, R.layout.nav_menu_drawer, container, false);

        setMenuItemList();

        menuDrawerBinding.rvDrawer.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        menuAdapter = new NavDrawerMenuAdapter(mActivity, mArrayList);
        menuDrawerBinding.rvDrawer.setAdapter(menuAdapter);
        menuAdapter.setItemClickListener((OnItemClickListener) FragmentDrawer.this);

        setRefreshMenu();
        setProfileImg();
        return menuDrawerBinding.getRoot();
    }

    public void setRefreshMenu() {
//        menuDrawerBinding.txtUserName.setText(Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_FIRST_NAME, "") + " " +
//                Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_LAST_NAME, ""));
//
//        menuDrawerBinding.txtProfession.setText(Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_PROFESSION, ""));
    }

    public void setProfileImg() {
//        ImageUtils.loadProfileImage(mActivity, Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_USER_PROFILE, "")
//                , menuDrawerBinding.imgProfile, menuDrawerBinding.mProgressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.rlProfile:
//                getPosition(AppConstants.MENU_ITEM.MENU_ITEM_PROFILE, v);
//                break;
        }
    }

    private void setMenuItemList() {
        mArrayList = new ArrayList<>();
        mArrayList.add(new BinDrawerMenu(R.drawable.ic_menu_home,
                getString(R.string.menu_home)));
        mArrayList.add(new BinDrawerMenu(R.drawable.ic_menu_profile,
                getString(R.string.menu_profile)));
        /*mArrayList.add(new BinDrawerMenu(R.drawable.ic_menu_test,
                getString(R.string.menu_my_test)));*/
        mArrayList.add(new BinDrawerMenu(R.drawable.ic_menu_help,
                getString(R.string.menu_help)));
        mArrayList.add(new BinDrawerMenu(R.drawable.ic_menu_sign_out,
                getString(R.string.menu_sign_out)));
    }

    @Override
    public void getPosition(int pos, View v) {
        drawerListener.onDrawerItemSelected(pos);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(int position);
    }

}

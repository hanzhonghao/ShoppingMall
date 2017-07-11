package com.zhonghao.shoppingmall.type.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhonghao.shoppingmall.R;
import com.zhonghao.shoppingmall.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.type.fragment
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/2 15:43
 */

public class TypeFragment extends BaseFragment {

    private SegmentTabLayout segmentTabLayout;
    private FrameLayout fl_type;
    private List<BaseFragment> fragmentList;
    private Fragment tempFragment;
    public ListFragment listFragment;
    public TagFragment tagFragment;
    public HotSaleFragment hotSaleFragment;
    public RecyclerFragment mRecyclerFragment;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        segmentTabLayout = (SegmentTabLayout) view.findViewById(R.id.tl_1);
        fl_type = (FrameLayout) view.findViewById(R.id.fl_type);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        initFragment();

        String[] titles = {"分类左", "标签","热卖","分类右"};
        segmentTabLayout.setTabData(titles);
        segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(tempFragment, fragmentList.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        switchFragment(tempFragment, fragmentList.get(0));
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }

                    transaction.add(R.id.fl_type, nextFragment, "tagFragment").commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        listFragment = new ListFragment();
        tagFragment = new TagFragment();
        hotSaleFragment = new HotSaleFragment();
        mRecyclerFragment = new RecyclerFragment();

        fragmentList.add(listFragment);
        fragmentList.add(tagFragment);
        fragmentList.add(hotSaleFragment);
        fragmentList.add(mRecyclerFragment);

        switchFragment(tempFragment, fragmentList.get(0));
    }

}

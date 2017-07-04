package com.zhonghao.shoppingmall.community.fragment;

import android.view.View;

import com.zhonghao.shoppingmall.R;
import com.zhonghao.shoppingmall.base.BaseFragment;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.community.fragment
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/2 15:46
 */

public class CommunityFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_community, null);
        return view;
    }
}

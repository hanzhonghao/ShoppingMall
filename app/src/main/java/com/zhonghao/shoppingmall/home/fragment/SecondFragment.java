package com.zhonghao.shoppingmall.home.fragment;

import android.view.View;
import android.widget.GridView;

import com.zhonghao.shoppingmall.R;
import com.zhonghao.shoppingmall.base.BaseFragment;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.home.fragment
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/5 17:40
 */

public class SecondFragment extends BaseFragment {
    private GridView gv_tag;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_recycler, null);
        gv_tag = (GridView) view.findViewById(R.id.gv_tag);

        return view;
    }
}

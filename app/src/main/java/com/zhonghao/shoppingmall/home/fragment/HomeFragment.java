package com.zhonghao.shoppingmall.home.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhonghao.shoppingmall.R;
import com.zhonghao.shoppingmall.base.BaseFragment;
import com.zhonghao.shoppingmall.home.activity.SecondHomeActivity;
import com.zhonghao.shoppingmall.home.adapter.HomeFragmentAdapter;
import com.zhonghao.shoppingmall.home.bean.ResultBeanData;
import com.zhonghao.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.zhonghao.shoppingmall.R.id.ib_top;
import static com.zhonghao.shoppingmall.R.id.tv_message_home;
import static com.zhonghao.shoppingmall.R.id.tv_search_home;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.home.fragment
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/2 15:46
 */

public class HomeFragment extends BaseFragment {
    @BindView(tv_search_home)
    TextView mTvSearchHome;
    @BindView(tv_message_home)
    TextView mTvMessageHome;
    @BindView(R.id.rv_home)
    RecyclerView mRvHome;
    @BindView(ib_top)
    ImageButton mIbTop;
    Unbinder unbinder;
    private static final String TAG = HomeFragment.class.getSimpleName();
    private ResultBeanData.ResultBean mResultBean;
    private HomeFragmentAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        //设置点击事件
        initListener();

        return view;
    }

    @Override
    protected void initData() {
        //请求网络
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(Constants.HOME_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "首页请求失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "首页请求成功==" + response);
                        //解析数据
                        processData(response);
                    }
                });
    }

    private void processData(String json) {
        ResultBeanData resultBeanData = JSON.parseObject(json, ResultBeanData.class);
        mResultBean = resultBeanData.getResult();
        if (mResultBean != null) {
            adapter = new HomeFragmentAdapter(mContext, mResultBean);
            mRvHome.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(mContext, 1);
            //设置跨度大小监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <= 3) {
                        //隐藏
                        mIbTop.setVisibility(View.GONE);
                    } else {
                        //显示
                        mIbTop.setVisibility(View.VISIBLE);
                    }
                    //只能返回1
                    return 1;
                }
            });
            //设置布局管理者
            mRvHome.setLayoutManager(manager);
        } else {
            //没有数据
        }
        Log.e(TAG, "解析成功==" + mResultBean.getHot_info().get(0).getName());
    }

    private void initListener() {
        //置顶的监听
        mIbTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到顶部
                mRvHome.scrollToPosition(0);
            }
        });

        //设置长安监听
        mIbTop.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getActivity(),SecondHomeActivity.class));
                return false;
            }
        });

        //搜素的监听
        mTvSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        //消息的监听
        mTvMessageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

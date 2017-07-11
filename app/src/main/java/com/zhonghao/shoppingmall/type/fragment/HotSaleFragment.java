package com.zhonghao.shoppingmall.type.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.zhonghao.shoppingmall.R;
import com.zhonghao.shoppingmall.base.BaseFragment;
import com.zhonghao.shoppingmall.home.activity.WareDetailActivity;
import com.zhonghao.shoppingmall.http.OkHttpHelper;
import com.zhonghao.shoppingmall.http.SpotCallback;
import com.zhonghao.shoppingmall.shoppingcart.utils.CartStorage;
import com.zhonghao.shoppingmall.type.adapter.HotWaresAdapter;
import com.zhonghao.shoppingmall.type.bean.Page;
import com.zhonghao.shoppingmall.type.bean.Wares;
import com.zhonghao.shoppingmall.utils.ConstantsSecond;
import com.zhonghao.shoppingmall.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.type.fragment
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/5 17:15
 */

public class HotSaleFragment extends BaseFragment {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.refresh_view)
    MaterialRefreshLayout mRefreshView;
    Unbinder unbinder;

    private int pageSize = 10;
    private int curPage = 1;


    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    private List<Wares> mDatas;
    private int totalPage = 1;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH=1;
    private static  final int STATE_MORE = 2;

    private int state = STATE_NORMAL;
    private HotWaresAdapter mHotWaresAdapter;

    private CartStorage mCartStorage;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_hotsale, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        initRefreshView();
        initRecyclerView();
        mCartStorage = new CartStorage(this.getActivity());
    }

    private void initRefreshView() {

    }

    private void loadData() {
        switch (state){
            case STATE_NORMAL:
                initNormalData();
                break;
            case STATE_REFRESH:
                initRefreshData();
                break;
            case STATE_MORE:
                initMoreData();
                break;
        }
    }

    private void initMoreData() {

    }

    private void initRefreshData() {
    }

    private void initNormalData() {

        mHotWaresAdapter = new HotWaresAdapter(this.getActivity(), mDatas);
        mHotWaresAdapter.setOnItemClickListener(new HotWaresAdapter.OnItemClickListener() {
            @Override
            public void onButtonClick(View view, Wares wares) {
                mCartStorage.put(wares);
                ToastUtils.show(getActivity(),"已经添加到购物车");
            }

            @Override
            public void onLayoutClick(View view, Wares wares) {
                Intent intent = new Intent(getActivity(), WareDetailActivity.class);

                intent.putExtra(ConstantsSecond.WARE, wares);
                startActivity(intent);
            }
        });

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerview.setAdapter(mHotWaresAdapter);
//        mRecyclerview.addItemDecoration(new DividerItemDecoration(this.getActivity(),DividerItemDecoration.VERTICAL_LIST));

    }

    private void initRecyclerView() {
        String url = ConstantsSecond.API.WARES_HOT + "?curPage=" + curPage + "&pageSize=" + pageSize;
        okHttpHelper.getData(url, new SpotCallback<Page<Wares>>(this.getActivity()) {

            @Override
            public void onSuccess(Response response, Page<Wares> waresPage) {
                mDatas = waresPage.getList();
                curPage = waresPage.getCurrentPage();
                pageSize = waresPage.getPageSize();
                totalPage = waresPage.getTotalPage();
                loadData();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
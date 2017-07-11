package com.zhonghao.shoppingmall.home.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.zhonghao.shoppingmall.R;
import com.zhonghao.shoppingmall.home.adapter.HomeAdapter;
import com.zhonghao.shoppingmall.home.bean.Banner;
import com.zhonghao.shoppingmall.home.bean.Campaign;
import com.zhonghao.shoppingmall.home.bean.HomeCampaign;
import com.zhonghao.shoppingmall.http.BaseCallback;
import com.zhonghao.shoppingmall.http.OkHttpHelper;
import com.zhonghao.shoppingmall.http.SpotCallback;
import com.zhonghao.shoppingmall.utils.ConstantsSecond;
import com.zhonghao.shoppingmall.view.Toolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;

import static com.zhonghao.shoppingmall.R.id.custom_indicator;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.home.activity
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/5 17:50
 */

public class SecondHomeActivity extends Activity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(custom_indicator)
    PagerIndicator mCustomIndicator;
    @BindView(R.id.slider)
    SliderLayout mSliderLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private HomeAdapter homeAdapter;

    private List<Banner> mBanners;
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    private static final String TAG = "SecondHomeActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondhome);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        requestImages();
        initRecyclerView();
    }


    private void initRecyclerView() {

        httpHelper.getData(ConstantsSecond.API.CAMPAIGN_HOME, new SpotCallback<List<HomeCampaign>>(this) {

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {

                initData(homeCampaigns);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private void initData(final List<HomeCampaign> homeCampaigns) {

        homeAdapter = new HomeAdapter(homeCampaigns,this);
        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Campaign campaign) {
//                Intent intent = new Intent(this, WareDisplayActivity.class);
//                intent.putExtra(ConstantsSecond.COMPAINGAIN_ID,campaign.getId());
//
//                startActivity(intent);

            }
        });

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerview.setAdapter(homeAdapter);
//        mRecyclerview.addItemDecoration(new CardViewtemDecortion());
    }

    private void requestImages() {
        final String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";
        httpHelper.getData(url, new BaseCallback<List<Banner>>() {

            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanners = banners;
                initSlider();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onTokenError(Response response, int code) {

            }
        });

    }

    private void initSlider() {
        if(mBanners !=null){

            for (Banner banner : mBanners){

                DefaultSliderView defaultSliderView = new DefaultSliderView(this);
                defaultSliderView.image(banner.getImgUrl());
                defaultSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(defaultSliderView);

            }
        }

        mSliderLayout.setCustomIndicator(mCustomIndicator);
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setDuration(5000);


    }
}

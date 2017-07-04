package com.zhonghao.shoppingmall.app;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zhonghao.shoppingmall.R;
import com.zhonghao.shoppingmall.base.BaseFragment;
import com.zhonghao.shoppingmall.community.fragment.CommunityFragment;
import com.zhonghao.shoppingmall.home.fragment.HomeFragment;
import com.zhonghao.shoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.zhonghao.shoppingmall.type.fragment.TypeFragment;
import com.zhonghao.shoppingmall.user.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.rb_home)
    RadioButton mRbHome;
    @BindView(R.id.rb_type)
    RadioButton mRbType;
    @BindView(R.id.rb_community)
    RadioButton mRbCommunity;
    @BindView(R.id.rb_cart)
    RadioButton mRbCart;
    @BindView(R.id.rb_user)
    RadioButton mRbUser;
    @BindView(R.id.rg_main)
    RadioGroup mRgMain;

    private List<BaseFragment> mFragments;
    private TypeFragment mTypeFragment;
    private int position = 0;
    private BaseFragment mBaseFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        initListener();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mTypeFragment = new TypeFragment();
        mFragments.add(new HomeFragment());
        mFragments.add(mTypeFragment);
        mFragments.add(new CommunityFragment());
        mFragments.add(new ShoppingCartFragment());
        mFragments.add(new UserFragment());
    }

    private void initListener() {
        mRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        position=0;
                        break;
                    case R.id.rb_type:
                        position= 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }
                BaseFragment curFragemnt = getFragment(position);
                switchFragment(mBaseFragment,curFragemnt);
            }
        });

        mRgMain.check(R.id.rb_home);
    }

    private BaseFragment getFragment(int position) {
        if (mFragments!=null&&mFragments.size()>0){
            BaseFragment curFragment = mFragments.get(position);
            return curFragment;
        }
        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment toFragemnt) {
        if (mBaseFragment!=toFragemnt){
            mBaseFragment = toFragemnt;
            if (toFragemnt!=null){
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                if (!toFragemnt.isAdded()){
                    if (fromFragment!=null){
                        fm.hide(fromFragment);
                    }
                    fm.add(R.id.frameLayout,toFragemnt).commit();
                }else{
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        fm.hide(fromFragment);
                    }
                    fm.show(toFragemnt).commit();
                }
            }
        }
    }
}

package com.zhonghao.shoppingmall.http;

import android.content.Context;

import com.zhonghao.shoppingmall.R;
import com.zhonghao.shoppingmall.utils.ToastUtils;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.http
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/6 16:41
 */

public abstract class SimpleCallback<T> extends BaseCallback<T>{

    protected Context mContext;

    public SimpleCallback(Context context){

        mContext = context;

    }

    @Override
    public void onBeforeRequest(Request request) {

    }

    @Override
    public void onFailure(Request request, Exception e) {

    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void onTokenError(Response response, int code) {
        ToastUtils.show(mContext, mContext.getString(R.string.token_error));

//        Intent intent = new Intent();
//        intent.setClass(mContext, MainActivity.class);
//        mContext.startActivity(intent);

//        EasyGoApplication.getInstance().clearUser();

    }


}

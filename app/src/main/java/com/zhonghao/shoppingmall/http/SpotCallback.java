package com.zhonghao.shoppingmall.http;

import android.content.Context;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.http
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/6 16:40
 */

public abstract class SpotCallback<T> extends SimpleCallback<T> {

    private SpotsDialog mDialog;

    public SpotCallback(Context context){
        super(context);

        initSpotsDialog();
    }


    private  void initSpotsDialog(){

        mDialog = new SpotsDialog(mContext,"拼命加载中...");
    }

    public  void showDialog(){
        mDialog.show();
    }

    public  void dismissDialog(){
        mDialog.dismiss();
    }


    public void setLoadMessage(int resId){
        mDialog.setMessage(mContext.getString(resId));
    }


    @Override
    public void onBeforeRequest(Request request) {

        showDialog();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }


}

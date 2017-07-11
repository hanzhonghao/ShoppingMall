package com.zhonghao.shoppingmall.http;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.http
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/6 16:39
 */

public abstract class BaseCallback<T> {
    /**
     * type用于方便JSON的解析
     */
    public Type mType;

    /**
     * 把type转换成对应的类，这里不用看明白也行。
     *
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 构造的时候获得type的class
     */
    public BaseCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * 请求之前调用
     */
    public  abstract void onBeforeRequest(Request request);

    /**
     * 请求失败调用（网络问题）
     *
     * @param request
     * @param e
     */
    public abstract void onFailure(Request request, Exception e);

    /**
     * 请求成功而且没有错误的时候调用
     * 状态码大于200，小于300 时调用此方法
     *
     * @param response
     * @param t
     */
    public abstract void onSuccess(Response response, T t);

    /**
     *请求成功时调用此方法
     * @param response
     */
    public abstract  void onResponse(Response response);

    /**
     * 请求成功但是有错误的时候调用，例如Gson解析错误等
     * 状态码400，404，403，500等时调用此方法
     *
     * @param response
     * @param errorCode
     * @param e
     */
    public abstract void onError(Response response, int errorCode, Exception e);


    /**
     * Token 验证失败。状态码401,402,403 等时调用此方法
     *
     * @param response
     * @param code
     */
    public abstract void onTokenError(Response response, int code);


}

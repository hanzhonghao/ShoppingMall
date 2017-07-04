package com.zhonghao.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.utils
 * 类描述：缓存工具类
 * 创建人：小豪
 * 创建时间：2017/7/3 20:33
 */

public class CacheUtils {
    //得到保存的String类型的数据
    public static String getString(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("xiaohao", Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }

    //保存String类型的数据
    public static void putString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences("xiaohao", Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
}

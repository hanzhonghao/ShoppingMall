package com.zhonghao.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhonghao.shoppingmall.app.MyAppliction;
import com.zhonghao.shoppingmall.home.bean.GoodsBean;
import com.zhonghao.shoppingmall.type.bean.Wares;
import com.zhonghao.shoppingmall.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.shoppingcart.utils
 * 类描述：购物车数据存储类
 * 创建人：小豪
 * 创建时间：2017/7/3 20:29
 */

public class CartStorage {
    private static final String JSON_CART = "json_cart";
    private Context mContext;
    private  static  CartStorage instance;

    //SparseArray的性能优于Hashmap
    private SparseArray<GoodsBean> mSparseArray;

    public CartStorage(Context context) {
        this.mContext = context;
        mSparseArray = new SparseArray<>(100);
        //把之前存储的数据读取
        listToSparseArray();
    }


    /**
     * 从本地读取的数据加入到SparseArray中
     */
    private void listToSparseArray() {
        List<GoodsBean> goodsBeanList = getAllData();
        for (int i= 0; i<goodsBeanList.size();i++){
            GoodsBean goodsBean = goodsBeanList.get(i);
            mSparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }
    }

    /**
     * 获取本地所有的数据
     *
     * @return
     */
    public  List<GoodsBean> getAllData() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        //1.从本地获取
        String json = CacheUtils.getString(mContext, JSON_CART);
        //2使用Gson转换成列表
        if (!TextUtils.isEmpty(json)) {
            //把String转成List
            goodsBeanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return goodsBeanList;
    }

    //得到购物车后实例
    public static CartStorage getInstance(){
        if (instance ==null){
            instance = new CartStorage(MyAppliction.getContext());
        }
        return instance;
    }

    //添加数据
    public void addData(GoodsBean goodsBean){
        //1.添加到内存中SparseArray
        //如果当前数据已经存在，就修改成number递增
        GoodsBean tempData = mSparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if(tempData != null){
            //内存中有了这条数据
            tempData.setNumber(tempData.getNumber()+1);
        }else{
            tempData = goodsBean;
            tempData.setNumber(1);
        }

        //同步到内存中
        mSparseArray.put(Integer.parseInt(tempData.getProduct_id()),tempData);


        //2.同步到本地
        saveLocal();
    }

    /**
     * 删除数据
     * @param goodsBean
     */
    public void deleteData(GoodsBean goodsBean){
        //1.内存中删除
        mSparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));

        //2.把内存的保持到本地
        saveLocal();
    }


    /**
     * 更新数据
     * @param goodsBean
     */
    public void updateData(GoodsBean goodsBean){
        //1.内存中更新
        mSparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);

        //2.同步到本地
        saveLocal();
    }

    private void saveLocal() {
        //1.SparseArray转换成List
        List<GoodsBean>  goodsBeanList = sparseToList();
        //2.使用Gson把列表转换成String类型
        String json = new Gson().toJson(goodsBeanList);
        //3.把String数据保存
        CacheUtils.putString(mContext,JSON_CART,json);

    }

    private List<GoodsBean> sparseToList() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i=0;i<mSparseArray.size();i++){
            GoodsBean goodsBean = mSparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }
        return goodsBeanList;
    }

    public void put(Wares wares){
        GoodsBean goods = convertData(wares);
        put(goods);
    }

    private void put(GoodsBean goods) {

        GoodsBean temp =  mSparseArray.get(goods.getId().intValue());

        if(temp !=null){
            temp.setNumber(temp.getNumber() + 1);
        }
        else{
            temp = goods;
            temp.setNumber(1);
            mSparseArray.put(Integer.parseInt(goods.getProduct_id()),goods);
        }

        mSparseArray.put(goods.getId().intValue(), temp);
        saveLocal();
    }


    private GoodsBean convertData(Wares wares) {
        GoodsBean goods = new GoodsBean();
        goods.setId(wares.getId());
        goods.setName(wares.getName());
        goods.setFigure(wares.getImgUrl());
        goods.setPrice(wares.getPrice());
        return goods;
    }
}

package com.zhonghao.shoppingmall.home.bean;

import java.io.Serializable;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.home.bean
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/6 17:33
 */

public class HomeCampaign implements Serializable {
    private Long id;
    private String title;
    private Campaign cpOne;
    private Campaign cpTwo;
    private Campaign cpThree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Campaign getCp(int i){
        if(i == 0 || i == 3){
            return cpOne;
        }else if(i == 1 || i == 4){
            return cpTwo;
        }else {
            return cpThree;
        }
    }

    public Campaign getCpOne() {
        return cpOne;
    }

    public void setCpOne(Campaign cpOne) {
        this.cpOne = cpOne;
    }

    public Campaign getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(Campaign cpTwo) {
        this.cpTwo = cpTwo;
    }

    public Campaign getCpThree() {
        return cpThree;
    }

    public void setCpThree(Campaign cpThree) {
        this.cpThree = cpThree;
    }
}
package com.zhonghao.shoppingmall.type.bean;

import java.io.Serializable;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.type.bean
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/11 19:36
 */

public class Wares implements Serializable{
    private Long id;
    private String name;
    private String imgUrl;
    private String description;
    private Float price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}

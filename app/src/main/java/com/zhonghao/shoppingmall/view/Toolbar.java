package com.zhonghao.shoppingmall.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhonghao.shoppingmall.R;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.view
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/6 15:54
 */

public class Toolbar extends android.support.v7.widget.Toolbar{
    private LayoutInflater mInflater;
    private View mView;
    private EditText mSearchView;
    private TextView mTitle;
    private Button mRightButton;
    private ImageButton mRightImage;
    public Toolbar(Context context) {
        this(context,null);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
        setContentInsetsRelative(10, 10);

        if(attrs != null){

            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.Toolbar, defStyleAttr, 0);
            final String rightButtonText = a.getString(R.styleable.Toolbar_rightButtonText);
            if (rightButtonText != null) {
                setRightButtonText(rightButtonText);
            }

            final Drawable rightIcon = a.getDrawable(R.styleable.Toolbar_rightButtonIcon);
            if (rightIcon != null) {
                //setNavigationIcon(navIcon);
                setRightImageIcon(rightIcon);
            }

            final int rightWidth = a.getDimensionPixelSize(R.styleable.Toolbar_rightButtonWidth, -1);
            if(rightWidth > 0){
                setRightButtonWidth(rightWidth);
            }

            final int rightHeight = a.getDimensionPixelSize(R.styleable.Toolbar_rightButtonHeight, -1);
            if(rightHeight > 0){
                setRightButtonHeight(rightHeight);
            }
            setTitleTextColor(getResources().getColor(R.color.white));

            Boolean isShowSearch = a.getBoolean(R.styleable.Toolbar_isShowSearchView, false);
            if(isShowSearch){
                showSearchView();
                hideTitleView();
            }
            a.recycle();
        }
    }

    private void setRightButtonHeight(int rightHeight) {
        if(mRightButton != null){
            mRightButton.setHeight(rightHeight);
        }
    }

    private void setRightButtonWidth(int rightWidth) {
        if(mRightButton != null){
            mRightButton.setWidth(rightWidth);
        }
    }

    public void setRightButtonText(String rightButtonText) {
        if(mRightButton != null){
            mRightButton.setBackgroundColor(Color.TRANSPARENT);
            mRightButton.setText(rightButtonText);
            showRightButton();
        }
    }


    public void setRightImageIcon(Drawable icon){
        if(mRightImage != null){
            mRightImage.setImageDrawable(icon);
            showRightImage();
        }
    }

    public void setRightImageIcon(int icon){
        setRightImageIcon(getResources().getDrawable(icon));
    }


    public void setRightButtonOnClickListener(OnClickListener listener){
        mRightButton.setOnClickListener(listener);
    }

    public void setRightImageOnClickListener(OnClickListener listener){
        mRightImage.setOnClickListener(listener);
    }

    public Button getmRightButton(){
        return this.mRightButton;
    }

    public ImageButton getmRightImage(){
        return this.mRightImage;
    }

    private void initView() {
        if(mView == null){

            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.toolbar, null);

            mSearchView = (EditText) mView.findViewById(R.id.mSearchView);
            mTitle = (TextView) mView.findViewById(R.id.mTitle);
            mRightButton = (Button) mView.findViewById(R.id.mRightButton);
            mRightImage = (ImageButton) mView.findViewById(R.id.mRightImage);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);
        }

    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if(mTitle != null){
            mTitle.setText(title);
            showTitleView();
        }
    }


    @Override
    public void setTitleTextColor(int color) {
        initView();
        if (mTitle != null) {
            mTitle.setTextColor(color);
        }
    }

    public void showSearchView(){
        if(mSearchView != null){
            mSearchView.setVisibility(VISIBLE);
        }
    }

    public void hideSearchView(){
        if(mSearchView != null){
            mSearchView.setVisibility(GONE);
        }
    }

    public void showTitleView(){
        if(mTitle != null){
            mTitle.setVisibility(VISIBLE);
        }
    }

    public void hideTitleView(){
        if(mTitle != null){
            mTitle.setVisibility(GONE);
        }
    }

    public void showRightButton(){
        if(mRightImage != null && mRightButton != null){
            mRightButton.setVisibility(VISIBLE);
            mRightImage.setVisibility(GONE);
        }
    }

    public void showRightImage(){
        if(mRightImage != null && mRightButton != null){
            mRightButton.setVisibility(GONE);
            mRightImage.setVisibility(VISIBLE);
        }
    }
}
package com.zhonghao.shoppingmall.type.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghao.shoppingmall.R;
import com.zhonghao.shoppingmall.type.bean.Wares;

import java.util.List;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.type.adapter
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/11 19:47
 */

public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.HotViewHolder> {
    private Context mContext;
    private List<Wares> mWares;
    private LayoutInflater mInflater;

    public HotWaresAdapter(Context mContext, List<Wares> datas) {
        this.mContext = mContext;
        this.mWares = datas;
    }

    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.template_hot_wares, null);
        return new HotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotViewHolder holder, int position) {
        Wares wares = mWares.get(position);
        holder.simpleDraweeView.setImageURI(Uri.parse(wares.getImgUrl()));
        holder.textTitle.setTextColor(mContext.getResources().getColor(R.color.gray));
        holder.textPrice.setTextColor(mContext.getResources().getColor(R.color.crimson));
        holder.textTitle.setText(wares.getName());
        holder.textPrice.setText("￥" + wares.getPrice());
    }

    @Override
    public int getItemCount() {
        return mWares.size();
    }

    class HotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        SimpleDraweeView simpleDraweeView;
        TextView textTitle;
        TextView textPrice;
        Button btnBuy;
        RelativeLayout mWareLayout;

        public HotViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.drawee_view);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            btnBuy = (Button) itemView.findViewById(R.id.btn_add);
            mWareLayout = (RelativeLayout) itemView.findViewById(R.id.ware_layout);

            btnBuy.setOnClickListener(this);
            mWareLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Wares ware = mWares.get(getLayoutPosition());
            switch (v.getId()) {
                case R.id.btn_add:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onButtonClick(v, ware);
                    }
                    break;
                case R.id.ware_layout:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onLayoutClick(v, ware);
                    }
                    break;
            }
        }
    }

    /**
     * 因为recyclerView没有对外设置字条目的点击事件，所以需要自己写一个接口，方便外边点击事件回调
     */
    public interface OnItemClickListener {
        void onButtonClick(View view, Wares wares);

        void onLayoutClick(View view, Wares wares);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public List<Wares> getDatas() {
        return mWares;
    }

    public void addWares(List<Wares> datas) {
        addWares(0, datas);
    }

    public void addWares(int position, List<Wares> datas) {
        if (datas != null && datas.size() > 0){
            mWares.addAll(datas);
            notifyItemChanged(position,mWares.size());
        }
    }

    public void clearWares(){
        mWares.clear();
        notifyItemRangeRemoved(0,mWares.size());
    }

}

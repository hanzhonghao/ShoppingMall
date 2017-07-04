package com.zhonghao.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhonghao.shoppingmall.R;
import com.zhonghao.shoppingmall.home.bean.GoodsBean;
import com.zhonghao.shoppingmall.shoppingcart.utils.CartStorage;
import com.zhonghao.shoppingmall.shoppingcart.view.AddSubView;
import com.zhonghao.shoppingmall.utils.Constants;

import java.util.List;

/**
 * 项目名称：ShoppingMall
 * 包名：com.zhonghao.shoppingmall.shoppingcart.adapter
 * 类描述：
 * 创建人：小豪
 * 创建时间：2017/7/3 19:44
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>{
    private Context mContext;
    private List<GoodsBean> datas;
    private  TextView tvShopcartTotal;
    private  CheckBox checkboxAll;
    //完成状态下的删除CheckBox
    private  CheckBox cbAll;

    public ShoppingCartAdapter(Context context, List<GoodsBean> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    public ShoppingCartAdapter(Context context, List<GoodsBean> datas, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll) {
        this.mContext = context;
        this.datas = datas;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        showTotalPrice();
        //设置点击事件
        setListener();
        //校验是否全选
        checkAll();
    }

    /**
     * 设置全选和非全选
     * @param isCheck
     */
    public void checkAll_none(boolean isCheck) {
        if(datas != null && datas.size() >0){
            for (int i=0;i<datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setSelected(isCheck);
                notifyItemChanged(i);
            }
        }
    }

    public  void checkAll() {
        if(datas != null && datas.size() >0){
            int number = 0;
            for (int i=0;i<datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                if(!goodsBean.isSelected()){
                    //非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                }else{
                    //选中的
                    number ++;
                }
            }

            if(number == datas.size()){
                //全选
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }
        }else{
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //1.根据位置找到对应的Bean对象
                GoodsBean goodsBean = datas.get(position);
                //2.设置取反状态
                goodsBean.setSelected(!goodsBean.isSelected());
                //3.刷新状态
                notifyItemChanged(position);
                //校验是否全选
                checkAll();
                //5.重新计算价格
                showTotalPrice();
            }
        });

        //CheckBox的点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1.得到状态
                boolean isCheck = checkboxAll.isChecked();

                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);

                //3.计算总价格
                showTotalPrice();

            }
        });

        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1.得到状态
                boolean isCheck = cbAll.isChecked();

                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);


            }
        });
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("合计：" +getTotalprice());
    }

    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.item_shop_cart, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingCartAdapter.ViewHolder holder, final int position) {
        //1.根据位置到的对应的Bean对象
        final GoodsBean goodsBean = datas.get(position);
        //2设置数据
        holder.cb_gov.setChecked(goodsBean.isSelected());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(holder.iv_gov);
        holder.tv_desc_gov.setText(goodsBean.getName());
        holder.tv_price_gov.setText("￥" + goodsBean.getCover_price());
        holder.ddSubView.setValue(goodsBean.getNumber());
        holder.ddSubView.setMinValue(1);
        holder.ddSubView.setMaxValue(8);

        //设置商品数量的变化
        holder.ddSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                //1当前列表内存中
                goodsBean.setNumber(value);
                //2本地更新
                CartStorage.getInstance().updateData(goodsBean);
                //3.刷新适配器
                notifyItemChanged(position);
                //4.再次计算价格
                showTotalPrice();
            }
        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 计算总价格
     * @return
     */
    private double getTotalprice() {
        double totalPrice=0.0;
        if (datas!= null&&datas.size()>0){
            for (int i =0 ;i<datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isSelected()){
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getNumber())*Double.valueOf(goodsBean.getCover_price());
                }
            }
        }
        return totalPrice;
    }

    public void deleteData() {
        if(datas != null && datas.size() >0){
            for (int i=0;i<datas.size();i++){
                //删除选中的
                GoodsBean goodsBean = datas.get(i);
                if(goodsBean.isSelected()){
                    //内存-把移除
                    datas.remove(goodsBean);
                    //保持到本地
                    CartStorage.getInstance().deleteData(goodsBean);
                    //刷新
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private AddSubView ddSubView;

        public ViewHolder(View itemView) {
            super(itemView);
            cb_gov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            iv_gov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            ddSubView = (AddSubView) itemView.findViewById(R.id.ddSubView);
            //设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface  OnItemClickListener{
        /**
         * 当点击某条的时候被回调
         * @param position
         */
        public void  onItemClick( int position);
    }
    private OnItemClickListener onItemClickListener;

    /**
     * 设置item的监听
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

package com.tsungweiho.ilovezappos.models;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tsungweiho.ilovezappos.MainActivity;
import com.tsungweiho.ilovezappos.R;
import com.tsungweiho.ilovezappos.constants.FragmentTag;
import com.tsungweiho.ilovezappos.database.SQLCartDB;
import com.tsungweiho.ilovezappos.fragments.CartFragment;
import com.tsungweiho.ilovezappos.objects.Product;

import java.util.ArrayList;

/**
 * Created by tsung on 2017/2/5.
 */

public class CartListVIewAdapter extends BaseAdapter implements FragmentTag {

    private Context context;
    private CartFragment cartFragment;
    private ArrayList<Product> allCartItemList;
    //functions
    private SQLCartDB sqlCartDB;
    private FragmentManager fm;

    public CartListVIewAdapter(Context context, ArrayList<Product> allCartItemList) {
        this.context = context;
        this.allCartItemList = allCartItemList;
    }

    @Override
    public int getCount() {
        return allCartItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return allCartItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Not used
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater li = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = li.inflate(R.layout.fragment_cart_list_item, null);

            //Link views
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.ivProduct = (ImageView) convertView.findViewById(R.id.fragment_cart_list_item_iv_product);
            viewHolder.tvProduct = (TextView) convertView.findViewById(R.id.fragment_cart_list_item_tv_product);
            viewHolder.tvBrand = (TextView) convertView.findViewById(R.id.fragment_cart_list_item_tv_brandname);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.fragment_cart_list_item_tv_price);
            viewHolder.tvOriPrice = (TextView) convertView.findViewById(R.id.fragment_cart_list_item_tv_oriprice);
            viewHolder.tvLink = (TextView) convertView.findViewById(R.id.fragment_cart_list_item_tv_viewmore);
            viewHolder.ibRemove = (ImageButton) convertView.findViewById(R.id.fragment_cart_list_item_btn_remove);

            final Product productAtPos = allCartItemList.get(position);
            if (!"".equalsIgnoreCase(productAtPos.getImgUrl())) {
                Picasso.with(viewHolder.ivProduct.getContext()).load(productAtPos.getImgUrl()).resize((int) MainActivity.getContext().getResources().getDimension(R.dimen.activity_main_img_size), (int) MainActivity.getContext().getResources().getDimension(R.dimen.activity_main_img_size)).into(viewHolder.ivProduct);
            } else {
                viewHolder.ivProduct.setImageDrawable(MainActivity.getContext().getResources().getDrawable(R.mipmap.img_product_preload));
            }
            viewHolder.tvProduct.setText(productAtPos.getProductName());
            viewHolder.tvBrand.setText(productAtPos.getBrandName());
            viewHolder.tvPrice.setText(productAtPos.getPrice());
            if (!"0%".equalsIgnoreCase(productAtPos.getPercentOff())) {
                viewHolder.tvOriPrice.setVisibility(View.VISIBLE);
                viewHolder.tvOriPrice.setPaintFlags(viewHolder.tvOriPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.tvOriPrice.setText(productAtPos.getOriginalPrice());
            } else {
                viewHolder.tvOriPrice.setVisibility(View.GONE);
            }
            if (null == fm)
                fm = ((MainActivity) MainActivity.getContext()).getSupportFragmentManager();
            cartFragment = (CartFragment) fm.findFragmentByTag(CartFragment);
            viewHolder.tvLink.setPaintFlags(viewHolder.tvLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            viewHolder.tvLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartFragment.openBrowserByUrl(productAtPos.getProductUrl());
                }
            });
            viewHolder.ibRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null == sqlCartDB)
                        sqlCartDB = new SQLCartDB(context);
                    sqlCartDB.deleteSingleProduct(sqlCartDB.getAllIdList().get(position));
                    cartFragment.setCartList();
                    ((MainActivity) MainActivity.getContext()).setTvCartItemCount();
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView ivProduct;
        TextView tvProduct;
        TextView tvBrand;
        TextView tvPrice;
        TextView tvOriPrice;
        TextView tvLink;
        ImageButton ibRemove;
    }
}

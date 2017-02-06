package com.tsungweiho.ilovezappos.models;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tsungweiho.ilovezappos.MainActivity;
import com.tsungweiho.ilovezappos.R;
import com.tsungweiho.ilovezappos.constants.FragmentTag;
import com.tsungweiho.ilovezappos.database.SQLCartDB;
import com.tsungweiho.ilovezappos.fragments.ProductFragment;
import com.tsungweiho.ilovezappos.objects.Product;

/**
 * Created by tsung on 2017/2/5.
 */

public class DialogManager implements FragmentTag {

    private Context context;
    // Link to ProductFragment
    private FragmentManager fm;
    private ProductFragment productFragment;

    public DialogManager(Context context) {
        this.context = context;
        if (null == fm)
            fm = ((MainActivity) MainActivity.getContext()).getSupportFragmentManager();
        productFragment = (ProductFragment) fm.findFragmentByTag(ProductFragment);
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }

    public void showAlertDialogOnFAB(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (null == fm)
                    fm = ((MainActivity) MainActivity.getContext()).getSupportFragmentManager();
                ProductFragment productFragment = (ProductFragment) fm.findFragmentByTag(ProductFragment);
                productFragment.mAnimUtilities.showFABAnim(productFragment.btnAddToCart);
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                productFragment.mAnimUtilities.showFABAnim(productFragment.btnAddToCart);
            }
        });

        alertDialog.show();
    }

    public void showProductDialog(final Product product) {
        if (null == product) {
            showAlertDialogOnFAB(context.getString(R.string.fragment_product_err_dialog_title), context.getString(R.string.fragment_product_err_dialog_msg));
            return;
        }

        final SQLCartDB sqlCartDB = new SQLCartDB(context);
        final Dialog dialog = new Dialog(context);
        LayoutInflater li = LayoutInflater.from(context);
        View dialogView = li.inflate(R.layout.view_product_dialog, null);
        dialog.setContentView(dialogView);

        ImageView ivProduct = (ImageView) dialogView.findViewById(R.id.view_product_dialog_iv_product);
        TextView tvBrand = (TextView) dialogView.findViewById(R.id.view_product_dialog_tv_brand);
        TextView tvProduct = (TextView) dialogView.findViewById(R.id.view_product_dialog_tv_product);
        TextView tvPrice = (TextView) dialogView.findViewById(R.id.view_product_dialog_tv_price);
        TextView tvOriginalPrice = (TextView) dialogView.findViewById(R.id.view_product_dialog_tv_oriprice);
        LinearLayout llDialog = (LinearLayout) dialogView.findViewById(R.id.view_product_dialog_layout);
        LinearLayout btnAdd = (LinearLayout) dialogView.findViewById(R.id.view_product_dialog_ll_add);
        llDialog.setMinimumWidth((int) (MainActivity.windowWidth * 0.75));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sqlCartDB.insertDB(product);
                ((MainActivity) MainActivity.getContext()).setTvCartItemCount();
                productFragment.setAnimAddtoCart();
            }
        });

        if (!"".equalsIgnoreCase(product.getImgUrl())) {
            Picasso.with(ivProduct.getContext()).load(product.getImgUrl()).resize((int) MainActivity.getContext().getResources().getDimension(R.dimen.activity_main_img_size), (int) MainActivity.getContext().getResources().getDimension(R.dimen.activity_main_img_size)).into(ivProduct);
        } else {
            ivProduct.setImageDrawable(MainActivity.getContext().getResources().getDrawable(R.mipmap.img_product_preload));
        }

        if (!"0%".equalsIgnoreCase(product.getPercentOff())) {
            tvOriginalPrice.setVisibility(View.VISIBLE);
            tvPrice.setTextColor(context.getResources().getColor(R.color.light_red));
            tvOriginalPrice.setText(product.getOriginalPrice());
            tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tvOriginalPrice.setVisibility(View.GONE);
        }

        tvBrand.setText(product.getBrandName());
        tvProduct.setText(product.getProductName());
        tvPrice.setText(product.getPrice());

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                productFragment.mAnimUtilities.showFABAnim(productFragment.btnAddToCart);
            }
        });

        dialog.show();
    }
}

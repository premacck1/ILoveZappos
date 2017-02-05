package com.tsungweiho.ilovezappos.models;

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tsungweiho.ilovezappos.MainActivity;
import com.tsungweiho.ilovezappos.R;

/**
 * Created by tsung on 2017/2/4.
 */

public class CustomBindingAdapter {
    // Separate prices row
    private static String SEPARATE_TAG = MainActivity.getContext().getString(R.string.app_name);

    @BindingAdapter({"bind:imgurl"})
    public static void loadImage(ImageView imageView, String url) {
        if (!"".equalsIgnoreCase(url)) {
            Picasso.with(imageView.getContext()).load(url).resize((int) MainActivity.getContext().getResources().getDimension(R.dimen.activity_main_img_size), (int) MainActivity.getContext().getResources().getDimension(R.dimen.activity_main_img_size)).into(imageView);
        } else {
            imageView.setImageDrawable(MainActivity.getContext().getResources().getDrawable(R.mipmap.img_product_preload));
        }
    }

    @BindingAdapter({"bind:price"})
    public static void loadPrice(TextView textView, String pricesrow) {
        if (!"".equalsIgnoreCase(pricesrow) && !"null".equalsIgnoreCase(pricesrow) && null != pricesrow){
            if (!"0%".equalsIgnoreCase(pricesrow.split(SEPARATE_TAG)[2])) {
                textView.setTextColor(MainActivity.getContext().getResources().getColor(R.color.light_red));
            } else {
                textView.setTextColor(MainActivity.getContext().getResources().getColor(R.color.light_gray));
            }
            textView.setText(pricesrow.split(SEPARATE_TAG)[1]);
        }
    }

    @BindingAdapter({"bind:originalprice"})
    public static void loadOriginalPrice(TextView textView, String pricesrow) {
        if (!"".equalsIgnoreCase(pricesrow) && !"null".equalsIgnoreCase(pricesrow) && null != pricesrow){
            if (!"0%".equalsIgnoreCase(pricesrow.split(SEPARATE_TAG)[2])) {
                textView.setVisibility(View.VISIBLE);
                textView.setTextColor(MainActivity.getContext().getResources().getColor(R.color.light_gray));
                textView.setText(pricesrow.split(SEPARATE_TAG)[0]);
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                textView.setVisibility(View.GONE);
            }
        }
    }

    @BindingAdapter({"bind:percentageoff"})
    public static void loadPercentageOff(TextView textView, String percentageOff) {
        if ("0%".equalsIgnoreCase(percentageOff) || "null".equalsIgnoreCase(percentageOff) || null == percentageOff) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(percentageOff + " off");
        }
    }
}

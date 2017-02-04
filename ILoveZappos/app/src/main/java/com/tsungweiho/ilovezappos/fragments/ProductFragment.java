package com.tsungweiho.ilovezappos.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsungweiho.ilovezappos.R;
import com.tsungweiho.ilovezappos.databinding.FragmentProductBinding;
import com.tsungweiho.ilovezappos.utilities.PHPUtilities;

/**
 * Created by mike on 2/3/17.
 */

public class ProductFragment extends Fragment {
    //Fragment View
    View view;
    private static Context context;
    private String TAG = "ProductFragment";
    //Functions
    private PHPUtilities mPHPUtilities;
    //UI Widgets
    private FloatingActionButton btnAddToCart;
    private LinearLayout llProduct;
    private ImageView ivProduct;
    private TextView tvStart, tvProduct, tvProductBrand, tvCartItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        FragmentProductBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
        view = binding.getRoot();
        init();
        return view;
    }

    private void init(){
        mPHPUtilities = new PHPUtilities(context);
        findViews();
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void findViews(){
        llProduct = (LinearLayout) view.findViewById(R.id.fragment_product_layout_product);
        ivProduct = (ImageView) view.findViewById(R.id.fragment_product_iv_product);
        tvProduct = (TextView) view.findViewById(R.id.fragment_product_tv_product);
        tvProductBrand = (TextView) view.findViewById(R.id.fragment_product_tv_brand);
        tvCartItemCount = (TextView) view.findViewById(R.id.activity_main_tv_cart_count);
//        tvCartItemCount.setVisibility(View.GONE);
        btnAddToCart = (FloatingActionButton) view.findViewById(R.id.fragment_product_btn_add);
        tvStart = (TextView) view.findViewById(R.id.fragment_product_tv_start);
    }

    private class TaskQueryProduct extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                result = mPHPUtilities.queryProduct(params[0]);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            try {

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, e.getMessage() + result);
            }
        }
    }
}

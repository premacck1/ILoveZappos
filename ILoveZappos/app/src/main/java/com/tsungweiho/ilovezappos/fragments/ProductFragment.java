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
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsungweiho.ilovezappos.R;
import com.tsungweiho.ilovezappos.constants.FragmentTag;
import com.tsungweiho.ilovezappos.constants.ProductResponseConstants;
import com.tsungweiho.ilovezappos.databinding.FragmentProductBinding;
import com.tsungweiho.ilovezappos.models.DialogManager;
import com.tsungweiho.ilovezappos.objects.Product;
import com.tsungweiho.ilovezappos.utilities.AnimUtilities;
import com.tsungweiho.ilovezappos.utilities.PHPUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by mike on 2/3/17.
 */

public class ProductFragment extends Fragment implements ProductResponseConstants, FragmentTag {
    //Fragment View
    View view;
    private static Context context;
    private String TAG = "ProductFragment";
    //Functions
    private PHPUtilities mPHPUtilities;
    private AnimUtilities mAnimUtilities;
    private DialogManager mDialogManager;
    private FragmentProductBinding binding;
    private Product currentProduct;
    //UI Widgets
    private FloatingActionButton btnAddToCart;
    private LinearLayout llProduct, btnViewWeb;
    private TextView tvStart, tvCartItemCount;
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
        view = binding.getRoot();
        init();
        return view;
    }

    private void init() {
        mPHPUtilities = new PHPUtilities(context);
        mAnimUtilities = new AnimUtilities(context);
        mDialogManager = new DialogManager(context);
        findViews();
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogManager.showProductDialog(currentProduct, "");
            }
        });
    }

    private void findViews() {
        llProduct = (LinearLayout) view.findViewById(R.id.fragment_product_layout_product);
        llProduct.setVisibility(View.GONE);
        btnViewWeb = (LinearLayout) view.findViewById(R.id.fragment_product_ll_viewmore);
        btnViewWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(currentProduct.getProductUrl());
            }
        });
        tvCartItemCount = (TextView) view.findViewById(R.id.activity_main_tv_cart_count);
        btnAddToCart = (FloatingActionButton) view.findViewById(R.id.fragment_product_btn_add);
        btnAddToCart.bringToFront();
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogManager.showProductDialog(currentProduct, "");
            }
        });
        tvStart = (TextView) view.findViewById(R.id.fragment_product_tv_start);
        webView = (WebView) view.findViewById(R.id.fragment_product_webview);
    }

    public void queryTerm(String queryString) {
        tvStart.setVisibility(View.GONE);
        new TaskQueryProduct().execute(queryString);
    }

    @Override
    public void OnResume() {

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
                JSONObject jsonObject = new JSONObject(result);
                if (!"".equalsIgnoreCase(result)) {
                    if (jsonObject.getInt(totalResultCount) > 0) {
                        mAnimUtilities.setllSearchResultAnimToVisible(llProduct);
                        JSONArray jsonArray = jsonObject.getJSONArray(results);
                        //only return the first search result
                        JSONObject jsonData = jsonArray.getJSONObject(0);
                        currentProduct = new Product(jsonData.getString(brandName), jsonData.getString(imgUrl), jsonData.getString(productId), jsonData.getString(originalPrice), jsonData.getString(colorId), jsonData.getString(price), jsonData.getString(percentOff), jsonData.getString(productUrl), jsonData.getString(productName));
                        binding.setProduct(currentProduct);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, e.getMessage() + result);
            }
        }
    }
}

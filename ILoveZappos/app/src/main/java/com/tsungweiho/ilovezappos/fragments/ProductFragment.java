package com.tsungweiho.ilovezappos.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
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
    public AnimUtilities mAnimUtilities;
    private DialogManager dialogManager;
    private FragmentProductBinding binding;
    private SharedPreferences sharedPreferences;
    private Handler animHandler;
    // Sharepreference params
    private Product currentProduct;
    private String CURRENT_PRODUCT = "CURRENT_PRODUCT";
    //UI Widgets
    public FloatingActionButton btnAddToCart;
    private LinearLayout llProduct, btnViewWeb;
    private TextView tvStart;

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
        dialogManager = new DialogManager(context);
        findViews();
    }

    private void findViews() {
        btnAddToCart = (FloatingActionButton) view.findViewById(R.id.fragment_product_btn_add);
        btnAddToCart.bringToFront();
        btnAddToCart.setVisibility(View.GONE);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnimUtilities.hideFABAnim(btnAddToCart);
                dialogManager.showProductDialog(currentProduct);
            }
        });
        // The floating action button need to wait its anchor (llProduct) ready
        llProduct = (LinearLayout) view.findViewById(R.id.fragment_product_layout_product);
        llProduct.setVisibility(View.INVISIBLE);
        ViewTreeObserver viewTreeObserver = llProduct.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mAnimUtilities.showFABAnim(btnAddToCart);
            }
        });
        btnViewWeb = (LinearLayout) view.findViewById(R.id.fragment_product_ll_viewmore);
        btnViewWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentProduct.getProductUrl()));
                startActivity(browserIntent);
            }
        });
        tvStart = (TextView) view.findViewById(R.id.fragment_product_tv_start);
    }

    public void queryTerm(String queryString) {
        tvStart.setVisibility(View.GONE);
        new TaskQueryProduct().execute(queryString);
    }

    public void setAnimAddtoCart() {
        mAnimUtilities.showAddToCartFABAnim(btnAddToCart);
        animHandler = new Handler();
        animHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAnimUtilities.switchFABIconAnim(btnAddToCart);
            }
        }, 3000);
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CURRENT_PRODUCT, "");
        currentProduct = gson.fromJson(json, Product.class);

        if (null != currentProduct) {
            binding.setProduct(currentProduct);
            llProduct.setVisibility(View.VISIBLE);
            tvStart.setVisibility(View.GONE);
        } else {
            tvStart.setVisibility(View.VISIBLE);
            tvStart.setText(context.getString(R.string.fragment_product_start_search));
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String savedProduct = gson.toJson(currentProduct);
        prefsEditor.putString(CURRENT_PRODUCT, savedProduct);
        prefsEditor.apply();
    }

    private class TaskQueryProduct extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llProduct.setVisibility(View.INVISIBLE);
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
                if (!"".equalsIgnoreCase(result) && null != result) {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt(totalResultCount) > 0) {
                        JSONArray jsonArray = jsonObject.getJSONArray(results);
                        //only return the first search result
                        JSONObject jsonData = jsonArray.getJSONObject(0);
                        currentProduct = new Product(jsonData.getString(brandName), jsonData.getString(imgUrl), jsonData.getString(productId), jsonData.getString(originalPrice), jsonData.getString(colorId), jsonData.getString(price), jsonData.getString(percentOff), jsonData.getString(productUrl), jsonData.getString(productName));
                        binding.setProduct(currentProduct);
                        llProduct.setVisibility(View.VISIBLE);
                    } else {
                        tvStart.setText(context.getString(R.string.fragment_product_no_result));
                        tvStart.setVisibility(View.VISIBLE);
                    }
                } else {
                    dialogManager.showAlertDialog(context.getString(R.string.fragment_product_noresult_dialog_title), context.getString(R.string.fragment_product_noresult_dialog_msg));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, e.getMessage() + result);
            }
        }
    }
}

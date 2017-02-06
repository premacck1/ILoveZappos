package com.tsungweiho.ilovezappos.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tsungweiho.ilovezappos.R;
import com.tsungweiho.ilovezappos.database.SQLCartDB;
import com.tsungweiho.ilovezappos.models.CartListVIewAdapter;

/**
 * Created by mike on 2/3/17.
 */

public class CartFragment extends Fragment {
    //Fragment View
    View view;
    private static Context context;
    //functions
    private SQLCartDB sqlCartDB;
    private CartListVIewAdapter cartListVIewAdapter;
    //UI
    private ListView lvCart;
    private TextView tvNoRecord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        context = getActivity();
        init();
        return view;
    }

    private void init() {
        sqlCartDB = new SQLCartDB(context);
        lvCart = (ListView) view.findViewById(R.id.fragment_cart_listview);
        tvNoRecord = (TextView) view.findViewById(R.id.fragment_cart_tv_norecord);
        setCartList();
    }

    public void openBrowserByUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void setCartList() {
        if (null == sqlCartDB) {
            sqlCartDB = new SQLCartDB(context);
        }
        if (sqlCartDB.getCartItemCount() > 0) {
            tvNoRecord.setVisibility(view.GONE);
            lvCart.setVisibility(View.VISIBLE);
            cartListVIewAdapter = new CartListVIewAdapter(context, sqlCartDB.getAllCartList());
            lvCart.setAdapter(cartListVIewAdapter);
        } else {
            tvNoRecord.setVisibility(view.VISIBLE);
            lvCart.setVisibility(View.GONE);
        }
    }
}

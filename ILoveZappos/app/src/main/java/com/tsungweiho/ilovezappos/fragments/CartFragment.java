package com.tsungweiho.ilovezappos.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsungweiho.ilovezappos.R;

/**
 * Created by mike on 2/3/17.
 */

public class CartFragment extends Fragment {
    //Fragment View
    View view;
    private static Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        context = getActivity();
        init();
        return view;
    }

    private void init(){

    }
}

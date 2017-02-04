package com.tsungweiho.ilovezappos;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsungweiho.ilovezappos.constants.FragmentTag;
import com.tsungweiho.ilovezappos.fragments.ProductFragment;
import com.tsungweiho.ilovezappos.objects.Product;
import com.tsungweiho.ilovezappos.utilities.AnimUtilities;
import com.tsungweiho.ilovezappos.utilities.PHPUtilities;


public class MainActivity extends AppCompatActivity implements FragmentTag{

    private String TAG = "MainActivity";
    private MainListener mainListener;
    //Functions
    private AnimUtilities mAnimUtilities;
    //UI widgets
    private ImageButton btnSearch, btnCart;
    private EditText edSearch;
    private TextView tvCartItemCount;
    private boolean ifSearchShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Product product = new Product("Test", "User");
        init();
    }

    private void init() {
        mAnimUtilities = new AnimUtilities(this);
        mainListener = new MainListener();
        btnSearch = (ImageButton) findViewById(R.id.activity_main_btn_search);
        btnSearch.setOnClickListener(mainListener);
        btnCart = (ImageButton) findViewById(R.id.activity_main_btn_cart);
        edSearch = (EditText) findViewById(R.id.activity_main_ed_search);
        tvCartItemCount = (TextView) findViewById(R.id.activity_main_tv_cart_count);
        edSearch.setOnEditorActionListener(mainListener);
        setFragment(new ProductFragment(), ProductFragment);
    }


    private class MainListener implements View.OnClickListener, TextView.OnEditorActionListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.activity_main_btn_search:
                    if (ifSearchShown) {
                        edSearch.setVisibility(View.GONE);
                        ifSearchShown = false;
                    } else {
                        mAnimUtilities.setEdAnimToVisible(edSearch);
                        ifSearchShown = true;
                    }
                    break;
                case R.id.activity_main_btn_cart:
                    break;
            }
        }

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //TODO search
                return true;
            }
            return false;
        }
    }

    public void setFragment(Fragment fragment, String fragmentTag) {
        //action bar switch
        if (fragmentTag.equalsIgnoreCase(ProductFragment)){

        } else {

        }

        try {
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            // TODO: 2015/11/13 don't addtoBackStacks or load all fragment, and chagne them. Don't save same fragment in stacks.
            transaction.replace(R.id.activity_main_container, fragment, fragmentTag).addToBackStack(fragmentTag).commit();
            transaction.commit();

        } catch (Exception e) {
            Log.e("MainActivity", e.toString());
        }
    }
}

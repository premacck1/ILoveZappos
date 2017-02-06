package com.tsungweiho.ilovezappos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tsungweiho.ilovezappos.constants.FragmentTag;
import com.tsungweiho.ilovezappos.database.SQLCartDB;
import com.tsungweiho.ilovezappos.fragments.CartFragment;
import com.tsungweiho.ilovezappos.fragments.ProductFragment;
import com.tsungweiho.ilovezappos.models.DialogManager;
import com.tsungweiho.ilovezappos.utilities.AnimUtilities;


public class MainActivity extends AppCompatActivity implements FragmentTag {

    private String TAG = "MainActivity";
    private static Context context;
    private MainListener mainListener;
    //functions
    private FragmentManager fm;
    private AnimUtilities mAnimUtilities;
    private InputMethodManager inputMethodManager;
    private SQLCartDB sqlCartDB;
    private DialogManager dialogManager;
    private SharedPreferences sharedPreferences;
    // Sharepreference params
    private String CURRENT_FRAGMENT = "CURRENT_FRAGMENT";
    private String currentFragment = "";
    //UI widgets
    private FrameLayout flSearch;
    private ImageButton btnBack, btnSearch, btnCart, btnDropDownSearch;
    private TextView tvCartItemCount;
    private EditText edSearch;
    private boolean ifSearchShown = false;
    //Window size
    public static int windowWidth;
    public static int windowHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        init();
    }

    private void init() {
        // Init utilities
        mAnimUtilities = new AnimUtilities(this);
        dialogManager = new DialogManager(this);
        mainListener = new MainListener();
        sqlCartDB = new SQLCartDB(this);
        fm = getSupportFragmentManager();
        getWindowSize();
        findViews();
        setTvCartItemCount();
        setAllListeners();
        setFragment(ProductFragment);
    }

    private void getWindowSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        windowHeight = displaymetrics.heightPixels;
        windowWidth = displaymetrics.widthPixels;
    }

    private void findViews() {
        flSearch = (FrameLayout) findViewById(R.id.activity_main_layout_search);
        btnBack = (ImageButton) findViewById(R.id.activity_main_btn_back);
        btnSearch = (ImageButton) findViewById(R.id.activity_main_btn_search);
        btnDropDownSearch = (ImageButton) findViewById(R.id.activity_main_dropdown_search);
        btnCart = (ImageButton) findViewById(R.id.activity_main_btn_cart);
        edSearch = (EditText) findViewById(R.id.activity_main_ed_search);
        tvCartItemCount = (TextView) findViewById(R.id.activity_main_tv_cart_count);
    }

    private void setAllListeners() {
        btnBack.setOnClickListener(mainListener);
        btnSearch.setOnClickListener(mainListener);
        btnCart.setOnClickListener(mainListener);
        edSearch.setOnEditorActionListener(mainListener);
        btnDropDownSearch.setOnClickListener(mainListener);
    }

    public void setTvCartItemCount() {
        if (null == sqlCartDB)
            sqlCartDB = new SQLCartDB(this);

        if (sqlCartDB.getCartItemCount() > 0) {
            tvCartItemCount.setText(String.valueOf(sqlCartDB.getCartItemCount()));
            mAnimUtilities.showCartCountAnim(tvCartItemCount);
        } else {
            tvCartItemCount.setVisibility(View.GONE);
        }
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (null == inputMethodManager) {
            inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Context getContext() {
        return context;
    }

    private void executeSearch() {
        if (!"".equalsIgnoreCase(edSearch.getText().toString())) {
            ProductFragment productFragment = (ProductFragment) fm.findFragmentByTag(ProductFragment);
            productFragment.queryTerm(edSearch.getText().toString());
            hideSoftKeyboard();
        } else {
            dialogManager.showAlertDialog(this.getString(R.string.activity_main_err_dialog_title), this.getString(R.string.activity_main_err_dialog_msg));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        currentFragment = sharedPreferences.getString(CURRENT_FRAGMENT, ProductFragment);
        setFragment(currentFragment);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(CURRENT_FRAGMENT, currentFragment);
        prefsEditor.apply();
    }

    private class MainListener implements View.OnClickListener, TextView.OnEditorActionListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_main_btn_back:
                    setFragment(ProductFragment);
                    break;
                case R.id.activity_main_btn_search:
                    if (ifSearchShown) {
                        flSearch.setVisibility(View.GONE);
                        btnSearch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_search));
                        ifSearchShown = false;
                    } else {
                        mAnimUtilities.setflSearchAnimToVisible(flSearch);
                        btnSearch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_fold));
                        ifSearchShown = true;
                    }
                    break;
                case R.id.activity_main_btn_cart:
                    setFragment(CartFragment);
                    break;
                case R.id.activity_main_dropdown_search:
                    executeSearch();
                    break;
            }
        }

        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            executeSearch();
            return false;
        }
    }

    public void setFragment(String fragmentTag) {
        Fragment fragment = null;
        currentFragment = fragmentTag;
        //action bar switch
        switch (fragmentTag) {
            case ProductFragment:
                btnBack.setVisibility(View.GONE);
                btnSearch.setVisibility(View.VISIBLE);
                fragment = fm.findFragmentByTag(ProductFragment);
                if (null == fragment) {
                    fragment = new ProductFragment();
                }
                break;
            case CartFragment:
                btnBack.setVisibility(View.VISIBLE);
                btnSearch.setVisibility(View.GONE);
                flSearch.setVisibility(View.GONE);
                btnSearch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_search));
                ifSearchShown = false;
                fragment = fm.findFragmentByTag(CartFragment);
                if (null == fragment) {
                    fragment = new CartFragment();
                }
                break;
        }
        try {
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.activity_main_container, fragment, fragmentTag).addToBackStack(fragmentTag).commit();
            edSearch.clearFocus();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}

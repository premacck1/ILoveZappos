package com.tsungweiho.ilovezappos;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tsungweiho.ilovezappos.object.Product;
import com.tsungweiho.ilovezappos.databinding.ActivityMainBinding;
import com.tsungweiho.ilovezappos.utilities.PHPUtilities;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private PHPUtilities mPHPUtilities;
    //UI Widgets
    private FloatingActionButton btnAddToCart;
    private TextView tvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        Product product = new Product("Test", "User");
        init();
    }

    private void init() {
        mPHPUtilities = new PHPUtilities(this);
        btnAddToCart = (FloatingActionButton) findViewById(R.id.activity_main_btn_add);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvStart = (TextView) findViewById(R.id.activity_main_tv_start);
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

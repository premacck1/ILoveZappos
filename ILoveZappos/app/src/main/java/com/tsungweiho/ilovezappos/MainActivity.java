package com.tsungweiho.ilovezappos;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tsungweiho.ilovezappos.Object.Product;
import com.tsungweiho.ilovezappos.databinding.ActivityMainBinding;
import com.tsungweiho.ilovezappos.utilities.PHPUtilities;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private PHPUtilities mPHPUtilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        Product product = new Product("Test", "User");
        init();
    }

    private void init(){
        mPHPUtilities = new PHPUtilities(this);
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

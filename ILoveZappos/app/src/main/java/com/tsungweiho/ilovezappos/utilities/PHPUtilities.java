package com.tsungweiho.ilovezappos.utilities;

import android.content.Context;
import android.content.Entity;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.tsungweiho.ilovezappos.constants.URLConstants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by tsung on 2017/1/30.
 */

public class PHPUtilities implements URLConstants {

    private String TAG = "PHPUtilities";
    private Context context;

    public PHPUtilities(Context context) {
        this.context = context;
    }

    private String sendHttpGetRequest(String url) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity resEntity = response.getEntity();
        return EntityUtils.toString(resEntity);
    }

    public String queryProduct(String term) throws Exception {
        String queryUrl = SERVER_QUERY_URL + term + "&" + QUERY_KEY;
        return sendHttpGetRequest(queryUrl);
    }
}

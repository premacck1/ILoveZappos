package com.tsungweiho.ilovezappos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tsungweiho.ilovezappos.constants.SQLDBConstants;
import com.tsungweiho.ilovezappos.objects.Product;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tsung on 2017/2/5.
 */

public class SQLCartDB extends SQLiteOpenHelper implements SQLDBConstants {
    private static final String DBNAME = "cart.sqlite";
    private static final int VERSION = 2;
    private static final String TABLENAME = "cart_list";

    public SQLCartDB(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
    }

    private void createDatabase(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLENAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                PRODUCT_ID + " VARCHAR(30)," +
                PRODUCT_BRAND + " VARCHAR(50)," +
                PRODUCT_NAME + " VARCHAR(50)," +
                PRODUCT_IMGURL + " TEXT," +
                PRODUCT_ORIPRICE + " VARCHAR(10), " +
                PRODUCT_PRICE + " VARCHAR(10), " +
                PRODUCT_PERCENTOFF + " VARCHAR(10), " +
                COLOR_ID + " VARCHAR(20), " +
                PRODUCT_URL + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    public long insertDB(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_ID, product.getProductId());
        values.put(PRODUCT_BRAND, product.getBrandName());
        values.put(PRODUCT_NAME, product.getProductName());
        values.put(PRODUCT_IMGURL, product.getImgUrl());
        values.put(PRODUCT_ORIPRICE, product.getOriginalPrice());
        values.put(PRODUCT_PRICE, product.getPrice());
        values.put(PRODUCT_PERCENTOFF, product.getPercentOff());
        values.put(PRODUCT_URL, product.getProductUrl());
        values.put(COLOR_ID, product.getColorId());
        long rowId = db.insert(TABLENAME, null, values);
        db.close();
        return rowId;
    }

    public int deleteSingleProduct(int databaseId) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID + "='" + databaseId + "'";
        int count = db.delete(TABLENAME, whereClause, null);
        db.close();
        return count;
    }

    public ArrayList<Product> getAllCartList() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Product> carttList = new ArrayList<Product>();
        String sql = "SELECT * FROM " + TABLENAME;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String productId = cursor.getString(1);
            String productBrand = cursor.getString(2);
            String productName = cursor.getString(3);
            String producImgUrl = cursor.getString(4);
            String producOriPrice = cursor.getString(5);
            String producPrice = cursor.getString(6);
            String producPercentOff = cursor.getString(7);
            String colorId = cursor.getString(8);
            String producUrl = cursor.getString(9);
            Product product = new Product(productBrand, producImgUrl, productId, producOriPrice, colorId, producPrice, producPercentOff, producUrl, productName);
            carttList.add(product);
        }
        cursor.close();
        db.close();
        return carttList;
    }

    public ArrayList<Integer> getAllIdList() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Integer> idList = new ArrayList<Integer>();
        String sql = "SELECT * FROM " + TABLENAME;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int databaseId = cursor.getInt(0);
            idList.add(databaseId);
        }
        cursor.close();
        db.close();
        return idList;
    }

    public int getCartItemCount() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLENAME;
        int recordCount = 0;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            recordCount++;
        }
        cursor.close();
        db.close();
        return recordCount;
    }
}

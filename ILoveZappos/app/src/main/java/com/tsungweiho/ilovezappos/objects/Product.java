package com.tsungweiho.ilovezappos.objects;

/**
 * Created by tsung on 2017/1/30.
 */

public class Product {
    private String brandName;
    private String thumbnailImageUrl;
    private String productId;
    private String originalPrice;
    private String colorId;
    private String price;
    private String percentOff;
    private String productUrl;
    private String productName;

    public Product() {

    }

    public Product(String brandName, String thumbnailImageUrl, String productId, String originalPrice, String colorId, String price, String percentOff, String productUrl, String productName) {
        this.brandName = brandName;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.productId = productId;
        this.originalPrice = originalPrice;
        this.colorId = colorId;
        this.price = price;
        this.percentOff = percentOff;
        this.productUrl = productUrl;
        this.productName = productName;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getImgUrl() {
        return this.thumbnailImageUrl;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getOriginalPrice() {
        return this.originalPrice;
    }

    public String getColorId() {
        return this.colorId;
    }

    public String getPrice() {
        return this.price;
    }

    public String getPercentOff() {
        return this.percentOff;
    }

    public String getProductUrl() {
        return this.productUrl;
    }

    public String getProductName() {
        return this.productName;
    }
}

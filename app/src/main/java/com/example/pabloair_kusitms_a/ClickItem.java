package com.example.pabloair_kusitms_a;

public class ClickItem {
    String productName;
    int productAmount;
    int productPrice;
    int resourceId;

    public ClickItem(String productName, int productAmount, int productPrice, int resourceId) {
        this.productName = productName;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
        this.resourceId = resourceId;
    }

    //setter
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }


    //getter
    public String getProductName() {
        return productName;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getResourceId() {
        return resourceId;
    }


}

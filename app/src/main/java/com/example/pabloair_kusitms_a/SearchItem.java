package com.example.pabloair_kusitms_a;

public class SearchItem {
    String productName;
    int image;

    public SearchItem (String productName, int image) {
        this.productName = productName;
        this.image = image;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getproductName() {
        return productName;

    }

    public void setImage(int image) {
        this.image = image;
    }
    public int getImage() {
        return image;
    }
}

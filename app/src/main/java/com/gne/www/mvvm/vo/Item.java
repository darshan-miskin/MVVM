package com.gne.www.mvvm.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {
    private String product_name, supplier, id;
    private int quantity;

    @SerializedName("product_name")
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @SerializedName("supplier")
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @SerializedName("quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    public String getId() {
        return id;
    }
}

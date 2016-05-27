package com.android.giggs.fooddelivery;

/**
 * Created by giggs on 5/27/2016.
 */
public class OrderModel {
    private int ID, ITEM_QUANTITY;
    private String ITEM_NAME, DELIVERY_DAY;
    private float ITEM_PRICE;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getITEM_QUANTITY() {
        return ITEM_QUANTITY;
    }

    public void setITEM_QUANTITY(int ITEM_QUANTITY) {
        this.ITEM_QUANTITY = ITEM_QUANTITY;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getDELIVERY_DAY() {
        return DELIVERY_DAY;
    }

    public void setDELIVERY_DAY(String DELIVERY_DAY) {
        this.DELIVERY_DAY = DELIVERY_DAY;
    }

    public float getITEM_PRICE() {
        return ITEM_PRICE;
    }

    public void setITEM_PRICE(float ITEM_PRICE) {
        this.ITEM_PRICE = ITEM_PRICE;
    }
}

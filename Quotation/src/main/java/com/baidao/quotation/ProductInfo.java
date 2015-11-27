package com.baidao.quotation;

/**
 * Created by hexi on 14/12/9.
 */
public class ProductInfo {
    public String id;
    public String bondCategory;
    public int decimalDigits;

    public ProductInfo(String id, String bondCategory, int decimalDigits) {
        this.id = id;
        this.bondCategory = bondCategory;
        this.decimalDigits = decimalDigits;
    }
}

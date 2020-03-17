package com.example.savedataex;
import android.widget.ProgressBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Products {

    private int _id;
    private String _productname;
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Products(){

    }

    public Products(String productname){
        this._productname = productname;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_productname() {
        return _productname;
    }

    public void set_productname(String _productname) {
        this._productname = _productname;
    }

    public static void main(String[] args) throws Exception {
        String datee = "22/03/2020";
        Date date = dateFormat.parse(datee);
        Date date1 = new Date();
        long weissnicht = date.getTime() - date1.getTime();
        System.out.println ("Days: " + TimeUnit.DAYS.convert(weissnicht, TimeUnit.MILLISECONDS));

    }
}

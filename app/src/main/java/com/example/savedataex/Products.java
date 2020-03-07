package com.example.savedataex;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

public class Products {

    private int _id;
    private String _productname;
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static java.util.Date dead;

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

    public static void main(String[] args) {
        System.out.println("2020-03-28");
        String deadString = "2020-03-28";
        try {
            dead = dateFormat.parse(deadString);
        }
        catch (Exception e){
            System.out.println("something went wrong");
        }
        System.out.println(dead);


    }
}

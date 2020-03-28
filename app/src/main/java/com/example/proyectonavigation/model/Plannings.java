package com.example.proyectonavigation.model;

import android.graphics.Bitmap;

public class Plannings {

    int activity_id;
    String activity_title;
    float activity_rating;
    String activity_category;
    String activity_subcategory;
    String activity_subcategory_1;
    String activity_start_date;
    String activity_end_date;
    Bitmap activity_picture;
    String activity_name;
    String activity_subcategory_2;

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public float getActivity_rating() {
        return activity_rating;
    }

    public void setActivity_rating(float activity_rating) {
        this.activity_rating = activity_rating;
    }

    public String getActivity_category() {
        return activity_category;
    }

    public void setActivity_category(String activity_category) {
        this.activity_category = activity_category;
    }

    public String getActivity_subcategory() {
        return activity_subcategory;
    }

    public void setActivity_subcategory(String activity_subcategory) {
        this.activity_subcategory = activity_subcategory;
    }

    public String getActivity_subcategory_1() {
        return activity_subcategory_1;
    }

    public void setActivity_subcategory_1(String activity_subcategory_1) {
        this.activity_subcategory_1 = activity_subcategory_1;
    }

    public String getActivity_start_date() {
        return activity_start_date;
    }

    public void setActivity_start_date(String activity_start_date) {
        this.activity_start_date = activity_start_date;
    }

    public String getActivity_end_date() {
        return activity_end_date;
    }

    public void setActivity_end_date(String activity_end_date) {
        this.activity_end_date = activity_end_date;
    }

    public Bitmap getActivity_picture() {
        return activity_picture;
    }

    public void setActivity_picture(Bitmap activity_picture) {
        this.activity_picture = activity_picture;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getActivity_subcategory_2() {
        return activity_subcategory_2;
    }

    public void setActivity_subcategory_2(String activity_subcategory_2) {
        this.activity_subcategory_2 = activity_subcategory_2;
    }

    public Plannings(int activity_id, String activity_name, String activity_title, float activity_rating, String activity_category, String activity_subcategory,
                     String activity_subcategory_1, String activity_subcategory_2, String activity_start_date, String activity_end_date, Bitmap activity_picture) {
        this.activity_id = activity_id;
        this.activity_name = activity_name;
        this.activity_title = activity_title;
        this.activity_rating = activity_rating;
        this.activity_category = activity_category;
        this.activity_subcategory = activity_subcategory;
        this.activity_subcategory_1 = activity_subcategory_1;
        this.activity_subcategory_2 = activity_subcategory_2;
        this.activity_start_date = activity_start_date;
        this.activity_end_date = activity_end_date;
        this.activity_picture = activity_picture;



    }
}


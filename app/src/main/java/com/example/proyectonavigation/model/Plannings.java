package com.example.proyectonavigation.model;

import android.graphics.Bitmap;

public class Plannings {

    //private int activity_id;
    //String activity_name;
    String activity_title;
    float activity_rating;
    String activitiy_category;
    String activity_subcategory;
    // String activity_start_date;
    // String activity_end_date;
    Bitmap activity_picture;

    public Plannings(String activity_title, float activity_rating, String activitiy_category, String activity_subcategory, Bitmap activity_picture) {
        this.activity_title = activity_title;
        this.activity_rating = activity_rating;
        this.activitiy_category = activitiy_category;
        this.activity_subcategory = activity_subcategory;
        this.activity_picture = activity_picture;
    }
}

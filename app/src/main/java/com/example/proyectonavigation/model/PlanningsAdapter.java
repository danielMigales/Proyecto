package com.example.proyectonavigation.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectonavigation.R;

import java.util.ArrayList;

public class PlanningsAdapter extends RecyclerView.Adapter<PlanningsAdapter.MyViewHolder> {

    ArrayList<Plannings> plans;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView planning;
        TextView name;
        ImageView picture;
        TextView tags;
        RatingBar rating;
        Button like;


        public MyViewHolder(View itemView) {
            super( itemView );
            planning = itemView.findViewById( R.id.cardviewActivityPlan);
            name = itemView.findViewById(R.id.textViewCardviewActivity);
            picture = itemView.findViewById(R.id.imageViewCardviewActivity);
            tags = itemView.findViewById(R.id.textViewTagsCardviewActivity);
            rating = itemView.findViewById(R.id.ratingBarCardviewActivity);
            like = itemView.findViewById(R.id.buttonLikeCardviewActivity);

        }
    }

    @NonNull
    @Override
    public PlanningsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.home_cardview, parent, false);
        PlanningsAdapter.MyViewHolder viewHolder = new PlanningsAdapter.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanningsAdapter.MyViewHolder holder, int position) {

        holder.name.setText(plans.get(position).activity_title);
        holder.tags.setText( plans.get(position).activitiy_category + ", " + plans.get(position).activity_subcategory );
        holder.picture.setImageBitmap(plans.get(position).activity_picture);
        holder.rating.setRating( plans.get(position).activity_rating );

    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public PlanningsAdapter(ArrayList<Plannings> plans){
        this.plans = plans;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView( recyclerView );
    }
}

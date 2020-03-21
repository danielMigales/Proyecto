package com.example.proyectonavigation.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectonavigation.R;
import com.example.proyectonavigation.ui.home.CardActivity;

import java.util.ArrayList;

public class PlanningsAdapter extends RecyclerView.Adapter<PlanningsAdapter.MyViewHolder> {

    ArrayList<Plannings> plans;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView planning;
        TextView name;
        ImageView picture;
        TextView tags;
        RatingBar rating;
        TextView date;


        public MyViewHolder(View itemView) {
            super( itemView );
            planning = itemView.findViewById( R.id.cardviewActivityPlan );
            name = itemView.findViewById( R.id.textViewCardviewActivity );
            picture = itemView.findViewById( R.id.imageViewCardviewActivity );
            tags = itemView.findViewById( R.id.textViewTagsCardviewActivity );
            rating = itemView.findViewById( R.id.ratingBarCardviewActivity );
            date = itemView.findViewById( R.id.textViewDate );
        }
    }

    @NonNull
    @Override
    public PlanningsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.home_cardview, parent, false );
        PlanningsAdapter.MyViewHolder viewHolder = new PlanningsAdapter.MyViewHolder( v );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanningsAdapter.MyViewHolder holder, final int position) {

        holder.planning.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( view.getContext(), CardActivity.class );
                intent.putExtra( "activity_id", plans.get( position ).activity_id );
                intent.putExtra( "activity_category", plans.get( position ).activitiy_category );
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                view.getContext().startActivity( intent );
            }
        } );

        holder.name.setText( plans.get( position ).activity_title );
        holder.tags.setText(plans.get( position ).activitiy_category + ", " + plans.get( position ).activity_subcategory );
        holder.picture.setImageBitmap( plans.get( position ).activity_picture );
        holder.rating.setRating( plans.get( position ).activity_rating );
        holder.date.setText( plans.get(position).activity_start_date + " hasta " +  plans.get( position ).activity_end_date);
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public PlanningsAdapter(ArrayList<Plannings> plans) {
        this.plans = plans;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView( recyclerView );
    }
}

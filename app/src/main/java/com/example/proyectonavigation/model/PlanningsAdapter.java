package com.example.proyectonavigation.model;

import android.content.Context;
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
import java.util.regex.Pattern;

public class PlanningsAdapter extends RecyclerView.Adapter<PlanningsAdapter.MyViewHolder> {

    ArrayList<Plannings> plans;
    private Context context;


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

    public PlanningsAdapter(ArrayList<Plannings> plans, Context context) {
        super();
        this.plans = plans;
        this.context = context;
    }

    /*
    Añade una lista completa de items
     */
    public void addAll(ArrayList<Plannings> plansList) {
        plans.addAll( plansList );
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    public void clear() {
        plans.clear();
    }

    @Override
    public int getItemCount() {

        if (plans != null) {
            return plans.size();
        } else {
            return 0;
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
                intent.putExtra( "activity_category", plans.get( position ).activity_category );
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                view.getContext().startActivity( intent );
            }
        } );

        holder.name.setText( plans.get( position ).activity_title );
        holder.tags.setText( plans.get( position ).activity_category + ", " + plans.get( position ).activity_subcategory + ", " + plans.get( position ).activity_subcategory_1 );
        holder.picture.setImageBitmap( plans.get( position ).activity_picture );
        holder.rating.setRating( plans.get( position ).activity_rating );

        String date1 = plans.get( position ).activity_start_date;
        String date2 = plans.get( position ).activity_end_date;

        String separador = Pattern.quote("-");
        String[] parts = date1.split(separador);

        for (int i = 0 ; i <= parts.length; i++) {
            if (parts[1].equals( "01")){
                parts[1] = "enero";
            }
            if (parts[1].equals( "02")){
                parts[1] = "febrero";
            }
            if (parts[1].equals( "03")){
                parts[1] = "marzo";
            }
            if (parts[1].equals( "04")){
                parts[1] = "abril";
            }
            if (parts[1].equals( "05")){
                parts[1] = "mayo";
            }
            if (parts[1].equals( "06")){
                parts[1] = "junio";
            }
            if (parts[1].equals( "07")){
                parts[1] = "julio";
            }
            if (parts[1].equals( "08")){
                parts[1] = "agosto";
            }
            if (parts[1].equals( "09")){
                parts[1] = "septiembre";
            }
            if (parts[1].equals( "10")){
                parts[1] = "octubre";
            }
            if (parts[1].equals( "11")){
                parts[1] = "noviembre";
            }
            if (parts[1].equals( "12")){
                parts[1] = "diciembre";
            }

            holder.date.setText(parts[2] + " de " + parts[1] + " del " + parts[0]);
        }

    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView( recyclerView );
    }


}


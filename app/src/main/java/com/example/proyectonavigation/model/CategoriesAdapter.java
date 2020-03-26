package com.example.proyectonavigation.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectonavigation.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    List<Categories> categories;

    public CategoriesAdapter(List<Categories> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.categories_cardview, parent, false );
        MyViewHolder viewHolder = new MyViewHolder( v );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText( categories.get( position ).name );
        holder.picture.setImageResource( categories.get( position ).picture );
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView( recyclerView );
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView categories;
        TextView name;
        ImageView picture;


        public MyViewHolder(View itemView) {
            super( itemView );
            categories = itemView.findViewById( R.id.cardviewCategories );
            name = itemView.findViewById( R.id.textViewCategory );
            picture = itemView.findViewById( R.id.imageViewCategory );
        }
    }
}
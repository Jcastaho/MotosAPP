package com.straccion.motos_admin.adapters;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.providers.PostProvider;

public class PostsAdapters extends FirestoreRecyclerAdapter<PostAuteco, PostsAdapters.ViewHolder>{

    Context contexto;
    NavController navController;
    int homeONoVisibles;
    public PostsAdapters(FirestoreRecyclerOptions<PostAuteco> options, Context context, NavController NavController, int HomeONoVisibles){
        super(options);
        this.contexto = context;
        this.navController = NavController;
        this.homeONoVisibles = HomeONoVisibles;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull PostAuteco post) {

        DocumentSnapshot document = getSnapshots().getSnapshot(position);
        String postId = document.getId();
        if (homeONoVisibles == 0){
            holder.lnlFondo.setBackgroundResource(R.color.white);
        }else {
            holder.lnlFondo.setBackgroundResource(R.color.grisclaro);
        }
        holder.txtMarca.setText(post.getMarcaMoto());
        holder.txtNombreMoto.setText(post.getNombreMoto());
        if (post.getImagenes() != null && !post.getImagenes().isEmpty()) {
            Picasso.get().load(post.getImagenes().get(0)).into(holder.imgMoto);
        }
        holder.ViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("idDocument", postId);
                if (homeONoVisibles == 0){
                    navController.navigate(R.id.action_nav_home_to_detallesMoto, args);
                }else {
                    navController.navigate(R.id.action_motosNoVisibles_to_detallesMoto, args);
                }

            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_motos, parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNombreMoto;
        TextView txtMarca;
        ImageView imgMoto;
        LinearLayout lnlFondo;
        View ViewHolder;


        public ViewHolder(View view){
            super(view);
            txtNombreMoto = view.findViewById(R.id.txtNombreMoto);
            imgMoto = view.findViewById(R.id.imgMoto);
            txtMarca = view.findViewById(R.id.txtMarca);
            lnlFondo = view.findViewById(R.id.lnlFondo);
            ViewHolder = view;
        }

    }
}

package com.straccion.motos_admin.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class ListaCompararAdapter extends FirestoreRecyclerAdapter<PostAuteco, ListaCompararAdapter.ViewHolder> {

    Context contexto;
    androidx.navigation.NavController NavController;
    String idDocument;
    ImageView imgMoto2;
    Dialog dialog2;
    TextView txtMoto2;
    public ListaCompararAdapter(FirestoreRecyclerOptions<PostAuteco> options, Context context,
                                NavController navController, String idDocument, ImageView imgMoto2, Dialog dialog2, TextView txtMoto2){
        super(options);
        this.contexto = context;
        this.NavController = navController;
        this.idDocument = idDocument;
        this.imgMoto2 = imgMoto2;
        this.dialog2 = dialog2;
        this.txtMoto2 = txtMoto2;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull PostAuteco post) {
        DocumentSnapshot document = getSnapshots().getSnapshot(position);
        String postId = document.getId();

        holder.txtNombreMoto.setText(post.getNombreMoto());
        if (post.getImagenes() != null && !post.getImagenes().isEmpty()) {
            Picasso.get().load(post.getImagenes().get(0)).into(holder.imgMoto);
        }
        holder.ViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imagen2 = post.getImagenes().get(0);
                dialog2.dismiss();
                Picasso.get().load(imagen2).into(imgMoto2);
                txtMoto2.setText(post.getNombreMoto());
                txtMoto2.setVisibility(View.VISIBLE);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opciones_comparar_moto, parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNombreMoto;
        ImageView imgMoto;
        View ViewHolder;


        public ViewHolder(View view){
            super(view);
            txtNombreMoto = view.findViewById(R.id.txtNombreMoto);
            imgMoto = view.findViewById(R.id.imgMoto);
            ViewHolder = view;
        }

    }
}


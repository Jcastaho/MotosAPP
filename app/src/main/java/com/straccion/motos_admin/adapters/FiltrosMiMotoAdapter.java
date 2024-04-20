package com.straccion.motos_admin.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.models.PostYamaha;

import java.util.List;

    public class FiltrosMiMotoAdapter extends FirestoreRecyclerAdapter<PostAuteco, com.straccion.motos_admin.adapters.FiltrosMiMotoAdapter.ViewHolder> {

        Context contexto;
        String economica;
        androidx.navigation.NavController NavController;
        public FiltrosMiMotoAdapter(FirestoreRecyclerOptions<PostAuteco> options, Context context,
                                    NavController navController, String Economica){
            super(options);
            this.contexto = context;
            this.NavController = navController;
            this.economica = Economica;
        }

        @Override
        protected void onBindViewHolder(@NonNull com.straccion.motos_admin.adapters.FiltrosMiMotoAdapter.ViewHolder holder,
                                        int position, @NonNull PostAuteco post) {
            DocumentSnapshot document = getSnapshots().getSnapshot(position);
            boolean eco = false;
            int galon = 0;
            if (economica.equals("true")){
                eco = true;
                galon = 110; // validar
            } else if (economica.equals("regular")) {
                eco = true;
                galon = 70; //validar
            }
            String postId = document.getId();
            if (eco){
                int consumoxGasolina = post.getConsumoPorGalon();
                if (consumoxGasolina >= galon) {
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
                            NavController.navigate(R.id.action_nav_mimotoideal_to_detallesMoto, args);
                        }
                    });
                }else {
                    holder.crdMoticos.setVisibility(View.GONE);
                }

            }else {
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
                        NavController.navigate(R.id.action_nav_mimotoideal_to_detallesMoto, args);
                    }
                });
            }

        }

        @NonNull
        @Override
        public com.straccion.motos_admin.adapters.FiltrosMiMotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mimotoideal, parent,false);
            return new com.straccion.motos_admin.adapters.FiltrosMiMotoAdapter.ViewHolder(view);
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView txtNombreMoto;
            TextView txtMarca;
            ImageView imgMoto;
            CardView crdMoticos;
            View ViewHolder;

            public ViewHolder(View view){
                super(view);
                txtNombreMoto = view.findViewById(R.id.txtNombreMoto);
                imgMoto = view.findViewById(R.id.imgMoto);
                txtMarca = view.findViewById(R.id.txtMarca);
                crdMoticos = view.findViewById(R.id.crdMoticos);
                ViewHolder = view;
            }

        }
}

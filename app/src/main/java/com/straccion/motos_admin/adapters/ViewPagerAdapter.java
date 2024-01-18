package com.straccion.motos_admin.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.models.ViewPagerItem;

import java.util.ArrayList;
import java.util.List;
public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder> {

    private Context context;
    private List<ViewPagerItem> mViewPagerItem = new ArrayList<>();
    private List<String> textoImagen = new ArrayList<>();
    ViewPager2 viewPager2;
    int cantidadImagenes= 0;
    private int ejecucionesRunnable = 0;



    public ViewPagerAdapter(Context context, List<ViewPagerItem> viewPagerItem, ViewPager2 viewPager2, List<String> textoImagen){
        mViewPagerItem = viewPagerItem;
        this.context = context;
        this.viewPager2 = viewPager2;
        this.textoImagen = textoImagen;
        this.cantidadImagenes = mViewPagerItem.size();
    }

    @NonNull
    @Override
    public ViewPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_imagen_adicional, parent, false);
        return new ViewPagerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerHolder view, int position) {
        long inicio = System.currentTimeMillis();
        int maximoEjecucionesRunnable = 4;
        int cantidad = position  % cantidadImagenes;
        ViewPagerItem viewPagerItem = mViewPagerItem.get(cantidad);
        if (viewPagerItem.getImageurl() != null){
            if (!viewPagerItem.getImageurl().isEmpty()){
                Picasso.get().load(viewPagerItem.getImageurl()).into(view.imageView);
                if (position == mViewPagerItem.size() - 2 && ejecucionesRunnable < maximoEjecucionesRunnable) {
                    viewPager2.post(runnable);
                    ejecucionesRunnable++;
                }
            }
        }
        long fin = System.currentTimeMillis();
        long tiempoTotal = fin - inicio;

        view.ViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarImagenAmpliada(viewPagerItem.getImageurl(), textoImagen.get(cantidad));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mViewPagerItem.size();
    }

    public class ViewPagerHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageView;
        View ViewHolder;


        public  ViewPagerHolder(@NonNull View view){
            super(view);
            imageView = view.findViewById(R.id.imageViewPager2);
            ViewHolder = view;
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mViewPagerItem.addAll(mViewPagerItem);
            notifyDataSetChanged();
        }
    };

    private void mostrarImagenAmpliada(String Url, String texto){

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.imagenes_caracteristicas);

        ImageView imgCaracteristicas = dialog.findViewById(R.id.imgCaracteristicas);
        TextView txtimgCaracteristicas = dialog.findViewById(R.id.txtimgCaracteristicas);

        Picasso.get().load(Url).into(imgCaracteristicas);
        txtimgCaracteristicas.setText(texto);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        dialog.show();

        imgCaracteristicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}

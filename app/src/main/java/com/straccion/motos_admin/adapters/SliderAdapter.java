package com.straccion.motos_admin.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.models.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();
    int heigth = 0;
    String fabricante = "";
    public SliderAdapter(Context context, List<SliderItem> sliderItems, String Fabricante) {
        this.context = context;
        mSliderItems = sliderItems;
        fabricante = Fabricante;
    }
    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        SliderItem sliderItem = mSliderItems.get(position);
        if (sliderItem.getImageurl() != null){
            if (!sliderItem.getImageurl().isEmpty()){
                if (fabricante.equals("BAJAJ")){
                    Picasso.get().load(sliderItem.getImageurl()).fit().into(viewHolder.imageViewSlider);
                }else {
                    Picasso.get().load(sliderItem.getImageurl()).resize(1024, 1020).into(viewHolder.imageViewSlider);
                }
            }
        }
    }

    public List<Integer> obtenerTamañosDeImagenes() {
        List<Integer> tamañosDeImagenes = new ArrayList<>();
        for (SliderItem item : mSliderItems) {
            // Aquí puedes obtener el tamaño de cada imagen y agregarlo a la lista
            int tamañoDeImagen = obtenerTamañoDeImagen(item);
            tamañosDeImagenes.add(tamañoDeImagen);
        }
        return tamañosDeImagenes;
    }

    // Método auxiliar para obtener el tamaño de una imagen
    private int obtenerTamañoDeImagen(SliderItem item) {
        final int[] tamaño = new int[2];

        try {
     // Pausa de 1 segundo (solo para este ejemplo)
            Thread.sleep(5000);
            SliderItem sliderItem = mSliderItems.get(0);
            Glide.with(context)
                    .asBitmap()
                    .load(sliderItem.getImageurl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            // Una vez que la imagen se ha cargado con éxito, obtén su tamaño
                            tamaño[0] = resource.getWidth(); // Ancho de la imagen
                            tamaño[1] = resource.getHeight(); // Alto de la imagen
                        }
                    });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tamaño[1];
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    class SliderAdapterVH extends ViewHolder {

        View itemView;
        ImageView imageViewSlider;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewSlider = itemView.findViewById(R.id.imageViewSlider);
            this.itemView = itemView;
        }
    }

}

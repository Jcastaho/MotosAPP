package com.straccion.motos_admin.ui.comparador;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.databinding.FragmentSlideshowBinding;
import com.straccion.motos_admin.providers.PostProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SlideshowFragment extends Fragment {
    View mview;
    View viewRaya1;
    View viewRaya2;
    CoordinatorLayout cdlImagenfondo;
    NestedScrollView nscImagenfondo;
    int imagenFondo1 = R.drawable.fondosuperior;
    int imagenFondo2 = R.drawable.compararmotos;
    int controlador_Imagenes1=0;
    int controlador_Imagenes2=0;
    int controlador_Imagenes3=0;

    ImageView imgComprarMoto1;
    ImageView imgComprarMoto2;
    ImageView imgComprarMoto3;

    TextView txtCilindrajeMoto1; TextView txtMotorMoto1; TextView txtTorqueMoto1; TextView txtAlimentacionMoto1; TextView txtRefrigeracionValor1; TextView txtTransmisionMoto1; TextView txtSuspensionDelanValor1; TextView txtSuspensionTrasValor1; TextView txtFrenoDelanValor1; TextView txtFrenoTrasMoto1; TextView txtTanqueMoto1; TextView txtPesoMoto1; TextView txtPrecioValor1;
    TextView txtCilindrajevalor1; TextView txtMotorvalor1; TextView txtTorqueValor1; TextView txtAlimentacionValor1; TextView txtRefrigeracionMoto1; TextView txtTransmisionValor1; TextView txtSuspensionDelanMoto1; TextView txtSuspensionTrasMoto1; TextView txtFrenoDelanMoto1; TextView txtFrenoTrasValor1; TextView txtTanqueValor1; TextView txtPesoValor1; TextView txtPrecioMoto1;

    TextView txtCilindrajeMoto2; TextView txtMotorMoto2; TextView txtTorqueMoto2; TextView txtAlimentacionMoto2; TextView txtRefrigeracionValor2; TextView txtTransmisionMoto2; TextView txtSuspensionDelanValor2; TextView txtSuspensionTrasValor2; TextView txtFrenoDelanValor2; TextView txtFrenoTrasMoto2; TextView txtTanqueMoto2; TextView txtPesoMoto2; TextView txtPrecioValor2;
    TextView txtCilindrajevalor2; TextView txtMotorvalor2; TextView txtTorqueValor2; TextView txtAlimentacionValor2; TextView txtRefrigeracionMoto2; TextView txtTransmisionValor2; TextView txtSuspensionDelanMoto2; TextView txtSuspensionTrasMoto2; TextView txtFrenoDelanMoto2; TextView txtFrenoTrasValor2; TextView txtTanqueValor2; TextView txtPesoValor2; TextView txtPrecioMoto2;

    TextView txtCilindrajeMoto3; TextView txtMotorMoto3; TextView txtTorqueMoto3; TextView txtAlimentacionMoto3; TextView txtRefrigeracionValor3; TextView txtTransmisionMoto3; TextView txtSuspensionDelanValor3; TextView txtSuspensionTrasValor3; TextView txtFrenoDelanValor3; TextView txtFrenoTrasMoto3; TextView txtTanqueMoto3; TextView txtPesoMoto3; TextView txtPrecioValor3;
    TextView txtCilindrajevalor3; TextView txtMotorvalor3; TextView txtTorqueValor3; TextView txtAlimentacionValor3; TextView txtRefrigeracionMoto3; TextView txtTransmisionValor3; TextView txtSuspensionDelanMoto3; TextView txtSuspensionTrasMoto3; TextView txtFrenoDelanMoto3; TextView txtFrenoTrasValor3; TextView txtTanqueValor3; TextView txtPesoValor3; TextView txtPrecioMoto3;
    PostProvider mpostProvider;
    private FragmentSlideshowBinding binding;
    List<String> imagenesList = new ArrayList<>();
    List<String> nombreImagenes = new ArrayList<>();
    List<List<String>> listasDocumentos = new ArrayList<>();

    QueryDocumentSnapshot documentoMoto = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_slideshow, container, false);

        imgComprarMoto1 = mview.findViewById(R.id.imgComprarMoto1);
        imgComprarMoto2 = mview.findViewById(R.id.imgComprarMoto2);
        imgComprarMoto3 = mview.findViewById(R.id.imgComprarMoto3);
        viewRaya1 = mview.findViewById(R.id.viewRaya1);
        viewRaya2 = mview.findViewById(R.id.viewRaya2);
        cdlImagenfondo = mview.findViewById(R.id.cdlImagenfondo);
        nscImagenfondo = mview.findViewById(R.id.nscImagenfondo);

        txtCilindrajeMoto1 = mview.findViewById(R.id.txtCilindrajeMoto1); txtMotorMoto1 = mview.findViewById(R.id.txtMotorMoto1);  txtTorqueMoto1 = mview.findViewById(R.id.txtTorqueMoto1);  txtAlimentacionMoto1 = mview.findViewById(R.id.txtAlimentacionMoto1);  txtRefrigeracionValor1 = mview.findViewById(R.id.txtRefrigeracionValor1);  txtTransmisionMoto1 = mview.findViewById(R.id.txtTransmisionMoto1);  txtSuspensionDelanValor1 = mview.findViewById(R.id.txtSuspensionDelanValor1);  txtSuspensionTrasValor1 = mview.findViewById(R.id.txtSuspensionTrasValor1);  txtFrenoDelanValor1 = mview.findViewById(R.id.txtFrenoDelanValor1);  txtFrenoTrasMoto1 = mview.findViewById(R.id.txtFrenoTrasMoto1);  txtTanqueMoto1 = mview.findViewById(R.id.txtTanqueMoto1);  txtPesoMoto1 = mview.findViewById(R.id.txtPesoMoto1); txtPrecioValor1 = mview.findViewById(R.id.txtPrecioValor1);
        txtCilindrajevalor1 = mview.findViewById(R.id.txtCilindrajevalor1);  txtMotorvalor1 = mview.findViewById(R.id.txtMotorvalor1);  txtTorqueValor1 = mview.findViewById(R.id.txtTorqueValor1);  txtAlimentacionValor1 = mview.findViewById(R.id.txtAlimentacionValor1);  txtRefrigeracionMoto1 = mview.findViewById(R.id.txtRefrigeracionMoto1);  txtTransmisionValor1 = mview.findViewById(R.id.txtTransmisionValor1);  txtSuspensionDelanMoto1 = mview.findViewById(R.id.txtSuspensionDelanMoto1);  txtSuspensionTrasMoto1 = mview.findViewById(R.id.txtSuspensionTrasMoto1);  txtFrenoDelanMoto1 = mview.findViewById(R.id.txtFrenoDelanMoto1);  txtFrenoTrasValor1 = mview.findViewById(R.id.txtFrenoTrasValor1);  txtTanqueValor1 = mview.findViewById(R.id.txtTanqueValor1);  txtPesoValor1 = mview.findViewById(R.id.txtPesoValor1); txtPrecioValor2 = mview.findViewById(R.id.txtPrecioValor2);

        txtCilindrajeMoto2= mview.findViewById(R.id.txtCilindrajeMoto2);  txtMotorMoto2 = mview.findViewById(R.id.txtMotorMoto2);  txtTorqueMoto2 = mview.findViewById(R.id.txtTorqueMoto2);  txtAlimentacionMoto2 = mview.findViewById(R.id.txtAlimentacionMoto2);  txtRefrigeracionValor2 = mview.findViewById(R.id.txtRefrigeracionValor2);  txtTransmisionMoto2 = mview.findViewById(R.id.txtTransmisionMoto2);  txtSuspensionDelanValor2 = mview.findViewById(R.id.txtSuspensionDelanValor2);  txtSuspensionTrasValor2 = mview.findViewById(R.id.txtSuspensionTrasValor2);  txtFrenoDelanValor2 = mview.findViewById(R.id.txtFrenoDelanValor2);  txtFrenoTrasMoto2 = mview.findViewById(R.id.txtFrenoTrasMoto2);  txtTanqueMoto2 = mview.findViewById(R.id.txtTanqueMoto2);  txtPesoMoto2 = mview.findViewById(R.id.txtPesoMoto2); txtPrecioValor3 = mview.findViewById(R.id.txtPrecioValor3);
        txtCilindrajevalor2= mview.findViewById(R.id.txtCilindrajevalor2);  txtMotorvalor2 = mview.findViewById(R.id.txtMotorvalor2);  txtTorqueValor2 = mview.findViewById(R.id.txtTorqueValor2);  txtAlimentacionValor2 = mview.findViewById(R.id.txtAlimentacionValor2);  txtRefrigeracionMoto2 = mview.findViewById(R.id.txtRefrigeracionMoto2);  txtTransmisionValor2 = mview.findViewById(R.id.txtTransmisionValor2);  txtSuspensionDelanMoto2 = mview.findViewById(R.id.txtSuspensionDelanMoto2);  txtSuspensionTrasMoto2 = mview.findViewById(R.id.txtSuspensionTrasMoto2);  txtFrenoDelanMoto2 = mview.findViewById(R.id.txtFrenoDelanMoto2);  txtFrenoTrasValor2 = mview.findViewById(R.id.txtFrenoTrasValor2);  txtTanqueValor2 = mview.findViewById(R.id.txtTanqueValor2);  txtPesoValor2 = mview.findViewById(R.id.txtPesoValor2); txtPrecioMoto1 = mview.findViewById(R.id.txtPrecioMoto1);

        txtCilindrajeMoto3= mview.findViewById(R.id.txtCilindrajeMoto3);  txtMotorMoto3 = mview.findViewById(R.id.txtMotorMoto3);  txtTorqueMoto3 = mview.findViewById(R.id.txtTorqueMoto3);  txtAlimentacionMoto3 = mview.findViewById(R.id.txtAlimentacionMoto3);  txtRefrigeracionValor3 = mview.findViewById(R.id.txtRefrigeracionValor3);  txtTransmisionMoto3 = mview.findViewById(R.id.txtTransmisionMoto3);  txtSuspensionDelanValor3 = mview.findViewById(R.id.txtSuspensionDelanValor3);  txtSuspensionTrasValor3 = mview.findViewById(R.id.txtSuspensionTrasValor3);  txtFrenoDelanValor3 = mview.findViewById(R.id.txtFrenoDelanValor3);  txtFrenoTrasMoto3 = mview.findViewById(R.id.txtFrenoTrasMoto3);  txtTanqueMoto3 = mview.findViewById(R.id.txtTanqueMoto3);  txtPesoMoto3 = mview.findViewById(R.id.txtPesoMoto3); txtPrecioMoto2 = mview.findViewById(R.id.txtPrecioMoto2);
        txtCilindrajevalor3= mview.findViewById(R.id.txtCilindrajevalor3);  txtMotorvalor3 = mview.findViewById(R.id.txtMotorvalor3);  txtTorqueValor3 = mview.findViewById(R.id.txtTorqueValor3);  txtAlimentacionValor3 = mview.findViewById(R.id.txtAlimentacionValor3);  txtRefrigeracionMoto3 = mview.findViewById(R.id.txtRefrigeracionMoto3);  txtTransmisionValor3 = mview.findViewById(R.id.txtTransmisionValor3);  txtSuspensionDelanMoto3 = mview.findViewById(R.id.txtSuspensionDelanMoto3);  txtSuspensionTrasMoto3 = mview.findViewById(R.id.txtSuspensionTrasMoto3);  txtFrenoDelanMoto3 = mview.findViewById(R.id.txtFrenoDelanMoto3);  txtFrenoTrasValor3 = mview.findViewById(R.id.txtFrenoTrasValor3);  txtTanqueValor3 = mview.findViewById(R.id.txtTanqueValor3);  txtPesoValor3 = mview.findViewById(R.id.txtPesoValor3); txtPrecioMoto3 = mview.findViewById(R.id.txtPrecioMoto3);

        mpostProvider = new PostProvider();

        //se agrega una imagen vacia a la lista
        imagenesList.add("https://firebasestorage.googleapis.com/v0/b/appmotos-fc479.appspot.com/o/vacio.jpg?alt=media&token=fce18573-39a5-45bc-907f-c426bb6f0bb2");
        nombreImagenes.add("  VACIO");
        mostrarTextos(0);

        llenarListas();

        imgComprarMoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarMotos(view, 1);
            }
        });
        imgComprarMoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarMotos(view, 2);
            }
        });
        imgComprarMoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarMotos(view, 3);
            }
        });



        return mview;
    }

    private void cargarMotos(View view, int contadorImgView){
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        final int[] index = {0};
        for (String imagePath : imagenesList) {

            Picasso.get().load(imagePath).resize(250,250).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {


                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);

                    Drawable drawable = new BitmapDrawable(getResources(), resizedBitmap);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                    String imageName = " " + nombreImagenes.get(index[0]);


                    SpannableString spannableString = new SpannableString(imageName);

                    ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);

                    spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                    popupMenu.getMenu().add(spannableString);
                    index[0]++;
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String selectedMoto = item.getTitle().toString();
                int position = -1;
                if (!selectedMoto.equals("   VACIO")){


                    for (int i = 0; i < nombreImagenes.size(); i++) {
                        if (selectedMoto.contains(nombreImagenes.get(i))) {
                            position = i;
                            break;
                        }
                    }
                    if (position != -1) {
                        String imagePath = imagenesList.get(position);
                        if (contadorImgView == 1) {
                            Picasso.get().load(imagePath).into(imgComprarMoto1);
                            mostrarTextos(1);
                            mostrarDatos(position, contadorImgView);
                        } else if (contadorImgView == 2) {
                            Picasso.get().load(imagePath).into(imgComprarMoto2);
                            mostrarTextos(2);
                            mostrarDatos(position, contadorImgView);
                        }else if (contadorImgView == 3){
                            Picasso.get().load(imagePath).into(imgComprarMoto3);
                            mostrarTextos(3);
                            mostrarDatos(position, contadorImgView);
                        }
                    } else {

                    }
                }else {
                    mostrarDatos(position, contadorImgView);
                }

                return true;
            }
        });
        popupMenu.show();
    }

    private void llenarListas(){
        final int[] sumar = {0};
        Query query = mpostProvider.getAll();
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    List<String> imagenes = (List<String>) document.get("imagenes");
                    if (imagenes != null && !imagenes.isEmpty()) {
                        imagenesList.add(imagenes.get(0));
                    }
                    String datos = document.get("nombreMoto").toString();
                    if (datos != null && !datos.isEmpty()) {
                        nombreImagenes.add(datos);
                    }
                    datos = document.get("marcaMoto").toString();
                    List<String> datosDocumentos = new ArrayList<>();
                    if (datos != null && datos.equals("AUTECO")) {
                        for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            String stringValue = String.valueOf(value);
                            datosDocumentos.add(stringValue);
                        }
                    }
                    else if (datos != null && datos.equals("YAMAHA")) {

                        for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            String stringValue = String.valueOf(value);
                            datosDocumentos.add(stringValue);
                        }
                    }
                    else if (datos != null && datos.equals("AKT")) {
                        for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            String stringValue = String.valueOf(value);
                            datosDocumentos.add(stringValue);
                        }
                    }
                    listasDocumentos.add(datosDocumentos);
                    sumar[0]++;
                }
            } else {
            }
        });
    }

    private void mostrarDatos(int position, int contadorimgView){
        if (position > 0){
            Query query = mpostProvider.getAll();
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    int contador = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (contador == position-1) {
                            if (contadorimgView == 1) {
                                txtCilindrajevalor1.setText(document.getString("cilindraje"));
                                if (document.getString("motor") == null){
                                    txtMotorvalor1.setText(document.getString("tipoMotor"));
                                }else {
                                    txtMotorvalor1.setText(document.getString("motor"));
                                }
                                txtTorqueValor1.setText(document.getString("torqueMaximo"));
                                txtAlimentacionValor1.setText(document.getString("sistemaAlimentacion"));
                                txtRefrigeracionValor1.setText(document.getString("refrigeracion"));
                                if (document.getString("transmision") == null){
                                    txtTransmisionValor1.setText(document.getString("tipoTransmision"));
                                }else {
                                    txtTransmisionValor1.setText(document.getString("transmision"));
                                }
                                if (document.getString("suspensionDelantera") == null){
                                    txtSuspensionDelanValor1.setText(document.getString("tipoSuspensionDelantera"));
                                }else {
                                    txtSuspensionDelanValor1.setText(document.getString("suspensionDelantera"));
                                }
                                if (document.getString("suspensionTrasera") == null){
                                    txtSuspensionTrasValor1.setText(document.getString("tipoSuspensionTrasera"));
                                }else {
                                    txtSuspensionTrasValor1.setText(document.getString("suspensionTrasera"));
                                }

                                txtFrenoDelanValor1.setText(document.getString("frenoDelantero"));
                                txtFrenoTrasValor1.setText(document.getString("frenoTrasero"));
                                txtTanqueValor1.setText(document.getString("capacidadTanque"));
                                if (document.getString("pesoNeto") == null){
                                    txtPesoValor1.setText(document.getString("peso"));
                                }else {
                                    txtPesoValor1.setText(document.getString("pesoNeto"));
                                }
                                txtPrecioValor1.setText(document.getString("precio"));
                                controlador_Imagenes1=1;
                            }
                            else if (contadorimgView == 2) {
                                txtCilindrajevalor2.setText(document.getString("cilindraje"));
                                if (document.getString("motor") == null){
                                    txtMotorvalor2.setText(document.getString("tipoMotor"));
                                }else {
                                    txtMotorvalor2.setText(document.getString("motor"));
                                }
                                txtTorqueValor2.setText(document.getString("torqueMaximo"));
                                txtAlimentacionValor2.setText(document.getString("sistemaAlimentacion"));
                                txtRefrigeracionValor2.setText(document.getString("refrigeracion"));
                                if (document.getString("transmision") == null){
                                    txtTransmisionValor2.setText(document.getString("tipoTransmision"));
                                }else {
                                    txtTransmisionValor2.setText(document.getString("transmision"));
                                }
                                if (document.getString("suspensionDelantera") == null){
                                    txtSuspensionDelanValor2.setText(document.getString("tipoSuspensionDelantera"));
                                }else {
                                    txtSuspensionDelanValor2.setText(document.getString("suspensionDelantera"));
                                }
                                if (document.getString("suspensionTrasera") == null){
                                    txtSuspensionTrasValor2.setText(document.getString("tipoSuspensionTrasera"));
                                }else {
                                    txtSuspensionTrasValor2.setText(document.getString("suspensionTrasera"));
                                }

                                txtFrenoDelanValor2.setText(document.getString("frenoDelantero"));
                                txtFrenoTrasValor2.setText(document.getString("frenoTrasero"));
                                txtTanqueValor2.setText(document.getString("capacidadTanque"));
                                if (document.getString("pesoNeto") == null){
                                    txtPesoValor2.setText(document.getString("peso"));
                                }else {
                                    txtPesoValor2.setText(document.getString("pesoNeto"));
                                }
                                txtPrecioValor2.setText(document.getString("precio"));
                                controlador_Imagenes2=1;
                            }
                            else if (contadorimgView == 3) {
                                txtCilindrajevalor3.setText(document.getString("cilindraje"));
                                if (document.getString("motor") == null){
                                    txtMotorvalor3.setText(document.getString("tipoMotor"));
                                }else {
                                    txtMotorvalor3.setText(document.getString("motor"));
                                }
                                txtTorqueValor3.setText(document.getString("torqueMaximo"));
                                txtAlimentacionValor3.setText(document.getString("sistemaAlimentacion"));
                                txtRefrigeracionValor3.setText(document.getString("refrigeracion"));
                                if (document.getString("transmision") == null){
                                    txtTransmisionValor3.setText(document.getString("tipoTransmision"));
                                }else {
                                    txtTransmisionValor3.setText(document.getString("transmision"));
                                }
                                if (document.getString("suspensionDelantera") == null){
                                    txtSuspensionDelanValor3.setText(document.getString("tipoSuspensionDelantera"));
                                }else {
                                    txtSuspensionDelanValor3.setText(document.getString("suspensionDelantera"));
                                }
                                if (document.getString("suspensionTrasera") == null){
                                    txtSuspensionTrasValor3.setText(document.getString("tipoSuspensionTrasera"));
                                }else {
                                    txtSuspensionTrasValor3.setText(document.getString("suspensionTrasera"));
                                }
                                txtFrenoDelanValor3.setText(document.getString("frenoDelantero"));
                                txtFrenoTrasValor3.setText(document.getString("frenoTrasero"));
                                txtTanqueValor3.setText(document.getString("capacidadTanque"));
                                if (document.getString("pesoNeto") == null){
                                    txtPesoValor3.setText(document.getString("peso"));
                                }else {
                                    txtPesoValor3.setText(document.getString("pesoNeto"));
                                }
                                txtPrecioValor3.setText(document.getString("precio"));
                                controlador_Imagenes3=1;
                            }
                            break;
                        }
                        contador++;
                    }
                } else {
                }
            });
        }else{
            if (contadorimgView == 1){
                imgComprarMoto1.setImageResource(R.drawable.ic_sumar_sinfondo);
                controlador_Imagenes1 = 0;
                limpiarTextos(contadorimgView);
            } else if (contadorimgView == 2){
                imgComprarMoto2.setImageResource(R.drawable.ic_sumar_sinfondo);
                controlador_Imagenes2= 0;
                limpiarTextos(contadorimgView);
            } else if (contadorimgView == 3){
                imgComprarMoto3.setImageResource(R.drawable.ic_sumar_sinfondo);
                controlador_Imagenes3= 0;
                limpiarTextos(contadorimgView);
            }

            if (controlador_Imagenes1 == 0 && controlador_Imagenes2 == 0 && controlador_Imagenes3 == 0) {
                mostrarTextos(0);
            }
        }

    }
    private void limpiarTextos(int controlador){
        if (controlador == 1) {
            txtCilindrajevalor1.setText("");
            txtCilindrajeMoto1.setVisibility(View.GONE);
            txtCilindrajevalor1.setVisibility(View.GONE);
            txtMotorMoto1.setVisibility(View.GONE);
            txtMotorvalor1.setVisibility(View.GONE);
            txtTorqueMoto1.setVisibility(View.GONE);
            txtTorqueValor1.setVisibility(View.GONE);
            txtAlimentacionMoto1.setVisibility(View.GONE);
            txtAlimentacionValor1.setVisibility(View.GONE);
            txtRefrigeracionMoto1.setVisibility(View.GONE);
            txtRefrigeracionValor1.setVisibility(View.GONE);
            txtTransmisionMoto1.setVisibility(View.GONE);
            txtTransmisionValor1.setVisibility(View.GONE);
            txtSuspensionDelanMoto1.setVisibility(View.GONE);
            txtSuspensionDelanValor1.setVisibility(View.GONE);
            txtSuspensionTrasMoto1.setVisibility(View.GONE);
            txtSuspensionTrasValor1.setVisibility(View.GONE);
            txtFrenoDelanMoto1.setVisibility(View.GONE);
            txtFrenoDelanValor1.setVisibility(View.GONE);
            txtFrenoTrasMoto1.setVisibility(View.GONE);
            txtFrenoTrasValor1.setVisibility(View.GONE);
            txtTanqueMoto1.setVisibility(View.GONE);
            txtTanqueValor1.setVisibility(View.GONE);
            txtPesoMoto1.setVisibility(View.GONE);
            txtPesoValor1.setVisibility(View.GONE);
            txtPrecioMoto1.setVisibility(View.GONE);
            txtPrecioValor1.setVisibility(View.GONE);
        }else if (controlador == 2) {
            txtCilindrajevalor2.setText("");
            txtCilindrajeMoto2.setVisibility(View.GONE);
            txtCilindrajevalor2.setVisibility(View.GONE);
            txtMotorMoto2.setVisibility(View.GONE);
            txtMotorvalor2.setVisibility(View.GONE);
            txtTorqueMoto2.setVisibility(View.GONE);
            txtTorqueValor2.setVisibility(View.GONE);
            txtAlimentacionMoto2.setVisibility(View.GONE);
            txtAlimentacionValor2.setVisibility(View.GONE);
            txtRefrigeracionMoto2.setVisibility(View.GONE);
            txtRefrigeracionValor2.setVisibility(View.GONE);
            txtTransmisionMoto2.setVisibility(View.GONE);
            txtTransmisionValor2.setVisibility(View.GONE);
            txtSuspensionDelanMoto2.setVisibility(View.GONE);
            txtSuspensionDelanValor2.setVisibility(View.GONE);
            txtSuspensionTrasMoto2.setVisibility(View.GONE);
            txtSuspensionTrasValor2.setVisibility(View.GONE);
            txtFrenoDelanMoto2.setVisibility(View.GONE);
            txtFrenoDelanValor2.setVisibility(View.GONE);
            txtFrenoTrasMoto2.setVisibility(View.GONE);
            txtFrenoTrasValor2.setVisibility(View.GONE);
            txtTanqueMoto2.setVisibility(View.GONE);
            txtTanqueValor2.setVisibility(View.GONE);
            txtPesoMoto2.setVisibility(View.GONE);
            txtPesoValor2.setVisibility(View.GONE);
            txtPrecioMoto2.setVisibility(View.GONE);
            txtPrecioValor2.setVisibility(View.GONE);

        }else if (controlador == 3) {
            txtCilindrajevalor3.setText("");
            txtCilindrajeMoto3.setVisibility(View.GONE);
            txtCilindrajevalor3.setVisibility(View.GONE);
            txtMotorMoto3.setVisibility(View.GONE);
            txtMotorvalor3.setVisibility(View.GONE);
            txtTorqueMoto3.setVisibility(View.GONE);
            txtTorqueValor3.setVisibility(View.GONE);
            txtAlimentacionMoto3.setVisibility(View.GONE);
            txtAlimentacionValor3.setVisibility(View.GONE);
            txtRefrigeracionMoto3.setVisibility(View.GONE);
            txtRefrigeracionValor3.setVisibility(View.GONE);
            txtTransmisionMoto3.setVisibility(View.GONE);
            txtTransmisionValor3.setVisibility(View.GONE);
            txtSuspensionDelanMoto3.setVisibility(View.GONE);
            txtSuspensionDelanValor3.setVisibility(View.GONE);
            txtSuspensionTrasMoto3.setVisibility(View.GONE);
            txtSuspensionTrasValor3.setVisibility(View.GONE);
            txtFrenoDelanMoto3.setVisibility(View.GONE);
            txtFrenoDelanValor3.setVisibility(View.GONE);
            txtFrenoTrasMoto3.setVisibility(View.GONE);
            txtFrenoTrasValor3.setVisibility(View.GONE);
            txtTanqueMoto3.setVisibility(View.GONE);
            txtTanqueValor3.setVisibility(View.GONE);
            txtPesoMoto3.setVisibility(View.GONE);
            txtPesoValor3.setVisibility(View.GONE);
            txtPrecioMoto3.setVisibility(View.GONE);
            txtPrecioValor3.setVisibility(View.GONE);
        }
    }
    private void mostrarTextos(int controlador){
        cdlImagenfondo.setBackgroundColor(Color.WHITE);
        nscImagenfondo.setBackgroundColor(Color.WHITE);
        viewRaya1.setVisibility(View.VISIBLE);
        viewRaya2.setVisibility(View.VISIBLE);
        if (controlador == 0) {
            cdlImagenfondo.setBackgroundResource(imagenFondo1);
            nscImagenfondo.setBackgroundResource(imagenFondo2);
            viewRaya1.setVisibility(View.GONE);
            viewRaya2.setVisibility(View.GONE);
            txtCilindrajeMoto1.setVisibility(View.GONE);
            txtCilindrajevalor1.setVisibility(View.GONE);
            txtMotorMoto1.setVisibility(View.GONE);
            txtMotorvalor1.setVisibility(View.GONE);
            txtTorqueMoto1.setVisibility(View.GONE);
            txtTorqueValor1.setVisibility(View.GONE);
            txtAlimentacionMoto1.setVisibility(View.GONE);
            txtAlimentacionValor1.setVisibility(View.GONE);
            txtRefrigeracionMoto1.setVisibility(View.GONE);
            txtRefrigeracionValor1.setVisibility(View.GONE);
            txtTransmisionMoto1.setVisibility(View.GONE);
            txtTransmisionValor1.setVisibility(View.GONE);
            txtSuspensionDelanMoto1.setVisibility(View.GONE);
            txtSuspensionDelanValor1.setVisibility(View.GONE);
            txtSuspensionTrasMoto1.setVisibility(View.GONE);
            txtSuspensionTrasValor1.setVisibility(View.GONE);
            txtFrenoDelanMoto1.setVisibility(View.GONE);
            txtFrenoDelanValor1.setVisibility(View.GONE);
            txtFrenoTrasMoto1.setVisibility(View.GONE);
            txtFrenoTrasValor1.setVisibility(View.GONE);
            txtTanqueMoto1.setVisibility(View.GONE);
            txtTanqueValor1.setVisibility(View.GONE);
            txtPesoMoto1.setVisibility(View.GONE);
            txtPesoValor1.setVisibility(View.GONE);
            txtCilindrajeMoto2.setVisibility(View.GONE);
            txtCilindrajevalor2.setVisibility(View.GONE);
            txtMotorMoto2.setVisibility(View.GONE);
            txtMotorvalor2.setVisibility(View.GONE);
            txtTorqueMoto2.setVisibility(View.GONE);
            txtTorqueValor2.setVisibility(View.GONE);
            txtAlimentacionMoto2.setVisibility(View.GONE);
            txtAlimentacionValor2.setVisibility(View.GONE);
            txtRefrigeracionMoto2.setVisibility(View.GONE);
            txtRefrigeracionValor2.setVisibility(View.GONE);
            txtTransmisionMoto2.setVisibility(View.GONE);
            txtTransmisionValor2.setVisibility(View.GONE);
            txtSuspensionDelanMoto2.setVisibility(View.GONE);
            txtSuspensionDelanValor2.setVisibility(View.GONE);
            txtSuspensionTrasMoto2.setVisibility(View.GONE);
            txtSuspensionTrasValor2.setVisibility(View.GONE);
            txtFrenoDelanMoto2.setVisibility(View.GONE);
            txtFrenoDelanValor2.setVisibility(View.GONE);
            txtFrenoTrasMoto2.setVisibility(View.GONE);
            txtFrenoTrasValor2.setVisibility(View.GONE);
            txtTanqueMoto2.setVisibility(View.GONE);
            txtTanqueValor2.setVisibility(View.GONE);
            txtPesoMoto2.setVisibility(View.GONE);
            txtPesoValor2.setVisibility(View.GONE);
            txtCilindrajeMoto3.setVisibility(View.GONE);
            txtCilindrajevalor3.setVisibility(View.GONE);
            txtMotorMoto3.setVisibility(View.GONE);
            txtMotorvalor3.setVisibility(View.GONE);
            txtTorqueMoto3.setVisibility(View.GONE);
            txtTorqueValor3.setVisibility(View.GONE);
            txtAlimentacionMoto3.setVisibility(View.GONE);
            txtAlimentacionValor3.setVisibility(View.GONE);
            txtRefrigeracionMoto3.setVisibility(View.GONE);
            txtRefrigeracionValor3.setVisibility(View.GONE);
            txtTransmisionMoto3.setVisibility(View.GONE);
            txtTransmisionValor3.setVisibility(View.GONE);
            txtSuspensionDelanMoto3.setVisibility(View.GONE);
            txtSuspensionDelanValor3.setVisibility(View.GONE);
            txtSuspensionTrasMoto3.setVisibility(View.GONE);
            txtSuspensionTrasValor3.setVisibility(View.GONE);
            txtFrenoDelanMoto3.setVisibility(View.GONE);
            txtFrenoDelanValor3.setVisibility(View.GONE);
            txtFrenoTrasMoto3.setVisibility(View.GONE);
            txtFrenoTrasValor3.setVisibility(View.GONE);
            txtTanqueMoto3.setVisibility(View.GONE);
            txtTanqueValor3.setVisibility(View.GONE);
            txtPesoMoto3.setVisibility(View.GONE);
            txtPesoValor3.setVisibility(View.GONE);
            txtPrecioMoto1.setVisibility(View.GONE);
            txtPrecioMoto2.setVisibility(View.GONE);
            txtPrecioMoto3.setVisibility(View.GONE);
            txtPrecioValor1.setVisibility(View.GONE);
            txtPrecioValor2.setVisibility(View.GONE);
            txtPrecioValor3.setVisibility(View.GONE);
        }else if (controlador == 1) {
            txtCilindrajeMoto1.setVisibility(View.VISIBLE);
            txtCilindrajevalor1.setVisibility(View.VISIBLE);
            txtMotorMoto1.setVisibility(View.VISIBLE);
            txtMotorvalor1.setVisibility(View.VISIBLE);
            txtTorqueMoto1.setVisibility(View.VISIBLE);
            txtTorqueValor1.setVisibility(View.VISIBLE);
            txtAlimentacionMoto1.setVisibility(View.VISIBLE);
            txtAlimentacionValor1.setVisibility(View.VISIBLE);
            txtRefrigeracionMoto1.setVisibility(View.VISIBLE);
            txtRefrigeracionValor1.setVisibility(View.VISIBLE);
            txtTransmisionMoto1.setVisibility(View.VISIBLE);
            txtTransmisionValor1.setVisibility(View.VISIBLE);
            txtSuspensionDelanMoto1.setVisibility(View.VISIBLE);
            txtSuspensionDelanValor1.setVisibility(View.VISIBLE);
            txtSuspensionTrasMoto1.setVisibility(View.VISIBLE);
            txtSuspensionTrasValor1.setVisibility(View.VISIBLE);
            txtFrenoDelanMoto1.setVisibility(View.VISIBLE);
            txtFrenoDelanValor1.setVisibility(View.VISIBLE);
            txtFrenoTrasMoto1.setVisibility(View.VISIBLE);
            txtFrenoTrasValor1.setVisibility(View.VISIBLE);
            txtTanqueMoto1.setVisibility(View.VISIBLE);
            txtTanqueValor1.setVisibility(View.VISIBLE);
            txtPesoMoto1.setVisibility(View.VISIBLE);
            txtPesoValor1.setVisibility(View.VISIBLE);
            txtPrecioMoto1.setVisibility(View.VISIBLE);
            txtPrecioValor1.setVisibility(View.VISIBLE);
        }else if (controlador == 2) {
            txtCilindrajeMoto2.setVisibility(View.VISIBLE);
            txtCilindrajevalor2.setVisibility(View.VISIBLE);
            txtMotorMoto2.setVisibility(View.VISIBLE);
            txtMotorvalor2.setVisibility(View.VISIBLE);
            txtTorqueMoto2.setVisibility(View.VISIBLE);
            txtTorqueValor2.setVisibility(View.VISIBLE);
            txtAlimentacionMoto2.setVisibility(View.VISIBLE);
            txtAlimentacionValor2.setVisibility(View.VISIBLE);
            txtRefrigeracionMoto2.setVisibility(View.VISIBLE);
            txtRefrigeracionValor2.setVisibility(View.VISIBLE);
            txtTransmisionMoto2.setVisibility(View.VISIBLE);
            txtTransmisionValor2.setVisibility(View.VISIBLE);
            txtSuspensionDelanMoto2.setVisibility(View.VISIBLE);
            txtSuspensionDelanValor2.setVisibility(View.VISIBLE);
            txtSuspensionTrasMoto2.setVisibility(View.VISIBLE);
            txtSuspensionTrasValor2.setVisibility(View.VISIBLE);
            txtFrenoDelanMoto2.setVisibility(View.VISIBLE);
            txtFrenoDelanValor2.setVisibility(View.VISIBLE);
            txtFrenoTrasMoto2.setVisibility(View.VISIBLE);
            txtFrenoTrasValor2.setVisibility(View.VISIBLE);
            txtTanqueMoto2.setVisibility(View.VISIBLE);
            txtTanqueValor2.setVisibility(View.VISIBLE);
            txtPesoMoto2.setVisibility(View.VISIBLE);
            txtPesoValor2.setVisibility(View.VISIBLE);
            txtPrecioMoto2.setVisibility(View.VISIBLE);
            txtPrecioValor2.setVisibility(View.VISIBLE);
        }else if (controlador == 3) {
            txtCilindrajeMoto3.setVisibility(View.VISIBLE);
            txtCilindrajevalor3.setVisibility(View.VISIBLE);
            txtMotorMoto3.setVisibility(View.VISIBLE);
            txtMotorvalor3.setVisibility(View.VISIBLE);
            txtTorqueMoto3.setVisibility(View.VISIBLE);
            txtTorqueValor3.setVisibility(View.VISIBLE);
            txtAlimentacionMoto3.setVisibility(View.VISIBLE);
            txtAlimentacionValor3.setVisibility(View.VISIBLE);
            txtRefrigeracionMoto3.setVisibility(View.VISIBLE);
            txtRefrigeracionValor3.setVisibility(View.VISIBLE);
            txtTransmisionMoto3.setVisibility(View.VISIBLE);
            txtTransmisionValor3.setVisibility(View.VISIBLE);
            txtSuspensionDelanMoto3.setVisibility(View.VISIBLE);
            txtSuspensionDelanValor3.setVisibility(View.VISIBLE);
            txtSuspensionTrasMoto3.setVisibility(View.VISIBLE);
            txtSuspensionTrasValor3.setVisibility(View.VISIBLE);
            txtFrenoDelanMoto3.setVisibility(View.VISIBLE);
            txtFrenoDelanValor3.setVisibility(View.VISIBLE);
            txtFrenoTrasMoto3.setVisibility(View.VISIBLE);
            txtFrenoTrasValor3.setVisibility(View.VISIBLE);
            txtTanqueMoto3.setVisibility(View.VISIBLE);
            txtTanqueValor3.setVisibility(View.VISIBLE);
            txtPesoMoto3.setVisibility(View.VISIBLE);
            txtPesoValor3.setVisibility(View.VISIBLE);
            txtPrecioMoto3.setVisibility(View.VISIBLE);
            txtPrecioValor3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
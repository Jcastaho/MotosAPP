package com.straccion.motos_admin.ui.addmotos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.UploadTask;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.providers.ImageProvider;
import com.straccion.motos_admin.providers.PostProvider;
import com.straccion.motos_admin.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AggImagenesFragment extends Fragment {
    View mview;
    File ImageFile1;
    File ImageFile2;
    File ImageFile3;
    File ImageFile4;
    File ImageFile5;
    ImageView imagen1;
    ImageView imagen2;
    ImageView imagen3;
    ImageView imagen4;
    ImageView imagen5;
    Button btnGuardar;
    Button btnHacerAnalisis;
    EditText edtColorAdd;
    Button btnActivarPrimario;
    AppCompatSpinner spnTodo;
    AppCompatSpinner spnSinDatos;
    AppCompatSpinner spnColoresTodo;

    PostProvider mpostProvider;
    ImageProvider mImageProvider;

    String nombreMotos="";
    String seleccionMoto="";
    String seleccionMotoSin="";
    String seleccionColorMoto="";
    String carpeta1;
    String carpeta2;
    String carpeta3;
    String dato ="";
    String marcaMoto ="";
    String transmision ="";
    int primarioAdicional=0;

    String id ="";
    List<String> nombreMotoSin = new ArrayList<>();
    List<String> nombreMotoCon = new ArrayList<>();
    List<String> colores = new ArrayList<>();
    List<String> caracteristicas = new ArrayList<>();
    double calificacion =0;
    double cilindraje =0;
    int controlador = 0;
    int posicion = 0;


    public AggImagenesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_agg_imagenes, container, false);

        spnTodo = mview.findViewById(R.id.spnTodo);
        spnColoresTodo = mview.findViewById(R.id.spnColoresTodo);
        spnSinDatos = mview.findViewById(R.id.spnSinDatos);
        imagen1 = mview.findViewById(R.id.imagen1);
        imagen2 = mview.findViewById(R.id.imagen2);
        imagen3 = mview.findViewById(R.id.imagen3);
        imagen4 = mview.findViewById(R.id.imagen4);
        imagen5 = mview.findViewById(R.id.imagen5);
        btnGuardar = mview.findViewById(R.id.btnGuardar);
        edtColorAdd = mview.findViewById(R.id.edtColorAdd);
        btnHacerAnalisis = mview.findViewById(R.id.btnHacerAnalisis);
        btnActivarPrimario = mview.findViewById(R.id.btnActivarPrimario);

        mpostProvider = new PostProvider();
        mImageProvider = new ImageProvider();
        llenarListas();

        spnTodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long id) {
                seleccionMoto = spnTodo.getSelectedItem().toString();
                llenarListacolores();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnSinDatos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long id) {
                seleccionMotoSin = spnSinDatos.getSelectedItem().toString();
                llenarListacolores();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnColoresTodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                posicion = pos;
                seleccionColorMoto = spnColoresTodo.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlador = 1;
                openGallery();
            }
        });
        imagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlador = 2;
                openGallery();
            }
        });

        imagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlador = 3;
                openGallery();
            }
        });
        imagen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlador = 4;
                openGallery();
            }
        });
        imagen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlador = 5;
                openGallery();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        btnHacerAnalisis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recolectarDatos();
            }
        });
        btnActivarPrimario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (primarioAdicional == 0){
                    primarioAdicional = 1;
                    btnActivarPrimario.setText("DESACTIVAR");
                }else {
                    primarioAdicional = 0;
                    btnActivarPrimario.setText("ACTIVAR");
                }

            }
        });


        return mview;
    }

    private void guardar(){
        dato = edtColorAdd.getText().toString().toString();
        if (!dato.equals("")){
            if (!seleccionMoto.equals("NINGUNA")){
                String nombreImagen1 = seleccionMoto + "_" + edtColorAdd.getText().toString() + "_1";
                String nombreImagen2 = seleccionMoto + "_" + edtColorAdd.getText().toString() + "_2";
                String nombreImagen3 = seleccionMoto + "_" + edtColorAdd.getText().toString() + "_3";
                String nombreImagen4 = seleccionMoto + "_" + edtColorAdd.getText().toString() + "_4";
                String nombreImagen5 = seleccionMoto + "_" + edtColorAdd.getText().toString() + "_5";
                saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                        nombreImagen4, nombreImagen5, carpeta1, carpeta2, carpeta3);
            }
            else {
                String nombreImagen1 = seleccionMotoSin + "_" + edtColorAdd.getText().toString() + "_1";
                String nombreImagen2 = seleccionMotoSin + "_" + edtColorAdd.getText().toString() + "_2";
                String nombreImagen3 = seleccionMotoSin + "_" + edtColorAdd.getText().toString() + "_3";
                String nombreImagen4 = seleccionMotoSin + "_" + edtColorAdd.getText().toString() + "_4";
                String nombreImagen5 = seleccionMotoSin + "_" + edtColorAdd.getText().toString() + "_5";

                saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                        nombreImagen4, nombreImagen5, carpeta1, carpeta2, carpeta3);
            }
        }else {
            if (!seleccionMoto.equals("NINGUNA")){
                String nombreImagen1 = seleccionMoto + "_" + seleccionColorMoto + "_1";
                String nombreImagen2 = seleccionMoto + "_" + seleccionColorMoto + "_2";
                String nombreImagen3 = seleccionMoto + "_" + seleccionColorMoto + "_3";
                String nombreImagen4 = seleccionMoto + "_" + seleccionColorMoto + "_4";
                String nombreImagen5 = seleccionMoto + "_" + seleccionColorMoto + "_5";

                saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                        nombreImagen4, nombreImagen5, carpeta1, carpeta2, carpeta3);
            }else {
                String nombreImagen1 = seleccionMotoSin + "_" + seleccionColorMoto + "_1";
                String nombreImagen2 = seleccionMotoSin + "_" + seleccionColorMoto + "_2";
                String nombreImagen3 = seleccionMotoSin + "_" + seleccionColorMoto + "_3";
                String nombreImagen4 = seleccionMotoSin + "_" + seleccionColorMoto + "_4";
                String nombreImagen5 = seleccionMotoSin + "_" + seleccionColorMoto + "_5";

                saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                        nombreImagen4, nombreImagen5, carpeta1, carpeta2, carpeta3);
            }
        }

    }


    private void llenarListas(){
        nombreMotoSin.add("NINGUNA");
        Query query = mpostProvider.getAllVacias();
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    nombreMotos = document.get("nombreMoto").toString();
                    if (nombreMotos != null && !nombreMotos.isEmpty()) {
                        nombreMotoSin.add(nombreMotos);
                        mostrarListas(1);
                    }
                }
            } else {
            }
        });
        nombreMotoCon.add("NINGUNA");
        query = mpostProvider.getAll();
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    nombreMotos = document.get("nombreMoto").toString();
                    if (nombreMotos != null && !nombreMotos.isEmpty()) {
                        nombreMotoCon.add(nombreMotos);
                        mostrarListas(2);
                    }
                }
            } else {
            }
        });
    }
    private void llenarListacolores(){
        if (!seleccionMoto.equals("NINGUNA")){
            colores.clear();
            Query query = mpostProvider.buscarPorNombreMoto(seleccionMoto);
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        id = document.getId();
                        mpostProvider.getPostById(id).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()){
                                    if (documentSnapshot.contains("colores")) {
                                        colores = (List<String>) documentSnapshot.get("colores");
                                        mostrarListas(3);
                                    }
                                    if (documentSnapshot.contains("carpeta1")) {
                                        carpeta1 = documentSnapshot.getString("carpeta1");
                                    }
                                    if (documentSnapshot.contains("carpeta2")) {
                                        carpeta2 = documentSnapshot.getString("carpeta2");
                                    }
                                    if (documentSnapshot.contains("carpeta3")) {
                                        carpeta3 = documentSnapshot.getString("carpeta3");
                                    }
                                    if (documentSnapshot.contains("marcaMoto") && documentSnapshot.get("marcaMoto") != null) {
                                        marcaMoto = documentSnapshot.getString("marcaMoto");
                                    }
                                }
                            }
                        });

                    }
                } else {
                }
            });

        }else if (!seleccionMotoSin.equals("NINGUNA")) {
            colores.clear();
            Query query = mpostProvider.buscarPorNombreMoto(seleccionMotoSin);
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        id = document.getId();
                        mpostProvider.getPostById(id).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()){
                                    if (documentSnapshot.contains("colores")) {
                                        colores = (List<String>) documentSnapshot.get("colores");
                                        mostrarListas(3);
                                    }
                                    if (documentSnapshot.contains("carpeta1")) {
                                        carpeta1 = documentSnapshot.getString("carpeta1");
                                    }
                                    if (documentSnapshot.contains("carpeta2")) {
                                        carpeta2 = documentSnapshot.getString("carpeta2");
                                    }
                                    if (documentSnapshot.contains("carpeta3")) {
                                        carpeta3 = documentSnapshot.getString("carpeta3");
                                    }
                                    if (documentSnapshot.contains("marcaMoto") && documentSnapshot.get("marcaMoto") != null) {
                                        marcaMoto = documentSnapshot.getString("marcaMoto");
                                    }
                                }
                            }
                        });

                    }
                } else {
                }
            });
        }
    }
    private void mostrarListas(int controlador){
        if (controlador == 1){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_custom, nombreMotoSin);
            spnSinDatos.setAdapter(adapter);
        }
        else if(controlador == 2) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_custom, nombreMotoCon);
            spnTodo.setAdapter(adapter);
        } else if (controlador == 3) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_custom, colores);
            spnColoresTodo.setAdapter(adapter);
        }

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        GalleryLauncher.launch(galleryIntent);
    }
    ActivityResultLauncher<Intent> GalleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        try {
                            if (controlador == 1){
                                ImageFile1 = FileUtil.from(getContext(), result.getData().getData());
                                imagen1.setImageBitmap(BitmapFactory.decodeFile(ImageFile1.getAbsolutePath()));
                            } else if (controlador == 2) {
                                ImageFile2 = FileUtil.from(getContext(), result.getData().getData());
                                imagen2.setImageBitmap(BitmapFactory.decodeFile(ImageFile2.getAbsolutePath()));
                            }else if (controlador == 3) {
                                ImageFile3 = FileUtil.from(getContext(), result.getData().getData());
                                imagen3.setImageBitmap(BitmapFactory.decodeFile(ImageFile3.getAbsolutePath()));
                            }else if (controlador == 4) {
                                ImageFile4 = FileUtil.from(getContext(), result.getData().getData());
                                imagen4.setImageBitmap(BitmapFactory.decodeFile(ImageFile4.getAbsolutePath()));
                            }else if (controlador == 5) {
                                ImageFile5 = FileUtil.from(getContext(), result.getData().getData());
                                imagen5.setImageBitmap(BitmapFactory.decodeFile(ImageFile5.getAbsolutePath()));
                            }
                        }catch (Exception e){
                            Log.d("Error", "Se produjo un error" + e.getMessage());
                            Toast.makeText(getContext(), "Se produjo un error" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
    );

    private void saveImage(File ImageFile1, File ImageFile2, File ImageFile3, File ImageFile4, File ImageFile5, String nombreImagen1,
                           String nombreImagen2, String nombreImagen3, String nombreImagen4, String nombreImagen5, String carpeta1, String carpeta2, String carpeta3) {
        mImageProvider.save(getContext(), ImageFile1, carpeta1, carpeta2, carpeta3, nombreImagen1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            final String url1 = uri1.toString();
                            if (ImageFile2 != null && ImageFile2.exists()) {
                                mImageProvider.save(getContext(), ImageFile2, carpeta1, carpeta2, carpeta3, nombreImagen2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                        if (task2.isSuccessful()){
                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri2) {
                                                    final String url2 = uri2.toString();
                                                    if (ImageFile3 != null && ImageFile3.exists()) {
                                                        mImageProvider.save(getContext(), ImageFile3, carpeta1, carpeta2, carpeta3, nombreImagen3).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task3) {
                                                                if (task3.isSuccessful()){
                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri3) {
                                                                            final String url3 = uri3.toString();
                                                                            if (ImageFile4 != null && ImageFile4.exists()) {
                                                                                mImageProvider.save(getContext(), ImageFile4, carpeta1, carpeta2, carpeta3, nombreImagen4).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                        if (task4.isSuccessful()){
                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri uri4) {
                                                                                                    final String url4 = uri4.toString();
                                                                                                    if (ImageFile5 != null && ImageFile5.exists()) {
                                                                                                        mImageProvider.save(getContext(), ImageFile5, carpeta1, carpeta2, carpeta3, nombreImagen5).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                                                if (task4.isSuccessful()){
                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Uri uri5) {
                                                                                                                            final String url5 = uri5.toString();
                                                                                                                            guardarDatos(url1, url2, url3, url4, url5);
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                    }else {
                                                                                                        guardarDatos(url1, url2, url3, url4, null);
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            }else {
                                                                                guardarDatos(url1, url2, url3, null, null);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    }else {
                                                        guardarDatos(url1, url2, null, null, null);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }else {
                                guardarDatos(url1, null, null, null, null);
                            }
                        }
                    });
                }
            }
        });
    }
    private void guardarDatos(String url1, String url2, String url3, String url4, String url5){
        String imagenColores = "imagenesColores1";
        if (posicion==1){
            imagenColores =  "imagenesColores1";
        } else if (posicion==2){
            imagenColores =  "imagenesColores2";
        } else if (posicion==3){
            imagenColores =  "imagenesColores3";
        } else if (posicion==4){
            imagenColores =  "imagenesColores4";
        } else if (posicion==5){
            imagenColores =  "imagenesColores5";
        }
        else if (posicion==5){
            imagenColores =  "imagenesColores6";
        }
        Map<String, Object> updates = new HashMap<>();
        ArrayList<String> imagenesColores = new ArrayList<>();
        dato = edtColorAdd.getText().toString().toUpperCase();
        if (dato.equals("")){
            if (url1 != null){
                imagenesColores.add(url1);
            }if (url2 != null) {
                imagenesColores.add(url2);
            }if (url3 != null) {
                imagenesColores.add(url3);
            }if (url4 != null) {
                imagenesColores.add(url4);
            }if (url5 != null) {
                imagenesColores.add(url5);
            }
            updates.put(imagenColores, imagenesColores);
        }else if (!dato.equals(" ") && primarioAdicional == 1){
            ArrayList<String> imagenes = new ArrayList<>();
            ArrayList<String> Colores = new ArrayList<>();
            String comprar = colores.get(0);
            if (!comprar.equals(dato)){
                Colores.add(dato);
            }
            Colores.addAll(colores);
            if (url1 != null){
                imagenes.add(url1);
            }if (url2 != null) {
                imagenes.add(url2);
            }if (url3 != null) {
                imagenes.add(url3);
            }if (url4 != null) {
                imagenes.add(url4);
            }if (url5 != null) {
                imagenes.add(url5);
            }
            updates.put("colores", Colores);
            updates.put("imagenes", imagenes);
        }else if (!dato.equals(" ") && primarioAdicional == 0){
            ArrayList<String> Colores = new ArrayList<>();
            Colores.addAll(colores);
            Colores.add(dato);
            int numcolores = Colores.size()-1;
            if (url1 != null){
                imagenesColores.add(url1);
            }if (url2 != null) {
                imagenesColores.add(url2);
            }if (url3 != null) {
                imagenesColores.add(url3);
            }if (url4 != null) {
                imagenesColores.add(url4);
            }if (url5 != null) {
                imagenesColores.add(url5);
            }
            if (numcolores==1){
                imagenColores =  "imagenesColores1";
            } else if (numcolores==2){
                imagenColores =  "imagenesColores2";
            } else if (numcolores==3){
                imagenColores =  "imagenesColores3";
            } else if (numcolores==4){
                imagenColores =  "imagenesColores4";
            } else if (numcolores==5){
                imagenColores =  "imagenesColores5";
            }
            else if (posicion==5){
                imagenColores =  "imagenesColores6";
            }
            updates.put("colores", Colores);
            updates.put(imagenColores, imagenesColores);
        }
        mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "Color guardado", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
        limpiarCampos();
    }
    public void  limpiarCampos() {
        imagen1.setImageResource(R.drawable.ic_menu_camera);
        ImageFile1 = null;
        imagen2.setImageResource(R.drawable.ic_menu_camera);
        ImageFile2 = null;
        imagen3.setImageResource(R.drawable.ic_menu_camera);
        ImageFile3 = null;
        imagen4.setImageResource(R.drawable.ic_menu_camera);
        ImageFile4 = null;
        imagen5.setImageResource(R.drawable.ic_menu_camera);
        ImageFile5 = null;
    }

    private void recolectarDatos(){
        mpostProvider.getPostById(id).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    switch (marcaMoto) {
                        case "AUTECO":
                            if (documentSnapshot.getString("cilindraje") != null || documentSnapshot.getString("cilindraje") != "--"){
                                String valor = documentSnapshot.getString("cilindraje");
                                cilindraje = Double.parseDouble(valor);
                            }
                            if (documentSnapshot.getString("carpeta3") != null || documentSnapshot.getString("carpeta3") != "--"){
                                carpeta3 = documentSnapshot.getString("carpeta3");
                            }
//                            if (documentSnapshot.getString("motor") != null){
//                                motor.add(documentSnapshot.getString("motor"));
//                            }
//                            if (documentSnapshot.getString("torqueMaximo") != null){
//                                txt2.add(documentSnapshot.getString("torqueMaximo"));
//                            }
//                            if (documentSnapshot.getString("potenciaMaxima") != null){
//                                txt1.add("Potencia Maxima");
//                                txt2.add(documentSnapshot.getString("potenciaMaxima"));
//                            }
//                            if (documentSnapshot.getString("relacionCompresion") != null){
//                                txt1.add("Relacion de Compresión");
//                                txt2.add(documentSnapshot.getString("relacionCompresion"));
//                            }
//                            if (documentSnapshot.getString("SistemaAlimentacion") != null){
//                                txt1.add("Sistema de Alimentación");
//                                txt2.add(documentSnapshot.getString("SistemaAlimentacion"));
//                            }
//                            if (documentSnapshot.getString("diametroCarrera") != null){
//                                txt1.add("Diametro de Carrera");
//                                txt2.add(documentSnapshot.getString("diametroCarrera"));
//                            }
//                            if (documentSnapshot.getString("refrigeracion") != null){
//                                txt1.add("Refrigeración");
//                                txt2.add(documentSnapshot.getString("refrigeracion"));
//                            }
//                            if (documentSnapshot.getString("combustible") != null){
//                                txt1.add("Combustible");
//                                txt2.add(documentSnapshot.getString("combustible"));
//                            }
//                            if (documentSnapshot.getString("arranque") != null){
//                                txt1.add("Arranque");
//                                txt2.add(documentSnapshot.getString("arranque"));
//                            }
                            if (documentSnapshot.getString("transmision") != null){
                                transmision = documentSnapshot.getString("transmision");
                            }
//                            if (documentSnapshot.getString("capacidadTanque") != null){
//                                txt1.add("Capacidad de Tanque");
//                                txt2.add(documentSnapshot.getString("capacidadTanque"));
//                            }
//                            if (documentSnapshot.getString("suspensionDelantera") != null){
//                                txt1.add("Suspensión Delantera");
//                                txt2.add(documentSnapshot.getString("suspensionDelantera"));
//                            }
//                            if (documentSnapshot.getString("suspensionTrasera") != null){
//                                txt1.add("Suspensión Trasera");
//                                txt2.add(documentSnapshot.getString("suspensionTrasera"));
//                            }
//                            if (documentSnapshot.getString("frenoDelantero") != null){
//                                txt1.add("Freno Delantero");
//                                txt2.add(documentSnapshot.getString("frenoDelantero"));
//                            }
//                            if (documentSnapshot.getString("frenoTrasero") != null){
//                                txt1.add("Freno Trasero");
//                                txt2.add(documentSnapshot.getString("frenoTrasero"));
//                            }
//                            if (documentSnapshot.getString("llantaDelantera") != null){
//                                txt1.add("Llanta Delantera");
//                                txt2.add(documentSnapshot.getString("llantaDelantera"));
//                            }
//                            if (documentSnapshot.getString("llantaTrasera") != null){
//                                txt1.add("Llanta Trasera");
//                                txt2.add(documentSnapshot.getString("llantaTrasera"));
//                            }
//                            if (documentSnapshot.getString("rines") != null){
//                                txt1.add("Rines");
//                                txt2.add(documentSnapshot.getString("rines"));
//                            }
//                            if (documentSnapshot.getString("capacidadTanque") != null){
//                                txt1.add("Capacidad de Tanque");
//                                txt2.add(documentSnapshot.getString("capacidadTanque"));
//                            }
//                            if (documentSnapshot.getString("sistemaEncendido") != null){
//                                txt1.add("Sistema de Encendido");
//                                txt2.add(documentSnapshot.getString("sistemaEncendido"));
//                            }
//                            if (documentSnapshot.getString("largoTotal") != null){
//                                txt1.add("Largo Total");
//                                txt2.add(documentSnapshot.getString("largoTotal"));
//                            }
//                            if (documentSnapshot.getString("alturaTotal") != null){
//                                txt1.add("Altura Total");
//                                txt2.add(documentSnapshot.getString("alturaTotal"));
//                            }
//                            if (documentSnapshot.getString("anchoTotal") != null){
//                                txt1.add("Ancho Total");
//                                txt2.add(documentSnapshot.getString("anchoTotal"));
//                            }
//                            if (documentSnapshot.getString("distanciaEntreEjes") != null){
//                                txt1.add("Distancia Entre Ejes");
//                                txt2.add(documentSnapshot.getString("distanciaEntreEjes"));
//                            }
//                            if (documentSnapshot.getString("alturaSillin") != null){
//                                txt1.add("Altura Sillin");
//                                txt2.add(documentSnapshot.getString("alturaSillin"));
//                            }
//                            if (documentSnapshot.getString("pesoNeto") != null){
//                                txt1.add("Peso Neto");
//                                txt2.add(documentSnapshot.getString("pesoNeto"));
//                            }

                            break;
                        case "YAMAHA":
                            if (documentSnapshot.getString("cilindraje") != null || documentSnapshot.getString("cilindraje") != "--"){
                                String valor = documentSnapshot.getString("cilindraje");
                                cilindraje = Double.parseDouble(valor);
                            }
                            if (documentSnapshot.getString("carpeta3") != null || documentSnapshot.getString("carpeta3") != "--"){
                                carpeta3 = documentSnapshot.getString("carpeta3");
                            }
//                            if (documentSnapshot.getString("peso") != null || documentSnapshot.getString("peso") != "--"){
//                                txt1.add("Peso");
//                                txt2.add(documentSnapshot.getString("peso"));
//                            }
//                            if (documentSnapshot.getString("potenciaMaxima") != null || documentSnapshot.getString("potenciaMaxima") != "--"){
//                                txt1.add("Potencia Maxima");
//                                txt2.add(documentSnapshot.getString("potenciaMaxima"));
//                            }
//                            if (documentSnapshot.getString("torqueMaximo") != null || documentSnapshot.getString("torqueMaximo") != "--"){
//                                txt1.add("Torque Maximo");
//                                txt2.add(documentSnapshot.getString("torqueMaximo"));
//                            }
//                            if (documentSnapshot.getString("tipoMotor") != null || documentSnapshot.getString("tipoMotor") != "--"){
//                                txt1.add("Tipo de Motor");
//                                txt2.add(documentSnapshot.getString("tipoMotor"));
//                            }
//                            if (documentSnapshot.getString("largoTotal") != null || documentSnapshot.getString("largoTotal") != "--"){
//                                txt1.add("Largo Total");
//                                txt2.add(documentSnapshot.getString("largoTotal"));
//                            }
//                            if (documentSnapshot.getString("anchoTotal") != null || documentSnapshot.getString("anchoTotal") != "--"){
//                                txt1.add("Ancho Total");
//                                txt2.add(documentSnapshot.getString("anchoTotal"));
//                            }
//                            if (documentSnapshot.getString("alturaTotal") != null || documentSnapshot.getString("alturaTotal") != "--"){
//                                txt1.add("Altura Total");
//                                txt2.add(documentSnapshot.getString("alturaTotal"));
//                            }
//                            if (documentSnapshot.getString("alturaAsiento") != null || documentSnapshot.getString("alturaAsiento") != "--"){
//                                txt1.add("Altura del Asiento");
//                                txt2.add(documentSnapshot.getString("alturaAsiento"));
//                            }
//                            if (documentSnapshot.getString("distanciaEntreEjes") != null || documentSnapshot.getString("distanciaEntreEjes") != "--"){
//                                txt1.add("Distancia EntreEjes");
//                                txt2.add(documentSnapshot.getString("distanciaEntreEjes"));
//                            }
//                            if (documentSnapshot.getString("distanciaMinimaPiso") != null || documentSnapshot.getString("distanciaMinimaPiso") != "--"){
//                                txt1.add("Distancia Minima del Piso");
//                                txt2.add(documentSnapshot.getString("distanciaMinimaPiso"));
//                            }
//                            if (documentSnapshot.getString("tipoLubricacion") != null || documentSnapshot.getString("tipoLubricacion") != "--"){
//                                txt1.add("Tipo de Lubricación");
//                                txt2.add(documentSnapshot.getString("tipoLubricacion"));
//                            }
//                            if (documentSnapshot.getString("bateria") != null || documentSnapshot.getString("bateria") != "--"){
//                                txt1.add("Batería");
//                                txt2.add(documentSnapshot.getString("bateria"));
//                            }
//                            if (documentSnapshot.getString("disposiciondeCilindros") != null || documentSnapshot.getString("disposiciondeCilindros") != "--"){
//                                txt1.add("Disposición de Cilindros");
//                                txt2.add(documentSnapshot.getString("disposiciondeCilindros"));
//                            }
//                            if (documentSnapshot.getString("diametroPorCarrera") != null || documentSnapshot.getString("diametroPorCarrera") != "--"){
//                                txt1.add("Diametro Por Carrera");
//                                txt2.add(documentSnapshot.getString("diametroPorCarrera"));
//                            }
//                            if (documentSnapshot.getString("relacionCompresion") != null || documentSnapshot.getString("relacionCompresion") != "--"){
//                                txt1.add("Relación de Compresión");
//                                txt2.add(documentSnapshot.getString("relacionCompresion"));
//                            }
//                            if (documentSnapshot.getString("arranque") != null || documentSnapshot.getString("arranque") != "--"){
//                                txt1.add("Arranque");
//                                txt2.add(documentSnapshot.getString("arranque"));
//                            }
//                            if (documentSnapshot.getString("sistemaAlimentacion") != null || documentSnapshot.getString("sistemaAlimentacion") != "--"){
//                                txt1.add("Sistema de Alimentación");
//                                txt2.add(documentSnapshot.getString("sistemaAlimentacion"));
//                            }
//                            if (documentSnapshot.getString("capacidadCombustible") != null || documentSnapshot.getString("capacidadCombustible") != "--"){
//                                txt1.add("Capacidad de Combustible");
//                                txt2.add(documentSnapshot.getString("capacidadCombustible"));
//                            }
//                            if (documentSnapshot.getString("encendido") != null || documentSnapshot.getString("encendido") != "--"){
//                                txt1.add("Encendido");
//                                txt2.add(documentSnapshot.getString("encendido"));
//                            }
//                            if (documentSnapshot.getString("capacidadBateria") != null || documentSnapshot.getString("capacidadBateria") != "--"){
//                                txt1.add("Capacidad de Batería");
//                                txt2.add(documentSnapshot.getString("capacidadBateria"));
//                            }
//                            if (documentSnapshot.getString("sistemadeReduccionPrimaria") != null || documentSnapshot.getString("sistemadeReduccionPrimaria") != "--"){
//                                txt1.add("Sistema de Reducción Primaria");
//                                txt2.add(documentSnapshot.getString("sistemadeReduccionPrimaria"));
//                            }
//                            if (documentSnapshot.getString("relaciondeReduccionPrimaria") != null || documentSnapshot.getString("relaciondeReduccionPrimaria") != "--"){
//                                txt1.add("Relación de Reducción Primaria");
//                                txt2.add(documentSnapshot.getString("relaciondeReduccionPrimaria"));
//                            }
//                            if (documentSnapshot.getString("sistemadeReduccionSecundaria") != null || documentSnapshot.getString("sistemadeReduccionSecundaria") != "--"){
//                                txt1.add("Sistema de Reducción Secundaria");
//                                txt2.add(documentSnapshot.getString("sistemadeReduccionSecundaria"));
//                            }
//                            if (documentSnapshot.getString("relaciondeReduccionSecundaria") != null || documentSnapshot.getString("relaciondeReduccionSecundaria") != "--"){
//                                txt1.add("Relación de Reducción Secundaria");
//                                txt2.add(documentSnapshot.getString("relaciondeReduccionSecundaria"));
//                            }
//                            if (documentSnapshot.getString("tipoEmbrague") != null || documentSnapshot.getString("tipoEmbrague") != "--"){
//                                txt1.add("Tipo de Embrague");
//                                txt2.add(documentSnapshot.getString("tipoEmbrague"));
//                            }
//                            if (documentSnapshot.getString("tipoTransmision") != null || documentSnapshot.getString("tipoTransmision") != "--"){
//                                txt1.add("Tipo de Transmisión");
//                                txt2.add(documentSnapshot.getString("tipoTransmision"));
//                            }
//                            if (documentSnapshot.getString("relacionTransmision1ra") != null || documentSnapshot.getString("relacionTransmision1ra") != "--"){
//                                txt1.add("Relación Transmision en 1ra");
//                                txt2.add(documentSnapshot.getString("relacionTransmision1ra"));
//                            }
//                            if (documentSnapshot.getString("relacionTransmision2da") != null || documentSnapshot.getString("relacionTransmision2da") != "--"){
//                                txt1.add("Relación Transmision en 2da");
//                                txt2.add(documentSnapshot.getString("relacionTransmision2da"));
//                            }
//                            if (documentSnapshot.getString("relacionTransmision3ra") != null || documentSnapshot.getString("relacionTransmision3ra") != "--"){
//                                txt1.add("Relación Transmision en 3ra");
//                                txt2.add(documentSnapshot.getString("relacionTransmision3ra"));
//                            }
//                            if (documentSnapshot.getString("relacionTransmision4ta") != null || documentSnapshot.getString("relacionTransmision4ta") != "--"){
//                                txt1.add("Relación Transmision en 4ta");
//                                txt2.add(documentSnapshot.getString("relacionTransmision4ta"));
//                            }
//                            if (documentSnapshot.getString("relacionTransmision5ta") != null || documentSnapshot.getString("relacionTransmision5ta") != "--"){
//                                txt1.add("Relación Transmision en 5ta");
//                                txt2.add(documentSnapshot.getString("relacionTransmision5ta"));
//                            }
//                            if (documentSnapshot.getString("relacionTransmision6ta") != null || documentSnapshot.getString("relacionTransmision6ta") != "--"){
//                                txt1.add("Relación Transmision en 6ta");
//                                txt2.add(documentSnapshot.getString("relacionTransmision6ta"));
//                            }
//                            if (documentSnapshot.getString("relacionEngranajes") != null || documentSnapshot.getString("relacionEngranajes") != "--"){
//                                txt1.add("Relación de Engranajes");
//                                txt2.add(documentSnapshot.getString("relacionEngranajes"));
//                            }
//                            if (documentSnapshot.getString("tipoChasis") != null || documentSnapshot.getString("tipoChasis") != "--"){
//                                txt1.add("Tipo de Chasis");
//                                txt2.add(documentSnapshot.getString("tipoChasis"));
//                            }
//                            if (documentSnapshot.getString("inclinacion") != null || documentSnapshot.getString("inclinacion") != "--"){
//                                txt1.add("Inclinación");
//                                txt2.add(documentSnapshot.getString("inclinacion"));
//                            }
//                            if (documentSnapshot.getString("avance") != null || documentSnapshot.getString("avance") != "--"){
//                                txt1.add("Avance");
//                                txt2.add(documentSnapshot.getString("avance"));
//                            }
//                            if (documentSnapshot.getString("ruedaDelantera") != null || documentSnapshot.getString("ruedaDelantera") != "--"){
//                                txt1.add("Rueda Delantera");
//                                txt2.add(documentSnapshot.getString("ruedaDelantera"));
//                            }
//                            if (documentSnapshot.getString("ruedaTrasera") != null || documentSnapshot.getString("ruedaTrasera") != "--"){
//                                txt1.add("Rueda Trasera");
//                                txt2.add(documentSnapshot.getString("ruedaTrasera"));
//                            }
//                            if (documentSnapshot.getString("frenoDelantero") != null || documentSnapshot.getString("frenoDelantero") != "--"){
//                                txt1.add("Freno Delantero");
//                                txt2.add(documentSnapshot.getString("frenoDelantero"));
//                            }
//                            if (documentSnapshot.getString("frenoTrasero") != null || documentSnapshot.getString("frenoTrasero") != "--"){
//                                txt1.add("Freno Trasero");
//                                txt2.add(documentSnapshot.getString("frenoTrasero"));
//                            }
//                            if (documentSnapshot.getString("tipoSuspensionDelantera") != null || documentSnapshot.getString("tipoSuspensionDelantera") != "--"){
//                                txt1.add("Tipo de Suspensión Delantera");
//                                txt2.add(documentSnapshot.getString("tipoSuspensionDelantera"));
//                            }
//                            if (documentSnapshot.getString("tipoSuspensionTrasera") != null || documentSnapshot.getString("tipoSuspensionTrasera") != "--"){
//                                txt1.add("Tipo de Suspensión Trasera");
//                                txt2.add(documentSnapshot.getString("tipoSuspensionTrasera"));
//                            }
//                            if (documentSnapshot.getString("LuzPrincipal") != null && documentSnapshot.getString("LuzPrincipal") != "--"){
//                                txt1.add("Luz Principal");
//                                txt2.add(documentSnapshot.getString("LuzPrincipal"));
//                            }
//
//                            for (int i = 0; i < tamano; i++) {
//                                TextView labelTextView = parentLinearLayout.findViewById(ids1.get(i));
//                                labelTextView.setText(txt1.get(i));
//                                TextView valueTextView = parentLinearLayout.findViewById(ids2.get(i));
//                                valueTextView.setText(txt2.get(i));
//                            }
                            break;
                    }
                    hacerAnalisis();
                }
            }
        });
    }
    private void hacerAnalisis() {
        if (carpeta3.equals("TODOTERRENO") || carpeta3.equals("URBANAS")){
            if (cilindraje <= 100){
                caracteristicas.add("Trabajo");
            } else if (cilindraje <= 125) {
                caracteristicas.add("MasTrabajo");
            } else if (cilindraje <= 150) {
                caracteristicas.add("Trabajo-Andar");
            } else if (cilindraje <= 196) {
                caracteristicas.add("Trabajo-Andar-Semideportiva");
            }
        }

        Map<String, Object> updates = new HashMap<>();
        updates.put("clasificacion", caracteristicas);
        mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "Dato guardado", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), "Hubo un error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
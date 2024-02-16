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
    Button btnURL;
    EditText edtURL;
    Button btnActualizarPrecios;
    AppCompatSpinner spnTodo;
    AppCompatSpinner spnSinDatos;
    AppCompatSpinner spnColoresTodo;

    WebScraping mWebScraping;
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
        btnURL = mview.findViewById(R.id.btnURL);
        edtURL = mview.findViewById(R.id.edtURL);
        btnActualizarPrecios = mview.findViewById(R.id.btnActualizarPrecios);

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
        btnActualizarPrecios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        WebScraping mWebScraping = new WebScraping(getContext());
                        mWebScraping.obtenerNombreyPreciosAutecoTVSTrabajo();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }
                }).start();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarBasedatos();
            }
        });


        return mview;
    }

    private void guardar(){
        if (!dato.equals("")){

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

    private void llenarBasedatos(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mWebScraping = new WebScraping(id, edtURL.getText().toString(), getContext(), seleccionMotoSin, carpeta1, carpeta2, carpeta3);
                mWebScraping.llenarInfo();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        }).start();
    }
}
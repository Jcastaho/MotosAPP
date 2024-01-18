package com.straccion.motos_admin.ui.addmotos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.models.PostAKT;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.models.PostYamaha;
import com.straccion.motos_admin.providers.ImageProvider;
import com.straccion.motos_admin.providers.PostProvider;
import com.straccion.motos_admin.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GalleryFragment extends Fragment {

    View mview;
    Button btnGuardar;
    Button btnpasar;
    Button btnPasar2;
    ImageView imagen1;
    ImageView imagen2;
    ImageView imagen3;
    ImageView imagen4;
    ImageView imagen5;
    File ImageFile1;
    File ImageFile2;
    File ImageFile3;
    File ImageFile4;
    File ImageFile5;
    EditText edtCarpeta1;
    EditText edtCarpeta2;
    EditText edtCarpeta3;
    EditText edtNombreMotos;
    ProgressBar ProgressBar;
    EditText edtColor;
    AppCompatSpinner spnCantidadColores;

    ImageProvider mImageProvider;
    PostProvider mPostProvider;

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    AlertDialog mDialog;

    int controlador = 0;
    int cantidadColores = 0;
    String Email="";
    String Password="";
    String correo="";
    String contra="";
    String carpeta1 = "";
    String carpeta2 = "";
    String carpeta3 = "";
    String idImagen = "";
    String documentId = "";
    String nombreMoto = "";

    SharedPreferences sharedPreferences;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GalleryFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reservaFiltro.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_gallery, container, false);

        imagen1 = mview.findViewById(R.id.imagen1);
        imagen2 = mview.findViewById(R.id.imagen2);
        imagen3 = mview.findViewById(R.id.imagen3);
        imagen4 = mview.findViewById(R.id.imagen4);
        imagen5 = mview.findViewById(R.id.imagen5);
        spnCantidadColores = mview.findViewById(R.id.spnCantidadColores);
        edtCarpeta1 = mview.findViewById(R.id.edtCarpeta1);
        edtCarpeta2 = mview.findViewById(R.id.edtCarpeta2);
        edtCarpeta3 = mview.findViewById(R.id.edtCarpeta3);
        edtNombreMotos = mview.findViewById(R.id.edtNombreMotos);
        edtColor = mview.findViewById(R.id.edtColor);
        btnGuardar = mview.findViewById(R.id.btnGuardar);
        btnpasar = mview.findViewById(R.id.btnpasar);
        btnPasar2 = mview.findViewById(R.id.btnPasar2);
        ProgressBar = mview.findViewById(R.id.progressBar);
        mImageProvider = new ImageProvider();
        mPostProvider = new PostProvider();

        mAuth =FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        sharedPreferences = getContext().getSharedPreferences("ingreso" , Context.MODE_PRIVATE);
        if (login() != 1){
            createUser();
        }
        String[] cantidad = {"Cantidad de colores...", "1", "2", "3", "4", "5", "6"};
        ArrayAdapter<String> adap = new ArrayAdapter<>(getContext(), R.layout.spinner_item_custom, cantidad);
        spnCantidadColores.setAdapter(adap);

        spnCantidadColores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long id) {
                if (posicion > 1){
                    btnGuardar.setText("Continuar");
                    cantidadColores = Integer.parseInt(spnCantidadColores.getSelectedItem().toString());
                }else {
                    btnGuardar.setText("Guardar");
                }
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
                idImagen = edtNombreMotos.getText().toString() + "_" + edtColor.getText().toString();
                nombreMoto = edtNombreMotos.getText().toString();
                String nombreImagen1 = edtNombreMotos.getText().toString() + "_" + edtColor.getText().toString() + "_1";
                String nombreImagen2 = edtNombreMotos.getText().toString() + "_" + edtColor.getText().toString() + "_2";
                String nombreImagen3 = edtNombreMotos.getText().toString() + "_" + edtColor.getText().toString() + "_3";
                String nombreImagen4 = edtNombreMotos.getText().toString() + "_" + edtColor.getText().toString() + "_4";
                String nombreImagen5 = edtNombreMotos.getText().toString() + "_" + edtColor.getText().toString() + "_5";
                carpeta1 = edtCarpeta1.getText().toString().toUpperCase();
                carpeta2 = edtCarpeta2.getText().toString().toUpperCase();
                carpeta3 = edtCarpeta3.getText().toString().toUpperCase();

                if (ImageFile1 == null && ImageFile2 == null && ImageFile3 == null && ImageFile4 == null && ImageFile5 == null){
                    guardarDatos(null, null, null, null, null);
                }else {
                    saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                            nombreImagen4, nombreImagen5, carpeta1, carpeta2, carpeta3);
                }


            }
        });
        btnpasar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteVentana(1);
            }
        });
        btnPasar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteVentana(2);
            }
        });
        return mview;
    }

    private void saveImage(File ImageFile1, File ImageFile2, File ImageFile3, File ImageFile4, File ImageFile5, String nombreImagen1,
                           String nombreImagen2, String nombreImagen3, String nombreImagen4, String nombreImagen5, String carpeta1, String carpeta2, String carpeta3) {
        ProgressBar.setVisibility(View.VISIBLE);
        btnGuardar.setVisibility(View.GONE);
        mImageProvider.save(getContext(), ImageFile1, carpeta1, carpeta2, carpeta3, nombreImagen1.toUpperCase()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            final String url1 = uri1.toString();
                            if (ImageFile2 != null && ImageFile2.exists()) {
                                mImageProvider.save(getContext(), ImageFile2, carpeta1, carpeta2, carpeta3, nombreImagen2.toUpperCase()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                        if (task2.isSuccessful()){
                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri2) {
                                                    final String url2 = uri2.toString();
                                                    if (ImageFile3 != null && ImageFile3.exists()) {
                                                        mImageProvider.save(getContext(), ImageFile3, carpeta1, carpeta2, carpeta3, nombreImagen3.toUpperCase()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task3) {
                                                                if (task3.isSuccessful()){
                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri3) {
                                                                            final String url3 = uri3.toString();
                                                                            if (ImageFile4 != null && ImageFile4.exists()) {
                                                                                mImageProvider.save(getContext(), ImageFile4, carpeta1, carpeta2, carpeta3, nombreImagen4.toUpperCase()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                        if (task4.isSuccessful()){
                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri uri4) {
                                                                                                    final String url4 = uri4.toString();
                                                                                                    if (ImageFile5 != null && ImageFile5.exists()) {
                                                                                                        mImageProvider.save(getContext(), ImageFile5, carpeta1, carpeta2, carpeta3, nombreImagen5.toUpperCase()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                                                if (task4.isSuccessful()){
                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Uri uri5) {
                                                                                                                            final String url5 = uri5.toString();
                                                                                                                            guardarDatos(url1, url2, url3, url4, url5);
                                                                                                                            ProgressBar.setVisibility(View.GONE);
                                                                                                                            btnGuardar.setVisibility(View.VISIBLE);
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                    } else {
                                                                                                        guardarDatos(url1, url2, url3, url4, null);
                                                                                                        ProgressBar.setVisibility(View.GONE);
                                                                                                        btnGuardar.setVisibility(View.VISIBLE);
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                guardarDatos(url1, url2, url3, null, null);
                                                                                ProgressBar.setVisibility(View.GONE);
                                                                                btnGuardar.setVisibility(View.VISIBLE);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    }else {
                                                        guardarDatos(url1, url2, null, null, null);
                                                        ProgressBar.setVisibility(View.GONE);
                                                        btnGuardar.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }else {
                                guardarDatos(url1, null, null, null, null);
                                ProgressBar.setVisibility(View.GONE);
                                btnGuardar.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });


    }

    private void guardarDatos(String url1, String url2, String url3, String url4, String url5){
        String moto = edtCarpeta1.getText().toString().toUpperCase();
        if (moto.equals("AUTECO")){
            PostAuteco post = new PostAuteco();
            post.setId(idImagen.toUpperCase().trim());
            post.setNombreMoto(edtNombreMotos.getText().toString().toUpperCase());
            post.setCarpeta1(carpeta1);
            post.setCarpeta2(carpeta2);
            post.setCarpeta3(carpeta3);
            ArrayList<String> colores = new ArrayList<>();
            colores.add(edtColor.getText().toString().toUpperCase());
            post.setColores(colores);
            ArrayList<String> imagenes = new ArrayList<>();
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
            post.setMarcaMoto(carpeta1.toUpperCase());
            post.setImagenes(imagenes);
            mPostProvider.save1(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        CollectionReference usuariosRef = mFirestore.collection("Motos");
                        usuariosRef.whereEqualTo("id",post.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task2.getResult()) {
                                        documentId = document.getId();//obtengo el id del documento
                                    }
                                } else {
                                    Log.d("Error al buscar documentos:", task2.getException().getMessage());
                                }
                            }
                        });
                        Toast.makeText(getContext(), "Imagen guardada", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else if (moto.equals("YAMAHA")) {
            PostYamaha post = new PostYamaha();
            post.setId(idImagen.toUpperCase().trim());
            post.setNombreMoto(edtNombreMotos.getText().toString().toUpperCase());
            post.setCarpeta1(carpeta1);
            post.setCarpeta2(carpeta2);
            post.setCarpeta3(carpeta3);
            ArrayList<String> colores = new ArrayList<>();
            colores.add(edtColor.getText().toString().toUpperCase());
            post.setColores(colores);
            ArrayList<String> imagenes = new ArrayList<>();
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
            post.setMarcaMoto(carpeta1.toUpperCase());
            post.setImagenes(imagenes);
            mPostProvider.save2(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        CollectionReference usuariosRef = mFirestore.collection("Motos");
                        usuariosRef.whereEqualTo("id",post.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task2.getResult()) {
                                        documentId = document.getId();//obtengo el id del documento
                                    }
                                } else {
                                    Log.d("Error al buscar documentos:", task2.getException().getMessage());
                                }
                            }
                        });
                        Toast.makeText(getContext(), "Imagen guardada", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }else if (moto.equals("AKT")) {
            PostAKT post = new PostAKT();
            post.setId(idImagen.toUpperCase().trim());
            post.setNombreMoto(edtNombreMotos.getText().toString().toUpperCase());
            post.setCarpeta1(carpeta1);
            post.setCarpeta2(carpeta2);
            post.setCarpeta3(carpeta3);
            ArrayList<String> colores = new ArrayList<>();
            colores.add(edtColor.getText().toString().toUpperCase());
            post.setColores(colores);
            ArrayList<String> imagenes = new ArrayList<>();
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
            post.setMarcaMoto(carpeta1.toUpperCase());
            post.setImagenes(imagenes);
            mPostProvider.save3(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        CollectionReference usuariosRef = mFirestore.collection("Motos");
                        usuariosRef.whereEqualTo("id",post.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task2.getResult()) {
                                        documentId = document.getId();//obtengo el id del documento
                                    }
                                } else {
                                    Log.d("Error al buscar documentos:", task2.getException().getMessage());
                                }
                            }
                        });
                        Toast.makeText(getContext(), "Imagen guardada", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                    }
                }
            });
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
    private int login(){
        int ingreso = 0;
        String usuario = sharedPreferences.getString("correo","error");
        String contraseña = sharedPreferences.getString("contra","error");
        if (usuario != "error"){
            ingreso=1;
        }

        mAuth.signInWithEmailAndPassword(usuario, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "ingreso", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "No ingreso", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return ingreso;
    }
    private void createUser(){
        int length = 10;
        String randomString = generarLetrayNumeroRandom(length);
        if(nombreEquipo() == null){
            Email = randomString + "@gmail.com";
            Password = "654123";
        }else {
            Email = nombreMarca() + nombreEquipo() + randomString + "@gmail.com";
            Password = "654123";
        }
        correo = Email;
        contra = Password;
        mAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", correo);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("correo", correo);
                    editor.putString("contra", contra);
                    editor.commit();
                    mFirestore.collection("Usuarios").document(id).set(map);
                    Toast.makeText(getContext(), "siuuo", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "nou", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public static String nombreEquipo() {
        return Build.MODEL;
    }
    public static String nombreMarca() {
        return Build.MANUFACTURER;
    }
    public static String generarLetrayNumeroRandom(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }

        return randomString.toString();
    }

    public void siguienteVentana(int ventana){
        Bundle args = new Bundle();
        args.putInt("colores", cantidadColores);
        args.putString("carpeta1", carpeta1);
        args.putString("carpeta2", carpeta2);
        args.putString("carpeta3", carpeta3);
        args.putString("color1", edtColor.getText().toString());
        args.putString("idImagen", documentId);
        args.putString("nombreMoto", nombreMoto);
        if (ventana == 1){
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_nav_gallery_to_coloresFragment, args);
        }else {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_nav_gallery_to_aggImagenesFragment, args);
        }


    }
}
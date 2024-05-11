package com.straccion.motos_admin.ui.addmotos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.models.PostYamaha;
import com.straccion.motos_admin.providers.ImageProvider;
import com.straccion.motos_admin.providers.PostProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GalleryFragment extends Fragment {

    View mview;
    Button btnGuardar;
    Button btnPasar2;
    EditText edtCarpeta1;
    EditText edtCarpeta2;
    EditText edtCarpeta3;
    EditText edtNombreMotos;
    ProgressBar ProgressBar;
    ImageProvider mImageProvider;
    PostProvider mPostProvider;

    FirebaseFirestore mFirestore;
    int cantidadColores = 0;
    String carpeta1 = "";
    String carpeta2 = "";
    String carpeta3 = "";
    String idImagen = "";
    String documentId = "";
    String nombreMoto = "";


//    FirebaseAuth mAuth;
//    String Email="";
//    String Password="";
//    String correo="";
//    String contra="";

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_gallery, container, false);

        edtCarpeta1 = mview.findViewById(R.id.edtCarpeta1);
        edtCarpeta2 = mview.findViewById(R.id.edtCarpeta2);
        edtCarpeta3 = mview.findViewById(R.id.edtCarpeta3);
        edtNombreMotos = mview.findViewById(R.id.edtNombreMotos);
        btnGuardar = mview.findViewById(R.id.btnGuardar);
        btnPasar2 = mview.findViewById(R.id.btnPasar2);
        ProgressBar = mview.findViewById(R.id.progressBar);
        mImageProvider = new ImageProvider();
        mPostProvider = new PostProvider();

//        mAuth =FirebaseAuth.getInstance();

//        sharedPreferences = getContext().getSharedPreferences("ingreso" , Context.MODE_PRIVATE);
//        if (login() != 1){
//            createUser();
//        }



        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idImagen = edtNombreMotos.getText().toString();
                nombreMoto = edtNombreMotos.getText().toString();
                carpeta1 = edtCarpeta1.getText().toString().toUpperCase();
                carpeta2 = edtCarpeta2.getText().toString().toUpperCase();
                carpeta3 = edtCarpeta3.getText().toString().toUpperCase();

                if (carpeta1 == "" && carpeta2 == "" && carpeta3 == "" && nombreMoto == ""){

                }else {
                    guardarDatos(carpeta1, carpeta2, carpeta3, nombreMoto);
                }


            }
        });
        btnPasar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteVentana();
            }
        });
        return mview;
    }



    private void guardarDatos(String carpeta1, String carpeta2, String carpeta3, String nombre) {
        if (carpeta1.equals("AUTECO")) {
            PostAuteco post = new PostAuteco();
            post.setId(idImagen.toUpperCase().trim());
            post.setNombreMoto(nombre.toUpperCase());
            post.setCarpeta1(carpeta1.toUpperCase());
            post.setCarpeta2(carpeta2.toUpperCase());
            post.setCarpeta3(carpeta3.toUpperCase());
            post.setMarcaMoto(carpeta1.toUpperCase());
            mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        CollectionReference usuariosRef = mFirestore.collection("Motos");
                        usuariosRef.whereEqualTo("id", post.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        Toast.makeText(getContext(), "Moto Guardada", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Hubo un error al almacenar la moto", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else if (carpeta1.equals("YAMAHA")) {
            PostYamaha post = new PostYamaha();
            post.setId(idImagen.toUpperCase().trim());
            post.setNombreMoto(nombre.toUpperCase());
            post.setCarpeta1(carpeta1.toUpperCase());
            post.setCarpeta2(carpeta2.toUpperCase());
            post.setCarpeta3(carpeta3.toUpperCase());
            post.setMarcaMoto(carpeta1.toUpperCase());
            mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        CollectionReference usuariosRef = mFirestore.collection("Motos");
                        usuariosRef.whereEqualTo("id", post.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        Toast.makeText(getContext(), "Moto Guardada", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Hubo un error al almacenar la Moto", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else if (carpeta1.equals("HERO")) {
            PostAuteco post = new PostAuteco();
            post.setId(idImagen.toUpperCase().trim());
            post.setNombreMoto(nombre.toUpperCase());
            post.setCarpeta1(carpeta1.toUpperCase());
            post.setCarpeta2(carpeta2.toUpperCase());
            post.setCarpeta3(carpeta3.toUpperCase());
            post.setMarcaMoto(carpeta1.toUpperCase());
            mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        CollectionReference usuariosRef = mFirestore.collection("Motos");
                        usuariosRef.whereEqualTo("id", post.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        Toast.makeText(getContext(), "Moto Guardada", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Hubo un error al almacenar la Moto", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else if (carpeta1.equals("SUZUKI")) {
            PostAuteco post = new PostAuteco();
            post.setId(idImagen.toUpperCase().trim());
            post.setNombreMoto(nombre.toUpperCase());
            post.setCarpeta1(carpeta1.toUpperCase());
            post.setCarpeta2(carpeta2.toUpperCase());
            post.setCarpeta3(carpeta3.toUpperCase());
            post.setMarcaMoto(carpeta1.toUpperCase());
            mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        CollectionReference usuariosRef = mFirestore.collection("Motos");
                        usuariosRef.whereEqualTo("id", post.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        Toast.makeText(getContext(), "Moto Guardada", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Hubo un error al almacenar la Moto", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else if (carpeta1.equals("BAJAJ")) {
            PostAuteco post = new PostAuteco();
            post.setId(idImagen.toUpperCase().trim());
            post.setNombreMoto(nombre.toUpperCase());
            post.setCarpeta1(carpeta1.toUpperCase());
            post.setCarpeta2(carpeta2.toUpperCase());
            post.setCarpeta3(carpeta3.toUpperCase());
            post.setMarcaMoto(carpeta1.toUpperCase());
            mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        CollectionReference usuariosRef = mFirestore.collection("Motos");
                        usuariosRef.whereEqualTo("id", post.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        Toast.makeText(getContext(), "Moto Guardada", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Hubo un error al almacenar la Moto", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else if (carpeta1.equals("HONDA")) {
            PostAuteco post = new PostAuteco();
            post.setId(idImagen.toUpperCase().trim());
            post.setNombreMoto(nombre.toUpperCase());
            post.setCarpeta1(carpeta1.toUpperCase());
            post.setCarpeta2(carpeta2.toUpperCase());
            post.setCarpeta3(carpeta3.toUpperCase());
            post.setMarcaMoto(carpeta1.toUpperCase());
            mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        CollectionReference usuariosRef = mFirestore.collection("Motos");
                        usuariosRef.whereEqualTo("id", post.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        Toast.makeText(getContext(), "Moto Guardada", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Hubo un error al almacenar la Moto", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else if (carpeta1.equals("KAWASAKI")) {
            PostAuteco post = new PostAuteco();
            post.setId(idImagen.toUpperCase().trim());
            post.setNombreMoto(nombre.toUpperCase());
            post.setCarpeta1(carpeta1.toUpperCase());
            post.setCarpeta2(carpeta2.toUpperCase());
            post.setCarpeta3(carpeta3.toUpperCase());
            post.setMarcaMoto(carpeta1.toUpperCase());
            mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        CollectionReference usuariosRef = mFirestore.collection("Motos");
                        usuariosRef.whereEqualTo("id", post.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        Toast.makeText(getContext(), "Moto Guardada", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Hubo un error al almacenar la Moto", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


//    private int login(){
//        int ingreso = 0;
//        String usuario = sharedPreferences.getString("correo","error");
//        String contraseña = sharedPreferences.getString("contra","error");
//        if (usuario != "error"){
//            ingreso=1;
//        }
//
//        mAuth.signInWithEmailAndPassword(usuario, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(getContext(), "ingreso", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getContext(), "No ingreso", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        return ingreso;
//    }
//    private void createUser(){
//        int length = 10;
//        String randomString = generarLetrayNumeroRandom(length);
//        if(nombreEquipo() == null){
//            Email = randomString + "@gmail.com";
//            Password = "654123";
//        }else {
//            Email = nombreMarca() + nombreEquipo() + randomString + "@gmail.com";
//            Password = "654123";
//        }
//        correo = Email;
//        contra = Password;
//        mAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    String id = mAuth.getCurrentUser().getUid();
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("email", correo);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("correo", correo);
//                    editor.putString("contra", contra);
//                    editor.commit();
//                    mFirestore.collection("Usuarios").document(id).set(map);
//                    Toast.makeText(getContext(), "siuuo", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getContext(), "nou", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//    public static String nombreEquipo() {
//        return Build.MODEL;
//    }
//    public static String nombreMarca() {
//        return Build.MANUFACTURER;
//    }
//    public static String generarLetrayNumeroRandom(int length) {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        StringBuilder randomString = new StringBuilder();
//
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            randomString.append(characters.charAt(random.nextInt(characters.length())));
//        }
//
//        return randomString.toString();
//    }

    public void siguienteVentana(){
        Bundle args = new Bundle();
        args.putInt("colores", cantidadColores);
        args.putString("carpeta1", carpeta1);
        args.putString("carpeta2", carpeta2);
        args.putString("carpeta3", carpeta3);
        args.putString("idImagen", documentId);
        args.putString("nombreMoto", nombreMoto);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.action_nav_gallery_to_aggImagenesFragment, args);
    }
}
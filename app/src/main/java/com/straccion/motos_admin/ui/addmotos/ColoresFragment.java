package com.straccion.motos_admin.ui.addmotos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.providers.ImageProvider;
import com.straccion.motos_admin.providers.PostProvider;
import com.straccion.motos_admin.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ColoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColoresFragment extends Fragment {
    View mview;
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
    EditText edtColor1;
    EditText edtColor2;
    EditText edtColor3;
    EditText edtColor4;
    EditText edtColor5;
    EditText edtColor6;
    Button btnGuardar1;
    Button btnGuardar2;
    Button btnGuardar3;
    Button btnGuardar4;
    Button btnGuardar5;
    Button btnGuardar6;


    ImageProvider mImageProvider;
    PostProvider mPostProvider;

    int colores=0;
    int controlador = 0;
    int contador = 0;
    String carpeta1 = "";
    String carpeta2 = "";
    String carpeta3 = "";
    String color1 = "";
    String idImagen = "";
    String nombreMoto = "";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ColoresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColoresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColoresFragment newInstance(String param1, String param2) {
        ColoresFragment fragment = new ColoresFragment();
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
            //recupero la info
            colores = getArguments().getInt("colores");
            carpeta1 = getArguments().getString("carpeta1");
            carpeta2 = getArguments().getString("carpeta2");
            carpeta3 = getArguments().getString("carpeta3");
            color1 = getArguments().getString("color1");
            idImagen = getArguments().getString("idImagen");
            nombreMoto = getArguments().getString("nombreMoto");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_colores, container, false);
        imagen1 = mview.findViewById(R.id.imagen1);
        imagen2 = mview.findViewById(R.id.imagen2);
        imagen3 = mview.findViewById(R.id.imagen3);
        imagen4 = mview.findViewById(R.id.imagen4);
        imagen5 = mview.findViewById(R.id.imagen5);
        edtColor1 = mview.findViewById(R.id.edtColor1);
        edtColor2 = mview.findViewById(R.id.edtColor2);
        edtColor3 = mview.findViewById(R.id.edtColor3);
        edtColor4 = mview.findViewById(R.id.edtColor4);
        edtColor5 = mview.findViewById(R.id.edtColor5);
        edtColor6 = mview.findViewById(R.id.edtColor6);
        btnGuardar1 = mview.findViewById(R.id.btnGuardar1);
        btnGuardar2 = mview.findViewById(R.id.btnGuardar2);
        btnGuardar3 = mview.findViewById(R.id.btnGuardar3);
        btnGuardar4 = mview.findViewById(R.id.btnGuardar4);
        btnGuardar5 = mview.findViewById(R.id.btnGuardar5);
        btnGuardar6 = mview.findViewById(R.id.btnGuardar6);


        mImageProvider = new ImageProvider();
        mPostProvider = new PostProvider();

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
        mostrar();

        limpiarTextos();

        //mejorar este codigo
        if (edtColor1.getText().toString() != "" || !edtColor1.getText().toString().isEmpty()){
            btnGuardar1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnGuardar2.setVisibility(View.VISIBLE);
                    btnGuardar1.setVisibility(View.INVISIBLE);
                    edtColor1.setEnabled(false);
                    edtColor1.setTextColor(Color.GREEN);
                    String nombreImagen1 = nombreMoto + "_" + edtColor1.getText().toString() + "_1";
                    String nombreImagen2 = nombreMoto + "_" + edtColor1.getText().toString() + "_2";
                    String nombreImagen3 = nombreMoto + "_" + edtColor1.getText().toString() + "_3";
                    String nombreImagen4 = nombreMoto + "_" + edtColor1.getText().toString() + "_4";
                    String nombreImagen5 = nombreMoto + "_" + edtColor1.getText().toString() + "_5";

                    saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                            nombreImagen4, nombreImagen5, carpeta1.toUpperCase(), carpeta2.toUpperCase(), carpeta3.toUpperCase());
                }
            });
            if (edtColor2.getText().toString() != "" || !edtColor2.getText().toString().isEmpty()){
                btnGuardar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contador = 2;
                        btnGuardar3.setVisibility(View.VISIBLE);
                        btnGuardar2.setVisibility(View.INVISIBLE);
                        edtColor2.setEnabled(false);
                        edtColor2.setTextColor(Color.GREEN);
                        String nombreImagen1 = nombreMoto + "_" + edtColor2.getText().toString() + "_1";
                        String nombreImagen2 = nombreMoto + "_" + edtColor2.getText().toString() + "_2";
                        String nombreImagen3 = nombreMoto + "_" + edtColor2.getText().toString() + "_3";
                        String nombreImagen4 = nombreMoto + "_" + edtColor2.getText().toString() + "_4";
                        String nombreImagen5 = nombreMoto + "_" + edtColor2.getText().toString() + "_5";

                        saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                                nombreImagen4, nombreImagen5, carpeta1.toUpperCase(), carpeta2.toUpperCase(), carpeta3.toUpperCase());
                    }
                });
            }
            if (edtColor3.getText().toString() != "" || !edtColor3.getText().toString().isEmpty()){
                btnGuardar3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contador = 3;
                        btnGuardar4.setVisibility(View.VISIBLE);
                        btnGuardar3.setVisibility(View.INVISIBLE);
                        edtColor3.setTextColor(Color.GREEN);
                        edtColor3.setEnabled(false);
                        String nombreImagen1 = nombreMoto + "_" + edtColor3.getText().toString() + "_1";
                        String nombreImagen2 = nombreMoto + "_" + edtColor3.getText().toString() + "_2";
                        String nombreImagen3 = nombreMoto + "_" + edtColor3.getText().toString() + "_3";
                        String nombreImagen4 = nombreMoto + "_" + edtColor3.getText().toString() + "_4";
                        String nombreImagen5 = nombreMoto + "_" + edtColor3.getText().toString() + "_5";

                        saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                                nombreImagen4, nombreImagen5, carpeta1.toUpperCase(), carpeta2.toUpperCase(), carpeta3.toUpperCase());
                    }
                });
            }
            if (edtColor4.getText().toString() != "" || !edtColor4.getText().toString().isEmpty()){
                btnGuardar4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contador = 4;
                        btnGuardar5.setVisibility(View.VISIBLE);
                        btnGuardar4.setVisibility(View.INVISIBLE);
                        edtColor4.setEnabled(false);
                        edtColor4.setTextColor(Color.GREEN);
                        String nombreImagen1 = nombreMoto + "_" + edtColor4.getText().toString() + "_1";
                        String nombreImagen2 = nombreMoto + "_" + edtColor4.getText().toString() + "_2";
                        String nombreImagen3 = nombreMoto + "_" + edtColor4.getText().toString() + "_3";
                        String nombreImagen4 = nombreMoto + "_" + edtColor4.getText().toString() + "_4";
                        String nombreImagen5 = nombreMoto + "_" + edtColor4.getText().toString() + "_5";

                        saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                                nombreImagen4, nombreImagen5, carpeta1.toUpperCase(), carpeta2.toUpperCase(), carpeta3.toUpperCase());
                    }
                });
            }
            if (edtColor5.getText().toString() != "" || !edtColor5.getText().toString().isEmpty()){
                btnGuardar5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contador = 5;
                        btnGuardar6.setVisibility(View.VISIBLE);
                        btnGuardar5.setVisibility(View.INVISIBLE);
                        edtColor5.setEnabled(false);
                        edtColor5.setTextColor(Color.GREEN);
                        String nombreImagen1 = nombreMoto + "_" + edtColor5.getText().toString() + "_1";
                        String nombreImagen2 = nombreMoto + "_" + edtColor5.getText().toString() + "_2";
                        String nombreImagen3 = nombreMoto + "_" + edtColor5.getText().toString() + "_3";
                        String nombreImagen4 = nombreMoto + "_" + edtColor5.getText().toString() + "_4";
                        String nombreImagen5 = nombreMoto + "_" + edtColor5.getText().toString() + "_5";

                        saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                                nombreImagen4, nombreImagen5, carpeta1.toUpperCase(), carpeta2.toUpperCase(), carpeta3.toUpperCase());
                    }
                });
            }
            if (edtColor6.getText().toString() != "" || !edtColor6.getText().toString().isEmpty()){
                btnGuardar6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contador = 6;
                        String nombreImagen1 = nombreMoto + "_" + edtColor6.getText().toString() + "_1";
                        String nombreImagen2 = nombreMoto + "_" + edtColor6.getText().toString() + "_2";
                        String nombreImagen3 = nombreMoto + "_" + edtColor6.getText().toString() + "_3";
                        String nombreImagen4 = nombreMoto + "_" + edtColor6.getText().toString() + "_4";
                        String nombreImagen5 = nombreMoto + "_" + edtColor6.getText().toString() + "_5";

                        saveImage(ImageFile1, ImageFile2, ImageFile3, ImageFile4, ImageFile5, nombreImagen1, nombreImagen2, nombreImagen3,
                                nombreImagen4, nombreImagen5, carpeta1.toUpperCase(), carpeta2.toUpperCase(), carpeta3.toUpperCase());
                    }
                });
            }
        }
        return mview;
    }
    public void limpiarTextos(){
        edtColor1.setEnabled(true);
        edtColor1.setTextColor(Color.BLACK);
        edtColor2.setEnabled(true);
        edtColor2.setTextColor(Color.BLACK);
        edtColor3.setEnabled(true);
        edtColor3.setTextColor(Color.BLACK);
        edtColor4.setEnabled(true);
        edtColor4.setTextColor(Color.BLACK);
        edtColor5.setEnabled(true);
        edtColor5.setTextColor(Color.BLACK);
        edtColor6.setEnabled(true);
        edtColor6.setTextColor(Color.BLACK);
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
        if (contador==2){
            imagenColores =  "imagenesColores2";
        } if (contador==3){
            imagenColores =  "imagenesColores3";
        } if (contador==4){
            imagenColores =  "imagenesColores4";
        } if (contador==5){
            imagenColores =  "imagenesColores5";
        } if (contador==6){
            imagenColores =  "imagenesColores6";
        }
        Map<String, Object> updates = new HashMap<>();
        ArrayList<String> Colores = new ArrayList<>();
        Colores.add(color1);
        Colores.add(edtColor1.getText().toString().toUpperCase());
        Colores.add(edtColor2.getText().toString().toUpperCase());
        Colores.add(edtColor3.getText().toString().toUpperCase());
        Colores.add(edtColor4.getText().toString().toUpperCase());
        Colores.add(edtColor5.getText().toString().toUpperCase());
        ArrayList<String> imagenesColores = new ArrayList<>();
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
        updates.put("colores", Colores);
        updates.put(imagenColores, imagenesColores);
        mPostProvider.updatePost(idImagen, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    private void mostrar(){
        edtColor2.setVisibility(View.INVISIBLE);
        btnGuardar2.setVisibility(View.INVISIBLE);
        edtColor3.setVisibility(View.INVISIBLE);
        btnGuardar3.setVisibility(View.INVISIBLE);
        edtColor4.setVisibility(View.INVISIBLE);
        btnGuardar4.setVisibility(View.INVISIBLE);
        edtColor5.setVisibility(View.INVISIBLE);
        btnGuardar5.setVisibility(View.INVISIBLE);
        edtColor6.setVisibility(View.INVISIBLE);
        btnGuardar6.setVisibility(View.INVISIBLE);
        switch (colores) {
            case 1:
                break;
            case 2:
                edtColor2.setVisibility(View.VISIBLE);
                break;
            case 3:
                edtColor2.setVisibility(View.VISIBLE);
                edtColor3.setVisibility(View.VISIBLE);
                break;
            case 4:
                edtColor2.setVisibility(View.VISIBLE);
                edtColor3.setVisibility(View.VISIBLE);
                edtColor4.setVisibility(View.VISIBLE);
                break;
            case 5:
                edtColor2.setVisibility(View.VISIBLE);
                edtColor3.setVisibility(View.VISIBLE);
                edtColor4.setVisibility(View.VISIBLE);
                edtColor5.setVisibility(View.VISIBLE);
                break;
            case 6:
                edtColor2.setVisibility(View.VISIBLE);
                edtColor3.setVisibility(View.VISIBLE);
                edtColor4.setVisibility(View.VISIBLE);
                edtColor5.setVisibility(View.VISIBLE);
                edtColor6.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
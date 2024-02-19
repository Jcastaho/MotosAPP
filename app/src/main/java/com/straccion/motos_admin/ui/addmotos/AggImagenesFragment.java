package com.straccion.motos_admin.ui.addmotos;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.providers.ImageProvider;
import com.straccion.motos_admin.providers.PostProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AggImagenesFragment extends Fragment {
    View mview;
    Button btnGuardar;
    Button btnURL;
    EditText edtURL;
    Button btnActualizarPrecios;
    ProgressBar progressBar;
    AppCompatSpinner spnSinDatos;

    WebScraping mWebScraping;
    PostProvider mpostProvider;
    ImageProvider mImageProvider;

    String nombreMotos="";
    String seleccionMoto="";
    String seleccionMotoSin="";
    String carpeta1;
    String carpeta2;
    String carpeta3;
    String dato ="";
    String marcaMoto ="";

    String id ="";
    List<String> nombreMotoSin = new ArrayList<>();

//    List<String> caracteristicas = new ArrayList<>();
//    double calificacion =0;
//    double cilindraje =0;
//    int controlador = 0;
//    int posicion = 0;


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

        spnSinDatos = mview.findViewById(R.id.spnSinDatos);
        btnGuardar = mview.findViewById(R.id.btnGuardar);
        btnURL = mview.findViewById(R.id.btnURL);
        edtURL = mview.findViewById(R.id.edtURL);
        progressBar = mview.findViewById(R.id.progressBar);
        btnActualizarPrecios = mview.findViewById(R.id.btnActualizarPrecios);

        mpostProvider = new PostProvider();
        mImageProvider = new ImageProvider();
        llenarListas();

        spnSinDatos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long id) {
                seleccionMotoSin = spnSinDatos.getSelectedItem().toString();
                SaberDatos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seleccionMotoSin != "NINGUNA") {
                    progressBar.setVisibility(View.VISIBLE);
                    btnURL.setVisibility(View.GONE);
                    llenarBasedatos();
                }
            }
        });
        return mview;
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
    }
    private void SaberDatos(){
        if (!seleccionMotoSin.equals("NINGUNA")) {
            Query query = mpostProvider.buscarPorNombreMoto(seleccionMotoSin);
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        id = document.getId();
                        mpostProvider.getPostById(id).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()){
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
    }
    //hacer el analisis
//    private void hacerAnalisis() {
//        if (carpeta3.equals("TODOTERRENO") || carpeta3.equals("URBANAS")){
//            if (cilindraje <= 100){
//                caracteristicas.add("Trabajo");
//            } else if (cilindraje <= 125) {
//                caracteristicas.add("MasTrabajo");
//            } else if (cilindraje <= 150) {
//                caracteristicas.add("Trabajo-Andar");
//            } else if (cilindraje <= 196) {
//                caracteristicas.add("Trabajo-Andar-Semideportiva");
//            }
//        }
//
//        Map<String, Object> updates = new HashMap<>();
//        updates.put("clasificacion", caracteristicas);
//        mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(getContext(), "Dato guardado", Toast.LENGTH_LONG).show();
//                }else {
//                    Toast.makeText(getContext(), "Hubo un error", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//    }

    private void llenarBasedatos(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mWebScraping = new WebScraping(id, edtURL.getText().toString(), getContext(), seleccionMotoSin, carpeta1, carpeta2, carpeta3, progressBar, btnURL);
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
package com.straccion.motos_admin.ui.addmotos;


import android.os.Bundle;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.providers.ImageProvider;
import com.straccion.motos_admin.providers.PostProvider;

import java.util.ArrayList;
import java.util.List;


public class AggImagenesFragment extends Fragment {
    View mview;
    Button btnGuardar;
    Button btnURL;
    EditText edtURL;
    EditText txtGalonAprox;
    Button btnActualizarPrecios;
    ProgressBar progressBar;
    AppCompatSpinner spnSinDatos;

    WebScraping mWebScraping;
    PostProvider mpostProvider;
    ImageProvider mImageProvider;

    String nombreMotos="";
    String seleccionMotoSin="";
    String carpeta1;
    String carpeta2;
    String carpeta3;
    String marcaMoto ="";
    int consumo =0;
    String id ="";
    List<String> nombreMotoSin = new ArrayList<>();

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
        txtGalonAprox = mview.findViewById(R.id.txtGalonAprox);

        mpostProvider = new PostProvider();
        mImageProvider = new ImageProvider();
        llenarListas();

        spnSinDatos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long id) {
                seleccionMotoSin = spnSinDatos.getSelectedItem().toString();
                saberDatos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnActualizarPrecios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Por favor espere", Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        WebScraping mWebScraping = new WebScraping(getContext());
                        mWebScraping.direccionamientoxFabricantePrecios();


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
                consumo = Integer.parseInt(txtGalonAprox.getText().toString());
                if(seleccionMotoSin != "NINGUNA" && consumo != 0) {
                    progressBar.setVisibility(View.VISIBLE);
                    btnURL.setVisibility(View.GONE);
                    llenarBaseDatos();
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
    private void saberDatos(){
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

    private void llenarBaseDatos(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mWebScraping = new WebScraping(id, edtURL.getText().toString(), getContext(), seleccionMotoSin, carpeta1, carpeta2, carpeta3, progressBar, btnURL, consumo);
                mWebScraping.direccionamientoxFabricante(carpeta1);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        }).start();
    }

}
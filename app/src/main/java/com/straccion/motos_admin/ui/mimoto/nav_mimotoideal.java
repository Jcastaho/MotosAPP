package com.straccion.motos_admin.ui.mimoto;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.adapters.FiltrosMiMotoAdapter;
import com.straccion.motos_admin.adapters.PostsAdapters;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.providers.PostProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_mimotoideal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_mimotoideal extends Fragment {
    View mview;

    PostProvider mpostProvider;
    FiltrosMiMotoAdapter mPostsAdapters;

    TextView txtPregunta;
    Button btnRespuesta1;
    Button btnRespuesta2;
    Button btnRespuesta3;
    Button btnRespuesta4;
    ProgressBar progressBarMiMotoIdeal;
    LinearLayout contenedorRecycler;
    LinearLayout lnlPantalla;
    RecyclerView reciclerViewMiMoto;



    String pregunta1="¿Que presupuesto tienes pensado para comprar una moto nueva?";
    String pregunta2="¿Cual sera principalmente el uso de la moto?";
    String pregunta3="¿Qué tan importante es la eficiencia de combustible para ti?";
    String pregunta4="¿Prefieres una moto con transmisión manual o automática?";
    String pregunta5="¿Qué nivel de experiencia tienes como motociclista?";
    String pregunta6="¿Llevaras un segundo pasajero con frecuencia?";
    String pregunta7="¿Cómo te sientes acerca de la ergonomía de la moto? ¿Prefieres una posición de conducción más erguida o más inclinada hacia adelante?";
    String pregunta8="¿Con que frecuencia usara la moto para trabajar?";
    String trabajo="";
    int contador=0;
    int sumadorClick=0;
    int numberOfColumns = 0;
    List<String> respuestas = new ArrayList<>();


    public static nav_mimotoideal newInstance(String param1, String param2) {
        nav_mimotoideal fragment = new nav_mimotoideal();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_nav_mimotoideal, container, false);
        txtPregunta = mview.findViewById(R.id.txtPregunta);
        btnRespuesta1 = mview.findViewById(R.id.btnRespuesta1);
        btnRespuesta2 = mview.findViewById(R.id.btnRespuesta2);
        btnRespuesta3 = mview.findViewById(R.id.btnRespuesta3);
        btnRespuesta4 = mview.findViewById(R.id.btnRespuesta4);
        progressBarMiMotoIdeal = mview.findViewById(R.id.progressBarMiMotoIdeal);
        contenedorRecycler = mview.findViewById(R.id.contenedorRecycler);
        reciclerViewMiMoto = mview.findViewById(R.id.reciclerViewMiMoto);
        lnlPantalla = mview.findViewById(R.id.lnlPantalla);


        mpostProvider = new PostProvider();

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            numberOfColumns = 2;
        } else {
            numberOfColumns = 4;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        reciclerViewMiMoto.setLayoutManager(layoutManager);


        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        contador = numbers.get(sumadorClick);

        preguntas();
        btnRespuesta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumadorClick = sumadorClick + 1;
                respuestas.add(btnRespuesta1.getText().toString());
                if (sumadorClick != 7){
                    contador = numbers.get(sumadorClick);
                    preguntas();
                }else {
                    pantallaCarga();
                    analisis();
                }
            }
        });
        btnRespuesta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumadorClick = sumadorClick + 1;
                respuestas.add(btnRespuesta2.getText().toString());
                if (sumadorClick != 7){
                    contador = numbers.get(sumadorClick);
                    preguntas();
                }else {
                    pantallaCarga();
                    analisis();
                }
            }
        });
        btnRespuesta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumadorClick = sumadorClick + 1;
                respuestas.add(btnRespuesta3.getText().toString());
                if (sumadorClick != 7){
                    if (btnRespuesta3.getText() == "Trabajo"){
                        contador=8;
                        sumadorClick = sumadorClick - 1;
                        preguntas();
                    }else {
                        contador = numbers.get(sumadorClick);
                        preguntas();
                    }
                }else {
                    pantallaCarga();
                    analisis();
                }
            }
        });
        btnRespuesta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumadorClick = sumadorClick + 1;
                respuestas.add(btnRespuesta4.getText().toString());
                if (sumadorClick != 7){
                    contador = numbers.get(sumadorClick);
                    preguntas();
                }else {
                    pantallaCarga();
                    analisis();
                }
            }
        });

        return  mview;
    }

    private void pantallaCarga(){
        txtPregunta.setVisibility(View.GONE);
        btnRespuesta1.setVisibility(View.GONE);
        btnRespuesta2.setVisibility(View.GONE);
        btnRespuesta3.setVisibility(View.GONE);
        btnRespuesta4.setVisibility(View.GONE);
        progressBarMiMotoIdeal.setVisibility(View.VISIBLE);
        lnlPantalla.setVisibility(View.VISIBLE);

    }

    private void preguntas(){
        btnRespuesta1.setVisibility(View.VISIBLE);
        btnRespuesta2.setVisibility(View.VISIBLE);
        btnRespuesta3.setVisibility(View.VISIBLE);
        btnRespuesta4.setVisibility(View.VISIBLE);
        if (contador == 1){
            txtPregunta.setText(pregunta1);
            btnRespuesta1.setText("Menos de $5.000.0000");
            btnRespuesta2.setText("Entre $5.000.000 y $8.000.000");
            btnRespuesta3.setText("Entre $8.000.000 y $10.000.000");
            btnRespuesta4.setText("Mas de $10.000.000");
        } else if (contador == 2) {
            txtPregunta.setText(pregunta2);
            btnRespuesta1.setText("Uso cotidiano en ciudad");
            btnRespuesta2.setText("Viajes");
            btnRespuesta3.setText("Trabajo");
            btnRespuesta4.setText("Uso mixto");
        } else if (contador == 3) {
            txtPregunta.setText(pregunta3);
            btnRespuesta1.setText("Muy importante");
            btnRespuesta2.setText("Moderadamente importante");
            btnRespuesta3.setText("No es importante");
            btnRespuesta4.setVisibility(View.GONE);
        } else if (contador == 4) {
            txtPregunta.setText(pregunta4);
            btnRespuesta1.setText("Manual");
            btnRespuesta2.setText("Semiautomaticas");
            btnRespuesta3.setText("Automatica");
            btnRespuesta4.setText("No es relevante");
        } else if (contador == 5) {
            txtPregunta.setText(pregunta5);
            btnRespuesta1.setText("Principiante");
            btnRespuesta2.setText("Intermedio");
            btnRespuesta3.setText("Experto");
            btnRespuesta4.setVisibility(View.GONE);
        } else if (contador == 6) {
            txtPregunta.setText(pregunta6);
            btnRespuesta1.setText("Si");
            btnRespuesta2.setText("No");
            btnRespuesta3.setVisibility(View.GONE);
            btnRespuesta4.setVisibility(View.GONE);
        } else if (contador == 7) {
            txtPregunta.setText(pregunta7);
            btnRespuesta1.setText("Erguida");
            btnRespuesta2.setText("Inclinada hacia adelante");
            btnRespuesta3.setText(" No estoy seguro");
            btnRespuesta4.setVisibility(View.GONE);
        } else if (contador == 8) {
            txtPregunta.setText(pregunta8);
            btnRespuesta1.setText("Diariamente");
            btnRespuesta2.setText("Algunas veces a la semana");
            btnRespuesta3.setText("Ocasionalmente");
            btnRespuesta4.setVisibility(View.GONE);
        }
    }

    private void analisis(){
        int precioFiltro1 = 0;
        int precioFiltro2 = 0;
        if (respuestas.contains("Menos de $5.000.0000")){
            precioFiltro1 = 5000000;
        } else if (respuestas.contains("Entre $5.000.000 y $8.000.000")) {
            precioFiltro1 = 5000000;
            precioFiltro2 = 8000000;
        }else if (respuestas.contains("Entre $8.000.000 y $10.000.000")) {
            precioFiltro1 = 8000000;
            precioFiltro2 = 10000000;
        } else if (respuestas.contains("Mas de $10.000.000")) {
            precioFiltro1 = 10000000;
        }

        if (respuestas.contains("Diariamente")){
            trabajo = "Trabajo";
        } else if (respuestas.contains("Algunas veces a la semana")) {
            trabajo = "Trabajo-Andar";
        } else if (respuestas.contains("Ocasionalmente")) {
            trabajo = "Trabajo-Andar-Semideportiva";
        } else if (respuestas.contains("Uso cotidiano en ciudad")) {
            trabajo = "Semideportiva";
        }
        
        Query query;
        if (precioFiltro1 == 10000000){
            query = mpostProvider.filtroMasivo(precioFiltro1, 999999999, trabajo);
        }else {
            query = mpostProvider.filtroMasivo(precioFiltro2, precioFiltro1, trabajo);
        }

        FirestoreRecyclerOptions<PostAuteco> options = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(query, PostAuteco.class).build();

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

        mPostsAdapters = new FiltrosMiMotoAdapter(options, getContext(), navController);
        reciclerViewMiMoto.setAdapter(mPostsAdapters);
        mPostsAdapters.startListening();

        int tiempoMostrandoProgressBar = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lnlPantalla.setVisibility(View.GONE);
                contenedorRecycler.setVisibility(View.VISIBLE);
                progressBarMiMotoIdeal.setVisibility(View.GONE);
                contenedorRecycler.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
                reciclerViewMiMoto.setVisibility(View.VISIBLE);
            }
        }, tiempoMostrandoProgressBar);


    }
}
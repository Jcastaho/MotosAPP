package com.straccion.motos_admin.ui.mimoto;

import static android.content.ContentValues.TAG;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.adapters.FiltrosMiMotoAdapter;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.providers.PostProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    ScrollView sclPantalla;
    RecyclerView reciclerViewMiMoto1;
    RecyclerView reciclerViewMiMoto2;
    RecyclerView reciclerViewMiMoto3;
    TextView txtTitulo1;
    TextView txtTitulo2;
    TextView txtTitulo3;

    String pregunta1 = "¿Que presupuesto tienes pensado para comprar una moto nueva?";
    String pregunta2 = "¿Cual sera principalmente el uso de la moto?";
    String pregunta3 = "¿Qué tan importante es la eficiencia de combustible para ti?";
    String pregunta4 = "¿Prefieres una moto con transmisión manual, automática o semiautomatica?";
    String pregunta5 = "¿Qué tipo de terreno y condiciones climáticas predominarán en tus trayectos habituales?";
    String pregunta6 = "¿Con que frecuencia usara la moto para trabajar?";
    List<String> trabajo = new ArrayList<>();
    int contador = 0;
    int sumadorClick = 0;
    int numberOfColumns = 0;
    int precioFiltro1 = 0;
    int precioFiltro2 = 0;
    List<String> respuestas = new ArrayList<>();


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
        txtTitulo1 = mview.findViewById(R.id.txtTitulo1);
        txtTitulo2 = mview.findViewById(R.id.txtTitulo2);
        txtTitulo3 = mview.findViewById(R.id.txtTitulo3);
        progressBarMiMotoIdeal = mview.findViewById(R.id.progressBarMiMotoIdeal);
        contenedorRecycler = mview.findViewById(R.id.contenedorRecycler);
        reciclerViewMiMoto1 = mview.findViewById(R.id.reciclerViewMiMoto1);
        reciclerViewMiMoto2 = mview.findViewById(R.id.reciclerViewMiMoto2);
        reciclerViewMiMoto3 = mview.findViewById(R.id.reciclerViewMiMoto3);
        sclPantalla = mview.findViewById(R.id.sclPantalla);


        mpostProvider = new PostProvider();

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            numberOfColumns = 2;
        } else {
            numberOfColumns = 4;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        reciclerViewMiMoto1.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        reciclerViewMiMoto2.setLayoutManager(layoutManager2);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        reciclerViewMiMoto3.setLayoutManager(layoutManager3);

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            if (i != 1) { // Asegura que el número 1 no se agregue al principio
                numbers.add(i);
            }
        }
        if (sumadorClick > 0){
            sumadorClick=8;
            pantallaCarga();
            analisis();
        }else {
            Collections.shuffle(numbers);
            numbers.add(1);
            contador = numbers.get(sumadorClick);

            preguntas();
        }

        btnRespuesta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumadorClick = sumadorClick + 1;
                respuestas.add(btnRespuesta1.getText().toString());
                if (sumadorClick != 5) {
                    contador = numbers.get(sumadorClick);
                    preguntas();
                } else {
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
                if (sumadorClick != 5) {
                    contador = numbers.get(sumadorClick);
                    preguntas();
                } else {
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
                if (sumadorClick != 5) {
                    if (btnRespuesta3.getText() == "Trabajo") {
                        contador = 6;
                        sumadorClick = sumadorClick - 1;
                        preguntas();
                    } else {
                        contador = numbers.get(sumadorClick);
                        preguntas();
                    }
                } else {
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
                if (sumadorClick != 5) {
                    contador = numbers.get(sumadorClick);
                    preguntas();
                } else {
                    pantallaCarga();
                    analisis();
                }
            }
        });

        return mview;
    }

    private void pantallaCarga() {
        txtPregunta.setVisibility(View.GONE);
        btnRespuesta1.setVisibility(View.GONE);
        btnRespuesta2.setVisibility(View.GONE);
        btnRespuesta3.setVisibility(View.GONE);
        btnRespuesta4.setVisibility(View.GONE);
        progressBarMiMotoIdeal.setVisibility(View.VISIBLE);
        sclPantalla.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_gris_claro));

    }

    private void preguntas() {
        btnRespuesta1.setVisibility(View.VISIBLE);
        btnRespuesta2.setVisibility(View.VISIBLE);
        btnRespuesta3.setVisibility(View.VISIBLE);
        btnRespuesta4.setVisibility(View.VISIBLE);
        if (contador == 1) {
            txtPregunta.setText(pregunta1);
            btnRespuesta1.setText("Menos de $7.000.0000");
            btnRespuesta2.setText("Entre $7.000.000 y $12.000.000");
            btnRespuesta3.setText("Entre $12.000.000 y $18.000.000");
            btnRespuesta4.setText("Mas de $18.000.000");
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
            btnRespuesta4.setText("Irrelevante");

        } else if (contador == 5) {
            txtPregunta.setText(pregunta5);
            btnRespuesta1.setText("Principalmente asfalto");
            btnRespuesta2.setText("Mayormente caminos de tierra/grava");
            btnRespuesta3.setText("Variado (mezcla de asfalto, tierra y grava)");
            btnRespuesta4.setVisibility(View.GONE);
        } else if (contador == 6) {
            txtPregunta.setText(pregunta6);
            btnRespuesta1.setText("Frecuentemente");
            btnRespuesta2.setText("Regularmente");
            btnRespuesta3.setText("Esporádicamente");
            btnRespuesta4.setVisibility(View.GONE);
        }
    }

    private void analisis() {
        trabajo.clear();
        Map<String, Integer> prioridadesPreguntas = new HashMap<>();
        if (respuestas.contains("Menos de $7.000.0000")) {
            precioFiltro2 = 6999999;
        } else if (respuestas.contains("Entre $7.000.000 y $12.000.000")) {
            precioFiltro1 = 7000000;
            precioFiltro2 = 11999999;
        } else if (respuestas.contains("Entre $12.000.000 y $18.000.000")) {
            precioFiltro1 = 12000000;
            precioFiltro2 = 17999999;
        } else if (respuestas.contains("Mas de $18.000.000")) {
            precioFiltro1 = 18000000;
            precioFiltro2 = 999999999;
        }


        if (respuestas.contains("Uso cotidiano en ciudad")) {
            prioridadesPreguntas.put("DEPORTIVA", 1);
            prioridadesPreguntas.put("URBANAS", 1);
            prioridadesPreguntas.put("AUTOMATICAS", 2);
            prioridadesPreguntas.put("SEMIAUTOMATICAS", 2);

        } else if (respuestas.contains("Viajes")) {
            prioridadesPreguntas.put("ADVENTURE", 1);
            prioridadesPreguntas.put("DEPORTIVA", 1);

        } else if (respuestas.contains("Uso mixto")) {
            prioridadesPreguntas.put("TRABAJO", 2);
            prioridadesPreguntas.put("URBANAS", 1);
        }

        //SOLO SI REPONDE QUE ES PARA TRABAJO
        if (respuestas.contains("Frecuentemente")) {
            if (!prioridadesPreguntas.containsKey("TRABAJO")) {
                // Incrementa todos los valores existentes en 1
                for (Map.Entry<String, Integer> entry : prioridadesPreguntas.entrySet()) {
                    entry.setValue(entry.getValue() + 1);
                }
                prioridadesPreguntas.put("TRABAJO", 1);
            }
        } else if (respuestas.contains("Regularmente")) {
            if (!prioridadesPreguntas.containsKey("TRABAJO")) {
                // Incrementa todos los valores existentes en 1
                for (Map.Entry<String, Integer> entry : prioridadesPreguntas.entrySet()) {
                    entry.setValue(entry.getValue() + 1);
                }
                prioridadesPreguntas.put("URBANAS", 1);
                prioridadesPreguntas.put("TRABAJO", 1);
            }
        } else if (respuestas.contains("Esporádicamente")) {
            if (!prioridadesPreguntas.containsKey("TRABAJO")) {
                // Incrementa todos los valores existentes en 1
                for (Map.Entry<String, Integer> entry : prioridadesPreguntas.entrySet()) {
                    entry.setValue(entry.getValue() + 1);
                }
                prioridadesPreguntas.put("DEPORTIVA", 1);
                prioridadesPreguntas.put("URBANAS", 2);
                prioridadesPreguntas.put("TRABAJO", 3);
            }
        }


        if (respuestas.contains("Principalmente asfalto")) {
            if (!prioridadesPreguntas.containsKey("TRABAJO")) {
                prioridadesPreguntas.put("TRABAJO", 3);
            }

        } else if (respuestas.contains("Mayormente caminos de tierra/grava")) {
            if (!prioridadesPreguntas.containsKey("TODOTERRENO")) {
                // Incrementa todos los valores existentes en 1
                for (Map.Entry<String, Integer> entry : prioridadesPreguntas.entrySet()) {
                    entry.setValue(entry.getValue() + 1);
                }
                prioridadesPreguntas.put("TODOTERRENO", 1);
                prioridadesPreguntas.remove("AUTOMATICAS");
                prioridadesPreguntas.remove("SEMIAUTOMATICAS");
                prioridadesPreguntas.remove("DEPORTIVA");
            }

        } else if (respuestas.contains("Variado (mezcla de asfalto, tierra y grava)")) {

            if (respuestas.contains("Uso mixto")) {
                if (!prioridadesPreguntas.containsKey("TODOTERRENO")) {
                    // Incrementa todos los valores existentes en 1
                    for (Map.Entry<String, Integer> entry : prioridadesPreguntas.entrySet()) {
                        entry.setValue(entry.getValue() + 1);
                    }
                    prioridadesPreguntas.put("TODOTERRENO", 3);
                }
            } else if (respuestas.contains("Viajes")) {
                if (!prioridadesPreguntas.containsKey("TODOTERRENO")) {
                    // Incrementa todos los valores existentes en 1
                    for (Map.Entry<String, Integer> entry : prioridadesPreguntas.entrySet()) {
                        entry.setValue(entry.getValue() + 1);
                    }
                    prioridadesPreguntas.put("TODOTERRENO", 3);
                }
            }
        }

        if (respuestas.contains("Semiautomaticas")) {
            if (!prioridadesPreguntas.containsKey("SEMIAUTOMATICAS")) {
                // Incrementa todos los valores existentes en 1
                for (Map.Entry<String, Integer> entry : prioridadesPreguntas.entrySet()) {
                    entry.setValue(entry.getValue() + 1);
                }
                prioridadesPreguntas.put("SEMIAUTOMATICAS", 1);
            }
        } else if (respuestas.contains("Automatica")) {
            if (!prioridadesPreguntas.containsKey("AUTOMATICAS")) {
                // Incrementa todos los valores existentes en 1
                for (Map.Entry<String, Integer> entry : prioridadesPreguntas.entrySet()) {
                    entry.setValue(entry.getValue() + 1);
                }
                prioridadesPreguntas.put("AUTOMATICAS", 1);
            }
        }

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(prioridadesPreguntas.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                // Comparar los valores enteros de las entradas del mapa
                return entry1.getValue().compareTo(entry2.getValue());
            }
        });
        List<String> sortedList = new ArrayList<>();

        // Agregar las entradas ordenadas a la nueva lista
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedList.add(entry.getKey());
        }

        Query query = mpostProvider.getAll2();


        List<Query> filteredQuery = new ArrayList<>();
        filteredQuery = filtrosQuery(query, sortedList, precioFiltro1, precioFiltro2);

        String economica = "";
        if (respuestas.contains("Muy importante")) {
            economica = "true";
        }else if (respuestas.contains("Moderadamente importante")) {
            economica = "regular";
        }
        FirestoreRecyclerOptions<PostAuteco> options = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(filteredQuery.get(0), PostAuteco.class).build();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
        mPostsAdapters = new FiltrosMiMotoAdapter(options, getContext(), navController, economica);
        reciclerViewMiMoto1.setAdapter(mPostsAdapters);
        mPostsAdapters.startListening();
        reciclerViewMiMoto1.scrollToPosition(0);

        if (filteredQuery.size() > 1) {
            FirestoreRecyclerOptions<PostAuteco> options2 = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(filteredQuery.get(1), PostAuteco.class).build();
            FiltrosMiMotoAdapter adapter2 = new FiltrosMiMotoAdapter(options2, getContext(), navController, economica);
            reciclerViewMiMoto2.setAdapter(adapter2);
            adapter2.startListening();
            reciclerViewMiMoto2.scrollToPosition(0);

        }
        if (filteredQuery.size() > 2) {
            txtTitulo3.setVisibility(View.VISIBLE);
            FirestoreRecyclerOptions<PostAuteco> options3 = new FirestoreRecyclerOptions.Builder<PostAuteco>().setQuery(filteredQuery.get(2), PostAuteco.class).build();
            FiltrosMiMotoAdapter adapter3 = new FiltrosMiMotoAdapter(options3, getContext(), navController, economica);
            reciclerViewMiMoto3.setAdapter(adapter3);
            adapter3.startListening();
            reciclerViewMiMoto3.scrollToPosition(0);
        }

        int tiempoMostrandoProgressBar = 800;
        if (sumadorClick==8){
            tiempoMostrandoProgressBar= 400;
        }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sclPantalla.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
                    contenedorRecycler.setVisibility(View.VISIBLE);
                    progressBarMiMotoIdeal.setVisibility(View.GONE);
                    contenedorRecycler.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
                    reciclerViewMiMoto1.setVisibility(View.VISIBLE);
                    reciclerViewMiMoto2.setVisibility(View.VISIBLE);
                    if (reciclerViewMiMoto1.getAdapter() != null && reciclerViewMiMoto1.getAdapter().getItemCount() != 0) {
                        txtTitulo1.setVisibility(View.VISIBLE);
                    } else {
                        txtTitulo1.setVisibility(View.GONE);
                    }
                    if (reciclerViewMiMoto2.getAdapter() != null && reciclerViewMiMoto2.getAdapter().getItemCount() != 0) {
                        txtTitulo2.setVisibility(View.VISIBLE);
                    } else {
                        txtTitulo2.setVisibility(View.GONE);
                    }
                    if (reciclerViewMiMoto3.getAdapter() != null && reciclerViewMiMoto3.getAdapter().getItemCount() != 0) {
                        txtTitulo3.setVisibility(View.VISIBLE);
                    } else {
                        txtTitulo3.setVisibility(View.GONE);
                        if (respuestas.contains("Mayormente caminos de tierra/grava")) {
                            txtTitulo2.setText("MOTOS QUE TE PUEDEN INTERESAR");
                        }if (respuestas.contains("Variado (mezcla de asfalto, tierra y grava)")) {
                            txtTitulo2.setText("MOTOS QUE TE PUEDEN INTERESAR");
                        }
                    }


                }
            }, tiempoMostrandoProgressBar);

    }

    private List<Query> filtrosQuery(Query queryAll, List<String> lista, int precioMin, int precioMax) {
        Query query = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax)
                .whereEqualTo("carpeta3", lista.get(0));

        if (lista.size() == 2) {
            query = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                    .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax).
                    whereIn("carpeta3", Arrays.asList(lista.get(0), lista.get(1)));

        } else if (lista.size() == 3) {
            query = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                    .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax).
                    whereIn("carpeta3", Arrays.asList(lista.get(0), lista.get(1), lista.get(2)));

        } else if (lista.size() == 4) {
            query = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                    .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax).
                    whereIn("carpeta3", Arrays.asList(lista.get(0), lista.get(1), lista.get(2), lista.get(3)));

        } else if (lista.size() == 5) {
            query = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                    .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax).
                    whereIn("carpeta3", Arrays.asList(lista.get(0), lista.get(1), lista.get(2), lista.get(3),
                            lista.get(4)));

        } else if (lista.size() == 6) {
            query = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                    .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax).
                    whereIn("carpeta3", Arrays.asList(lista.get(0), lista.get(1), lista.get(2), lista.get(3),
                            lista.get(4), lista.get(5)));
        }
        List<Query> queries = new ArrayList<>();
        queries.add(query);
        Query query1 = null;
        if (respuestas.contains("Uso cotidiano en ciudad")) {
            if (!lista.contains("TODOTERRENO")){
                query1 = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                        .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax)
                        .whereEqualTo("carpeta3", "TODOTERRENO");
                queries.add(query1);
                txtTitulo2.setText("MOTOS GRANDES / TODOTERRENO");
            }
        }

        if (1==1) {

            txtTitulo3.setText("MOTOS QUE TE PUEDEN INTERESAR");
            if (precioMin == 7000000) {
                precioMin = 12000000;
                precioMax = 17999999;
            } else if (precioMin == 12000000) {
                precioMin = 7000000;
                precioMax = 11999999;
            } else if (precioMin == 18000000) {
                precioMin = 12000000;
                precioMax = 17999999;
            } else {
                precioMin = 7000000;
                precioMax = 11999999;
            }


            Query query2 = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                    .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax)
                    .whereEqualTo("carpeta3", lista.get(0));

            if (lista.size() == 2) {
                query2 = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                        .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax).
                        whereIn("carpeta3", Arrays.asList(lista.get(0), lista.get(1)));

            } else if (lista.size() == 3) {
                query2 = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                        .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax).
                        whereIn("carpeta3", Arrays.asList(lista.get(0), lista.get(1), lista.get(2)));

            } else if (lista.size() == 4) {
                query2 = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                        .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax).
                        whereIn("carpeta3", Arrays.asList(lista.get(0), lista.get(1), lista.get(2), lista.get(3)));

            } else if (lista.size() == 5) {
                query2 = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                        .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax).
                        whereIn("carpeta3", Arrays.asList(lista.get(0), lista.get(1), lista.get(2), lista.get(3),
                                lista.get(4)));

            } else if (lista.size() == 6) {
                query2 = queryAll.whereGreaterThanOrEqualTo("nuevoValorDescuento", precioMin)
                        .whereLessThanOrEqualTo("nuevoValorDescuento", precioMax).
                        whereIn("carpeta3", Arrays.asList(lista.get(0), lista.get(1), lista.get(2), lista.get(3),
                                lista.get(4), lista.get(5)));
            }
            queries.add(query2);

        }
        return queries;
    }
}
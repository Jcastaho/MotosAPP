package com.straccion.motos_admin.ui.Estadistica;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.providers.PostProvider;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;


public class EstadisticaFragment extends Fragment {
    View mview;
    List<String> nombreMoto = new ArrayList<>();
    List<String> busquedas = new ArrayList<>();
    PieChart barraCircularMasVisitas;
    PieChart barraCircularMenosVisitas;
    BarChart barraMenosBusquedas;
    BarChart barraMasBusquedas;
    Button btnMostrarNoVisibles;
    PostProvider mPostProvider;
    MotosNoVisibles motosNoVisibles;


    public EstadisticaFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_estadistica, container, false);

        barraCircularMasVisitas = mview.findViewById(R.id.barraCircularMasVisitas);
        barraCircularMenosVisitas = mview.findViewById(R.id.barraCircularMenosVisitas);
        btnMostrarNoVisibles = mview.findViewById(R.id.btnMostrarNoVisibles);
        barraMenosBusquedas = mview.findViewById(R.id.barraMenosBusquedas);
        barraMasBusquedas = mview.findViewById(R.id.barraMasBusquedas);

        mPostProvider = new PostProvider();
        motosNoVisibles = new MotosNoVisibles();

        btnMostrarNoVisibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.action_nav_estadisticaFragment_to_motosNoVisibles);
            }
        });

        recuperarDatos();
        return mview;
    }

    private void mostrarBarras(){

        List<String[]> elementos = new ArrayList<>();

        for (String str : nombreMoto) {
            String[] partes = str.split(":");
            String nombre = partes[0].trim();
            double visita =  Double.parseDouble(partes[1].trim());
            int visitas = (int) visita;
            elementos.add(new String[] {nombre, String.valueOf(visitas)});
        }
        // Ordenar la lista en función del total de visitas (descendente)
        elementos.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

        //Barra 1
        ArrayList<PieEntry> datos = new ArrayList<>();

        for (int i = 0; i < Math.min(5, elementos.size()); i++) {
            datos.add(new PieEntry((float) Double.parseDouble(elementos.get(i)[1]), elementos.get(i)[0]));
        }


        PieDataSet pieDataSet = new PieDataSet(datos, "Motos");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextSize(14f);

        ArrayList<Integer> colores = new ArrayList<>();
        colores.add(Color.rgb(255, 102, 0)); // Color naranja
        colores.add(Color.rgb(0, 153, 255)); // Color azul
        colores.add(Color.rgb(255, 51, 51)); // Color rojo
        colores.add(Color.rgb(102, 255, 51)); // Color verde
        colores.add(Color.rgb(153, 0, 153)); // Color morado

        pieDataSet.setColors(colores);

        PieData pieData = new PieData(pieDataSet);
        barraCircularMasVisitas.setData(pieData);
        barraCircularMasVisitas.getDescription().setEnabled(false);
        barraCircularMasVisitas.setRotationEnabled(false);
        barraCircularMasVisitas.getLegend().setEnabled(false);
        barraCircularMasVisitas.animateY(1500);
        barraCircularMasVisitas.invalidate();


        //Barra 2
        // Ordenar la lista en función del total de visitas (ascendente)
        elementos.sort((c, d) -> Integer.compare(Integer.parseInt(c[1]), Integer.parseInt(d[1])));
        ArrayList<PieEntry> datos2 = new ArrayList<>();
        for (int i = 0; i < Math.min(5, elementos.size()); i++) {
            datos2.add(new PieEntry((float) Double.parseDouble(elementos.get(i)[1]), elementos.get(i)[0]));
        }

        PieDataSet pieDataSet2 = new PieDataSet(datos2, "Motos");
        pieDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet2.setValueTextSize(14f);
        PieData pieData2 = new PieData(pieDataSet2);
        barraCircularMenosVisitas.setData(pieData2);
        barraCircularMenosVisitas.getDescription().setEnabled(false);
        barraCircularMenosVisitas.setRotationEnabled(false);
        barraCircularMenosVisitas.getLegend().setEnabled(false);
        barraCircularMenosVisitas.animateY(1500);
        barraCircularMenosVisitas.invalidate();




        //grafico de barras 1
        elementos.clear();
        for (String str : busquedas) {
            String[] partes = str.split(":");
            String nombre = partes[0].trim();
            double visita =  Double.parseDouble(partes[1].trim());
            int visitas = (int) visita;
            elementos.add(new String[] {nombre, String.valueOf(visitas)});
        }
        // Ordenar la lista en función del total de visitas (descendente)
        elementos.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));


        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < elementos.size(); i++) {
            map.put(elementos.get(i)[0], Integer.parseInt(elementos.get(i)[1]));
        }

        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            barEntries.add(new BarEntry(i,  Integer.parseInt(elementos.get(i)[1])));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS); // Configurar los colores
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Mostrar solo el valor en la parte superior de la barra
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    if (entry.getValue() == (int) value) {
                        return entry.getKey() + ": "+ entry.getValue();
                    }
                }
                return "";
            }
        });

        // Configurar el tamaño del texto de los valores en las barras
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);
        barraMasBusquedas.setData(barData);

        barraMasBusquedas.setScaleEnabled(false);
        barraMasBusquedas.getXAxis().setDrawGridLines(false);
        barraMasBusquedas.getAxisLeft().setDrawGridLines(false);
        barraMasBusquedas.getAxisRight().setDrawGridLines(false);
        barraMasBusquedas.setBackgroundColor(Color.TRANSPARENT);
        barraMasBusquedas.animateY(3500);

        // Actualizar el gráfico
        barraMasBusquedas.invalidate();




        //grafico de barras 2

        // Ordenar la lista en función del total de visitas (ascendente)
        elementos.sort((c, d) -> Integer.compare(Integer.parseInt(c[1]), Integer.parseInt(d[1])));

        Map<String, Integer> map2 = new HashMap<>();
        for (int i = 0; i < elementos.size(); i++) {
            map2.put(elementos.get(i)[0], Integer.parseInt(elementos.get(i)[1]));
        }

        List<BarEntry> barEntries2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int valor = Integer.parseInt(elementos.get(i)[1]);
            if (valor == 0){
                valor = 1;
            }
            barEntries2.add(new BarEntry(i,  valor));
        }
        BarDataSet barDataSet2 = new BarDataSet(barEntries2,"");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS); // Configurar los colores
        barDataSet2.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Mostrar solo el valor en la parte superior de la barra
                for (Map.Entry<String, Integer> entry2 : map.entrySet()) {
                    if (entry2.getValue() == (int) value) {
                        return entry2.getKey() + ": "+ entry2.getValue();
                    }
                }
                return "";
            }
        });

        // Configurar el tamaño del texto de los valores en las barras
        barDataSet2.setValueTextSize(10f);

        BarData barData2 = new BarData(barDataSet2);
        barraMenosBusquedas.setData(barData2);

        barraMenosBusquedas.setScaleEnabled(false);
        barraMenosBusquedas.getXAxis().setDrawGridLines(false);
        barraMenosBusquedas.getAxisLeft().setDrawGridLines(false);
        barraMenosBusquedas.getAxisRight().setDrawGridLines(false);
        barraMenosBusquedas.setBackgroundColor(Color.TRANSPARENT);
        barraMenosBusquedas.animateY(1500);

        // Actualizar el gráfico
        barraMenosBusquedas.invalidate();

    }

    private void recuperarDatos(){

        AtomicInteger counter = new AtomicInteger(0);
        Query query = mPostProvider.getAll2();
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int total = task.getResult().size();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String id = document.getId();

                    mPostProvider.getPostById(id).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    String nombre = documentSnapshot.getString("nombreMoto");
                                    double contador = documentSnapshot.getDouble("vistas");
                                    double contadorbusquedas = documentSnapshot.getDouble("busquedas");
                                    nombreMoto.add(nombre + ":" + contador);
                                    busquedas.add(nombre + ":" + contadorbusquedas);
                                    counter.incrementAndGet();
                                }
                            }
                            if (total == counter.get()){
                                mostrarBarras();
                            }
                        }
                    });

                }

            }
        });

    }

}
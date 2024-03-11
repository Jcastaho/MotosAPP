package com.straccion.motos_admin.ui.Estadistica;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.providers.PostProvider;

import java.util.ArrayList;
import java.util.List;


public class EstadisticaFragment extends Fragment {
    View mview;

    List<String> nombreMoto = new ArrayList<>();
    List<Double> vistas = new ArrayList<>();

    PieChart barraCircularMasVisitas;
    PieChart barraCircularMenosVisitas;
    Button btnMostrarNoVisibles;
    PostProvider mPostProvider;
    MotosNoVisibles motosNoVisibles;


    public EstadisticaFragment() {
        // Required empty public constructor
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

    private void MostrarBarras(){
        double valor1 = vistas.get(0), valor2= vistas.get(1), valor3= vistas.get(2), valor4= vistas.get(3), valor5= vistas.get(4);
        //Barra 1
        ArrayList<PieEntry> datos = new ArrayList<>();

        datos.add(new PieEntry((float) valor1, nombreMoto.get(0)));
        datos.add(new PieEntry((float) valor2, nombreMoto.get(1)));
        datos.add(new PieEntry((float) valor3, nombreMoto.get(2)));
        datos.add(new PieEntry((float) valor4, nombreMoto.get(3)));
        datos.add(new PieEntry((float) valor5, nombreMoto.get(4)));

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
        ArrayList<PieEntry> datos2 = new ArrayList<>();
        datos2.add(new PieEntry(10, "Moto 1"));
        datos2.add(new PieEntry(3, "Moto 2"));
        datos2.add(new PieEntry(10, "Moto 3"));
        datos2.add(new PieEntry(10, "Moto 4"));


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
    }

    private void recuperarDatos(){
        Query query = mPostProvider.getAll2();
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String id = document.getId();
                    mPostProvider.getPostById(id).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombreMoto.add(documentSnapshot.getString("nombreMoto"));
                                    vistas.add(documentSnapshot.getDouble("vistas"));

                                }
                            }
                            if (vistas.size() == 5){
                                MostrarBarras();
                            }
                        }
                    });
                }
            } else {
            }

        });

    }

}
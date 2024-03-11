package com.straccion.motos_admin.ui.inicio;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.models.SliderItem;
import com.straccion.motos_admin.models.ViewPagerItem;
import com.straccion.motos_admin.providers.PostProvider;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NuevoCompararFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevoCompararFragment extends Fragment {
    View mview;
    LinearLayout lnlcontenedorInfo;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, progressBar6, progressBar7, progressBar8, progressBar9, progressBar10;
    Button btnMotoNombre1, btnMotoNombre2;
    TextView txtPreguntaTitulo, txtNombreMoto1, txtNombreMoto2, txtPotencia1, txtPotencia2, txtTorque1, txtTorque2, txtPeso1, txtPeso2,
            txtOption1, txtOption2, txtOption3, txtOption4, txtOption5, txtOption6, txtOption7, txtOption8;
    ImageView imgMoto1, imgMoto2;
    PostProvider mPostProvider;
    int progressStatus = 0;
    Handler handler = new Handler(Looper.getMainLooper());
    String idDocument1 = "";
    String idDocument2 = "";
    String nombre1 = "";
    String nombre2 = "";
    String potencia = "";
    String torque = "";
    String arranque = "";
    String peso = "";
    String sistemaAlimentacion1 = "";
    String sistemaAlimentacion2 = "";
    boolean ABS1 = false;
    boolean ABS2 = false;
    boolean arranqueElectrico1 = false;
    boolean arranqueElectrico2 = false;
    double potencia1 = 0;
    double potencia2 = 0;
    double torque1 = 0;
    double torque2 = 0;
    double peso1 = 0;
    double peso2 = 0;
    int precio1 = 0;
    int precio2 = 0;
    int consumoPorGalon1 = 0;
    int consumoPorGalon2 = 0;
    double velocidadMaxima1 = 0;
    double velocidadMaxima2 = 0;
    double valor1 = 0;
    double valor2 = 0;


    List<String> listaImagenes = new ArrayList<>();




    int color=0;


    public NuevoCompararFragment() {
        // Required empty public constructor
    }


    public static NuevoCompararFragment newInstance(String param1, String param2) {
        NuevoCompararFragment fragment = new NuevoCompararFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idDocument1 = getArguments().getString("idDocument");
            idDocument2 = getArguments().getString("idDocument2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_nuevo_comparar, container, false);
        progressBar1 = mview.findViewById(R.id.progressBar1);
        progressBar2 = mview.findViewById(R.id.progressBar2);
        progressBar3 = mview.findViewById(R.id.progressBar3);
        progressBar4 = mview.findViewById(R.id.progressBar4);
        progressBar5 = mview.findViewById(R.id.progressBar5);
        progressBar6 = mview.findViewById(R.id.progressBar6);
        progressBar7 = mview.findViewById(R.id.progressBar7);
        progressBar8 = mview.findViewById(R.id.progressBar8);
        progressBar9 = mview.findViewById(R.id.progressBar9);
        btnMotoNombre1 = mview.findViewById(R.id.btnMotoNombre1);
        btnMotoNombre2 = mview.findViewById(R.id.btnMotoNombre2);
        imgMoto1 = mview.findViewById(R.id.imgMoto1);
        imgMoto2 = mview.findViewById(R.id.imgMoto2);
        txtPreguntaTitulo = mview.findViewById(R.id.txtPreguntaTitulo);
        txtNombreMoto1 = mview.findViewById(R.id.txtNombreMoto1);
        txtNombreMoto2 = mview.findViewById(R.id.txtNombreMoto2);
        progressBar10 = mview.findViewById(R.id.progressBar10);
        txtPotencia1 = mview.findViewById(R.id.txtPotencia1);
        txtPotencia2 = mview.findViewById(R.id.txtPotencia2);
        txtTorque1 = mview.findViewById(R.id.txtTorque1);
        txtTorque2 = mview.findViewById(R.id.txtTorque2);
        txtPeso1 = mview.findViewById(R.id.txtPeso1);
        txtPeso2 = mview.findViewById(R.id.txtPeso2);
        lnlcontenedorInfo = mview.findViewById(R.id.lnlcontenedorInfo);
        txtOption1 = mview.findViewById(R.id.txtOption1);
        txtOption2 = mview.findViewById(R.id.txtOption2);
        txtOption3 = mview.findViewById(R.id.txtOption3);
        txtOption4 = mview.findViewById(R.id.txtOption4);
        txtOption5 = mview.findViewById(R.id.txtOption5);
        txtOption6 = mview.findViewById(R.id.txtOption6);
        txtOption7 = mview.findViewById(R.id.txtOption7);
        txtOption8 = mview.findViewById(R.id.txtOption8);


        mPostProvider = new PostProvider();
//colores
        int colorClaro1 = ContextCompat.getColor(getContext(), R.color.verdeTransparente);
        int colorClaro2 = ContextCompat.getColor(getContext(), R.color.redTransparente);
        Drawable color1 = new ColorDrawable(colorClaro1);
        Drawable color2 = new ColorDrawable(colorClaro2);
        lnlcontenedorInfo.setBackground(color1);
        btnMotoNombre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnlcontenedorInfo.setBackground(color1);
                if (!nombre1.equals("")){
                    txtPreguntaTitulo.setText("¿Por que la "+ nombre1 + " es mejor?");
                }
                comparacion();
            }
        });
        btnMotoNombre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnlcontenedorInfo.setBackground(color2);
                if (!nombre2.equals("")) {
                    txtPreguntaTitulo.setText("¿Por que la "+ nombre2 + " es mejor?");
                }
                comparacion();
            }
        });

        traerDatos();

        return mview;
    }

    private void traerDatos(){
        mPostProvider.getPostById(idDocument1).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    if (documentSnapshot.contains("nombreMoto")) {
                        nombre1 = documentSnapshot.get("nombreMoto").toString();
                        txtNombreMoto1.setText(nombre1);
                        btnMotoNombre1.setText(nombre1);
                        txtPreguntaTitulo.setText("¿Por que la "+ nombre1 + " es mejor?");
                    }
                    if (documentSnapshot.contains("nuevoValorDescuento")) {
                        precio1 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                    }
                    if (documentSnapshot.contains("potenciaMaxima")) {
                        potencia = documentSnapshot.get("potenciaMaxima").toString();
                        potencia1 = buscarNumero(potencia);
                        txtPotencia1.setText(potencia1 + " HP");
                    }
                    if (documentSnapshot.contains("torqueMaximo")) {
                        torque = documentSnapshot.get("torqueMaximo").toString();
                        torque1 = buscarNumero(torque);
                        txtTorque1.setText(torque1 + " Nm");
                    }
                    if (documentSnapshot.contains("pesoNeto")) {
                        peso = documentSnapshot.get("pesoNeto").toString();
                        peso1 = buscarNumero(peso);
                        txtPeso1.setText(peso1 + " KG");
                    }
                    if (documentSnapshot.contains("sistemaAlimentacion")) {
                        sistemaAlimentacion1 = documentSnapshot.get("sistemaAlimentacion").toString();
                        ABS1 = abs(sistemaAlimentacion1);
                    }
                    if (documentSnapshot.contains("consumoPorGalon")) {
                        velocidadMaxima1 = Double.parseDouble(documentSnapshot.get("consumoPorGalon").toString());
                    }
                    if (documentSnapshot.contains("velocidadMaxima")) {
                        consumoPorGalon1 = Integer.parseInt(documentSnapshot.get("velocidadMaxima").toString());
                    }
                    if (documentSnapshot.contains("imagenes")) {
                        listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                        String imagen = listaImagenes.get(0);
                        Picasso.get().load(imagen).into(imgMoto1);
                    }
                    if (documentSnapshot.contains("arranque")) {
                        arranque = documentSnapshot.get("arranque").toString();
                        arranqueElectrico1 = tipoArranque(arranque);
                    }
                    if (documentSnapshot.contains("nuevoValorDescuento")) {
                        valor1 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                    }
                }
                mPostProvider.getPostById(idDocument2).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        if (document.exists()){

                            if (document.contains("nombreMoto")) {
                                nombre2 = document.get("nombreMoto").toString();
                                txtNombreMoto2.setText(nombre2);
                                btnMotoNombre2.setText(nombre2);
                            }
                            if (document.contains("nuevoValorDescuento")) {
                                precio2 = Integer.parseInt(document.get("nuevoValorDescuento").toString());
                            }
                            if (document.contains("potenciaMaxima")) {
                                potencia = document.get("potenciaMaxima").toString();
                                potencia2 = buscarNumero(potencia);
                                txtPotencia2.setText(potencia2 + " HP");
                            }
                            if (document.contains("torqueMaximo")) {
                                torque = document.get("torqueMaximo").toString();
                                torque2 = buscarNumero(torque);
                                txtTorque2.setText(torque2 + " Nm");
                            }
                            if (document.contains("pesoNeto")) {
                                peso = document.get("pesoNeto").toString();
                                peso2 = buscarNumero(peso);
                                txtPeso2.setText(peso2 + " KG");
                            }
                            if (document.contains("sistemaAlimentacion")) {
                                sistemaAlimentacion2 = document.get("sistemaAlimentacion").toString();
                                ABS2 = abs(sistemaAlimentacion2);
                            }
                            if (document.contains("consumoPorGalon")) {
                                velocidadMaxima2 = Double.parseDouble(document.get("consumoPorGalon").toString());
                            }
                            if (document.contains("velocidadMaxima")) {
                                consumoPorGalon2 = Integer.parseInt(document.get("velocidadMaxima").toString());
                            }
                            if (document.contains("imagenes")) {
                                listaImagenes = (List<String>) document.get("imagenes");
                                String imagen = listaImagenes.get(0);
                                Picasso.get().load(imagen).into(imgMoto2);
                            }
                            if (document.contains("arranque")) {
                                arranque = document.get("arranque").toString();
                                arranqueElectrico2 = tipoArranque(arranque);
                            }
                            if (document.contains("nuevoValorDescuento")) {
                                valor2 = Integer.parseInt(document.get("nuevoValorDescuento").toString());
                            }
                            barras();
                            comparacion();
                        }
                    }
                });
            }
        });
    }
    private static double buscarNumero(String texto) {
        // Patrón de expresión regular para buscar el primer número seguido de letras
        Pattern patron = Pattern.compile("(\\d+\\.?\\d*)(?=\\s*[a-zA-Z])");
        Matcher matcher = patron.matcher(texto);

        // Encontrar el primer número seguido de letras en la cadena de texto
        if (matcher.find()) {
            String numero = matcher.group();
            return Double.parseDouble(numero);
        }

        return 0.0;
    }
    private void barras(){
        progressBar1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar1, potencia1);
            }
        });
        progressBar2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar2, 60);
            }
        });
        progressBar3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar3, torque1);
            }
        });
        progressBar4.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar4, 60);
            }
        });
        progressBar5.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar5, peso1);
            }
        });
        progressBar6.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar6, 60);
            }
        });
        progressBar7.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar7, 80);
            }
        });
        progressBar8.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar8, 60);
            }
        });
        progressBar9.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar9, 80);
            }
        });
        progressBar10.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar10, 60);
            }
        });
    }

    private void comparacion(){
        //colores
        int colorClaro1 = ContextCompat.getColor(getContext(), R.color.verdeTransparente);
        int colorClaro2 = ContextCompat.getColor(getContext(), R.color.redTransparente);

        Drawable background = lnlcontenedorInfo.getBackground();

        if (background instanceof ColorDrawable) {
            color = ((ColorDrawable) background).getColor();

        } else {

        }
        mostrarTextos();
        if (color == colorClaro1){
            if (potencia1 > potencia2){
                double diferencia = potencia1 - potencia2;
                double porcentaje = (diferencia / Math.min(potencia1, potencia2)) * 100;
                String porcentajeFormateado = String.format("%.2f", porcentaje);
                txtOption1.setText("✔  Cuenta con " + porcentajeFormateado + "% mas de potencia");
            }else {
                txtOption1.setVisibility(View.GONE);
            }
            if (torque1 > torque2){
                txtOption2.setText("✔  Tiene un mayor torque");
            }else {
                txtOption2.setVisibility(View.GONE);
            }
            if (peso1 < peso2){
                txtOption3.setText("✔  Tiene un peso menor");
            }else {
                txtOption3.setVisibility(View.GONE);
            }
            if (ABS1 && !ABS2){
                txtOption4.setText("✔  Cuenta con ABS");
            }else {
                txtOption4.setVisibility(View.GONE);
            }
            if (consumoPorGalon1 < consumoPorGalon2){
                txtOption5.setText("✔  Tiene un menor consumo de gasolina con un promedio de " + consumoPorGalon1 + "KM por galon");
            }else {
                txtOption5.setVisibility(View.GONE);
            }
            if (arranqueElectrico1 && !arranqueElectrico2){
                txtOption6.setText("✔  Cuenta encendido eléctrico");
            }else {
                txtOption6.setVisibility(View.GONE);
            }
            if (valor1 < valor2){
                double diferencia = valor2 - valor1;
                double porcentaje = (diferencia / Math.min(valor2, valor1)) * 100;
                String porcentajeFormateado = String.format("%.2f", porcentaje);
                txtOption7.setText("✔  Es un " + porcentajeFormateado + "% mas barata con un precio de: " + valor1);
            }else {
                txtOption7.setVisibility(View.GONE);
            }
        }
        if (color == colorClaro2){
            if (potencia1 < potencia2){
                double diferencia = potencia2 - potencia1;
                double porcentaje = (diferencia / Math.min(potencia2, potencia1)) * 100;
                String porcentajeFormateado = String.format("%.2f", porcentaje);
                txtOption1.setText("✔  Cuenta con " + porcentajeFormateado + "% mas de potencia");
            }else {
                txtOption1.setVisibility(View.GONE);
            }
            if (torque1 < torque2){
                txtOption2.setText("✔  Tiene un mayor torque");
            }else {
                txtOption2.setVisibility(View.GONE);
            }
            if (peso1 > peso2){
                txtOption3.setText("✔  Tiene un peso menor");
            }else {
                txtOption3.setVisibility(View.GONE);
            }
            if (peso1 > peso2){
                txtOption3.setText("✔  Tiene un peso menor");
            }else {
                txtOption3.setVisibility(View.GONE);
            }
            if (ABS2 && !ABS1){
                txtOption4.setText("✔  Cuenta con ABS");
            }else {
                txtOption4.setVisibility(View.GONE);
            }
            if (consumoPorGalon1 > consumoPorGalon2){
                txtOption5.setText("✔  Tiene un menor consumo de gasolina con un promedio de " + consumoPorGalon2 + "KM por galon");
            }else {
                txtOption5.setVisibility(View.GONE);
            }
            if (arranqueElectrico2 && !arranqueElectrico1){
                txtOption6.setText("✔  Cuenta encendido eléctrico");
            }else {
                txtOption6.setVisibility(View.GONE);
            }
            if (valor1 > valor2){
                double diferencia = valor1 - valor2;
                double porcentaje = (diferencia / Math.min(valor1, valor2)) * 100;
                String porcentajeFormateado = String.format("%.2f", porcentaje);
                txtOption7.setText("✔  Es un " + porcentajeFormateado + "% mas barata con un precio de: " + valor2);
            }else {
                txtOption7.setVisibility(View.GONE);
            }
        }
    }



    private void mostrarTextos(){
        txtOption1.setVisibility(View.VISIBLE);
        txtOption2.setVisibility(View.VISIBLE);
        txtOption3.setVisibility(View.VISIBLE);
        txtOption4.setVisibility(View.VISIBLE);
        txtOption5.setVisibility(View.VISIBLE);
        txtOption6.setVisibility(View.VISIBLE);
        txtOption7.setVisibility(View.VISIBLE);
        txtOption8.setVisibility(View.VISIBLE);
    }

    private void startProgressAnimation(ProgressBar progressBar, double dato) {
        //se encarga de hacer mas lento el llenado de las barras y da un efecto visual bonito
        int porcentaje = Double.valueOf(dato).intValue();
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < porcentaje) {
                    //hay que modificarlo, se debe de sacar la media/moda/promedio de todos los datos para colocarlo como 100%
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        // Hacer que el hilo duerma para simular una velocidad de llenado más lenta
                        Thread.sleep(15); // Ajusta este valor para cambiar la velocidad
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public boolean abs(String args) {

        // Definir el patrón de búsqueda
        Pattern patron = Pattern.compile("Inyección|electrónica|fi|full|ABS", Pattern.CASE_INSENSITIVE);

        // Buscar coincidencias en el texto
        Matcher matcher = patron.matcher(args);

        // Verificar si se encontraron coincidencias
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean tipoArranque(String arranque) {

        // Definir el patrón de búsqueda
        Pattern patron = Pattern.compile("Eléctrico|electrónico|eléctrica|batería", Pattern.CASE_INSENSITIVE);

        // Buscar coincidencias en el texto
        Matcher matcher = patron.matcher(arranque);

        // Verificar si se encontraron coincidencias
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
}
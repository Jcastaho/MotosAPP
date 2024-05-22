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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NuevoCompararFragment extends Fragment {
    View mview;
    LinearLayout lnlcontenedorInfo;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, progressBar6, progressBar7,
            progressBar8, progressBar9, progressBar10;
    Button btnMotoNombre1, btnMotoNombre2;
    TextView txtPreguntaTitulo, txtNombreMoto1, txtNombreMoto2, txtPotencia1, txtPotencia2, txtTorque1, txtTorque2, txtPeso1, txtPeso2,
            txtABS1, txtABS2, txtOption1, txtOption2, txtConsumoXGalon1, txtConsumoXGalon2, txtPrecio1, txtPrecio2, txtEncendido1,
            txtEncendido2, txtFrenos1, txtFrenos2, txtCilindraje1, txtCilindraje2, txtVelMaxima1, txtVelMaxima2,
            txtFI1, txtFI2, txtOption3, txtOption4, txtOption5, txtOption6, txtOption7, txtOption8, txtOption9, txtOption10,
            txtOption11, txtOption12, txtOption13, txtOption14;
    ImageView imgMoto1, imgMoto2;
    PostProvider mPostProvider;
    int progressStatus = 0;
    Handler handler = new Handler(Looper.getMainLooper());
    String idDocument1 = "";
    String idDocument2 = "";
    String nombre1 = "";
    String nombre2 = "";
    String potencia = "";
    String cilindraje1 = "";
    String cilindraje2 = "";
    String torque = "";
    String arranque1 = "";
    String arranque2 = "";
    String peso = "";
    String fabricante = "";
    String sistemaAlimentacion1 = "";
    String sistemaAlimentacion2 = "";
    String asc1 = "";
    String asc2 = "";
    String aho1 = "";
    String aho2 = "";
    String qss1 = "";
    String qss2 = "";
    String tcs1 = "";
    String tcs2 = "";
    String frenodelantero1 = "";
    String frenodelantero2 = "";
    String frenotrasero1 = "";
    String frenotrasero2 = "";
    String especialidad1 = "";
    String especialidad2 = "";

    boolean ABSLlantaTrasera1 = false;
    boolean ABSLlantaTrasera2 = false;
    boolean ABS1 = false;
    boolean ABS2 = false;
    boolean Fi1 = false;
    boolean Fi2 = false;
    boolean arranqueElectrico1 = false;
    boolean arranqueElectrico2 = false;
    double potencia1 = 0;
    double potencia2 = 0;
    double torque1 = 0;
    double torque2 = 0;
    double peso1 = 0;
    double peso2 = 0;
    int consumoPorGalon1 = 0;
    int consumoPorGalon2 = 0;
    int velocidadMaxima1 = 0;
    int velocidadMaxima2 = 0;
    double valor1 = 0;
    double valor2 = 0;
    int datoMaximo =0;
    double cilindrajeValor1 =0;
    double cilindrajeValor2 =0;
    int randomInt=0;
    List<String> listaImagenes = new ArrayList<>();
    int color=0;


    public NuevoCompararFragment() {
        // Required empty public constructor
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
        progressBar10 = mview.findViewById(R.id.progressBar10);
        btnMotoNombre1 = mview.findViewById(R.id.btnMotoNombre1);
        btnMotoNombre2 = mview.findViewById(R.id.btnMotoNombre2);
        imgMoto1 = mview.findViewById(R.id.imgMoto1);
        imgMoto2 = mview.findViewById(R.id.imgMoto2);
        txtPreguntaTitulo = mview.findViewById(R.id.txtPreguntaTitulo);
        txtNombreMoto1 = mview.findViewById(R.id.txtNombreMoto1);
        txtNombreMoto2 = mview.findViewById(R.id.txtNombreMoto2);
        txtPotencia1 = mview.findViewById(R.id.txtPotencia1);
        txtPotencia2 = mview.findViewById(R.id.txtPotencia2);
        txtTorque1 = mview.findViewById(R.id.txtTorque1);
        txtTorque2 = mview.findViewById(R.id.txtTorque2);
        txtPeso1 = mview.findViewById(R.id.txtPeso1);
        txtPeso2 = mview.findViewById(R.id.txtPeso2);
        txtABS1 = mview.findViewById(R.id.txtABS1);
        txtABS2 = mview.findViewById(R.id.txtABS2);
        txtPrecio1 = mview.findViewById(R.id.txtPrecio1);
        txtPrecio2 = mview.findViewById(R.id.txtPrecio2);
        txtFI1 = mview.findViewById(R.id.txtFI1);
        txtFI2 = mview.findViewById(R.id.txtFI2);
        txtFrenos1 = mview.findViewById(R.id.txtFrenos1);
        txtFrenos2 = mview.findViewById(R.id.txtFrenos2);
        txtEncendido1 = mview.findViewById(R.id.txtEncendido1);
        txtEncendido2 = mview.findViewById(R.id.txtEncendido2);
        txtCilindraje1 = mview.findViewById(R.id.txtCilindraje1);
        txtCilindraje2 = mview.findViewById(R.id.txtCilindraje2);
        txtVelMaxima1 = mview.findViewById(R.id.txtVelMaxima1);
        txtVelMaxima2 = mview.findViewById(R.id.txtVelMaxima2);
        txtConsumoXGalon1 = mview.findViewById(R.id.txtConsumoXGalon1);
        txtConsumoXGalon2 = mview.findViewById(R.id.txtConsumoXGalon2);
        lnlcontenedorInfo = mview.findViewById(R.id.lnlcontenedorInfo);
        txtOption1 = mview.findViewById(R.id.txtOption1);
        txtOption2 = mview.findViewById(R.id.txtOption2);
        txtOption3 = mview.findViewById(R.id.txtOption3);
        txtOption4 = mview.findViewById(R.id.txtOption4);
        txtOption5 = mview.findViewById(R.id.txtOption5);
        txtOption6 = mview.findViewById(R.id.txtOption6);
        txtOption7 = mview.findViewById(R.id.txtOption7);
        txtOption8 = mview.findViewById(R.id.txtOption8);
        txtOption9 = mview.findViewById(R.id.txtOption9);
        txtOption10 = mview.findViewById(R.id.txtOption10);
        txtOption11 = mview.findViewById(R.id.txtOption11);
        txtOption12 = mview.findViewById(R.id.txtOption12);
        txtOption13 = mview.findViewById(R.id.txtOption13);
        txtOption14 = mview.findViewById(R.id.txtOption14);


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
        List<String> ids = Arrays.asList(idDocument1, idDocument2);
        mPostProvider.getPostsByIds(ids).addOnSuccessListener(new OnSuccessListener<List<DocumentSnapshot>>() {
            @Override
            public void onSuccess(List<DocumentSnapshot> documentSnapshots) {
                for (int i = 0; i < documentSnapshots.size(); i++) {
                    DocumentSnapshot documentSnapshot = documentSnapshots.get(i);
                    if (i == 0){
                        if (documentSnapshot.exists()){
                            if (documentSnapshot.contains("carpeta1")) {
                                fabricante = documentSnapshot.get("carpeta1").toString();
                            }
                            if (fabricante.equals("AUTECO")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre1 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto1.setText(nombre1);
                                    btnMotoNombre1.setText(nombre1);
                                    ABS1 = ABSoSisAlimen(nombre1);
                                    txtPreguntaTitulo.setText("¿Por que la "+ nombre1 + " es mejor?");
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad1 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("frenoDelantero")) {
                                    String TipoFreno = documentSnapshot.get("frenoDelantero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero1 = "Disco";
                                    }else {
                                        frenodelantero1 = "Tambor";
                                    }
                                    if (!ABS1){
                                        ABS1 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("frenoTrasero")) {
                                    String TipoFreno = documentSnapshot.get("frenoTrasero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera1 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero1 = "Disco";
                                    }else {
                                        frenotrasero1 = "Tambor";
                                    }
                                    if (!ABS1){
                                        ABS1 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("potenciaMaxima")) {
                                    potencia = documentSnapshot.get("potenciaMaxima").toString();
                                    potencia1 = buscarNumero(potencia);
                                    txtPotencia1.setText(potencia1 + " HP");
                                }
                                if (documentSnapshot.contains("cilindraje")) {
                                    cilindraje1 = documentSnapshot.get("cilindraje").toString();
                                    cilindrajeValor1 = buscarNumero(cilindraje1);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje1);
                                    if (matcher.find()) {
                                        txtCilindraje1.setText(cilindraje1.toUpperCase());
                                    } else {
                                        txtCilindraje1.setText(cilindraje1 + " CC");
                                    }

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
                                    Fi1 = ABSoSisAlimen(sistemaAlimentacion1);
                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon1 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());
                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima1 = (int) dato;
                                    txtVelMaxima1.setText(velocidadMaxima1 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto1);
                                    imgMoto1.setPadding(0,0,0,0);
                                }
                                if (documentSnapshot.contains("arranque")) {
                                    arranque1 = documentSnapshot.get("arranque").toString();
                                    arranqueElectrico1 = tipoArranque(arranque1);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor1 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }
                            //Yamaha
                            if (fabricante.equals("YAMAHA")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre1 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto1.setText(nombre1);
                                    btnMotoNombre1.setText(nombre1);
                                    ABS1 = ABSoSisAlimen(nombre1);
                                    txtPreguntaTitulo.setText("¿Por que la "+ nombre1 + " es mejor?");
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad1 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("asc")) {
                                    asc1 = documentSnapshot.get("asc").toString();
                                }
                                if (documentSnapshot.contains("aho")) {
                                    aho1 = documentSnapshot.get("aho").toString();
                                }
                                if (documentSnapshot.contains("qss")) {
                                    qss1 = documentSnapshot.get("qss").toString();
                                }
                                if (documentSnapshot.contains("tcs")) {
                                    tcs1 = documentSnapshot.get("tcs").toString();
                                }
                                if (documentSnapshot.contains("frenodelantero")) {
                                    String TipoFreno = documentSnapshot.get("frenodelantero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero1 = "Disco";
                                    }else {
                                        frenodelantero1 = "Tambor";
                                    }
                                    if (!ABS1){
                                        ABS1 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("frenotrasero")) {
                                    String TipoFreno = documentSnapshot.get("frenotrasero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera1 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero1 = "Disco";
                                    }else {
                                        frenotrasero1 = "Tambor";
                                    }
                                    if (!ABS1){
                                        ABS1 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("potenciamaxima")) {
                                    potencia = documentSnapshot.get("potenciamaxima").toString();
                                    potencia1 = buscarNumero(potencia);
                                    txtPotencia1.setText(potencia1 + " HP");
                                }
                                if (documentSnapshot.contains("cilindraje")) {
                                    cilindraje1 = documentSnapshot.get("cilindraje").toString();
                                    cilindrajeValor1 = buscarNumero(cilindraje1);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje1);
                                    if (matcher.find()) {
                                        txtCilindraje1.setText(cilindraje1.toUpperCase());
                                    } else {
                                        txtCilindraje1.setText(cilindraje1 + " CC");
                                    }
                                }
                                if (documentSnapshot.contains("torquemaximo")) {
                                    torque = documentSnapshot.get("torquemaximo").toString();
                                    torque1 = buscarNumero(torque);
                                    txtTorque1.setText(torque1 + " Nm");
                                }
                                if (documentSnapshot.contains("peso")) {
                                    peso = documentSnapshot.get("peso").toString();
                                    peso1 = buscarNumero(peso);
                                    txtPeso1.setText(peso1 + " KG");
                                }
                                if (documentSnapshot.contains("sistemadealimentacion")) {
                                    sistemaAlimentacion1 = documentSnapshot.get("sistemadealimentacion").toString();
                                    Fi1 = ABSoSisAlimen(sistemaAlimentacion1);

                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon1 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());

                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima1 = (int) dato;
                                    txtVelMaxima1.setText(velocidadMaxima1 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto1);
                                    imgMoto1.setPadding(18,18,18,18);
                                }
                                if (documentSnapshot.contains("arranque")) {
                                    arranque1 = documentSnapshot.get("arranque").toString();
                                    arranqueElectrico1 = tipoArranque(arranque1);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor1 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }

                            if (fabricante.equals("HERO")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre1 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto1.setText(nombre1);
                                    btnMotoNombre1.setText(nombre1);
                                    ABS1 = ABSoSisAlimen(nombre1);
                                    txtPreguntaTitulo.setText("¿Por que la "+ nombre1 + " es mejor?");
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad1 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("delanteros")) {
                                    String TipoFreno = documentSnapshot.get("delanteros").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero1 = "Disco";
                                    }else {
                                        frenodelantero1 = "Tambor";
                                    }
                                    if (!ABS1){
                                        ABS1 = ABSoSisAlimen(TipoFreno);
                                    }else {

                                    }
                                }
                                if (documentSnapshot.contains("traseros")) {
                                    String TipoFreno = documentSnapshot.get("traseros").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera1 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero1 = "Disco";
                                    }else {
                                        frenotrasero1 = "Tambor";
                                    }
                                    if (!ABS1){
                                        ABS1 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("maxpotencia")) {
                                    potencia = documentSnapshot.get("maxpotencia").toString();
                                    potencia1 = buscarNumero(potencia);
                                    txtPotencia1.setText(potencia1 + " HP");
                                }
                                if (documentSnapshot.contains("cilindraje")) {
                                    cilindraje1 = documentSnapshot.get("cilindraje").toString();
                                    cilindrajeValor1 = buscarNumero(cilindraje1);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje1);
                                    if (matcher.find()) {
                                        txtCilindraje1.setText(cilindraje1.toUpperCase());
                                    } else {
                                        txtCilindraje1.setText(cilindraje1 + " CC");
                                    }
                                }
                                if (documentSnapshot.contains("maxtorque")) {
                                    torque = documentSnapshot.get("maxtorque").toString();
                                    torque1 = buscarNumero(torque);
                                    txtTorque1.setText(torque1 + " Nm");
                                }
                                if (documentSnapshot.contains("pesosincarga")) {
                                    peso = documentSnapshot.get("pesosincarga").toString();
                                    peso1 = buscarNumero(peso);
                                    txtPeso1.setText(peso1 + " KG");
                                }
                                if (!documentSnapshot.contains("alimentacion")) {
                                    Fi1 = false;
                                }else {
                                    sistemaAlimentacion1 = documentSnapshot.get("alimentacion").toString();
                                    Fi1 = ABSoSisAlimen(sistemaAlimentacion1);
                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon1 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());
                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima1 = (int) dato;
                                    txtVelMaxima1.setText(velocidadMaxima1 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto1);
                                    imgMoto1.setPadding(18,18,18,18);
                                }
                                if (documentSnapshot.contains("arranque")) {
                                    arranque1 = documentSnapshot.get("arranque").toString();
                                    arranqueElectrico1 = tipoArranque(arranque1);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor1 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }
                            if (fabricante.equals("SUZUKI")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre1 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto1.setText(nombre1);
                                    btnMotoNombre1.setText(nombre1);
                                    ABS1 = ABSoSisAlimen(nombre1);
                                    txtPreguntaTitulo.setText("¿Por que la "+ nombre1 + " es mejor?");
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad1 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("frenodelantero")) {
                                    String TipoFreno = documentSnapshot.get("frenodelantero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero1 = "Disco";
                                    }else {
                                        frenodelantero1 = "Tambor";
                                    }
                                    if (!ABS1){
                                        ABS1 = ABSoSisAlimen(TipoFreno);
                                    }else {

                                    }
                                }
                                if (documentSnapshot.contains("frenotrasero")) {
                                    String TipoFreno = documentSnapshot.get("frenotrasero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera1 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero1 = "Disco";
                                    }else {
                                        frenotrasero1 = "Tambor";
                                    }
                                    if (!ABS1){
                                        ABS1 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("potencia")) {
                                    potencia = documentSnapshot.get("potencia").toString();
                                    potencia1 = buscarNumero(potencia);
                                    txtPotencia1.setText(potencia1 + " HP");
                                }
                                if (documentSnapshot.contains("cilindraje")) {
                                    cilindraje1 = documentSnapshot.get("cilindraje").toString();
                                    cilindrajeValor1 = buscarNumero(cilindraje1);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje1);
                                    if (matcher.find()) {
                                        txtCilindraje1.setText(cilindraje1.toUpperCase());
                                    } else {
                                        txtCilindraje1.setText(cilindraje1 + " CC");
                                    }
                                }
                                if (documentSnapshot.contains("torque")) {
                                    torque = documentSnapshot.get("torque").toString();
                                    torque1 = buscarNumero(torque);
                                    txtTorque1.setText(torque1 + " Nm");
                                }
                                if (documentSnapshot.contains("pesoenseco")) {
                                    peso = documentSnapshot.get("pesoenseco").toString();
                                    peso1 = buscarNumero(peso);
                                    txtPeso1.setText(peso1 + " KG");
                                }
                                if (documentSnapshot.contains("sistemadealimentaciondecombustible")) {
                                    sistemaAlimentacion1 = documentSnapshot.get("sistemadealimentaciondecombustible").toString();
                                    Fi1 = ABSoSisAlimen(sistemaAlimentacion1);
                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon1 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());
                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima1 = (int) dato;
                                    txtVelMaxima1.setText(velocidadMaxima1 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto1);
                                    imgMoto1.setPadding(18,18,18,18);
                                }
                                if (documentSnapshot.contains("sistemadearranque")) {
                                    arranque1 = documentSnapshot.get("sistemadearranque").toString();
                                    arranqueElectrico1 = tipoArranque(arranque1);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor1 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }
                            if (fabricante.equals("HONDA")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre1 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto1.setText(nombre1);
                                    btnMotoNombre1.setText(nombre1);
                                    ABS1 = ABSoSisAlimen(nombre1);
                                    txtPreguntaTitulo.setText("¿Por que la "+ nombre1 + " es mejor?");
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad1 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("frenodelantero")) {
                                    String TipoFreno = documentSnapshot.get("frenodelantero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero1 = "Disco";
                                    }else {
                                        frenodelantero1 = "Tambor";
                                    }
                                    if (!ABS1){
                                        ABS1 = ABSoSisAlimen(TipoFreno);
                                    }else {

                                    }
                                }
                                if (documentSnapshot.contains("frenotrasero")) {
                                    String TipoFreno = documentSnapshot.get("frenotrasero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera1 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero1 = "Disco";
                                    }else {
                                        frenotrasero1 = "Tambor";
                                    }
                                    if (!ABS1){
                                        ABS1 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("potenciamaxima")) {
                                    potencia = documentSnapshot.get("potenciamaxima").toString();
                                    potencia1 = buscarNumero(potencia);
                                    txtPotencia1.setText(potencia1 + " HP");
                                }
                                if (documentSnapshot.contains("cilindraje")) {
                                    cilindraje1 = documentSnapshot.get("cilindraje").toString();
                                    cilindrajeValor1 = buscarNumero(cilindraje1);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje1);
                                    if (matcher.find()) {
                                        txtCilindraje1.setText(cilindraje1.toUpperCase());
                                    } else {
                                        txtCilindraje1.setText(cilindraje1 + " CC");
                                    }
                                }
                                if (documentSnapshot.contains("torquemaximo")) {
                                    torque = documentSnapshot.get("torquemaximo").toString();
                                    torque1 = buscarNumero(torque);
                                    txtTorque1.setText(torque1 + " Nm");
                                }
                                if (documentSnapshot.contains("peso")) {
                                    peso = documentSnapshot.get("peso").toString();
                                    peso1 = buscarNumero(peso);
                                    txtPeso1.setText(peso1 + " KG");
                                }
                                if (documentSnapshot.contains("sistemadealimentacion")) {
                                    sistemaAlimentacion1 = documentSnapshot.get("sistemadealimentacion").toString();
                                    Fi1 = ABSoSisAlimen(sistemaAlimentacion1);
                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon1 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());
                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima1 = (int) dato;
                                    txtVelMaxima1.setText(velocidadMaxima1 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto1);
                                    imgMoto1.setPadding(18,18,18,18);
                                }
                                if (documentSnapshot.contains("tipodearranque")) {
                                    arranque1 = documentSnapshot.get("tipodearranque").toString();
                                    arranqueElectrico1 = tipoArranque(arranque1);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor1 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }
                        }
                    }else {
                        //moto a comparar numero 2
                        if (documentSnapshot.exists()){
                            if (documentSnapshot.contains("carpeta1")) {
                                fabricante = documentSnapshot.get("carpeta1").toString();
                            }
                            if (fabricante.equals("AUTECO")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre2 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto2.setText(nombre2);
                                    btnMotoNombre2.setText(nombre2);
                                    ABS2 = ABSoSisAlimen(nombre2);
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad2 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("frenoDelantero")) {
                                    String TipoFreno = documentSnapshot.get("frenoDelantero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero2 = "Disco";
                                    }else {
                                        frenodelantero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("frenoTrasero")) {
                                    String TipoFreno = documentSnapshot.get("frenoTrasero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera2 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero2 = "Disco";
                                    }else {
                                        frenotrasero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("potenciaMaxima")) {
                                    potencia = documentSnapshot.get("potenciaMaxima").toString();
                                    potencia2 = buscarNumero(potencia);
                                    txtPotencia2.setText(potencia2 + " HP");
                                }
                                if (documentSnapshot.contains("cilindraje")) {
                                    cilindraje2 = documentSnapshot.get("cilindraje").toString();
                                    cilindrajeValor2 = buscarNumero(cilindraje2);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje2);
                                    if (matcher.find()) {
                                        txtCilindraje2.setText(cilindraje2.toUpperCase());
                                    } else {
                                        txtCilindraje2.setText(cilindraje2 + " CC");
                                    }
                                }
                                if (documentSnapshot.contains("torqueMaximo")) {
                                    torque = documentSnapshot.get("torqueMaximo").toString();
                                    torque2 = buscarNumero(torque);
                                    txtTorque2.setText(torque2 + " Nm");
                                }
                                if (documentSnapshot.contains("pesoNeto")) {
                                    peso = documentSnapshot.get("pesoNeto").toString();
                                    peso2 = buscarNumero(peso);
                                    txtPeso2.setText(peso2 + " KG");
                                }
                                if (documentSnapshot.contains("sistemaAlimentacion")) {
                                    sistemaAlimentacion2 = documentSnapshot.get("sistemaAlimentacion").toString();
                                    Fi2 = ABSoSisAlimen(sistemaAlimentacion2);

                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon2 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());
                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima2 = (int) dato;
                                    txtVelMaxima2.setText(velocidadMaxima2 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto2);
                                    imgMoto2.setPadding(0,0,0,0);
                                }
                                if (documentSnapshot.contains("arranque")) {
                                    arranque2 = documentSnapshot.get("arranque").toString();
                                    arranqueElectrico2 = tipoArranque(arranque2);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor2 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }
                            //Yamaha
                            if (fabricante.equals("YAMAHA")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre2 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto2.setText(nombre2);
                                    btnMotoNombre2.setText(nombre2);
                                    ABS2 = ABSoSisAlimen(nombre2);
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad2 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("asc")) {
                                    asc2 = documentSnapshot.get("asc").toString();
                                }
                                if (documentSnapshot.contains("aho")) {
                                    aho2 = documentSnapshot.get("aho").toString();
                                }
                                if (documentSnapshot.contains("qss")) {
                                    qss2 = documentSnapshot.get("qss").toString();
                                }
                                if (documentSnapshot.contains("tcs")) {
                                    tcs2 = documentSnapshot.get("tcs").toString();
                                }
                                if (documentSnapshot.contains("frenodelantero")) {
                                    String TipoFreno = documentSnapshot.get("frenodelantero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero2 = "Disco";
                                    }else {
                                        frenodelantero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("frenotrasero")) {
                                    String TipoFreno = documentSnapshot.get("frenotrasero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera2 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero2 = "Disco";
                                    }else {
                                        frenotrasero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("potenciamaxima")) {
                                    potencia = documentSnapshot.get("potenciamaxima").toString();
                                    potencia2 = buscarNumero(potencia);
                                    txtPotencia2.setText(potencia2 + " HP");
                                }
                                if (documentSnapshot.contains("cilindraje")) {
                                    cilindraje2 = documentSnapshot.get("cilindraje").toString();
                                    cilindrajeValor2 = buscarNumero(cilindraje2);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje2);
                                    if (matcher.find()) {
                                        txtCilindraje2.setText(cilindraje2.toUpperCase());
                                    } else {
                                        txtCilindraje2.setText(cilindraje2 + " CC");
                                    }
                                }
                                if (documentSnapshot.contains("torquemaximo")) {
                                    torque = documentSnapshot.get("torquemaximo").toString();
                                    torque2 = buscarNumero(torque);
                                    txtTorque2.setText(torque2 + " Nm");
                                }
                                if (documentSnapshot.contains("peso")) {
                                    peso = documentSnapshot.get("peso").toString();
                                    peso2 = buscarNumero(peso);
                                    txtPeso2.setText(peso2 + " KG");
                                }
                                if (documentSnapshot.contains("sistemadealimentacion")) {
                                    sistemaAlimentacion2 = documentSnapshot.get("sistemadealimentacion").toString();
                                    Fi2 = ABSoSisAlimen(sistemaAlimentacion2);
                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon2 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());
                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima2 = (int) dato;
                                    txtVelMaxima2.setText(velocidadMaxima2 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto2);
                                    imgMoto2.setPadding(18,18,18,18);
                                }
                                if (documentSnapshot.contains("arranque")) {
                                    arranque2 = documentSnapshot.get("arranque").toString();
                                    arranqueElectrico2 = tipoArranque(arranque2);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor2 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }
                            if (fabricante.equals("HERO")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre2 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto2.setText(nombre2);
                                    btnMotoNombre2.setText(nombre2);
                                    ABS2 = ABSoSisAlimen(nombre2);
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad2 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("delanteros")) {
                                    String TipoFreno = documentSnapshot.get("delanteros").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero2 = "Disco";
                                    }else {
                                        frenodelantero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }else {

                                    }
                                }
                                if (documentSnapshot.contains("traseros")) {
                                    String TipoFreno = documentSnapshot.get("traseros").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera2 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero2 = "Disco";
                                    }else {
                                        frenotrasero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("maxpotencia")) {
                                    potencia = documentSnapshot.get("maxpotencia").toString();
                                    potencia2 = buscarNumero(potencia);
                                    txtPotencia2.setText(potencia1 + " HP");
                                }
                                if (documentSnapshot.contains("cilindraje")) {
                                    cilindraje2 = documentSnapshot.get("cilindraje").toString();
                                    cilindrajeValor2 = buscarNumero(cilindraje2);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje2);
                                    if (matcher.find()) {
                                        txtCilindraje2.setText(cilindraje2.toUpperCase());
                                    } else {
                                        txtCilindraje2.setText(cilindraje2 + " CC");
                                    }
                                }
                                if (documentSnapshot.contains("maxtorque")) {
                                    torque = documentSnapshot.get("maxtorque").toString();
                                    torque2 = buscarNumero(torque);
                                    txtTorque2.setText(torque2 + " Nm");
                                }
                                if (documentSnapshot.contains("pesosincarga")) {
                                    peso = documentSnapshot.get("pesosincarga").toString();
                                    peso2 = buscarNumero(peso);
                                    txtPeso2.setText(peso2 + " KG");
                                }
                                if (!documentSnapshot.contains("alimentacion")) {
                                    Fi2 = false;
                                }else {
                                    sistemaAlimentacion1 = documentSnapshot.get("alimentacion").toString();
                                    Fi2 = ABSoSisAlimen(sistemaAlimentacion1);
                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon2 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());
                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima2 = (int) dato;
                                    txtVelMaxima2.setText(velocidadMaxima2 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto2);
                                    imgMoto2.setPadding(18,18,18,18);
                                }
                                if (documentSnapshot.contains("arranque")) {
                                    arranque2 = documentSnapshot.get("arranque").toString();
                                    arranqueElectrico2 = tipoArranque(arranque2);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor2 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }
                            if (fabricante.equals("SUZUKI")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre2 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto2.setText(nombre2);
                                    btnMotoNombre2.setText(nombre2);
                                    ABS2 = ABSoSisAlimen(nombre2);
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad2 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("frenodelantero")) {
                                    String TipoFreno = documentSnapshot.get("frenodelantero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero2 = "Disco";
                                    }else {
                                        frenodelantero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("frenotrasero")) {
                                    String TipoFreno = documentSnapshot.get("frenotrasero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera2 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero2 = "Disco";
                                    }else {
                                        frenotrasero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("potencia")) {
                                    potencia = documentSnapshot.get("potencia").toString();
                                    potencia2 = buscarNumero(potencia);
                                    txtPotencia2.setText(potencia2 + " HP");
                                }
                                if (documentSnapshot.contains("cilindraje")) {
                                    cilindraje2 = documentSnapshot.get("cilindraje").toString();
                                    cilindrajeValor2 = buscarNumero(cilindraje2);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje2);
                                    if (matcher.find()) {
                                        txtCilindraje2.setText(cilindraje2.toUpperCase());
                                    } else {
                                        txtCilindraje2.setText(cilindraje2 + " CC");
                                    }
                                }
                                if (documentSnapshot.contains("torque")) {
                                    torque = documentSnapshot.get("torque").toString();
                                    torque2 = buscarNumero(torque);
                                    txtTorque2.setText(torque2 + " Nm");
                                }
                                if (documentSnapshot.contains("pesoenseco")) {
                                    peso = documentSnapshot.get("pesoenseco").toString();
                                    peso2 = buscarNumero(peso);
                                    txtPeso2.setText(peso2 + " KG");
                                }
                                if (documentSnapshot.contains("sistemadealimentaciondecombustible")) {
                                    sistemaAlimentacion2 = documentSnapshot.get("sistemadealimentaciondecombustible").toString();
                                    Fi2 = ABSoSisAlimen(sistemaAlimentacion2);
                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon2 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());
                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima2 = (int) dato;
                                    txtVelMaxima2.setText(velocidadMaxima2 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto2);
                                    imgMoto2.setPadding(18,18,18,18);
                                }
                                if (documentSnapshot.contains("sistemadearranque")) {
                                    arranque2 = documentSnapshot.get("sistemadearranque").toString();
                                    arranqueElectrico2 = tipoArranque(arranque2);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor2 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }
                            if (fabricante.equals("HONDA")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre2 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto2.setText(nombre2);
                                    btnMotoNombre2.setText(nombre2);
                                    ABS2 = ABSoSisAlimen(nombre2);
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad2 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("frenodelantero")) {
                                    String TipoFreno = documentSnapshot.get("frenodelantero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero2 = "Disco";
                                    }else {
                                        frenodelantero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("frenotrasero")) {
                                    String TipoFreno = documentSnapshot.get("frenotrasero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera2 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero2 = "Disco";
                                    }else {
                                        frenotrasero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("potenciamaxima")) {
                                    potencia = documentSnapshot.get("potenciamaxima").toString();
                                    potencia2 = buscarNumero(potencia);
                                    txtPotencia2.setText(potencia2 + " HP");
                                }
                                if (documentSnapshot.contains("cilindraje")) {
                                    cilindraje2 = documentSnapshot.get("cilindraje").toString();
                                    cilindrajeValor2 = buscarNumero(cilindraje2);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje2);
                                    if (matcher.find()) {
                                        txtCilindraje2.setText(cilindraje2.toUpperCase());
                                    } else {
                                        txtCilindraje2.setText(cilindraje2 + " CC");
                                    }
                                }
                                if (documentSnapshot.contains("torquemaximo")) {
                                    torque = documentSnapshot.get("torquemaximo").toString();
                                    torque2 = buscarNumero(torque);
                                    txtTorque2.setText(torque2 + " Nm");
                                }
                                if (documentSnapshot.contains("peso")) {
                                    peso = documentSnapshot.get("peso").toString();
                                    peso2 = buscarNumero(peso);
                                    txtPeso2.setText(peso2 + " KG");
                                }
                                if (documentSnapshot.contains("sistemadealimentacion")) {
                                    sistemaAlimentacion1 = documentSnapshot.get("sistemadealimentacion").toString();
                                    Fi2 = ABSoSisAlimen(sistemaAlimentacion1);
                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon2 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());
                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima2 = (int) dato;
                                    txtVelMaxima2.setText(velocidadMaxima2 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto2);
                                    imgMoto2.setPadding(18,18,18,18);
                                }
                                if (documentSnapshot.contains("tipodearranque")) {
                                    arranque2 = documentSnapshot.get("tipodearranque").toString();
                                    arranqueElectrico2 = tipoArranque(arranque2);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor2 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }
                            if (fabricante.equals("BAJAJ")){
                                if (documentSnapshot.contains("nombreMoto")) {
                                    nombre2 = documentSnapshot.get("nombreMoto").toString();
                                    txtNombreMoto2.setText(nombre2);
                                    btnMotoNombre2.setText(nombre2);
                                    ABS2 = ABSoSisAlimen(nombre2);
                                }
                                if (documentSnapshot.contains("carpeta3")) {
                                    especialidad2 = documentSnapshot.get("carpeta3").toString();
                                }
                                if (documentSnapshot.contains("frenodelantero")) {
                                    String TipoFreno = documentSnapshot.get("frenodelantero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    if (disco){
                                        frenodelantero2 = "Disco";
                                    }else {
                                        frenodelantero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("frenotrasero")) {
                                    String TipoFreno = documentSnapshot.get("frenotrasero").toString();
                                    boolean disco = tipoDeFreno(TipoFreno);
                                    ABSLlantaTrasera2 = ABSoSisAlimen(TipoFreno); // abs en la llanta trasera
                                    if (disco){
                                        frenotrasero2 = "Disco";
                                    }else {
                                        frenotrasero2 = "Tambor";
                                    }
                                    if (!ABS2){
                                        ABS2 = ABSoSisAlimen(TipoFreno);
                                    }
                                }
                                if (documentSnapshot.contains("potenciamaxima")) {
                                    potencia = documentSnapshot.get("potenciamaxima").toString();
                                    potencia2 = buscarNumero(potencia);
                                    txtPotencia2.setText(potencia2 + " HP");
                                }
                                if (documentSnapshot.contains("cilindrada")) {
                                    cilindraje2 = documentSnapshot.get("cilindrada").toString();
                                    cilindrajeValor2 = buscarNumero(cilindraje2);
                                    Pattern patron = Pattern.compile(".*[a-zA-Z].*");
                                    Matcher matcher = patron.matcher(cilindraje2);
                                    if (matcher.find()) {
                                        txtCilindraje2.setText(cilindraje2.toUpperCase());
                                    } else {
                                        txtCilindraje2.setText(cilindraje2 + " CC");
                                    }
                                }
                                if (documentSnapshot.contains("torquemaximo")) {
                                    torque = documentSnapshot.get("torquemaximo").toString();
                                    torque2 = buscarNumero(torque);
                                    txtTorque2.setText(torque2 + " Nm");
                                }
                                if (documentSnapshot.contains("pesoenseco")) {
                                    peso = documentSnapshot.get("pesoenseco").toString();
                                    peso2 = buscarNumero(peso);
                                    txtPeso2.setText(peso2 + " KG");
                                }
                                if (documentSnapshot.contains("sistemadealimentacion")) {
                                    sistemaAlimentacion1 = documentSnapshot.get("sistemadealimentacion").toString();
                                    Fi2 = ABSoSisAlimen(sistemaAlimentacion1);
                                }
                                if (documentSnapshot.contains("consumoPorGalon")) {
                                    consumoPorGalon2 = Integer.parseInt(documentSnapshot.get("consumoPorGalon").toString());
                                }
                                if (documentSnapshot.contains("velocidadMaxima")) {
                                    double dato = Double.parseDouble(documentSnapshot.get("velocidadMaxima").toString());
                                    velocidadMaxima2 = (int) dato;
                                    txtVelMaxima2.setText(velocidadMaxima2 + " KM/H");
                                }
                                if (documentSnapshot.contains("imagenes")) {
                                    listaImagenes = (List<String>) documentSnapshot.get("imagenes");
                                    String imagen = listaImagenes.get(0);
                                    Picasso.get().load(imagen).into(imgMoto2);
                                    imgMoto2.setPadding(18,18,18,18);
                                }
                                if (documentSnapshot.contains("arranque")) {
                                    arranque2 = documentSnapshot.get("arranque").toString();
                                    arranqueElectrico2 = tipoArranque(arranque2);
                                }
                                if (documentSnapshot.contains("nuevoValorDescuento")) {
                                    valor2 = Integer.parseInt(documentSnapshot.get("nuevoValorDescuento").toString());
                                }
                            }
                            barras();
                            comparacion();
                            mostrarDatosSinBarra();
                        }
                    }
                }
            }
        });
    }
    private void barras(){
        Random random = new Random();
        progressBar1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (potencia1 > potencia2){
                    datoMaximo = (int) potencia1;
                }else {
                    datoMaximo = (int) potencia2;
                }
                randomInt = random.nextInt((int) datoMaximo);
                startProgressAnimation(progressBar1, potencia1, datoMaximo+randomInt);
            }
        });
        progressBar2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar2, potencia2, datoMaximo+randomInt);
            }
        });
        progressBar3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (torque1 > torque2){
                    datoMaximo = (int) torque1;
                }else {
                    datoMaximo = (int) torque2;
                }
                randomInt = random.nextInt((int) datoMaximo);
                startProgressAnimation(progressBar3, torque1, datoMaximo+randomInt);
            }
        });
        progressBar4.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar4, torque2, datoMaximo+randomInt);
            }
        });
        progressBar5.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (peso1 > peso2){
                    datoMaximo = (int) peso1;
                }else {
                    datoMaximo = (int) peso2;
                }
                randomInt = random.nextInt((int) datoMaximo);
                startProgressAnimation(progressBar5, peso1, datoMaximo+randomInt);
            }
        });
        progressBar6.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar6, peso2, datoMaximo+randomInt);
            }
        });
        progressBar7.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (cilindrajeValor1 > cilindrajeValor2){
                    datoMaximo = (int) cilindrajeValor1;
                }else {
                    datoMaximo = (int) cilindrajeValor2;
                }
                randomInt = random.nextInt((int) datoMaximo);
                startProgressAnimation(progressBar7, cilindrajeValor1, datoMaximo+randomInt);
            }
        });
        progressBar8.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar8, cilindrajeValor2, datoMaximo+randomInt);
            }
        });
        progressBar9.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (velocidadMaxima1 > velocidadMaxima2){
                    datoMaximo = velocidadMaxima1;
                }else {
                    datoMaximo = velocidadMaxima2;
                }
                //el +1 es para evitar error en caso de que traiga valor 0
                randomInt = random.nextInt(datoMaximo+1);
                startProgressAnimation(progressBar9, velocidadMaxima1, datoMaximo+randomInt);
            }
        });
        progressBar10.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startProgressAnimation(progressBar10, velocidadMaxima2, datoMaximo+randomInt);
            }
        });
    }

    private void mostrarDatosSinBarra(){
        //simbolos
        String chulo = "✔";
        String equis = "✖";

        SpannableString si1 = new SpannableString(chulo + " " + nombre1);
        ForegroundColorSpan colorVerde = new ForegroundColorSpan(Color.GREEN); // Establece el color verde
        si1.setSpan(colorVerde, 0, chulo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString no1 = new SpannableString(equis + " " + nombre1);
        ForegroundColorSpan colorRojo = new ForegroundColorSpan(Color.RED); // Establece el color verde
        no1.setSpan(colorRojo, 0, equis.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString si2 = new SpannableString(chulo + " " + nombre2);
        si2.setSpan(colorVerde, 0, chulo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        SpannableString no2 = new SpannableString(equis + " "  + nombre2);
        no2.setSpan(colorRojo, 0, equis.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //abs
        if (ABS1){
            txtABS1.setText(si1);
        }else {
            txtABS1.setText(no1);
        }
        if (ABS2){
            txtABS2.setText(si2);
        }else {
            txtABS2.setText(no2);
        }
        //SISTEMA DE ALIMENTACION
        String fuel1="";
        String fuel2="";
        if (Fi1){
            fuel1 = "Sistema de inyección de combustible (FI)";
        }else {
            fuel1 = "Sistema de carburación";
        }
        if (Fi2){
            fuel2 = "Sistema de inyección de combustible (FI)";
        }else {
            fuel2 = "Sistema de carburación";
        }
        SpannableString alimentacion1 = new SpannableString(chulo + " " + fuel1 + " - " + nombre1);
        alimentacion1.setSpan(colorVerde, 0, chulo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString alimentacion2 = new SpannableString(chulo + " " + fuel2 + " - " + nombre2);
        alimentacion2.setSpan(colorVerde, 0, chulo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtFI1.setText(alimentacion1);
        txtFI2.setText(alimentacion2);

        //consumo
        SpannableString consumo1 = new SpannableString(chulo + " " + consumoPorGalon1 + "KM - " + nombre1);
        consumo1.setSpan(colorVerde, 0, chulo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString consumo2 = new SpannableString(chulo + " " + consumoPorGalon2 + "KM - " + nombre2);
        consumo2.setSpan(colorVerde, 0, chulo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtConsumoXGalon1.setText(consumo1);
        txtConsumoXGalon2.setText(consumo2);

        //FRENOS
        SpannableString frenos1 = new SpannableString(chulo + " " + frenodelantero1 + " - " + nombre1);
        frenos1.setSpan(colorVerde, 0, chulo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString frenos2 = new SpannableString(chulo + " " + frenodelantero2 + " - " + nombre2);
        frenos2.setSpan(colorVerde, 0, chulo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtFrenos1.setText(frenos1);
        txtFrenos2.setText(frenos2);


        //encendido
        SpannableString Arranque1 = new SpannableString(chulo + " " + arranque1 + " - " + nombre1);
        Arranque1.setSpan(colorVerde, 0, chulo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString Arranque2 = new SpannableString(chulo + " " + arranque2 + " - " + nombre2);
        Arranque2.setSpan(colorVerde, 0, chulo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtEncendido1.setText(Arranque1);
        txtEncendido2.setText(Arranque2);

        //precio
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        int precioMostrar1 = (int) valor1;
        int precioMostrar2 = (int) valor2;
        DecimalFormat formato = new DecimalFormat("#,###", symbols);
        String numero1 = formato.format(precioMostrar1);
        String numero2 = formato.format(precioMostrar2);

        txtPrecio1.setText("$" + numero1 + " - " + nombre1);
        txtPrecio2.setText("$" + numero2 + " - " + nombre2);
    }
    private void comparacion(){
        //colores
        int colorClaro1 = ContextCompat.getColor(getContext(), R.color.verdeTransparente);
        int colorClaro2 = ContextCompat.getColor(getContext(), R.color.redTransparente);

        Drawable background = lnlcontenedorInfo.getBackground();

        if (background instanceof ColorDrawable) {
            color = ((ColorDrawable) background).getColor();
        }

        if (color == colorClaro1){
            mostrarTextos();
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
            if (consumoPorGalon1 > consumoPorGalon2){
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


                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                symbols.setGroupingSeparator('.');
                symbols.setDecimalSeparator(',');
                int precioMostrar = (int) valor1;
                DecimalFormat formato = new DecimalFormat("#,###", symbols);
                String numeroFormato = formato.format(precioMostrar);
                txtOption7.setText("✔  Es un " + porcentajeFormateado + "% mas barata con un precio de: $" + numeroFormato);
            }else {
                txtOption7.setVisibility(View.GONE);
            }
            if (!aho1.isEmpty() && aho2 == ""){
                txtOption8.setText("✔  Cuenta con el control de luces AHO");
            }else {
                txtOption8.setVisibility(View.GONE);
            }
            if (!asc1.isEmpty() && asc2 == ""){
                txtOption9.setText("✔  Cuenta con el Control Automatico de Estabilidad ASC");
            }else {
                txtOption9.setVisibility(View.GONE);
            }
            //valida si ambos frenos son disco
            if ((frenodelantero1.equals("Disco") && frenotrasero1.equals("Disco")) &&
                    (!frenodelantero2.equals("Disco") || !frenotrasero2.equals("Disco"))) {
                // Mostrar mensaje cuando frenodelantero1 y frenotrasero1 son "Disco"
                // y al menos una de frenodelantero2 o frenotrasero2 no es "Disco"
                txtOption10.setText("✔  Cuenta con freno de Disco en ambas llantas");
            } else if ((frenodelantero1.equals("Disco") || frenotrasero1.equals("Disco")) &&
                    (!frenodelantero2.equals("Disco") || !frenotrasero2.equals("Disco"))) {
                // Mostrar mensaje cuando al menos una de frenodelantero1 o frenotrasero1 es "Disco"
                // y al menos una de frenodelantero2 o frenotrasero2 no es "Disco"
                txtOption10.setText("✔  Cuenta con freno de Disco");
            } else {
                // En caso contrario, ocultar el TextView
                txtOption10.setVisibility(View.GONE);
            }
            if (!qss1.isEmpty() && qss2 == ""){
                txtOption11.setText("✔  Cuenta con teconologia QSS para realizar cambios de marcha sin accionar embrague");
            }else {
                txtOption11.setVisibility(View.GONE);
            }
            if (!tcs1.isEmpty() && tcs2 == ""){
                txtOption12.setText("✔  Cuenta con teconologia de control de tracción TCS");
            }else {
                txtOption12.setVisibility(View.GONE);
            }
            if (Fi1 && !Fi2){
                txtOption13.setText("✔  Posee Sistema de inyección por combustible (FI)");
            }else {
                txtOption13.setVisibility(View.GONE);
            }
            if (especialidad1.equals("TRABAJO")){
                txtOption14.setText("✔  Ideal para el trabajo en zonas urbanas");
            }else if (especialidad1.equals("TODOTERRENO")){
                txtOption14.setText("✔  Ideal para todo tipo de terreno");
            }else if (especialidad1.equals("URBANA")){
                txtOption14.setText("✔  Ideal para zonas urbanas");
            }else if (especialidad1.equals("DEPORTIVA")){
                txtOption14.setText("✔  Óptima para la conducción deportiva en entornos urbanos");
            }else {
                txtOption14.setVisibility(View.GONE);
            }
        }
        //moto 2
        if (color == colorClaro2){
            mostrarTextos();
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
            if (consumoPorGalon2 > consumoPorGalon1){
                txtOption5.setText("✔  Tiene un menor consumo de gasolina con un promedio de " + consumoPorGalon2 + "KM por galon");
            }else {
                txtOption5.setVisibility(View.GONE);
            }
            if (arranqueElectrico2 && !arranqueElectrico1){
                txtOption6.setText("✔  Cuenta con encendido eléctrico");
            }else {
                txtOption6.setVisibility(View.GONE);
            }
            if (valor1 > valor2){
                double diferencia = valor1 - valor2;
                double porcentaje = (diferencia / Math.min(valor1, valor2)) * 100;
                String porcentajeFormateado = String.format("%.2f", porcentaje);
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                symbols.setGroupingSeparator('.');
                symbols.setDecimalSeparator(',');
                int precioMostrar = (int) valor2;
                DecimalFormat formato = new DecimalFormat("#,###", symbols);
                String numeroFormato = formato.format(precioMostrar);
                txtOption7.setText("✔  Es un " + porcentajeFormateado + "% mas barata con un precio de: $" + numeroFormato);
            }else {
                txtOption7.setVisibility(View.GONE);
            }
            if (!aho2.isEmpty() && aho1 == ""){
                txtOption8.setText("✔  Cuenta con el control de luces AHO");
            }else {
                txtOption8.setVisibility(View.GONE);
            }
            if (!asc2.isEmpty() && asc1 == ""){
                txtOption9.setText("✔  Cuenta con el Control Automatico de Estabilidad ASC");
            }else {
                txtOption9.setVisibility(View.GONE);
            }
            if ((frenodelantero2.equals("Disco") && frenotrasero2.equals("Disco")) &&
                    (!frenodelantero1.equals("Disco") || !frenotrasero1.equals("Disco"))) {
                // Mostrar mensaje cuando frenodelantero2 y frenotrasero2 son "Disco"
                // y al menos una de frenodelantero1 o frenotrasero1 no es "Disco"
                txtOption10.setText("✔  Cuenta con freno de Disco en ambas llantas");
            } else if ((frenodelantero2.equals("Disco") || frenotrasero2.equals("Disco")) &&
                    (!frenodelantero1.equals("Disco") || !frenotrasero1.equals("Disco"))) {
                // Mostrar mensaje cuando al menos una de frenodelantero2 o frenotrasero2 es "Disco"
                // y al menos una de frenodelantero1 o frenotrasero1 no es "Disco"
                txtOption10.setText("✔  Cuenta con freno de Disco");
            } else {
                // En caso contrario, ocultar el TextView
                txtOption10.setVisibility(View.GONE);
            }
            if (!qss2.isEmpty() && qss1 == ""){
                txtOption11.setText("✔  Cuenta con teconologia QSS para realizar cambios de marcha sin accionar embrague");
            }else {
                txtOption11.setVisibility(View.GONE);
            }
            if (!tcs2.isEmpty() && tcs1 == ""){
                txtOption12.setText("✔  Cuenta con teconologia de control de tracción TCS");
            }else {
                txtOption12.setVisibility(View.GONE);
            }
            if (Fi2 && !Fi1){
                txtOption13.setText("✔  Posee Sistema de inyección por combustible (FI)");
            }else {
                txtOption13.setVisibility(View.GONE);
            }
            if (especialidad2.equals("TRABAJO")){
                txtOption14.setText("✔  Ideal para el trabajo en zonas urbanas");
            }else if (especialidad2.equals("TODOTERRENO")){
                txtOption14.setText("✔  Ideal para todo tipo de terreno");
            }else if (especialidad2.equals("URBANA")){
                txtOption14.setText("✔  Ideal para zonas urbanas");
            }else if (especialidad2.equals("SEMIAUTOMATICAS")){
                txtOption14.setText("✔  Ideal para zonas urbanas");
            }else if (especialidad2.equals("AUTOMATICAS")){
                txtOption14.setText("✔  Ideal para zonas urbanas");
            }else if (especialidad2.equals("ADVENTURE")){
                txtOption14.setText("✔  Ideal para viajar");
            }else if (especialidad2.equals("DEPORTIVA")){
                txtOption14.setText("✔  Óptima para la conducción deportiva en entornos urbanos");
            }else {
                txtOption14.setVisibility(View.GONE);
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
        txtOption9.setVisibility(View.VISIBLE);
        txtOption10.setVisibility(View.VISIBLE);
        txtOption11.setVisibility(View.VISIBLE);
        txtOption12.setVisibility(View.VISIBLE);
        txtOption13.setVisibility(View.VISIBLE);
        txtOption14.setVisibility(View.VISIBLE);
    }

    private void startProgressAnimation(ProgressBar progressBar, double dato, double porcentajeTotal) {
        //se encarga de hacer mas lento el llenado de las barras y da un efecto visual bonito
        double porcentaje = (dato * 100) / (porcentajeTotal);
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
    private boolean ABSoSisAlimen(String args) {
        Pattern patron = Pattern.compile("Inyección|electrónica|fi|full|ABS|IBS", Pattern.CASE_INSENSITIVE);

        // Buscar coincidencias en el texto
        Matcher matcher = patron.matcher(args);

        // Verificar si se encontraron coincidencias
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
    public boolean tipoDeFreno(String args) {

        Pattern patron = Pattern.compile("Disco|disco", Pattern.CASE_INSENSITIVE);

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
        Pattern patron = Pattern.compile("Eléctrico|Electrico|electrónico|eléctrica|batería|Eléctrico y Pedal|automático", Pattern.CASE_INSENSITIVE);

        // Buscar coincidencias en el texto
        Matcher matcher = patron.matcher(arranque);

        // Verificar si se encontraron coincidencias
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
    private static double buscarNumero(String texto) {
        Pattern patron = Pattern.compile("(\\d+[.,]?\\d*)(?=\\s*[a-zA-Z])");
        Matcher matcher = patron.matcher(texto);

        if (matcher.find()) {
            // Obtener el número encontrado
            String numero = matcher.group();

            // Reemplazar comas con puntos para que Double.parseDouble pueda analizar el número
            numero = numero.replace(',', '.');

            // Parsear el número a Double
            return Double.parseDouble(numero);
        }

        if (TextUtils.isEmpty(texto)) {
            // La cadena está vacía
            // Realiza acciones en caso de cadena vacía
        } else {
            // Remueve el punto decimal y luego verifica si el resto de la cadena son dígitos
            String stringWithoutDecimal = texto.replace(".", "");

            if (TextUtils.isDigitsOnly(stringWithoutDecimal)) {
                // La cadena contiene solo dígitos, por lo tanto, es numérica
                double numericValue = Double.parseDouble(texto);
                return numericValue;
                // Realiza acciones adicionales con el valor numérico
            }
        }
        return 0.0;
    }
}
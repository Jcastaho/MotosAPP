package com.straccion.motos_admin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.models.SliderItem;
import com.straccion.motos_admin.providers.PostProvider;

import java.util.ArrayList;
import java.util.List;

public class FichaTecnicaAdapters {

    TextView txtDato1;
    TextView txtDato1_1;
    TextView txtDato2;
    TextView txtDato2_1;
    View ViewHolder;
    Context contexto;
    String idDocument;
    String marcaMoto;
    PostProvider mPostProvider;
    ArrayList<Integer> ids1 = new ArrayList<>();
    ArrayList<Integer> ids2 = new ArrayList<>();
    ArrayList<String> txt1 = new ArrayList<>();
    ArrayList<String> txt2 = new ArrayList<>();
    int contador = 0;
    int tamano = 0;
    int idTextView1 = 0;
    int idTextView2 = 6000;

    int colorGris = Color.parseColor("#70D2D2D2");
    int colorBlanco = Color.parseColor("#0070D2D2");
    public FichaTecnicaAdapters (Context context, String id) {
        this.contexto = context;
        this.idDocument = id;
        mPostProvider = new PostProvider();
    }

    public void showData(LinearLayout  parentLinearLayout) {
        mPostProvider = new PostProvider();
        mPostProvider.getPostById(idDocument).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.contains("marcaMoto")) {
                        marcaMoto = documentSnapshot.get("marcaMoto").toString();
                    }

                    switch (marcaMoto) {
                        case "AUTECO":
                            if (documentSnapshot.getString("cilindraje") != null){
                                txt1.add("Cilindraje");
                                txt2.add(documentSnapshot.getString("cilindraje"));
                            }
                            if (documentSnapshot.getString("motor") != null){
                                txt1.add("Motor");
                                txt2.add(documentSnapshot.getString("motor"));
                            }
                            if (documentSnapshot.getString("torqueMaximo") != null){
                                txt1.add("Torque Maximo");
                                txt2.add(documentSnapshot.getString("torqueMaximo"));
                            }
                            if (documentSnapshot.getString("potenciaMaxima") != null){
                                txt1.add("Potencia Maxima");
                                txt2.add(documentSnapshot.getString("potenciaMaxima"));
                            }
                            if (documentSnapshot.getString("relacionCompresion") != null){
                                txt1.add("Relacion de Compresión");
                                txt2.add(documentSnapshot.getString("relacionCompresion"));
                            }
                            if (documentSnapshot.getString("SistemaAlimentacion") != null){
                                txt1.add("Sistema de Alimentación");
                                txt2.add(documentSnapshot.getString("SistemaAlimentacion"));
                            }
                            if (documentSnapshot.getString("diametroCarrera") != null){
                                txt1.add("Diametro de Carrera");
                                txt2.add(documentSnapshot.getString("diametroCarrera"));
                            }
                            if (documentSnapshot.getString("refrigeracion") != null){
                                txt1.add("Refrigeración");
                                txt2.add(documentSnapshot.getString("refrigeracion"));
                            }
                            if (documentSnapshot.getString("combustible") != null){
                                txt1.add("Combustible");
                                txt2.add(documentSnapshot.getString("combustible"));
                            }
                            if (documentSnapshot.getString("arranque") != null){
                                txt1.add("Arranque");
                                txt2.add(documentSnapshot.getString("arranque"));
                            }
                            if (documentSnapshot.getString("transmision") != null){
                                txt1.add("Transmisión");
                                txt2.add(documentSnapshot.getString("transmision"));
                            }
                            if (documentSnapshot.getString("capacidadTanque") != null){
                                txt1.add("Capacidad de Tanque");
                                txt2.add(documentSnapshot.getString("capacidadTanque"));
                            }
                            if (documentSnapshot.getString("suspensionDelantera") != null){
                                txt1.add("Suspensión Delantera");
                                txt2.add(documentSnapshot.getString("suspensionDelantera"));
                            }
                            if (documentSnapshot.getString("suspensionTrasera") != null){
                                txt1.add("Suspensión Trasera");
                                txt2.add(documentSnapshot.getString("suspensionTrasera"));
                            }
                            if (documentSnapshot.getString("frenoDelantero") != null){
                                txt1.add("Freno Delantero");
                                txt2.add(documentSnapshot.getString("frenoDelantero"));
                            }
                            if (documentSnapshot.getString("frenoTrasero") != null){
                                txt1.add("Freno Trasero");
                                txt2.add(documentSnapshot.getString("frenoTrasero"));
                            }
                            if (documentSnapshot.getString("llantaDelantera") != null){
                                txt1.add("Llanta Delantera");
                                txt2.add(documentSnapshot.getString("llantaDelantera"));
                            }
                            if (documentSnapshot.getString("llantaTrasera") != null){
                                txt1.add("Llanta Trasera");
                                txt2.add(documentSnapshot.getString("llantaTrasera"));
                            }
                            if (documentSnapshot.getString("rines") != null){
                                txt1.add("Rines");
                                txt2.add(documentSnapshot.getString("rines"));
                            }
                            if (documentSnapshot.getString("capacidadTanque") != null){
                                txt1.add("Capacidad de Tanque");
                                txt2.add(documentSnapshot.getString("capacidadTanque"));
                            }
                            if (documentSnapshot.getString("sistemaEncendido") != null){
                                txt1.add("Sistema de Encendido");
                                txt2.add(documentSnapshot.getString("sistemaEncendido"));
                            }
                            if (documentSnapshot.getString("largoTotal") != null){
                                txt1.add("Largo Total");
                                txt2.add(documentSnapshot.getString("largoTotal"));
                            }
                            if (documentSnapshot.getString("alturaTotal") != null){
                                txt1.add("Altura Total");
                                txt2.add(documentSnapshot.getString("alturaTotal"));
                            }
                            if (documentSnapshot.getString("anchoTotal") != null){
                                txt1.add("Ancho Total");
                                txt2.add(documentSnapshot.getString("anchoTotal"));
                            }
                            if (documentSnapshot.getString("distanciaEntreEjes") != null){
                                txt1.add("Distancia Entre Ejes");
                                txt2.add(documentSnapshot.getString("distanciaEntreEjes"));
                            }
                            if (documentSnapshot.getString("alturaSillin") != null){
                                txt1.add("Altura Sillin");
                                txt2.add(documentSnapshot.getString("alturaSillin"));
                            }
                            if (documentSnapshot.getString("pesoNeto") != null){
                                txt1.add("Peso Neto");
                                txt2.add(documentSnapshot.getString("pesoNeto"));
                            }

                            for (int i = 0; i < tamano; i++) {
                                TextView labelTextView = parentLinearLayout.findViewById(ids1.get(i));
                                labelTextView.setText(txt1.get(i));
                                TextView valueTextView = parentLinearLayout.findViewById(ids2.get(i));
                                valueTextView.setText(txt2.get(i));
                            }
                            break;
                        case "YAMAHA":
                            if (documentSnapshot.getString("cilindraje") != null && documentSnapshot.getString("cilindraje") != "--"){
                                txt1.add("Cilindraje");
                                txt2.add(documentSnapshot.getString("cilindraje"));
                            }
                            if (documentSnapshot.getString("potenciamaxima") != null && documentSnapshot.getString("potenciaMaxima") != "--"){
                                txt1.add("Potencia Maxima");
                                txt2.add(documentSnapshot.getString("potenciamaxima"));
                            }
                            if (documentSnapshot.getString("torquemaximo") != null && documentSnapshot.getString("torqueMaximo") != "--"){
                                txt1.add("Torque Maximo");
                                txt2.add(documentSnapshot.getString("torquemaximo"));
                            }
                            if (documentSnapshot.getString("tipodemotor") != null && documentSnapshot.getString("tipoMotor") != "--"){
                                txt1.add("Tipo de Motor");
                                txt2.add(documentSnapshot.getString("tipodemotor"));
                            }
                            if (documentSnapshot.getString("largototal") != null && documentSnapshot.getString("largoTotal") != "--"){
                                txt1.add("Largo Total");
                                txt2.add(documentSnapshot.getString("largototal"));
                            }
                            if (documentSnapshot.getString("anchototal") != null && documentSnapshot.getString("anchoTotal") != "--"){
                                txt1.add("Ancho Total");
                                txt2.add(documentSnapshot.getString("anchototal"));
                            }
                            if (documentSnapshot.getString("alturatotal") != null && documentSnapshot.getString("alturaTotal") != "--"){
                                txt1.add("Altura Total");
                                txt2.add(documentSnapshot.getString("alturatotal"));
                            }
                            if (documentSnapshot.getString("alturaalasiento") != null && documentSnapshot.getString("alturaAsiento") != "--"){
                                txt1.add("Altura del Asiento");
                                txt2.add(documentSnapshot.getString("alturaalasiento"));
                            }
                            if (documentSnapshot.getString("distanciaentreejes") != null && documentSnapshot.getString("distanciaEntreEjes") != "--"){
                                txt1.add("Distancia EntreEjes");
                                txt2.add(documentSnapshot.getString("distanciaentreejes"));
                            }
                            if (documentSnapshot.getString("distanciaminimadelpiso") != null && documentSnapshot.getString("distanciaMinimaPiso") != "--"){
                                txt1.add("Distancia Minima del Piso");
                                txt2.add(documentSnapshot.getString("distanciaminimadelpiso"));
                            }
                            if (documentSnapshot.getString("tipodelubricacion") != null && documentSnapshot.getString("tipoLubricacion") != "--"){
                                txt1.add("Tipo de Lubricación");
                                txt2.add(documentSnapshot.getString("tipodelubricacion"));
                            }
                            if (documentSnapshot.getString("bateria") != null && documentSnapshot.getString("bateria") != "--"){
                                txt1.add("Batería");
                                txt2.add(documentSnapshot.getString("bateria"));
                            }
                            if (documentSnapshot.getString("disposiciondeloscilindros") != null && documentSnapshot.getString("disposiciondeCilindros") != "--"){
                                txt1.add("Disposición de Cilindros");
                                txt2.add(documentSnapshot.getString("disposiciondeloscilindros"));
                            }
                            if (documentSnapshot.getString("diametroporcarrera") != null && documentSnapshot.getString("diametroPorCarrera") != "--"){
                                txt1.add("Diametro Por Carrera");
                                txt2.add(documentSnapshot.getString("diametroporcarrera"));
                            }
                            if (documentSnapshot.getString("relaciondecompresion") != null && documentSnapshot.getString("relacionCompresion") != "--"){
                                txt1.add("Relación de Compresión");
                                txt2.add(documentSnapshot.getString("relaciondecompresion"));
                            }
                            if (documentSnapshot.getString("arranque") != null && documentSnapshot.getString("arranque") != "--"){
                                txt1.add("Arranque");
                                txt2.add(documentSnapshot.getString("arranque"));
                            }
                            if (documentSnapshot.getString("sistemadealimentacion") != null && documentSnapshot.getString("sistemaAlimentacion") != "--"){
                                txt1.add("Sistema de Alimentación");
                                txt2.add(documentSnapshot.getString("sistemadealimentacion"));
                            }
                            if (documentSnapshot.getString("capacidaddecombustible") != null && documentSnapshot.getString("capacidadCombustible") != "--"){
                                txt1.add("Capacidad de Combustible");
                                txt2.add(documentSnapshot.getString("capacidaddecombustible"));
                            }
                            if (documentSnapshot.getString("encendido") != null && documentSnapshot.getString("encendido") != "--"){
                                txt1.add("Encendido");
                                txt2.add(documentSnapshot.getString("encendido"));
                            }
                            if (documentSnapshot.getString("capacidadbateria") != null && documentSnapshot.getString("capacidadBateria") != "--"){
                                txt1.add("Capacidad de Batería");
                                txt2.add(documentSnapshot.getString("capacidadbateria"));
                            }
                            if (documentSnapshot.getString("sistemadereduccionprimaria") != null && documentSnapshot.getString("sistemadeReduccionPrimaria") != "--"){
                                txt1.add("Sistema de Reducción Primaria");
                                txt2.add(documentSnapshot.getString("sistemadereduccionprimaria"));
                            }
                            if (documentSnapshot.getString("relaciondereduccionprimaria") != null && documentSnapshot.getString("relaciondeReduccionPrimaria") != "--"){
                                txt1.add("Relación de Reducción Primaria");
                                txt2.add(documentSnapshot.getString("relaciondereduccionprimaria"));
                            }
                            if (documentSnapshot.getString("sistemadereduccionsecundaria") != null && documentSnapshot.getString("sistemadeReduccionSecundaria") != "--"){
                                txt1.add("Sistema de Reducción Secundaria");
                                txt2.add(documentSnapshot.getString("sistemadereduccionsecundaria"));
                            }
                            if (documentSnapshot.getString("relaciondereduccionsecundaria") != null && documentSnapshot.getString("relaciondeReduccionSecundaria") != "--"){
                                txt1.add("Relación de Reducción Secundaria");
                                txt2.add(documentSnapshot.getString("relaciondereduccionsecundaria"));
                            }
                            if (documentSnapshot.getString("tipodeembrague") != null && documentSnapshot.getString("tipoEmbrague") != "--"){
                                txt1.add("Tipo de Embrague");
                                txt2.add(documentSnapshot.getString("tipodeembrague"));
                            }
                            if (documentSnapshot.getString("tipodetransmision") != null && documentSnapshot.getString("tipoTransmision") != "--"){
                                txt1.add("Tipo de Transmisión");
                                txt2.add(documentSnapshot.getString("tipodetransmision"));
                            }
                            if (documentSnapshot.getString("relaciondetransmisionen1ra") != null && documentSnapshot.getString("relacionTransmision1ra") != "--"){
                                txt1.add("Relación Transmision en 1ra");
                                txt2.add(documentSnapshot.getString("relaciondetransmisionen1ra"));
                            }
                            if (documentSnapshot.getString("relaciondetransmisionen2da") != null && documentSnapshot.getString("relacionTransmision2da") != "--"){
                                txt1.add("Relación Transmision en 2da");
                                txt2.add(documentSnapshot.getString("relaciondetransmisionen2da"));
                            }
                            if (documentSnapshot.getString("relaciondetransmisionen3ra") != null && documentSnapshot.getString("relacionTransmision3ra") != "--"){
                                txt1.add("Relación Transmision en 3ra");
                                txt2.add(documentSnapshot.getString("relaciondetransmisionen3ra"));
                            }
                            if (documentSnapshot.getString("relaciondetransmisionen4ta") != null && documentSnapshot.getString("relacionTransmision4ta") != "--"){
                                txt1.add("Relación Transmision en 4ta");
                                txt2.add(documentSnapshot.getString("relaciondetransmisionen4ta"));
                            }
                            if (documentSnapshot.getString("relaciondetransmisionen5ta") != null && documentSnapshot.getString("relacionTransmision5ta") != "--"){
                                txt1.add("Relación Transmision en 5ta");
                                txt2.add(documentSnapshot.getString("relaciondetransmisionen5ta"));
                            }
                            if (documentSnapshot.getString("relaciondetransmisionen6ta") != null && documentSnapshot.getString("relacionTransmision6ta") != "--"){
                                txt1.add("Relación Transmision en 6ta");
                                txt2.add(documentSnapshot.getString("relaciondetransmisionen6ta"));
                            }
                            if (documentSnapshot.getString("relaciondeengranajes") != null && documentSnapshot.getString("relacionEngranajes") != "--"){
                                txt1.add("Relación de Engranajes");
                                txt2.add(documentSnapshot.getString("relaciondeengranajes"));
                            }
                            if (documentSnapshot.getString("tipodechasis") != null && documentSnapshot.getString("tipoChasis") != "--"){
                                txt1.add("Tipo de Chasis");
                                txt2.add(documentSnapshot.getString("tipodechasis"));
                            }
                            if (documentSnapshot.getString("inclinacion") != null && documentSnapshot.getString("inclinacion") != "--"){
                                txt1.add("Inclinación");
                                txt2.add(documentSnapshot.getString("inclinacion"));
                            }
                            if (documentSnapshot.getString("avance") != null && documentSnapshot.getString("avance") != "--"){
                                txt1.add("Avance");
                                txt2.add(documentSnapshot.getString("avance"));
                            }
                            if (documentSnapshot.getString("ruedadelantera") != null && documentSnapshot.getString("ruedaDelantera") != "--"){
                                txt1.add("Rueda Delantera");
                                txt2.add(documentSnapshot.getString("ruedadelantera"));
                            }
                            if (documentSnapshot.getString("ruedatrasera") != null && documentSnapshot.getString("ruedaTrasera") != "--"){
                                txt1.add("Rueda Trasera");
                                txt2.add(documentSnapshot.getString("ruedatrasera"));
                            }
                            if (documentSnapshot.getString("frenodelantero") != null && documentSnapshot.getString("frenoDelantero") != "--"){
                                txt1.add("Freno Delantero");
                                txt2.add(documentSnapshot.getString("frenodelantero"));
                            }
                            if (documentSnapshot.getString("frenotrasero") != null && documentSnapshot.getString("frenoTrasero") != "--"){
                                txt1.add("Freno Trasero");
                                txt2.add(documentSnapshot.getString("frenotrasero"));
                            }
                            if (documentSnapshot.getString("tipodesuspensiondelantera") != null && documentSnapshot.getString("tipoSuspensionDelantera") != "--"){
                                txt1.add("Tipo de Suspensión Delantera");
                                txt2.add(documentSnapshot.getString("tipodesuspensiondelantera"));
                            }
                            if (documentSnapshot.getString("tipodesuspensiontrasera") != null && documentSnapshot.getString("tipoSuspensionTrasera") != "--"){
                                txt1.add("Tipo de Suspensión Trasera");
                                txt2.add(documentSnapshot.getString("tipodesuspensiontrasera"));
                            }
                            if (documentSnapshot.getString("peso") != null && documentSnapshot.getString("peso") != "--"){
                                txt1.add("Peso");
                                txt2.add(documentSnapshot.getString("peso"));
                            }
                            if (documentSnapshot.getString("luzprincipal") != null && documentSnapshot.getString("LuzPrincipal") != "--"){
                                txt1.add("Luz Principal");
                                txt2.add(documentSnapshot.getString("luzprincipal"));
                            }

                            break;


                    }
                    tamano=txt1.size();

                    for (int i = 1; i <= tamano; i++) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                (int) TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP, 45, contexto.getResources().getDisplayMetrics())
                        );
                        LinearLayout dataLinearLayout = new LinearLayout(contexto);
                        dataLinearLayout.setLayoutParams(params);
                        dataLinearLayout.setGravity(Gravity.CENTER);
                        dataLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                        if (contador % 2 == 0){
                            dataLinearLayout.setBackgroundColor(colorGris);
                        }else {
                            dataLinearLayout.setBackgroundColor(colorBlanco);
                        }
                        contador++;

                        TextView labelTextView = new TextView(contexto);
                        labelTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        labelTextView.setId(generateUniqueId());
                        ids1.add(labelTextView.getId());

                        LinearLayout dataLinearLayout2 = new LinearLayout(contexto);
                        dataLinearLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        dataLinearLayout2.setGravity(Gravity.RIGHT);

                        TextView valueTextView = new TextView(contexto);
                        valueTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        valueTextView.setGravity(Gravity.RIGHT);
                        valueTextView.setPadding(80,10,10,10);
                        valueTextView.setMaxLines(2);
                        valueTextView.setId(generateUniqueId());
                        ids2.add(valueTextView.getId());

                        dataLinearLayout.addView(labelTextView);
                        parentLinearLayout.addView(dataLinearLayout);

                        dataLinearLayout2.addView(valueTextView);
                        dataLinearLayout.addView(dataLinearLayout2);
                    }

                    for (int i = 0; i < tamano; i++) {
                        TextView labelTextView = parentLinearLayout.findViewById(ids1.get(i));
                        labelTextView.setText(txt1.get(i));
                        TextView valueTextView = parentLinearLayout.findViewById(ids2.get(i));
                        valueTextView.setText(txt2.get(i));
                    }
                }
            }
        });
    }
    private int generateUniqueId() {
        return idTextView1++;
    }
}
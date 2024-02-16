package com.straccion.motos_admin.ui.addmotos;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.UploadTask;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.providers.ImageProvider;
import com.straccion.motos_admin.providers.PostProvider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.security.auth.callback.Callback;

public class WebScraping {
    List<String> nombreMoto = new ArrayList<>();
    List<String> nombresMotos = new ArrayList<>();
    List<String> precioMoto = new ArrayList<>();
    List<String> imagenesColorPrincipal = new ArrayList<>();
    List<String> coloresLista = new ArrayList<>();

    List<String> imagenesColores1 = new ArrayList<>();
    List<String> imagenesColores2 = new ArrayList<>();
    List<String> imagenesColores3 = new ArrayList<>();
    List<String> imagenesColores4 = new ArrayList<>();
    List<String> imagenesColores5 = new ArrayList<>();
    List<String> imagenesColores6 = new ArrayList<>();
    List<File> files1 = new ArrayList<>();
    List<File> files2 = new ArrayList<>();
    List<File> files3 = new ArrayList<>();
    List<File> files4 = new ArrayList<>();
    List<File> files5 = new ArrayList<>();
    List<File> files6 = new ArrayList<>();
    List<File> files7 = new ArrayList<>();
    List<File> files8 = new ArrayList<>();



    String id = "";
    String url = "";
    String modelo = "";
    String descripcion = "";
    String nombreImagen = "";
    String carpeta1 = "";
    String carpeta2 = "";
    String carpeta3 = "";
    List<String> prueba = new ArrayList<>();
    List<String> urls = new ArrayList<>();
    Context context;
    File imagenAdd1 = new File("");
    File imagenAdd2 = new File("");
    File imagenAdd3 = new File("");
    File imagenAdd4 = new File("");
    File imagenAdd5 = new File("");

    File imagen1 = new File("");
    File imagen2 = new File("");
    File imagen3 = new File("");
    File imagen4 = new File("");
    File imagen5 = new File("");
    File imagen6 = new File("");
    File imagen7 = new File("");
    File imagen8 = new File("");

    File colorImagen1 = new File("");
    File colorImagen2 = new File("");
    File colorImagen3 = new File("");
    File colorImagen4 = new File("");
    File colorImagen5 = new File("");
    File colorImagen6 = new File("");
    File colorImagen7 = new File("");
    File colorImagen8 = new File("");
    List<String> recuperarImagenes = new ArrayList<>();

    PostProvider mpostProvider = new PostProvider();
    ImageProvider mImageProvider = new ImageProvider();

    Elements colorPrincipalDiv;
    Elements liElements;
    Element liElement;
    Elements divElements;
    Elements color;
    Document doc = null;
    public WebScraping(Context context){
        this.context = context;
    }

    public WebScraping(String ID, String Url, Context context, String nombre, String carpeta1, String carpeta2, String carpeta3) {
        this.id = ID;
        this.url = Url;
        this.context = context;
        this.nombreImagen = nombre;
        this.carpeta1 = carpeta1;
        this.carpeta2 = carpeta2;
        this.carpeta3 = carpeta3;
    }

    public void obtenerNombreyPreciosAutecoTVSTrabajo() {

        try {
            String url = "https://www.auteco.com.co/motos-tvs-trabajo-y-transporte/";

            doc = Jsoup.connect(url).get();

            Elements nombresMotosElements = doc.select("h2.card-ref-title");
            Elements precioMotosElements = doc.select("div.info-card > h2.card-ref-precio-tvs");
            if (nombresMotosElements.size() >= 2) {
                for (int i = 0; i < nombresMotosElements.size(); i++) {
                    Element segundoElemento = nombresMotosElements.get(i);
                    nombresMotos.add(segundoElemento.text());
                }
            }
            if (precioMotosElements.size() >= 2) {
                for (int i = 0; i < nombresMotosElements.size(); i++) {
                    Element segundoElemento = precioMotosElements.get(i);
                    precioMoto.add(segundoElemento.text().split("\\*")[0].trim());
                }
            }
            guardarPreciosenFirebase();
        } catch (IOException e) {
            Log.e("MainActivity", "Error al obtener el nombre de la moto: " + e.getMessage());
        }
    }

    private void guardarPreciosenFirebase() {
        for (int i = 0; i < nombresMotos.size() ; i++) {
            Map<String, Object> nuevoPrecio = new HashMap<>();
            int precio = Integer.parseInt(precioMoto.get(i).replaceAll("\\D", ""));
            nuevoPrecio.put("precio", precio);
            Query query = mpostProvider.buscarPorNombreMoto(nombresMotos.get(i));
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        id = document.getId();
                        if (!id.isEmpty()){
                            mpostProvider.updatePost2(id, nuevoPrecio).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(context, "melo", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    public void llenarInfo() {
        nombreMoto.clear();
        try {
            //conexion
            Document doc = Jsoup.connect(url).get();

            Elements DatosFichaTecnicaTVSSPORT100ELSSPEDICIONESPECIAL = doc.select("div.jet-table__cell-text");
            Elements descripcionMotos = doc.select("div.elementor-widget-container");
            Elements imagenesAdicionales = doc.select("a.e-gallery-item.elementor-gallery-item.elementor-animated-content");
            colorPrincipalDiv = doc.select("div.jet-listing-dynamic-repeater__item");


            if (descripcionMotos.size() >= 2) {
                Element modeloMoto = descripcionMotos.get(10);
                Element description = descripcionMotos.get(15);
                modelo = modeloMoto.text();
                descripcion = description.text();
                if (descripcion.length() < 12){
                    Element modeloMotos= descripcionMotos.get(11);
                    Element descriptions = descripcionMotos.get(16);
                    modelo = modeloMotos.text();
                    descripcion = descriptions.text();
                }
            }
            if (colorPrincipalDiv.size() >= 2) {
                liElements = colorPrincipalDiv.select("li[id^=linkImages_]");
                color = colorPrincipalDiv.select("div[class^=nameMixtura]");
                for (Element liElement : liElements) {
                    prueba.add(liElement.id());
                }
                for (Element colores : color) {
                    coloresLista.add(colores.text());
                }
                int contador = 0;
                int con = 0;
                for (int i = 0; i < prueba.size(); i++) {
                    liElement = doc.getElementById(prueba.get(i)); // Reemplaza "linkImages_66972" con el ID específico que has identificado
                    divElements = liElement.select("img.jet-engine-gallery-grid__item-img");

                    for (Element divElement : divElements) {
                        // Seleccionar todas las imágenes <img> dentro del <div>
                        Elements imgElements = divElement.select("img");

                        for (Element imgElement : imgElements) {
                            if (contador == 0) {
                                imagenesColorPrincipal.add(imgElement.attr("src"));
                                try{
                                    //convierto las url a imagenes
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColorPrincipal.get(con))
                                            .submit();

                                    if (con == 0){
                                        imagen1 = futureTarget.get();
                                    } else if (con == 1) {
                                        imagen2 = futureTarget.get();
                                    } else if (con == 2) {
                                        imagen3 = futureTarget.get();
                                    } else if (con == 3) {
                                        imagen4 = futureTarget.get();
                                    } else if (con == 4) {
                                        imagen5 = futureTarget.get();
                                    } else if (con == 5) {
                                        imagen6 = futureTarget.get();
                                    } else if (con == 6) {
                                        imagen7 = futureTarget.get();
                                    } else if (con == 7) {
                                        imagen8 = futureTarget.get();
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                                con=con+1;

                            } else if (contador == 1){
                                imagenesColores1.add(imgElement.attr("src"));
                                try{
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores1.get(con))
                                            .submit();

                                    if (con == 0){
                                        files1.add(futureTarget.get());
                                    } else if (con == 1) {
                                        files1.add(futureTarget.get());
                                    } else if (con == 2) {
                                        files1.add(futureTarget.get());
                                    } else if (con == 3) {
                                        files1.add(futureTarget.get());
                                    } else if (con == 4) {
                                        files1.add(futureTarget.get());
                                    } else if (con == 5) {
                                        files1.add(futureTarget.get());
                                    } else if (con == 6) {
                                        files1.add(futureTarget.get());
                                    } else if (con == 7) {
                                        files1.add(futureTarget.get());
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                                con=con+1;
                            } else if (contador == 2){
                                imagenesColores2.add(imgElement.attr("src"));
                                try{
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores2.get(con))
                                            .submit();

                                    if (con == 0){
                                        files2.add(futureTarget.get());
                                    } else if (con == 1) {
                                        files2.add(futureTarget.get());
                                    } else if (con == 2) {
                                        files2.add(futureTarget.get());
                                    } else if (con == 3) {
                                        files2.add(futureTarget.get());
                                    } else if (con == 4) {
                                        files2.add(futureTarget.get());
                                    } else if (con == 5) {
                                        files2.add(futureTarget.get());
                                    } else if (con == 6) {
                                        files2.add(futureTarget.get());
                                    } else if (con == 7) {
                                        files2.add(futureTarget.get());
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                                con=con+1;
                            } else if (contador == 3){
                                imagenesColores3.add(imgElement.attr("src"));
                                try{
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores3.get(con))
                                            .submit();

                                    if (con == 0){
                                        files3.add(futureTarget.get());
                                    } else if (con == 1) {
                                        files3.add(futureTarget.get());
                                    } else if (con == 2) {
                                        files3.add(futureTarget.get());
                                    } else if (con == 3) {
                                        files3.add(futureTarget.get());
                                    } else if (con == 4) {
                                        files3.add(futureTarget.get());
                                    } else if (con == 5) {
                                        files3.add(futureTarget.get());
                                    } else if (con == 6) {
                                        files3.add(futureTarget.get());
                                    } else if (con == 7) {
                                        files3.add(futureTarget.get());
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                                con=con+1;
                            } else if (contador == 4){
                                imagenesColores4.add(imgElement.attr("src"));
                                try{
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores4.get(con))
                                            .submit();

                                    if (con == 0){
                                        files4.add(futureTarget.get());
                                    } else if (con == 1) {
                                        files4.add(futureTarget.get());
                                    } else if (con == 2) {
                                        files4.add(futureTarget.get());
                                    } else if (con == 3) {
                                        files4.add(futureTarget.get());
                                    } else if (con == 4) {
                                        files4.add(futureTarget.get());
                                    } else if (con == 5) {
                                        files4.add(futureTarget.get());
                                    } else if (con == 6) {
                                        files4.add(futureTarget.get());
                                    } else if (con == 7) {
                                        files4.add(futureTarget.get());
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                                con=con+1;
                            } else if (contador == 5){
                                imagenesColores5.add(imgElement.attr("src"));
                                try{
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores5.get(con))
                                            .submit();

                                    if (con == 0){
                                        files5.add(futureTarget.get());
                                    } else if (con == 1) {
                                        files5.add(futureTarget.get());
                                    } else if (con == 2) {
                                        files5.add(futureTarget.get());
                                    } else if (con == 3) {
                                        files5.add(futureTarget.get());
                                    } else if (con == 4) {
                                        files5.add(futureTarget.get());
                                    } else if (con == 5) {
                                        files5.add(futureTarget.get());
                                    } else if (con == 6) {
                                        files5.add(futureTarget.get());
                                    } else if (con == 7) {
                                        files5.add(futureTarget.get());
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                                con=con+1;
                            } else if (contador == 6){
                                imagenesColores6.add(imgElement.attr("src"));
                                try{
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores6.get(con))
                                            .submit();

                                    if (con == 0){
                                        files6.add(futureTarget.get());
                                    } else if (con == 1) {
                                        files6.add(futureTarget.get());
                                    } else if (con == 2) {
                                        files6.add(futureTarget.get());
                                    } else if (con == 3) {
                                        files6.add(futureTarget.get());
                                    } else if (con == 4) {
                                        files6.add(futureTarget.get());
                                    } else if (con == 5) {
                                        files6.add(futureTarget.get());
                                    } else if (con == 6) {
                                        files6.add(futureTarget.get());
                                    } else if (con == 7) {
                                        files6.add(futureTarget.get());
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                                con=con+1;
                            }
                        }
                    }
                    con=0;
                    imagenesColorPrincipal.clear();
                    if (contador == 0){
                        guardarImagenesPrincipales(imagen1, imagen2, imagen3, imagen4, imagen5, imagen6, imagen7, imagen8);
                    }
                    contador=contador+1;
                }
            }

            if (DatosFichaTecnicaTVSSPORT100ELSSPEDICIONESPECIAL.size() >= 2) {
                for (int i = 2; i < 54; i++) {
                    Element datos = DatosFichaTecnicaTVSSPORT100ELSSPEDICIONESPECIAL.get(i);
                    nombreMoto.add(datos.text());
                }
            }

            if (imagenesAdicionales.size() >= 2) {
                for (int i = 0; i < imagenesAdicionales.size(); i++) {
                    Element imagen = imagenesAdicionales.get(i);
                    recuperarImagenes.add(imagen.attr("href"));
                }
            }
            if (imagenesAdicionales.size() >= 2) {
                for (int i = 0; i < imagenesAdicionales.size(); i++) {
                    Element imagen = imagenesAdicionales.get(i);
                    recuperarImagenes.add(imagen.attr("href"));
                }
                for (int i = 0; i < imagenesAdicionales.size(); i++) {
                    try{
                        //convierto las url a imagenes
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(recuperarImagenes.get(i))
                                .submit();

                        if (i == 0){
                            imagenAdd1 = futureTarget.get();
                        } else if (i == 1) {
                            imagenAdd2 = futureTarget.get();
                        } else if (i == 2) {
                            imagenAdd3 = futureTarget.get();
                        } else if (i == 3) {
                            imagenAdd4 = futureTarget.get();
                        } else if (i == 4) {
                            imagenAdd5 = futureTarget.get();
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
            Map<String, Object> updates = new HashMap<>();
            updates.put("cilindraje", nombreMoto.get(1));
            updates.put("motor", nombreMoto.get(3));
            updates.put("torqueMaximo", nombreMoto.get(5));
            updates.put("potenciaMaxima", nombreMoto.get(7));
            updates.put("relacionCompresion", nombreMoto.get(9));
            updates.put("sistemaAlimentacion", nombreMoto.get(11));
            updates.put("diametroCarrera", nombreMoto.get(13));
            updates.put("refrigeracion", nombreMoto.get(15));
            updates.put("combustible", nombreMoto.get(17));
            updates.put("arranque", nombreMoto.get(19));
            updates.put("transmision", nombreMoto.get(21));
            updates.put("suspensionDelantera", nombreMoto.get(23));
            updates.put("suspensionTrasera", nombreMoto.get(25));
            updates.put("frenoDelantero", nombreMoto.get(27));
            updates.put("frenoTrasero", nombreMoto.get(29));
            updates.put("llantaDelantera", nombreMoto.get(31));
            updates.put("llantaTrasera", nombreMoto.get(33));
            updates.put("rines", nombreMoto.get(35));
            updates.put("capacidadTanque", nombreMoto.get(37));
            updates.put("sistemaEncendido", nombreMoto.get(39));
            updates.put("largoTotal", nombreMoto.get(41));
            updates.put("alturaTotal", nombreMoto.get(43));
            updates.put("anchoTotal", nombreMoto.get(45));
            updates.put("distanciaEntreEjes", nombreMoto.get(47));
            updates.put("alturaSillin", nombreMoto.get(49));
            updates.put("pesoNeto", nombreMoto.get(51));
            updates.put("descripcion", descripcion);
            updates.put("modelo", modelo);
            mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            });
        } catch (IOException e) {
            Log.e("MainActivity", "Error al obtener el nombre de la moto: " + e.getMessage());
        }
    }

    private  void guardarImagenesPrincipales(File ImageFile1, File ImageFile2, File ImageFile3, File ImageFile4, File ImageFile5, File ImageFile6, File ImageFile7, File ImageFile8){
        List<String> contador = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        mImageProvider.save1(context, ImageFile1, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+contador.get(0)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            final String url1 = uri1.toString();
                            if (ImageFile2 != null && ImageFile2.exists()) {
                                mImageProvider.save1(context, ImageFile2, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+contador.get(1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                        if (task2.isSuccessful()){
                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri2) {
                                                    final String url2 = uri2.toString();
                                                    if (ImageFile3 != null && ImageFile3.exists()) {
                                                        mImageProvider.save1(context, ImageFile3, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+contador.get(2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task3) {
                                                                if (task3.isSuccessful()){
                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri3) {
                                                                            final String url3 = uri3.toString();
                                                                            if (ImageFile4 != null && ImageFile4.exists()) {
                                                                                mImageProvider.save1(context, ImageFile4, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+contador.get(3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                        if (task4.isSuccessful()){
                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri uri4) {
                                                                                                    final String url4 = uri4.toString();
                                                                                                    if (ImageFile5 != null && ImageFile5.exists()) {
                                                                                                        mImageProvider.save1(context, ImageFile5, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+contador.get(4)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task5) {
                                                                                                                if (task5.isSuccessful()){
                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Uri uri5) {
                                                                                                                            final String url5 = uri5.toString();
                                                                                                                            if (ImageFile6 != null && ImageFile6.exists()) {
                                                                                                                                mImageProvider.save1(context, ImageFile6, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+contador.get(5)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task6) {
                                                                                                                                        if (task6.isSuccessful()){
                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                @Override
                                                                                                                                                public void onSuccess(Uri uri6) {
                                                                                                                                                    final String url6 = uri6.toString();
                                                                                                                                                    if (ImageFile7 != null && ImageFile7.exists()) {
                                                                                                                                                        mImageProvider.save1(context, ImageFile7, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+contador.get(6)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task7) {
                                                                                                                                                                if (task7.isSuccessful()){
                                                                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onSuccess(Uri uri7) {
                                                                                                                                                                            final String url7 = uri7.toString();
                                                                                                                                                                            if (ImageFile8 != null && ImageFile8.exists()) {
                                                                                                                                                                                mImageProvider.save1(context, ImageFile8, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+contador.get(7)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task8) {
                                                                                                                                                                                        if (task8.isSuccessful()){
                                                                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                                                @Override
                                                                                                                                                                                                public void onSuccess(Uri uri8) {
                                                                                                                                                                                                    final String url8 = uri8.toString();
                                                                                                                                                                                                    guardarDatos2(url1, url2, url3, url4, url5, url6, url7, url8);
                                                                                                                                                                                                }
                                                                                                                                                                                            });
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                });
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    });
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                    }else {
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            }else {

                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    }else {

                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }else {

                            }
                        }
                    });
                }
            }
        });
    }

    private void guardarDatos2(String url1, String url2, String url3, String url4, String url5, String url6, String url7, String url8){
        urls.clear();
        Map<String, Object> updates = new HashMap<>();
        if (url1 != null){
            urls.add(url1);
        }if (url2 != null) {
            urls.add(url2);
        }if (url3 != null) {
            urls.add(url3);
        }if (url4 != null) {
            urls.add(url4);
        }if (url5 != null) {
            urls.add(url5);
        }if (url6 != null) {
            urls.add(url6);
        }if (url7 != null) {
            urls.add(url7);
        }if (url8 != null) {
            urls.add(url8);
        }
        updates.put("imagenes", urls);
        updates.put("colores", coloresLista);
        mpostProvider.updatePost2(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    guardarImagenesAdd(imagenAdd1, imagenAdd2, imagenAdd3, imagenAdd4, imagenAdd5);
                }else {
                    Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private  void guardarImagenesAdd(File ImageFile1, File ImageFile2, File ImageFile3, File ImageFile4, File ImageFile5){
        List<String> contador = Arrays.asList("1", "2", "3", "4", "5");
        mImageProvider.save(context, ImageFile1, carpeta1, carpeta2, carpeta3, nombreImagen+"_IMAGENES_ADD_"+contador.get(0)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> tarea1) {
                if (tarea1.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri url1) {
                            final String uri1 = url1.toString();
                            if (ImageFile2 != null && ImageFile2.exists()) {
                                mImageProvider.save(context, ImageFile2, carpeta1, carpeta2, carpeta3, nombreImagen+"_IMAGENES_ADD_"+contador.get(1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> tarea2) {
                                        if (tarea2.isSuccessful()){
                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri url2) {
                                                    final String uri2 = url2.toString();
                                                    if (ImageFile3 != null && ImageFile3.exists()) {
                                                        mImageProvider.save(context, ImageFile3, carpeta1, carpeta2, carpeta3, nombreImagen+"_IMAGENES_ADD_"+contador.get(2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> tarea3) {
                                                                if (tarea3.isSuccessful()){
                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri url3) {
                                                                            final String uri3 = url3.toString();
                                                                            if (ImageFile4 != null && ImageFile4.exists()) {
                                                                                mImageProvider.save(context, ImageFile4, carpeta1, carpeta2, carpeta3, nombreImagen+"_IMAGENES_ADD_"+contador.get(3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> tarea4) {
                                                                                        if (tarea4.isSuccessful()){
                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri url4) {
                                                                                                    final String uri4 = url4.toString();
                                                                                                    if (ImageFile5 != null && ImageFile5.exists()) {
                                                                                                        mImageProvider.save(context, ImageFile5, carpeta1, carpeta2, carpeta3, nombreImagen+"_IMAGENES_ADD_"+contador.get(4)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                                                if (task4.isSuccessful()){
                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Uri url5) {
                                                                                                                            final String uri5 = url5.toString();
                                                                                                                            guardarDatos(uri1, uri2, uri3, uri4, uri5);
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private void guardarDatos(String uri1, String uri2, String uri3, String uri4, String uri5){
        urls.clear();
        Map<String, Object> updates = new HashMap<>();
        if (uri1 != null){
            urls.add(uri1);
        }if (uri2 != null) {
            urls.add(uri2);
        }if (uri3 != null) {
            urls.add(uri3);
        }if (uri4 != null) {
            urls.add(uri4);
        }if (uri5 != null) {
            urls.add(uri5);
        }
        updates.put("caracteristicasImagenes", urls);
        mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    guardarColores(files1, files2, files3, files4, files5, files6);
                }else {
                    Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void reiniciar(){
        colorImagen1=null;
        colorImagen2=null;
        colorImagen3=null;
        colorImagen4=null;
        colorImagen5=null;
        colorImagen6=null;
        colorImagen7=null;
        colorImagen8=null;
    }

    private  void guardarColores(List<File> colorImagenes1, List<File> colorImagenes2, List<File> colorImagenes3, List<File> colorImagenes4, List<File> colorImagenes5, List<File> colorImagenes6){
        reiniciar();
        if (!colorImagenes1.isEmpty()) {
            for (int i = 0; i < colorImagenes1.size(); i++) {
                if (i == 0) {
                    colorImagen1 = colorImagenes1.get(0);
                } else if (i == 1) {
                    colorImagen2 = colorImagenes1.get(1);
                } else if (i == 2) {
                    colorImagen3 = colorImagenes1.get(2);
                } else if (i == 3) {
                    colorImagen4 = colorImagenes1.get(3);
                } else if (i == 4) {
                    colorImagen5 = colorImagenes1.get(4);
                } else if (i == 5) {
                    colorImagen6 = colorImagenes1.get(5);
                } else if (i == 6) {
                    colorImagen7 = colorImagenes1.get(6);
                } else if (i == 7) {
                    colorImagen8 = colorImagenes1.get(7);
                }
            }
            guarda(colorImagen1, colorImagen2, colorImagen3, colorImagen4, colorImagen5, colorImagen6, colorImagen7, colorImagen8, 1, coloresLista.get(1), new Callback() {
                @Override
                public int hashCode() {
                    if (!colorImagenes2.isEmpty()) {
                        for (int j = 0; j < colorImagenes2.size(); j++) {
                            if (j == 0) {
                                colorImagen1 = colorImagenes2.get(0);
                            } else if (j == 1) {
                                colorImagen2 = colorImagenes2.get(1);
                            } else if (j == 2) {
                                colorImagen3 = colorImagenes2.get(2);
                            } else if (j == 3) {
                                colorImagen4 = colorImagenes2.get(3);
                            } else if (j == 4) {
                                colorImagen5 = colorImagenes2.get(4);
                            } else if (j == 5) {
                                colorImagen6 = colorImagenes2.get(5);
                            } else if (j == 6) {
                                colorImagen7 = colorImagenes2.get(6);
                            } else if (j == 7) {
                                colorImagen8 = colorImagenes2.get(7);
                            }
                        }
                        guarda(colorImagen1, colorImagen2, colorImagen3, colorImagen4, colorImagen5, colorImagen6, colorImagen7, colorImagen8, 2, coloresLista.get(2), new Callback() {
                            @Override
                            public int hashCode() {
                                return super.hashCode();
                            }
                        });
                        reiniciar();
                    } else {}
                    return super.hashCode();
                }
            });
        }else {}
    }

    private void guarda(File ImageFile1, File ImageFile2, File ImageFile3, File ImageFile4, File ImageFile5, File ImageFile6, File ImageFile7, File ImageFile8, int posicion, String nombreColor, Callback callback){
        List<String> contador = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        mImageProvider.save2(context, ImageFile1, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+nombreColor+contador.get(0)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            final String url1 = uri1.toString();
                            if (ImageFile2 != null && ImageFile2.exists()) {
                                mImageProvider.save2(context, ImageFile2, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+nombreColor+contador.get(1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                        if (task2.isSuccessful()){
                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri2) {
                                                    final String url2 = uri2.toString();
                                                    if (ImageFile3 != null && ImageFile3.exists()) {
                                                        mImageProvider.save2(context, ImageFile3, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+nombreColor+contador.get(2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task3) {
                                                                if (task3.isSuccessful()){
                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri3) {
                                                                            final String url3 = uri3.toString();
                                                                            if (ImageFile4 != null && ImageFile4.exists()) {
                                                                                mImageProvider.save2(context, ImageFile4, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+nombreColor+contador.get(3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                        if (task4.isSuccessful()){
                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri uri4) {
                                                                                                    final String url4 = uri4.toString();
                                                                                                    if (ImageFile5 != null && ImageFile5.exists()) {
                                                                                                        mImageProvider.save2(context, ImageFile5, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+nombreColor+contador.get(4)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task5) {
                                                                                                                if (task5.isSuccessful()){
                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Uri uri5) {
                                                                                                                            final String url5 = uri5.toString();
                                                                                                                            if (ImageFile6 != null && ImageFile6.exists()) {
                                                                                                                                mImageProvider.save2(context, ImageFile6, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+nombreColor+contador.get(5)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task6) {
                                                                                                                                        if (task6.isSuccessful()){
                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                @Override
                                                                                                                                                public void onSuccess(Uri uri6) {
                                                                                                                                                    final String url6 = uri6.toString();
                                                                                                                                                    if (ImageFile7 != null && ImageFile7.exists()) {
                                                                                                                                                        mImageProvider.save2(context, ImageFile7, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+nombreColor+contador.get(6)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task7) {
                                                                                                                                                                if (task7.isSuccessful()){
                                                                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onSuccess(Uri uri7) {
                                                                                                                                                                            final String url7 = uri7.toString();
                                                                                                                                                                            if (ImageFile8 != null && ImageFile8.exists()) {
                                                                                                                                                                                mImageProvider.save2(context, ImageFile8, carpeta1, carpeta2, carpeta3, nombreImagen+"_"+nombreColor+contador.get(7)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task8) {
                                                                                                                                                                                        if (task8.isSuccessful()){
                                                                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                                                @Override
                                                                                                                                                                                                public void onSuccess(Uri uri8) {
                                                                                                                                                                                                    final String url8 = uri8.toString();
                                                                                                                                                                                                    guardarColor(url1, url2, url3, url4, url5, url6, url7, url8, posicion);
                                                                                                                                                                                                    callback.hashCode();
                                                                                                                                                                                                }
                                                                                                                                                                                            });
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                });
                                                                                                                                                                            }else {
                                                                                                                                                                                guardarColor(url1, url2, url3, url4, url5, url6, url7, null, posicion);
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    });
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                                    }else {
                                                                                                                                                        guardarColor(url1, url2, url3, url4, url5, url6, null, null, posicion);
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            }else {
                                                                                                                                guardarColor(url1, url2, url3, url4, url5, null, null, null, posicion);
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                    }else {
                                                                                                        guardarColor(url1, url2, url3, url4, null, null, null, null, posicion);
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            }else {
                                                                                guardarColor(url1, url2, url3, null, null, null, null, null, posicion);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    }else {
                                                        guardarColor(url1, url2, null, null, null, null, null, null, posicion);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }else {
                                guardarColor(url1, null, null, null, null, null, null, null, posicion);
                            }
                        }
                    });
                }
            }
        });
    }
    private void guardarColor(String url1, String url2, String url3, String url4, String url5, String url6, String url7, String url8, int posicion){
        urls.clear();
        String imagenColores = "";
        if (posicion==1){
            imagenColores =  "imagenesColores1";
        } else if (posicion==2){
            imagenColores =  "imagenesColores2";
        } else if (posicion==3){
            imagenColores =  "imagenesColores3";
        } else if (posicion==4){
            imagenColores =  "imagenesColores4";
        } else if (posicion==5){
            imagenColores =  "imagenesColores5";
        }
        else if (posicion==6){
            imagenColores =  "imagenesColores6";
        }
        Map<String, Object> updates = new HashMap<>();
        ArrayList<String> imagenesColores = new ArrayList<>();
        updates.clear();
        imagenesColores.clear();
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
        }if (url6 != null) {
            imagenesColores.add(url6);
        }if (url7 != null) {
            imagenesColores.add(url7);
        }if (url8 != null) {
            imagenesColores.add(url8);
        }
        updates.put(imagenColores, imagenesColores);
        mpostProvider.updatePost3(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "mellll", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}




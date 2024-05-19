package com.straccion.motos_admin.ui.addmotos;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;
import com.straccion.motos_admin.R;
import com.straccion.motos_admin.providers.ImageProvider;
import com.straccion.motos_admin.providers.PostProvider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.callback.Callback;

public class WebScraping {

    //region VARIABLES

    ProgressBar progressBar;
    Button btnURL;
    int consumoGasolina;
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
    String id = "";
    String url = "";
    String modelo = "";
    String modelo2 = "";
    String descripcion = "";
    String nombreImagen = "";
    String carpeta1 = "";
    String carpeta2 = "";
    String carpeta3 = "";
    List<String> prueba = new ArrayList<>();
    List<String> urls = new ArrayList<>();
    Context context;
    List<File> imagenesAdd = new ArrayList<>();
    File imagen1 = new File("");
    File imagen2 = new File("");
    File imagen3 = new File("");
    File imagen4 = new File("");
    File imagen5 = new File("");
    File imagen6 = new File("");
    File imagen7 = new File("");
    File imagen8 = new File("");
    ArrayList<String> imagenesColores = new ArrayList<>();
    List<String> recuperarImagenes = new ArrayList<>();
    PostProvider mpostProvider = new PostProvider();
    ImageProvider mImageProvider = new ImageProvider();
    Elements colorPrincipalDiv;
    Elements liElements;
    Element liElement;
    Elements divElements;
    Elements color;
    Document doc = null;
    int contador = 0;
    //endregion

    //region CONSTRUCTORES
    public WebScraping(Context context) {
        this.context = context;
    }

    public WebScraping(String ID, String Url, Context context, String nombre, String carpeta1, String carpeta2, String carpeta3, ProgressBar progressBar, Button btnURL, int consumo) {
        this.id = ID;
        this.url = Url;
        this.context = context;
        this.nombreImagen = nombre;
        this.carpeta1 = carpeta1;
        this.carpeta2 = carpeta2;
        this.carpeta3 = carpeta3;
        this.progressBar = progressBar;
        this.btnURL = btnURL;
        this.consumoGasolina = consumo;
    }
    //endregion

    //region ACTUALIZADOR DE PRECIOS

    private void preciosAutecoTVS() {

        try {
            List<String> url = new ArrayList<>();
            url.add("https://www.auteco.com.co/motos-tvs-trabajo-y-transporte/");
            url.add("https://www.auteco.com.co/motos-tvs-deportivas-apache/");
            url.add("https://www.auteco.com.co/motos-tvs-scooters/");
            url.add("https://www.auteco.com.co/motos-tvs-semiautomaticas/");
            for (int i = 0; i < url.size(); i++) {
                doc = Jsoup.connect(url.get(i)).get();
                Elements nombresMotosElements = doc.select("h2.card-ref-title");
                Elements precioMotosElements = doc.select("div.info-card > h2.card-ref-precio-tvs");
                if (!nombresMotosElements.isEmpty()) {
                    for (int j = 0; j < nombresMotosElements.size(); j++) {
                        Element segundoElemento = nombresMotosElements.get(j);
                        nombresMotos.add(segundoElemento.text());
                    }
                }
                if (!precioMotosElements.isEmpty()) {
                    for (int j = 0; j < nombresMotosElements.size(); j++) {
                        Element segundoElemento = precioMotosElements.get(j);
                        precioMoto.add(segundoElemento.text().split("\\*")[0].trim());
                    }
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error al obtener el nombre de la moto: " + e.getMessage());
        }
    }

    private void preciosYamaha() {
        try {
            List<String> url = new ArrayList<>();
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/mt152024-2/25495/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/mtn1000-2-2/23549/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/mt152024-2/25495/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/mt07a/8225/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/mt09tracer-gt/7412/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/mt03a/2349/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/mt09a-2/2317/");

            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/tmaxtechmax/22567/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/gpd155a/15956/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/xmax300/9603/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/t115fi/2105/");

            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/xtz690/20500/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/xt1200ze/11986/");

            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/xsr900/11127/");

            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/yzfr15v3/23945/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/yzf690/19528/");

            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/xtz250a-2/25506/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/xtz150/9869/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/xtz125/2245/");

            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/fzn250a-2/25504/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/sz15rr/25491/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/ybr125zr/25488/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/ycz110-3/25485/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/fz15v3/25294/");
            url.add("https://www.incolmotos-yamaha.com.co/vehiculo/fz15d/2158/");

            for (int i = 0; i < url.size(); i++) {
                doc = Jsoup.connect(url.get(i)).get();
                Element nombresMotosElements = doc.select("li.modelo h1").first();
                Element precioMotosElements = doc.select("div.box-introduction strong").first();
                if (nombresMotosElements != null) {
                    nombresMotos.add(nombresMotosElements.text());
                }
                if (precioMotosElements != null) {
                    precioMoto.add(precioMotosElements.text().split("\\*")[0].trim());
                }
                Elements descripcionMotos = doc.select("span.current");
                if (descripcionMotos.size() >= 1) {
                    for (Element element : descripcionMotos) {
                        // Obtener el año
                        Element precioMotos = doc.selectFirst("div.box-introduction");
                        String precio = precioMotos.selectFirst("strong").text();

                        // Verificar si el siguiente elemento hermano es un enlace <a>
                        Element nextSibling = element.nextElementSibling();
                        if (nextSibling != null && nextSibling.tagName().equals("a")) {
                            // Obtener la URL del enlace
                            String urlNew = nextSibling.attr("href");
                            try {
                                if (urlNew != null) {
                                    precioMotosElements = null;
                                    doc = Jsoup.connect(urlNew).get();
                                    precioMotosElements = doc.select("div.box-introduction strong").first();
                                    if (precioMotosElements != null) {
                                        nombresMotos.add(nombresMotosElements.text());
                                        precioMoto.add(precioMotosElements.text().split("\\*")[0].trim());
                                    }
                                }
                            } catch (Exception e) {

                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error al obtener el nombre de la moto: " + e.getMessage());
        }
    }

    private void preciosHERO() {
        try {
            List<String> url = new ArrayList<>();
            url.add("https://heromotos.com.co/trabajo/");
            url.add("https://heromotos.com.co/urbanas/");
            url.add("https://heromotos.com.co/doble-proposito/");
            url.add("https://heromotos.com.co/scooter/");
            url.add("https://heromotos.com.co/trabajo/eco-t/");

            for (int i = 0; i < url.size(); i++) {
                doc = Jsoup.connect(url.get(i)).get();
                if (!url.get(i).equals("https://heromotos.com.co/trabajo/eco-t/")) {
                    Element seccion0 = doc.select("div.elementor-container.elementor-column-gap-default").get(4);
                    // Extrae los elementos h2 y h3 dentro del quinto div
                    Elements elementosH2 = seccion0.select("h2");
                    Elements elementosH3 = seccion0.select("h3");
                    for (Element h2 : elementosH2) {
                        nombresMotos.add(h2.text());
                    }

                    // Guarda el texto de h3 en la lista
                    for (Element h3 : elementosH3) {
                        String textoParaQuitar = h3.text().replace("Precio desde ", "");
                        precioMoto.add(textoParaQuitar.replace("*", ""));
                    }
                } else {//esta moto toca asi por que no tiene precio
                    Element seccionPrecio = doc.select("div.elementor-widget-wrap.elementor-element-populated").get(6);
                    Elements elementosH5 = seccionPrecio.select("h5");
                    int indice = nombresMotos.indexOf("ECO T");
                    //probar y terminar

                    for (Element h5 : elementosH5) {
                        String textoParaQuitar = "*";
                        precioMoto.add(indice, h5.text().replace(textoParaQuitar, ""));
                    }
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error al obtener el nombre de la moto: " + e.getMessage());
        }
    }

    private void preciosSUZUKI() {
        try {
            List<String> url = new ArrayList<>();
            List<Integer> motosConDescuentoIndice = new ArrayList<>();

            url.add("http://www.suzuki.com.co/motocicletas/lista?page=0");
            url.add("http://www.suzuki.com.co/motocicletas/lista?page=1");
            url.add("http://www.suzuki.com.co/motocicletas/lista?page=2");
            url.add("http://www.suzuki.com.co/motocicletas/lista?page=3");
            url.add("http://www.suzuki.com.co/motocicletas/lista?page=4");

            for (int i = 0; i < url.size(); i++) {
                doc = Jsoup.connect(url.get(i)).get();

                Elements NombreMotoYPrecio = doc.select("div.col-md-12.col-lg-12.col-sm-12.col-xs-12.content");
                if (NombreMotoYPrecio != null) {

                    Elements nombremotos = NombreMotoYPrecio.select("h4.text-uppercase.text-center");
                    Elements pElements = NombreMotoYPrecio.select("h4.text-uppercase.text-center, p.precio, p.precio-antes");

//                    for (Element nombremoto : nombremotos) {
//                        nombresMotos.add(nombremoto.text());
//                    }
                    String info = "";
                    for (Element pElement : pElements) {
                        // Obtén el texto del elemento <h5>
                        if (pElement.hasClass("text-uppercase text-center")) {
                            nombresMotos.add(pElement.text());
                        } else if (pElement.hasClass("precio")) {
                            // Agregar a la lista de elementos con clase 'precio-antes'
                            info = pElement.text().replace(" IVA Incluido", "");
                            precioMoto.add(info.replace(",", "."));
                        } else if (pElement.hasClass("precio-antes")) {
                            // Agregar a la lista de elementos con clase 'precio'
                            int ultimoDato = nombresMotos.size() - 1;
                            info = pElement.text().replace(" IVA Incluido", "");
                            precioMoto.add(info.replace(",", "."));
                            String ultimoNombreMoto = nombresMotos.get(ultimoDato);
                            nombresMotos.add(ultimoNombreMoto);
                            motosConDescuentoIndice.add(precioMoto.size());
                        }
                    }//cambiar las comas por puntos.
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error al obtener el nombre de la moto: " + e.getMessage());
        }
    }

    private void preciosHONDA() {
        try {
            List<String> url = new ArrayList<>();
            List<Integer> motosConDescuentoIndice = new ArrayList<>();

            url.add("https://motos.honda.com.co/motos-honda/motos-sport/CB-100");
            url.add("https://motos.honda.com.co/motos-honda/motos-sport/CB-125F");
            url.add("https://motos.honda.com.co/motos-honda/motos-sport/CB-125F-DLX");
            url.add("https://motos.honda.com.co/motos-honda/motos-sport/XBLADE-160");
            url.add("https://motos.honda.com.co/motos-honda/motos-sport/CB-190-R");
            url.add("https://motos.honda.com.co/motos-honda/motos-sport/CB-190-R-Tricolor");
            url.add("https://motos.honda.com.co/motos-honda/motos-sport/CB-190-REPSOL");
            url.add("https://motos.honda.com.co/motos-honda/motos-sport/CB-300F");
            url.add("https://motos.honda.com.co/motos-honda/motos-sport/CB-1000R");
            url.add("https://motos.honda.com.co/motos-honda/super-sport/CBR-650R");
            url.add("https://motos.honda.com.co/motos-honda/todo-terreno/XR-150L");
            url.add("https://motos.honda.com.co/motos-honda/todo-terreno/XR-190L");
            url.add("https://motos.honda.com.co/motos-honda/todo-terreno/xre-300-abs-std");
            url.add("https://motos.honda.com.co/motos-honda/todo-terreno/xre-300-abs-dlx");
            url.add("https://motos.honda.com.co/motos-honda/scooter-y-semiautomatica/DIO-LED-STD");
            url.add("https://motos.honda.com.co/motos-honda/scooter-y-semiautomatica/WAVE-110S");
            url.add("https://motos.honda.com.co/motos-honda/scooter-y-semiautomatica/DIO-LED-DLX");
            url.add("https://motos.honda.com.co/motos-honda/scooter-y-semiautomatica/pcx-160?q=motos-honda/scooter-y-semiautomatica/PCX-150");
            url.add("https://motos.honda.com.co/motos-honda/scooter-y-semiautomatica/pcx-160?q=motos-honda/scooter-y-semiautomatica/PCX-150-DLX");
            url.add("https://motos.honda.com.co/motos-honda/scooter-y-semiautomatica/PCX-160");
            url.add("https://motos.honda.com.co/motos-honda/NAVI/NAVI");
            url.add("https://motos.honda.com.co/motos-honda/NAVI/navi-mix");
            url.add("https://motos.honda.com.co/motos-honda/NAVI/NAVI-RALLY");
            url.add("https://motos.honda.com.co/motos-honda/NAVI/NAVI-ADVENTURE");
            url.add("https://motos.honda.com.co/motos-honda/aventura/NX-500");
            url.add("https://motos.honda.com.co/motos-honda/aventura/CB-500X");
            url.add("https://motos.honda.com.co/motos-honda/aventura/NC-750XD");
            url.add("https://motos.honda.com.co/motos-honda/aventura/AFRICA-TWIN-CRF-1100L");
            url.add("https://motos.honda.com.co/motos-honda/aventura/africa-twin-adventure-sports");
            url.add("https://motos.honda.com.co/motos-honda/aventura/AFRICA-TWIN-ADVENTURE-SPORTS-SE");
            url.add("https://motos.honda.com.co/motos-honda/custom/CMX-500");

            for (int i = 0; i < url.size(); i++) {
                doc = Jsoup.connect(url.get(i)).get();

                Elements NombreMotoYPrecio = doc.select("h1.t1TitleMoto.moto-title-color");
                Element PreciosMoto = doc.selectFirst("div.price.1");

                if (NombreMotoYPrecio != null) {
                    if (PreciosMoto != null) {
                        String infoPrecio = PreciosMoto.text();
                        precioMoto.add(infoPrecio.replace(",", "."));
                        for (Element h1Element : NombreMotoYPrecio) {
                            nombresMotos.add(h1Element.text());
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error al obtener el nombre de la moto: " + e.getMessage());
        }
    }
    private void preciosBAJAJ() {
        try {
            List<String> url = new ArrayList<>();
            List<Integer> motosConDescuentoIndice = new ArrayList<>();

            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Boxer/Boxer-S");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Boxer/Boxer-S-Pro");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Boxer/Boxer-CT100-KS");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Boxer/Boxer-Carguera");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Boxer/BOXER-CT-100-ES");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Boxer/Boxer-CT100ES-Titanium");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Boxer/Boxer-CT-125");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Discover/Discover-125-ST-R");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Discover/Discover-125-ST-R-Pro");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Platino/platino-125");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Pulsar/Pulsar-NS-125");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Pulsar/Pulsar-150-Neon");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Pulsar/Pulsar-180-Neon-FI");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Pulsar/Pulsar-N160");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Pulsar/Pulsar-NS160-FI");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Pulsar/Pulsar-NS160-FI-ABS");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Pulsar/Pulsar-NS200-FI");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Pulsar/Pulsar-N250");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Pulsar/Pulsar-NS-200-FI-ABS");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Pulsar/Pulsar-RS-200");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Dominar/Dominar-250");
            url.add("https://colombia.globalbajaj.com/es-CO/BRANDS/Dominar/Dominar-D400");

            for (int i = 0; i < url.size(); i++) {
                doc = Jsoup.connect(url.get(i)).get();

                Elements NombreMotoYPrecio = doc.select("div.variant-banner-text");
                Elements NombreMotoYPrecio2 = doc.select("div.newsheader");

                if (NombreMotoYPrecio != null) {
                    Element h1Element = NombreMotoYPrecio.select("div.variant-banner-text h1").first();
                    nombresMotos.add(h1Element.text());

                    Element pElemen = NombreMotoYPrecio.select("div.variant-banner-text p").first();
                    String infoPrecio = pElemen.text();
                    precioMoto.add(infoPrecio.replace(" COP*",""));
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error al obtener el nombre de la moto: " + e.getMessage());
        }
        guardarPreciosenFirebase();
    }

    private void guardarPreciosenFirebase() {
        mpostProvider.getAll2().addSnapshotListener((querySnapshot, error) -> {
            if (error != null) {
                // Manejar el error
                return;
            }

            if (querySnapshot != null) {
                // Iterar sobre cada documento obtenido de la base de datos
                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                    Map<String, Object> DescuentosAActualizar1 = new HashMap<>();
                    Map<String, Object> ValorDesAActualizar2 = new HashMap<>();
                    Map<String, Object> PrecioAActualizar3 = new HashMap<>();
                    Map<String, Object> Precio2AActualizar4 = new HashMap<>();
                    // Obtener el nombre y precio de la moto del documento
                    String idMoto = document.getId();
                    String marcaMoto = document.getString("marcaMoto");
                    String nombreMoto = document.getString("nombreMoto");
                    double nuevoValorDescuento = document.getDouble("nuevoValorDescuento");
                    double precio = document.getDouble("precio");
                    double precio2 = 0;
                    if (document.contains("precio2")) {
                        precio2 = document.getDouble("precio2"); // para los que tienen dos modelos diferentes
                    }
                    boolean descuento = document.getBoolean("descuento");

                    // Buscar el índice correspondiente en la lista nombresMotos
                    List<Integer> indices = new ArrayList<>();
                    String nombreBuscado = nombreMoto.replace(" ", "").replace("-", "");//elinmino espacios

                    for (int i = 0; i < nombresMotos.size(); i++) {
                        String nombreLista = nombresMotos.get(i).replace(" ", "").toUpperCase();//elinmino espacios
                        nombreLista = nombreLista.replace("-", "");
                        // Normaliza la cadena para eliminar tildes y caracteres diacríticos
                        nombreLista = Normalizer.normalize(nombreLista, Normalizer.Form.NFD);
                        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                        nombreLista = pattern.matcher(nombreLista).replaceAll("");
                        if (nombreLista.contains(nombreBuscado)) {
                            indices.add(i);
                        }
                    }

                    // Verificar si el nombre de la moto existe en la lista nombresMotos
                    if (!indices.isEmpty()) {
                        // Obtener el precio almacenado en la lista precioMoto
                        int precioConvertido = 0;
                        int precioConvertido2 = 0;

                        if (indices.size() == 2) {
                            precioConvertido = Integer.parseInt(precioMoto.get(indices.get(0)).replaceAll("\\D", ""));
                            precioConvertido2 = Integer.parseInt(precioMoto.get(indices.get(1)).replaceAll("\\D", ""));
                            int mayor = Math.max(precioConvertido2, precioConvertido);
                            int menor = Math.min(precioConvertido2, precioConvertido);
                            if (marcaMoto.equals("SUZUKI")) {
                                precioConvertido = menor;
                                precioConvertido2 = mayor;
                            } else {
                                precioConvertido = mayor;
                                precioConvertido2 = menor;
                            }

                        } else {
                            precioConvertido = Integer.parseInt(precioMoto.get(indices.get(0)).replaceAll("\\D", ""));
                        }


                        // Comparar los precios
                        if (nuevoValorDescuento != precioConvertido) {
                            if (nuevoValorDescuento > precioConvertido) {
                                DescuentosAActualizar1.put("descuento", true);
                                ValorDesAActualizar2.put("nuevoValorDescuento", precioConvertido);
                                PrecioAActualizar3.put("precio", (int) nuevoValorDescuento);
                                if (precio == 0) {
                                    PrecioAActualizar3.put("precio", precioConvertido);
                                }
                            } else {
                                if (descuento) {
                                    DescuentosAActualizar1.put("descuento", false);
                                }
                                if (nuevoValorDescuento == 0) {
                                    ValorDesAActualizar2.put("nuevoValorDescuento", precioConvertido);
                                }
                                if (precio == 0) {
                                    PrecioAActualizar3.put("precio", precioConvertido);
                                } else {
                                    ValorDesAActualizar2.put("nuevoValorDescuento", precioConvertido);
                                    PrecioAActualizar3.put("precio", (int) precioConvertido);
                                }
                            }
                        }

                        //precio 2
                        if (precioConvertido2 != 0) {
                            if (precio2 != precioConvertido2) {
                                Precio2AActualizar4.put("precio2", precioConvertido2);
                            }
                        } else if (marcaMoto == "SUZUKI") {

                        }
                    }


                    if (!ValorDesAActualizar2.isEmpty()) {
                        mpostProvider.updateMasive1(idMoto, ValorDesAActualizar2).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                    if (!DescuentosAActualizar1.isEmpty()) {
                        mpostProvider.updateMasive2(idMoto, DescuentosAActualizar1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                    if (!PrecioAActualizar3.isEmpty()) {
                        mpostProvider.updateMasive3(idMoto, PrecioAActualizar3).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                    if (!Precio2AActualizar4.isEmpty()) {
                        mpostProvider.updateMasive4(idMoto, Precio2AActualizar4).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                    Toast.makeText(context, "Proceso finalizado", Toast.LENGTH_SHORT).show();
                }
                ;
            }
        });

    }

    //endregion

    //region DIRECCIONADOR POR FABRICANTE

    public void direccionamientoxFabricantePrecios() {
        preciosAutecoTVS();
        preciosYamaha();
        preciosHERO();
        preciosSUZUKI();
        preciosHONDA();
        preciosBAJAJ();
    }

    public void direccionamientoxFabricante(String fabricante) {
        if (fabricante.equals("AUTECO")) {
            llenarInfoAuteco();
        } else if (fabricante.equals("YAMAHA")) {
            llenarInfoYamaha();
        } else if (fabricante.equals("HERO")) {
            llenarInfoHERO();
        } else if (fabricante.equals("SUZUKI")) {
            llenarInfoSUZUKI();
        } else if (fabricante.equals("HONDA")) {
            llenarInfoHONDA();
        } else if (fabricante.equals("BAJAJ")) {
            llenarInfoBAJAJ();
        }
    }

    //endregion

    //region PROCESO DE AUTECO

    private void llenarInfoAuteco() {
        nombreMoto.clear();
        try {
            //conexion
            Document doc = Jsoup.connect(url).get();

            Elements DatosFichaTecnicaMotos = doc.select("div.jet-table__cell-text");
            Elements descripcionMotos = doc.select("div.elementor-widget-container");
            Elements imagenesAdicionales = doc.select("a.e-gallery-item.elementor-gallery-item.elementor-animated-content");
            colorPrincipalDiv = doc.select("div.jet-listing-dynamic-repeater__item");
            Elements catalogoPartes = doc.select("a.jet-table__cell-inner");

            if (catalogoPartes.size() >= 2) {
                Element catalogoPDF = catalogoPartes.get(1);
                String url = catalogoPDF.attr("href");
                guardarPDF(url);
            }

            if (descripcionMotos.size() >= 2) {
                Element modeloMoto = descripcionMotos.get(10);
                Element description = descripcionMotos.get(14);
                modelo = modeloMoto.text();
                descripcion = description.text();
                if (descripcion.length() < 15) {
                    Element descriptions = descripcionMotos.get(15);
                    descripcion = descriptions.text();
                }
                if (!modelo.contains("Modelo")) {
                    modelo = "";
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
                    liElement = doc.getElementById(prueba.get(i));
                    divElements = liElement.select("img.jet-engine-gallery-grid__item-img");

                    for (Element divElement : divElements) {
                        // Seleccionar todas las imágenes <img> dentro del <div>
                        Elements imgElements = divElement.select("img");

                        for (Element imgElement : imgElements) {
                            if (contador == 0) {
                                imagenesColorPrincipal.add(imgElement.attr("src"));
                                try {
                                    //convierto las url a imagenes
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColorPrincipal.get(con))
                                            .submit();

                                    if (con == 0) {
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
                                con = con + 1;

                            } else if (contador == 1) {
                                imagenesColores1.add(imgElement.attr("src"));
                                try {
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores1.get(con))
                                            .submit();

                                    if (con == 0) {
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
                                con = con + 1;
                            } else if (contador == 2) {
                                imagenesColores2.add(imgElement.attr("src"));
                                try {
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores2.get(con))
                                            .submit();

                                    if (con == 0) {
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
                                con = con + 1;
                            } else if (contador == 3) {
                                imagenesColores3.add(imgElement.attr("src"));
                                try {
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores3.get(con))
                                            .submit();

                                    if (con == 0) {
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
                                con = con + 1;
                            } else if (contador == 4) {
                                imagenesColores4.add(imgElement.attr("src"));
                                try {
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores4.get(con))
                                            .submit();

                                    if (con == 0) {
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
                                con = con + 1;
                            } else if (contador == 5) {
                                imagenesColores5.add(imgElement.attr("src"));
                                try {
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores5.get(con))
                                            .submit();

                                    if (con == 0) {
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
                                con = con + 1;
                            } else if (contador == 6) {
                                imagenesColores6.add(imgElement.attr("src"));
                                try {
                                    FutureTarget<File> futureTarget = Glide.with(context)
                                            .asFile()
                                            .load(imagenesColores6.get(con))
                                            .submit();

                                    if (con == 0) {
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
                                con = con + 1;
                            }
                        }
                    }
                    con = 0;
                    imagenesColorPrincipal.clear();
                    if (contador == 0) {
                        guardarImagenesPrincipales(imagen1, imagen2, imagen3, imagen4, imagen5, imagen6, imagen7, imagen8);
                    }
                    contador = contador + 1;
                }
            }

            if (DatosFichaTecnicaMotos.size() >= 2) {
                for (int i = 2; i < 54; i++) {
                    Element datos = DatosFichaTecnicaMotos.get(i);
                    nombreMoto.add(datos.text());
                }
            }
            if (imagenesAdicionales.size() >= 2) {
                for (int i = 0; i < imagenesAdicionales.size(); i++) {
                    Element imagen = imagenesAdicionales.get(i);
                    recuperarImagenes.add(imagen.attr("href"));
                }
                for (int i = 0; i < imagenesAdicionales.size(); i++) {
                    try {
                        //convierto las url a imagenes
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(recuperarImagenes.get(i))
                                .submit();

                        if (i == 0) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 1) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 2) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 3) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 4) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 5) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 6) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 7) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 8) {
                            imagenesAdd.add(futureTarget.get());
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
            updates.put("consumoPorGalon", consumoGasolina);
            updates.put("visible", true);
            mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            });
        } catch (IOException e) {
            Log.e("MainActivity", "Error al obtener el nombre de la moto: " + e.getMessage());
        }
    }

    private void guardarPDF(String dato) {

        if (dato.startsWith("/wp")) {
            dato = "https://www.auteco.com.co" + dato;
        }
        File pdf = descargarArchivo(dato);

        mImageProvider.guardarPDF(context, pdf, carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen).addOnSuccessListener(uri -> {
            String url1 = uri.toString();
            guardarDatosPDF(url1);
        }).addOnFailureListener(e -> {
            System.out.println("Error al descargar el archivo. Código de respuesta: " + e);
        });
    }

    private static File descargarArchivo(String url) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Verificar si la respuesta del servidor es exitosa
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Crear un archivo temporal para guardar el archivo descargado
                String fileName = url.substring(url.lastIndexOf("/") + 1);
                File file = new File(System.getProperty("java.io.tmpdir"), fileName);

                // Guardar el archivo descargado en el archivo temporal
                InputStream inputStream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();

                return file;
            } else {
                System.out.println("Error al descargar el archivo. Código de respuesta: " + responseCode);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void guardarDatosPDF(String url1) {
        urls.clear();
        Map<String, Object> updates = new HashMap<>();
        if (url1 != null) {
            urls.add(url1);
        }
        List<String> catalogos = new ArrayList<>();
        catalogos.add("Catalogo de Partes");
        updates.put("manualesArchivos", urls);
        updates.put("nombresArchivos", catalogos);
        mpostProvider.updatePost2(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
                    Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void guardarImagenesPrincipales(File ImageFile1, File ImageFile2, File ImageFile3, File ImageFile4, File ImageFile5, File ImageFile6, File ImageFile7, File ImageFile8) {
        List<String> contador = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        mImageProvider.save1(context, ImageFile1, carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + contador.get(0)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            final String url1 = uri1.toString();
                            if (ImageFile2 != null && ImageFile2.exists()) {
                                mImageProvider.save1(context, ImageFile2, carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + contador.get(1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                        if (task2.isSuccessful()) {
                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri2) {
                                                    final String url2 = uri2.toString();
                                                    if (ImageFile3 != null && ImageFile3.exists()) {
                                                        mImageProvider.save1(context, ImageFile3, carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + contador.get(2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task3) {
                                                                if (task3.isSuccessful()) {
                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri3) {
                                                                            final String url3 = uri3.toString();
                                                                            if (ImageFile4 != null && ImageFile4.exists()) {
                                                                                mImageProvider.save1(context, ImageFile4, carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + contador.get(3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                        if (task4.isSuccessful()) {
                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri uri4) {
                                                                                                    final String url4 = uri4.toString();
                                                                                                    if (ImageFile5 != null && ImageFile5.exists()) {
                                                                                                        mImageProvider.save1(context, ImageFile5, carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + contador.get(4)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task5) {
                                                                                                                if (task5.isSuccessful()) {
                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Uri uri5) {
                                                                                                                            final String url5 = uri5.toString();
                                                                                                                            if (ImageFile6 != null && ImageFile6.exists()) {
                                                                                                                                mImageProvider.save1(context, ImageFile6, carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + contador.get(5)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task6) {
                                                                                                                                        if (task6.isSuccessful()) {
                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                @Override
                                                                                                                                                public void onSuccess(Uri uri6) {
                                                                                                                                                    final String url6 = uri6.toString();
                                                                                                                                                    if (ImageFile7 != null && ImageFile7.exists()) {
                                                                                                                                                        mImageProvider.save1(context, ImageFile7, carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + contador.get(6)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task7) {
                                                                                                                                                                if (task7.isSuccessful()) {
                                                                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onSuccess(Uri uri7) {
                                                                                                                                                                            final String url7 = uri7.toString();
                                                                                                                                                                            if (ImageFile8 != null && ImageFile8.exists()) {
                                                                                                                                                                                mImageProvider.save1(context, ImageFile8, carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + contador.get(7)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task8) {
                                                                                                                                                                                        if (task8.isSuccessful()) {
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
                                                                                                                                                                            } else {
                                                                                                                                                                                guardarDatos2(url1, url2, url3, url4, url5, url6, url7, null);
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    });
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                                    } else {
                                                                                                                                                        guardarDatos2(url1, url2, url3, url4, url5, url6, null, null);
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            } else {
                                                                                                                                guardarDatos2(url1, url2, url3, url4, url5, null, null, null);
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                    } else {
                                                                                                        guardarDatos2(url1, url2, url3, url4, null, null, null, null);
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                guardarDatos2(url1, url2, url3, null, null, null, null, null);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        guardarDatos2(url1, url2, null, null, null, null, null, null);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                guardarDatos2(url1, null, null, null, null, null, null, null);
                            }
                        }
                    });
                }
            }
        });
    }

    private void guardarDatos2(String url1, String url2, String url3, String url4, String url5, String url6, String url7, String url8) {
        urls.clear();
        Map<String, Object> updates = new HashMap<>();
        if (url1 != null) {
            urls.add(url1);
        }
        if (url2 != null) {
            urls.add(url2);
        }
        if (url3 != null) {
            urls.add(url3);
        }
        if (url4 != null) {
            urls.add(url4);
        }
        if (url5 != null) {
            urls.add(url5);
        }
        if (url6 != null) {
            urls.add(url6);
        }
        if (url7 != null) {
            urls.add(url7);
        }
        if (url8 != null) {
            urls.add(url8);
        }
        updates.put("imagenes", urls);
        updates.put("colores", coloresLista);
        mpostProvider.updatePost2(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    guardarImagenesAdd(imagenesAdd);
                } else {
                    Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void guardarImagenesAdd(List<File> imagenesAdd) {
        List<String> contador = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        if (imagenesAdd.isEmpty()) {
            guardarDatos(null, null, null, null, null, null, null, null, null);
        } else {
            mImageProvider.save(context, imagenesAdd.get(0), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_IMAGENES_ADD_" + contador.get(0)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> tarea1) {
                    if (tarea1.isSuccessful()) {
                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri url1) {
                                final String uri1 = url1.toString();
                                if (imagenesAdd.size() > 1 && imagenesAdd.get(1) != null && imagenesAdd.get(1).exists()) {
                                    mImageProvider.save(context, imagenesAdd.get(1), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_IMAGENES_ADD_" + contador.get(1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> tarea2) {
                                            if (tarea2.isSuccessful()) {
                                                mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri url2) {
                                                        final String uri2 = url2.toString();
                                                        if (imagenesAdd.size() > 2 && imagenesAdd.get(2) != null && imagenesAdd.get(2).exists()) {
                                                            mImageProvider.save(context, imagenesAdd.get(2), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_IMAGENES_ADD_" + contador.get(2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> tarea3) {
                                                                    if (tarea3.isSuccessful()) {
                                                                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                            @Override
                                                                            public void onSuccess(Uri url3) {
                                                                                final String uri3 = url3.toString();
                                                                                if (imagenesAdd.size() > 3 && imagenesAdd.get(3) != null && imagenesAdd.get(3).exists()) {
                                                                                    mImageProvider.save(context, imagenesAdd.get(3), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_IMAGENES_ADD_" + contador.get(3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> tarea4) {
                                                                                            if (tarea4.isSuccessful()) {
                                                                                                mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(Uri url4) {
                                                                                                        final String uri4 = url4.toString();
                                                                                                        if (imagenesAdd.size() > 4 && imagenesAdd.get(4) != null && imagenesAdd.get(4).exists()) {
                                                                                                            mImageProvider.save(context, imagenesAdd.get(4), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_IMAGENES_ADD_" + contador.get(4)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                                                    if (task4.isSuccessful()) {
                                                                                                                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                            @Override
                                                                                                                            public void onSuccess(Uri url5) {
                                                                                                                                final String uri5 = url5.toString();
                                                                                                                                if (imagenesAdd.size() > 5 && imagenesAdd.get(5) != null && imagenesAdd.get(5).exists()) {
                                                                                                                                    mImageProvider.save(context, imagenesAdd.get(5), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_IMAGENES_ADD_" + contador.get(5)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task5) {
                                                                                                                                            if (task5.isSuccessful()) {
                                                                                                                                                mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onSuccess(Uri url6) {
                                                                                                                                                        final String uri6 = url6.toString();
                                                                                                                                                        if (imagenesAdd.size() > 6 && imagenesAdd.get(6) != null && imagenesAdd.get(6).exists()) {
                                                                                                                                                            mImageProvider.save(context, imagenesAdd.get(6), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_IMAGENES_ADD_" + contador.get(6)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task6) {
                                                                                                                                                                    if (task6.isSuccessful()) {
                                                                                                                                                                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                            @Override
                                                                                                                                                                            public void onSuccess(Uri url7) {
                                                                                                                                                                                final String uri7 = url7.toString();
                                                                                                                                                                                if (imagenesAdd.size() > 7 && imagenesAdd.get(7) != null && imagenesAdd.get(7).exists()) {
                                                                                                                                                                                    mImageProvider.save(context, imagenesAdd.get(7), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_IMAGENES_ADD_" + contador.get(7)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                        @Override
                                                                                                                                                                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task7) {
                                                                                                                                                                                            if (task7.isSuccessful()) {
                                                                                                                                                                                                mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                                                    @Override
                                                                                                                                                                                                    public void onSuccess(Uri url8) {
                                                                                                                                                                                                        final String uri8 = url8.toString();
                                                                                                                                                                                                        if (imagenesAdd.size() > 8 && imagenesAdd.get(8) != null && imagenesAdd.get(8).exists()) {
                                                                                                                                                                                                            mImageProvider.save(context, imagenesAdd.get(8), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_IMAGENES_ADD_" + contador.get(8)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                                                @Override
                                                                                                                                                                                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task8) {
                                                                                                                                                                                                                    if (task8.isSuccessful()) {
                                                                                                                                                                                                                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                                                                            @Override
                                                                                                                                                                                                                            public void onSuccess(Uri url9) {
                                                                                                                                                                                                                                final String uri9 = url9.toString();
                                                                                                                                                                                                                                guardarDatos(uri1, uri2, uri3, uri4, uri5, uri6, uri7, uri8, uri9);
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        });
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                }
                                                                                                                                                                                                            });
                                                                                                                                                                                                        } else {
                                                                                                                                                                                                            guardarDatos(uri1, uri2, uri3, uri4, uri5, uri6, uri7, uri8, null);
                                                                                                                                                                                                        }
                                                                                                                                                                                                    }
                                                                                                                                                                                                });
                                                                                                                                                                                            }
                                                                                                                                                                                        }
                                                                                                                                                                                    });
                                                                                                                                                                                } else {
                                                                                                                                                                                    guardarDatos(uri1, uri2, uri3, uri4, uri5, uri6, uri7, null, null);
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                        });
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            });
                                                                                                                                                        } else {
                                                                                                                                                            guardarDatos(uri1, uri2, uri3, uri4, uri5, uri6, null, null, null);
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                });
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });
                                                                                                                                } else {
                                                                                                                                    guardarDatos(uri1, uri2, uri3, uri4, uri5, null, null, null, null);
                                                                                                                                }
                                                                                                                            }
                                                                                                                        });
                                                                                                                    }
                                                                                                                }
                                                                                                            });
                                                                                                        } else {
                                                                                                            guardarDatos(uri1, uri2, uri3, uri4, null, null, null, null, null);
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                } else {
                                                                                    guardarDatos(uri1, uri2, uri3, null, null, null, null, null, null);
                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            });
                                                        } else {
                                                            guardarDatos(uri1, uri2, null, null, null, null, null, null, null);
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    guardarDatos(uri1, null, null, null, null, null, null, null, null);
                                }
                            }
                        });
                    }
                }
            });
        }

    }

    private void guardarDatos(String uri1, String uri2, String uri3, String uri4, String uri5, String uri6, String uri7, String uri8, String uri9) {
        urls.clear();
        Map<String, Object> updates = new HashMap<>();
        if (uri1 != null) {
            urls.add(uri1);
        }
        if (uri2 != null) {
            urls.add(uri2);
        }
        if (uri3 != null) {
            urls.add(uri3);
        }
        if (uri4 != null) {
            urls.add(uri4);
        }
        if (uri5 != null) {
            urls.add(uri5);
        }
        if (uri6 != null) {
            urls.add(uri6);
        }
        if (uri7 != null) {
            urls.add(uri7);
        }
        if (uri8 != null) {
            urls.add(uri8);
        }
        if (uri9 != null) {
            urls.add(uri9);
        }

        updates.put("caracteristicasImagenes", urls);
        if (urls.isEmpty()) {
            if (coloresLista.size() > 1) {
                if (coloresLista.get(0).equals(coloresLista.get(1))) {
                    String color = coloresLista.get(1);
                    coloresLista.remove(1);
                    coloresLista.add(1, color + 1);
                }
                guardaColores1(files1, 1, coloresLista.get(1));
            } else {
                progressBar.setVisibility(View.GONE);
                btnURL.setVisibility(View.VISIBLE);
            }
        }
        mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (coloresLista.size() > 1) {
                        guardaColores1(files1, 1, coloresLista.get(1));
                    }
                } else {
                    Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void guardaColores1(List<File> colores, int posicion, String nombreColor) {
        List<String> contador = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        mImageProvider.save2(context, colores.get(0), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(0)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            final String url1 = uri1.toString();
                            if (colores.size() > 1 && colores.get(1) != null && colores.get(1).exists()) {
                                mImageProvider.save2(context, colores.get(1), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                        if (task2.isSuccessful()) {
                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri2) {
                                                    final String url2 = uri2.toString();
                                                    if (colores.size() > 2 && colores.get(2) != null && colores.get(2).exists()) {
                                                        mImageProvider.save2(context, colores.get(2), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task3) {
                                                                if (task3.isSuccessful()) {
                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri3) {
                                                                            final String url3 = uri3.toString();
                                                                            if (colores.size() > 3 && colores.get(3) != null && colores.get(3).exists()) {
                                                                                mImageProvider.save2(context, colores.get(3), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                        if (task4.isSuccessful()) {
                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri uri4) {
                                                                                                    final String url4 = uri4.toString();
                                                                                                    if (colores.size() > 4 && colores.get(4) != null && colores.get(4).exists()) {
                                                                                                        mImageProvider.save2(context, colores.get(4), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(4)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task5) {
                                                                                                                if (task5.isSuccessful()) {
                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Uri uri5) {
                                                                                                                            final String url5 = uri5.toString();
                                                                                                                            if (colores.size() > 5 && colores.get(5) != null && colores.get(5).exists()) {
                                                                                                                                mImageProvider.save2(context, colores.get(5), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(5)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task6) {
                                                                                                                                        if (task6.isSuccessful()) {
                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                @Override
                                                                                                                                                public void onSuccess(Uri uri6) {
                                                                                                                                                    final String url6 = uri6.toString();
                                                                                                                                                    if (colores.size() > 6 && colores.get(6) != null && colores.get(6).exists()) {
                                                                                                                                                        mImageProvider.save2(context, colores.get(6), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(6)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task7) {
                                                                                                                                                                if (task7.isSuccessful()) {
                                                                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onSuccess(Uri uri7) {
                                                                                                                                                                            final String url7 = uri7.toString();
                                                                                                                                                                            if (colores.size() > 7 && colores.get(7) != null && colores.get(7).exists()) {
                                                                                                                                                                                mImageProvider.save2(context, colores.get(7), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(7)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task8) {
                                                                                                                                                                                        if (task8.isSuccessful()) {
                                                                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                                                @Override
                                                                                                                                                                                                public void onSuccess(Uri uri8) {
                                                                                                                                                                                                    final String url8 = uri8.toString();
                                                                                                                                                                                                    guardarColor1(url1, url2, url3, url4, url5, url6, url7, url8, posicion);
                                                                                                                                                                                                }
                                                                                                                                                                                            });
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                });
                                                                                                                                                                            } else {
                                                                                                                                                                                guardarColor1(url1, url2, url3, url4, url5, url6, url7, null, posicion);
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    });
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                                    } else {
                                                                                                                                                        guardarColor1(url1, url2, url3, url4, url5, url6, null, null, posicion);
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            } else {
                                                                                                                                guardarColor1(url1, url2, url3, url4, url5, null, null, null, posicion);
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                    } else {
                                                                                                        guardarColor1(url1, url2, url3, url4, null, null, null, null, posicion);
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                guardarColor1(url1, url2, url3, null, null, null, null, null, posicion);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        guardarColor1(url1, url2, null, null, null, null, null, null, posicion);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                guardarColor1(url1, null, null, null, null, null, null, null, posicion);
                            }
                        }
                    });
                }
            }
        });
    }

    private void guardarColor1(String url1, String url2, String url3, String url4, String url5, String url6, String url7, String url8, int posicion) {
        urls.clear();
        String imagenColores = "";
        if (posicion == 1) {
            imagenColores = "imagenesColores1";
        } else if (posicion == 2) {
            imagenColores = "imagenesColores2";
        } else if (posicion == 3) {
            imagenColores = "imagenesColores3";
        } else if (posicion == 4) {
            imagenColores = "imagenesColores4";
        } else if (posicion == 5) {
            imagenColores = "imagenesColores5";
        } else if (posicion == 6) {
            imagenColores = "imagenesColores6";
        }
        Map<String, Object> updates = new HashMap<>();
        updates.clear();
        imagenesColores.clear();
        if (url1 != null) {
            imagenesColores.add(url1);
        }
        if (url2 != null) {
            imagenesColores.add(url2);
        }
        if (url3 != null) {
            imagenesColores.add(url3);
        }
        if (url4 != null) {
            imagenesColores.add(url4);
        }
        if (url5 != null) {
            imagenesColores.add(url5);
        }
        if (url6 != null) {
            imagenesColores.add(url6);
        }
        if (url7 != null) {
            imagenesColores.add(url7);
        }
        if (url8 != null) {
            imagenesColores.add(url8);
        }
        updates.put(imagenColores, imagenesColores);
        mpostProvider.updatePost3(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (files2.size() > 0) {
                        if (coloresLista.get(1).equals(coloresLista.get(2))) {
                            String color = coloresLista.get(2);
                            coloresLista.remove(2);
                            coloresLista.add(2, color + 2);
                        }
                        guardaColores2(files2, 2, coloresLista.get(2));
                    } else {
                        progressBar.setVisibility(View.GONE);
                        btnURL.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void guardaColores2(List<File> colores, int posicion, String nombreColor) {
        List<String> contador = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        mImageProvider.save2(context, colores.get(0), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(0)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            final String url1 = uri1.toString();
                            if (colores.size() > 1 && colores.get(1) != null && colores.get(1).exists()) {
                                mImageProvider.save2(context, colores.get(1), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                        if (task2.isSuccessful()) {
                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri2) {
                                                    final String url2 = uri2.toString();
                                                    if (colores.size() > 2 && colores.get(2) != null && colores.get(2).exists()) {
                                                        mImageProvider.save2(context, colores.get(2), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task3) {
                                                                if (task3.isSuccessful()) {
                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri3) {
                                                                            final String url3 = uri3.toString();
                                                                            if (colores.size() > 3 && colores.get(3) != null && colores.get(3).exists()) {
                                                                                mImageProvider.save2(context, colores.get(3), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                        if (task4.isSuccessful()) {
                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri uri4) {
                                                                                                    final String url4 = uri4.toString();
                                                                                                    if (colores.size() > 4 && colores.get(4) != null && colores.get(4).exists()) {
                                                                                                        mImageProvider.save2(context, colores.get(4), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(4)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task5) {
                                                                                                                if (task5.isSuccessful()) {
                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Uri uri5) {
                                                                                                                            final String url5 = uri5.toString();
                                                                                                                            if (colores.size() > 5 && colores.get(5) != null && colores.get(5).exists()) {
                                                                                                                                mImageProvider.save2(context, colores.get(5), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(5)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task6) {
                                                                                                                                        if (task6.isSuccessful()) {
                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                @Override
                                                                                                                                                public void onSuccess(Uri uri6) {
                                                                                                                                                    final String url6 = uri6.toString();
                                                                                                                                                    if (colores.size() > 6 && colores.get(6) != null && colores.get(6).exists()) {
                                                                                                                                                        mImageProvider.save2(context, colores.get(6), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(6)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task7) {
                                                                                                                                                                if (task7.isSuccessful()) {
                                                                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onSuccess(Uri uri7) {
                                                                                                                                                                            final String url7 = uri7.toString();
                                                                                                                                                                            if (colores.size() > 7 && colores.get(7) != null && colores.get(7).exists()) {
                                                                                                                                                                                mImageProvider.save2(context, colores.get(7), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(7)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task8) {
                                                                                                                                                                                        if (task8.isSuccessful()) {
                                                                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                                                @Override
                                                                                                                                                                                                public void onSuccess(Uri uri8) {
                                                                                                                                                                                                    final String url8 = uri8.toString();
                                                                                                                                                                                                    guardarColor2(url1, url2, url3, url4, url5, url6, url7, url8, posicion);
                                                                                                                                                                                                }
                                                                                                                                                                                            });
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                });
                                                                                                                                                                            } else {
                                                                                                                                                                                guardarColor2(url1, url2, url3, url4, url5, url6, url7, null, posicion);
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    });
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                                    } else {
                                                                                                                                                        guardarColor2(url1, url2, url3, url4, url5, url6, null, null, posicion);
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            } else {
                                                                                                                                guardarColor2(url1, url2, url3, url4, url5, null, null, null, posicion);
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                    } else {
                                                                                                        guardarColor2(url1, url2, url3, url4, null, null, null, null, posicion);
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                guardarColor2(url1, url2, url3, null, null, null, null, null, posicion);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        guardarColor2(url1, url2, null, null, null, null, null, null, posicion);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                guardarColor2(url1, null, null, null, null, null, null, null, posicion);
                            }
                        }
                    });
                }
            }
        });
    }

    private void guardarColor2(String url1, String url2, String url3, String url4, String url5, String url6, String url7, String url8, int posicion) {
        urls.clear();
        String imagenColores = "";
        if (posicion == 1) {
            imagenColores = "imagenesColores1";
        } else if (posicion == 2) {
            imagenColores = "imagenesColores2";
        } else if (posicion == 3) {
            imagenColores = "imagenesColores3";
        } else if (posicion == 4) {
            imagenColores = "imagenesColores4";
        } else if (posicion == 5) {
            imagenColores = "imagenesColores5";
        } else if (posicion == 6) {
            imagenColores = "imagenesColores6";
        }
        Map<String, Object> updates = new HashMap<>();
        updates.clear();
        imagenesColores.clear();
        if (url1 != null) {
            imagenesColores.add(url1);
        }
        if (url2 != null) {
            imagenesColores.add(url2);
        }
        if (url3 != null) {
            imagenesColores.add(url3);
        }
        if (url4 != null) {
            imagenesColores.add(url4);
        }
        if (url5 != null) {
            imagenesColores.add(url5);
        }
        if (url6 != null) {
            imagenesColores.add(url6);
        }
        if (url7 != null) {
            imagenesColores.add(url7);
        }
        if (url8 != null) {
            imagenesColores.add(url8);
        }
        updates.put(imagenColores, imagenesColores);
        mpostProvider.updatePost3(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (files3.size() > 0) {
                        guardaColores3(files3, 3, coloresLista.get(3));
                    } else {
                        progressBar.setVisibility(View.GONE);
                        btnURL.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void guardaColores3(List<File> colores, int posicion, String nombreColor) {
        List<String> contador = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        mImageProvider.save2(context, colores.get(0), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(0)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            final String url1 = uri1.toString();
                            if (colores.size() > 1 && colores.get(1) != null && colores.get(1).exists()) {
                                mImageProvider.save2(context, colores.get(1), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                        if (task2.isSuccessful()) {
                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri2) {
                                                    final String url2 = uri2.toString();
                                                    if (colores.size() > 2 && colores.get(2) != null && colores.get(2).exists()) {
                                                        mImageProvider.save2(context, colores.get(2), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task3) {
                                                                if (task3.isSuccessful()) {
                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri3) {
                                                                            final String url3 = uri3.toString();
                                                                            if (colores.size() > 3 && colores.get(3) != null && colores.get(3).exists()) {
                                                                                mImageProvider.save2(context, colores.get(3), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                        if (task4.isSuccessful()) {
                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri uri4) {
                                                                                                    final String url4 = uri4.toString();
                                                                                                    if (colores.size() > 4 && colores.get(4) != null && colores.get(4).exists()) {
                                                                                                        mImageProvider.save2(context, colores.get(4), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(4)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task5) {
                                                                                                                if (task5.isSuccessful()) {
                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Uri uri5) {
                                                                                                                            final String url5 = uri5.toString();
                                                                                                                            if (colores.size() > 5 && colores.get(5) != null && colores.get(5).exists()) {
                                                                                                                                mImageProvider.save2(context, colores.get(5), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(5)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task6) {
                                                                                                                                        if (task6.isSuccessful()) {
                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                @Override
                                                                                                                                                public void onSuccess(Uri uri6) {
                                                                                                                                                    final String url6 = uri6.toString();
                                                                                                                                                    if (colores.size() > 6 && colores.get(6) != null && colores.get(6).exists()) {
                                                                                                                                                        mImageProvider.save2(context, colores.get(6), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(6)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task7) {
                                                                                                                                                                if (task7.isSuccessful()) {
                                                                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onSuccess(Uri uri7) {
                                                                                                                                                                            final String url7 = uri7.toString();
                                                                                                                                                                            if (colores.size() > 7 && colores.get(7) != null && colores.get(7).exists()) {
                                                                                                                                                                                mImageProvider.save2(context, colores.get(7), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(7)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task8) {
                                                                                                                                                                                        if (task8.isSuccessful()) {
                                                                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                                                @Override
                                                                                                                                                                                                public void onSuccess(Uri uri8) {
                                                                                                                                                                                                    final String url8 = uri8.toString();
                                                                                                                                                                                                    guardarColor3(url1, url2, url3, url4, url5, url6, url7, url8, posicion);
                                                                                                                                                                                                }
                                                                                                                                                                                            });
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                });
                                                                                                                                                                            } else {
                                                                                                                                                                                guardarColor3(url1, url2, url3, url4, url5, url6, url7, null, posicion);
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    });
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                                    } else {
                                                                                                                                                        guardarColor3(url1, url2, url3, url4, url5, url6, null, null, posicion);
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            } else {
                                                                                                                                guardarColor3(url1, url2, url3, url4, url5, null, null, null, posicion);
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                    } else {
                                                                                                        guardarColor3(url1, url2, url3, url4, null, null, null, null, posicion);
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                guardarColor3(url1, url2, url3, null, null, null, null, null, posicion);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        guardarColor3(url1, url2, null, null, null, null, null, null, posicion);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                guardarColor3(url1, null, null, null, null, null, null, null, posicion);
                            }
                        }
                    });
                }
            }
        });
    }

    private void guardarColor3(String url1, String url2, String url3, String url4, String url5, String url6, String url7, String url8, int posicion) {
        urls.clear();
        String imagenColores = "";
        if (posicion == 1) {
            imagenColores = "imagenesColores1";
        } else if (posicion == 2) {
            imagenColores = "imagenesColores2";
        } else if (posicion == 3) {
            imagenColores = "imagenesColores3";
        } else if (posicion == 4) {
            imagenColores = "imagenesColores4";
        } else if (posicion == 5) {
            imagenColores = "imagenesColores5";
        } else if (posicion == 6) {
            imagenColores = "imagenesColores6";
        }
        Map<String, Object> updates = new HashMap<>();
        updates.clear();
        imagenesColores.clear();
        if (url1 != null) {
            imagenesColores.add(url1);
        }
        if (url2 != null) {
            imagenesColores.add(url2);
        }
        if (url3 != null) {
            imagenesColores.add(url3);
        }
        if (url4 != null) {
            imagenesColores.add(url4);
        }
        if (url5 != null) {
            imagenesColores.add(url5);
        }
        if (url6 != null) {
            imagenesColores.add(url6);
        }
        if (url7 != null) {
            imagenesColores.add(url7);
        }
        if (url8 != null) {
            imagenesColores.add(url8);
        }
        updates.put(imagenColores, imagenesColores);
        mpostProvider.updatePost3(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (files4.size() > 0) {
                        guardaColores4(files4, 4, coloresLista.get(4));
                    } else {
                        progressBar.setVisibility(View.GONE);
                        btnURL.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void guardaColores4(List<File> colores, int posicion, String nombreColor) {
        List<String> contador = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        mImageProvider.save2(context, colores.get(0), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(0)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            final String url1 = uri1.toString();
                            if (colores.size() > 1 && colores.get(1) != null && colores.get(1).exists()) {
                                mImageProvider.save2(context, colores.get(1), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                        if (task2.isSuccessful()) {
                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri2) {
                                                    final String url2 = uri2.toString();
                                                    if (colores.size() > 2 && colores.get(2) != null && colores.get(2).exists()) {
                                                        mImageProvider.save2(context, colores.get(2), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task3) {
                                                                if (task3.isSuccessful()) {
                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri3) {
                                                                            final String url3 = uri3.toString();
                                                                            if (colores.size() > 3 && colores.get(3) != null && colores.get(3).exists()) {
                                                                                mImageProvider.save2(context, colores.get(3), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task4) {
                                                                                        if (task4.isSuccessful()) {
                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri uri4) {
                                                                                                    final String url4 = uri4.toString();
                                                                                                    if (colores.size() > 4 && colores.get(4) != null && colores.get(4).exists()) {
                                                                                                        mImageProvider.save2(context, colores.get(4), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(4)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task5) {
                                                                                                                if (task5.isSuccessful()) {
                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Uri uri5) {
                                                                                                                            final String url5 = uri5.toString();
                                                                                                                            if (colores.size() > 5 && colores.get(5) != null && colores.get(5).exists()) {
                                                                                                                                mImageProvider.save2(context, colores.get(5), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(5)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task6) {
                                                                                                                                        if (task6.isSuccessful()) {
                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                @Override
                                                                                                                                                public void onSuccess(Uri uri6) {
                                                                                                                                                    final String url6 = uri6.toString();
                                                                                                                                                    if (colores.size() > 6 && colores.get(6) != null && colores.get(6).exists()) {
                                                                                                                                                        mImageProvider.save2(context, colores.get(6), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(6)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task7) {
                                                                                                                                                                if (task7.isSuccessful()) {
                                                                                                                                                                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onSuccess(Uri uri7) {
                                                                                                                                                                            final String url7 = uri7.toString();
                                                                                                                                                                            if (colores.size() > 7 && colores.get(7) != null && colores.get(7).exists()) {
                                                                                                                                                                                mImageProvider.save2(context, colores.get(7), carpeta1, carpeta2, carpeta3, nombreImagen, nombreImagen + "_" + nombreColor + contador.get(7)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task8) {
                                                                                                                                                                                        if (task8.isSuccessful()) {
                                                                                                                                                                                            mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                                                                                                                @Override
                                                                                                                                                                                                public void onSuccess(Uri uri8) {
                                                                                                                                                                                                    final String url8 = uri8.toString();
                                                                                                                                                                                                    guardarColor4(url1, url2, url3, url4, url5, url6, url7, url8, posicion);
                                                                                                                                                                                                }
                                                                                                                                                                                            });
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                });
                                                                                                                                                                            } else {
                                                                                                                                                                                guardarColor4(url1, url2, url3, url4, url5, url6, url7, null, posicion);
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    });
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                                    } else {
                                                                                                                                                        guardarColor4(url1, url2, url3, url4, url5, url6, null, null, posicion);
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            } else {
                                                                                                                                guardarColor4(url1, url2, url3, url4, url5, null, null, null, posicion);
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                    } else {
                                                                                                        guardarColor4(url1, url2, url3, url4, null, null, null, null, posicion);
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                guardarColor4(url1, url2, url3, null, null, null, null, null, posicion);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        guardarColor4(url1, url2, null, null, null, null, null, null, posicion);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                guardarColor4(url1, null, null, null, null, null, null, null, posicion);
                            }
                        }
                    });
                }
            }
        });
    }

    private void guardarColor4(String url1, String url2, String url3, String url4, String url5, String url6, String url7, String url8, int posicion) {
        urls.clear();
        String imagenColores = "";
        if (posicion == 1) {
            imagenColores = "imagenesColores1";
        } else if (posicion == 2) {
            imagenColores = "imagenesColores2";
        } else if (posicion == 3) {
            imagenColores = "imagenesColores3";
        } else if (posicion == 4) {
            imagenColores = "imagenesColores4";
        } else if (posicion == 5) {
            imagenColores = "imagenesColores5";
        } else if (posicion == 6) {
            imagenColores = "imagenesColores6";
        }
        Map<String, Object> updates = new HashMap<>();
        updates.clear();
        imagenesColores.clear();
        if (url1 != null) {
            imagenesColores.add(url1);
        }
        if (url2 != null) {
            imagenesColores.add(url2);
        }
        if (url3 != null) {
            imagenesColores.add(url3);
        }
        if (url4 != null) {
            imagenesColores.add(url4);
        }
        if (url5 != null) {
            imagenesColores.add(url5);
        }
        if (url6 != null) {
            imagenesColores.add(url6);
        }
        if (url7 != null) {
            imagenesColores.add(url7);
        }
        if (url8 != null) {
            imagenesColores.add(url8);
        }
        updates.put(imagenColores, imagenesColores);
        mpostProvider.updatePost3(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (files4.size() > 0) {
                        //guardaColores5(files5, 5, coloresLista.get(5));
                    } else {
                        progressBar.setVisibility(View.GONE);
                        btnURL.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(context, "Hubo un error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //endregion

    //region PROCESO DE YAMAHA
    private void llenarInfoYamaha() {
        nombreMoto.clear();
        try {
            //conexion
            Document doc = Jsoup.connect(url).get();

            //ficha tecnica
            Element specificationInfoDiv = doc.selectFirst("div[class=specification-info]");
            Elements DatosFichaTecnicaMotos = specificationInfoDiv.select("tr");

            Elements descripcionMotos = doc.select("span.current");
            Elements imagenesAdicionales = doc.select("a[data-fancybox=gallery]");
            colorPrincipalDiv = doc.select("label.check");
            for (Element label : colorPrincipalDiv) {
                // Obtener las imagenes de cada color
                imagenesColores1.add(label.attr("data-color"));
            }


//            if (catalogoPartes.size() >= 2) {
//                Element catalogoPDF = catalogoPartes.get(1);
//                String url = catalogoPDF.attr("href");
//                guardarPDF(url);
//            }

            if (descripcionMotos.size() >= 1) {
                for (Element element : descripcionMotos) {
                    // Obtener el año
                    modelo = element.text();
                    Element precioMotos = doc.selectFirst("div.box-introduction");
                    String precio = precioMotos.selectFirst("strong").text();

                    // Verificar si el siguiente elemento hermano es un enlace <a>
                    Element nextSibling = element.nextElementSibling();
                    if (nextSibling != null && nextSibling.tagName().equals("a")) {
                        // Obtener la URL del enlace
                        String urlNew = nextSibling.attr("href");
                        if (Integer.parseInt(modelo) == 2024) {
                            modelo2 = "2025";
                        } else {
                            modelo2 = "2024";
                        }
                        try {
                            doc = Jsoup.connect(urlNew).get();
                        } catch (Exception e) {

                        }
                    }
                }
            }
            if (colorPrincipalDiv.size() >= 2) {

                color = doc.select("div.front-end.box");
                for (Element colores : color) {
                    String style = colores.attr("style");


                    int colorIndex = style.indexOf("background-color:#");

                    String color = style.substring(colorIndex + 17, colorIndex + 24);
                    coloresLista.add(color);
//                    String[] parts = color.split("/");
//                    String lastPart = parts[parts.length - 1]; // Obtener la última parte de la URL
//                    String[] nameParts = lastPart.split("_"); // Dividir el nombre del archivo en partes separadas por "_"
//                    String colorHexa = nameParts[nameParts.length - 1]; // El color debería estar en la última parte
//                    colorHexa = colorHexa.split("\\.")[0]; // Eliminar la extensión del archivo (".png")
//                    String colorName = getColorName(colorHexa);


                }
                for (int i = 0; i < imagenesColores1.size(); i++) {
                    if (i == 0) {
                        try {
                            //convierto las url a imagenes
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColores1.get(i))
                                    .submit();
                            imagen1 = futureTarget.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }

                    } else if (i == 1) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColores1.get(i))
                                    .submit();
                            files1.add(futureTarget.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 2) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColores1.get(i))
                                    .submit();
                            files2.add(futureTarget.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 3) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColores1.get(i))
                                    .submit();
                            files3.add(futureTarget.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 4) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColores1.get(i))
                                    .submit();
                            files4.add(futureTarget.get());

                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 5) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColores1.get(i))
                                    .submit();
                            files5.add(futureTarget.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 6) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColores1.get(i))
                                    .submit();
                            files6.add(futureTarget.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    imagenesColorPrincipal.clear();
                    if (i == 0) {
                        //guardarImagenesPrincipales(imagen1, null, null, null, null, null, null, null);
                    }
                }
            }
            for (Element row : DatosFichaTecnicaMotos) {
                // Obtener las celdas de la fila
                Elements cells = row.select("td");
                // Verificar si la fila tiene exactamente dos celdas
                if (cells.size() == 2) {
                    nombreMoto.add(cells.get(0).text());
                    nombreMoto.add(cells.get(1).text());
                }
            }

            if (imagenesAdicionales.size() >= 2) {
                imagenesAdd.clear();
                imagenesColores.clear();
                for (int i = 0; i < imagenesAdicionales.size(); i++) {
                    Element imagen = imagenesAdicionales.get(i);
                    recuperarImagenes.add(imagen.attr("href"));
                }
                //obetener texto de imagenes:
                Elements divItems = doc.select("div.items");
                for (Element divItem : divItems) {
                    Element h2Element = divItem.selectFirst("p");
                    if (h2Element != null) {
                        //OBTIENE EL TEXTO DE LAS IMAGENES
                        String h2Text = h2Element.text();
                        imagenesColores.add(h2Text);
                    }
                }
                for (int i = 0; i < imagenesAdicionales.size(); i++) {
                    try {
                        //convierto las url a imagenes
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(recuperarImagenes.get(i))
                                .submit();

                        if (i == 0) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 1) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 2) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 3) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 4) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 5) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 6) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 7) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 8) {
                            imagenesAdd.add(futureTarget.get());
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Elements divItems = doc.select("div.items");
                imagenesColores.clear();
                imagenesAdd.clear();

                // Iterar sobre cada elemento div con la clase "items"
                for (Element divItem : divItems) {
                    // Obtener el elemento img dentro de cada div "items"
                    Element imgElement = divItem.selectFirst("img");
                    if (imgElement != null) {
                        // Obtener la URL de la imagen
                        String imgUrl = imgElement.attr("src");
                        recuperarImagenes.add(imgUrl);
                    }
                    Element h2Element = divItem.selectFirst("p");
                    if (h2Element != null) {
                        //OBTIENE EL TEXTO DE LAS IMAGENES
                        String h2Text = h2Element.text();
                        imagenesColores.add(h2Text);
                    }
                }
                for (int i = 0; i < recuperarImagenes.size(); i++) {
                    try {
                        //convierto las url a imagenes
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(recuperarImagenes.get(i))
                                .submit();

                        if (i == 0) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 1) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 2) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 3) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 4) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 5) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 6) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 7) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 8) {
                            imagenesAdd.add(futureTarget.get());
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
            guardarImagenesPrincipales(imagen1, null, null, null, null, null, null, null);
            //melo hasta aqui

            Map<String, Object> updates = new HashMap<>();
            for (int i = 0; i < nombreMoto.size(); i++) {
                //quita los espacios, tildes, parentesis, puntos:, y lo coloca en minuscula
                String textoSinEspacios = nombreMoto.get(i)
                        .replaceAll("\\s+|\\p{InCombiningDiacriticalMarks}|\\(.*?\\)|:", "")
                        .toLowerCase();
                //quita las tildes
                textoSinEspacios = Normalizer.normalize(textoSinEspacios, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                updates.put(textoSinEspacios, nombreMoto.get(i + 1));
                i++;
            }
            // problema con los datos a guardar de la ficha tecnica

            updates.put("caracteristicasTexto", imagenesColores);
            if (modelo.matches(".*[a-zA-Z].*")) {
                updates.put("Modelo", modelo);
                if (!modelo2.isEmpty()) {
                    updates.put("Modelo2", modelo2);
                }
            } else {
                updates.put("modelo", "Modelo " + modelo);
                if (!modelo2.isEmpty()) {
                    updates.put("modelo2", "Modelo" + modelo2);
                }
            }

            updates.put("consumoPorGalon", consumoGasolina);
            updates.put("visible", true);

            mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            });
        } catch (IOException e) {
            Log.e("MainActivity", "" + e.getMessage());
        }
    }

    //saber el color aproximado
//    public static String getColorName(String colorHexadeximal) {
//        // Convertir el color hexadecimal a RGB
//        int color = Color.parseColor(colorHexadeximal);
//        int r = Color.red(color);
//        int g = Color.green(color);
//        int b = Color.blue(color);
//
//        // Asignar nombres aproximados basados en los valores RGB
//        if (isBlack(r, g, b)) {
//            return "Negro";
//        } else if (isWhite(r, g, b)) {
//            return "Blanco";
//        } else if (isHueso(r, g, b)) {
//            return "Blanco";
//        } else if (isRed(r, g, b)) {
//            return "Rojo";
//        } else if (isGreen(r, g, b)) {
//            return "Verde";
//        } else if (isDarkGreen(r, g, b)) {
//            return "Verde Oscuro";
//        } else if (isBlue(r, g, b)) {
//            return "Azul";
//        } else if (isGold(r, g, b)) {
//            return "Dorado";
//        } else if (isDarkBlue(r, g, b)) {
//            return "Azul Oscuro";
//        } else if (isDarkRed(r, g, b)) {
//            return "Rojo Oscuro";
//        } else if (isPurple(r, g, b)) {
//            return "Morado";
//        } else if (isYellow(r, g, b)) {
//            return "Amarillo";
//        } else if (isSkyBlue(r, g, b)) {
//            return "Celeste";
//        }
//        // Agregar más condiciones según sea necesario para otros colores
//        // Si el color no coincide con ninguno de los nombres predefinidos, puedes devolver "Desconocido" o algo similar
//        return "AZUL";
//    }
//
//    private static boolean isBlack(int r, int g, int b) {
//        return r <= 10 && g <= 10 && b <= 10;
//    }
//
//    private static boolean isWhite(int r, int g, int b) {
//        return r >= 240 && g >= 240 && b >= 240;
//    }
//
//    private static boolean isHueso(int r, int g, int b) {
//        return r >= 232 && g >= 232 && b >= 232;
//    }
//
//    private static boolean isRed(int r, int g, int b) {
//        return r >= 200 && g <= 100 && b <= 100;
//    }
//
//    private static boolean isGreen(int r, int g, int b) {
//        return r <= 100 && g >= 200 && b <= 100;
//    }
//
//    private static boolean isDarkGreen(int r, int g, int b) {
//        // Ajusta estos valores según los tonos de azul oscuro que desees reconocer
//        return r <= 57 && g <= 90 && b >= 86;
//    }
//
//    private static boolean isBlue(int r, int g, int b) {
//        return r <= 100 && g <= 100 && b >= 200;
//    }
//
//    private static boolean isGold(int r, int g, int b) {
//        // Ajusta estos valores según los tonos de dorado que desees reconocer
//        return r >= 160 && g >= 160 && g <= 220 && b <= 56;
//    }
//
//    private static boolean isDarkBlue(int r, int g, int b) {
//        // Ajusta estos valores según los tonos de azul oscuro que desees reconocer
//        return r <= 30 && g <= 30 && b >= 100;
//    }
//
//    private static boolean isDarkRed(int r, int g, int b) {
//        // Ajusta estos valores según los tonos de rojo oscuro que desees reconocer
//        return r >= 100 && g <= 30 && b <= 30;
//    }
//
//    private static boolean isPurple(int r, int g, int b) {
//        // Ajusta estos valores según los tonos de morado que desees reconocer
//        return r >= 100 && g <= 30 && b >= 100;
//    }
//
//    private static boolean isYellow(int r, int g, int b) {
//        // Ajusta estos valores según los tonos de amarillo que desees reconocer
//        return r >= 200 && g >= 200 && b <= 50;
//    }
//
//    private static boolean isSkyBlue(int r, int g, int b) {
//        // Ajustamos los valores para definir el color celeste
//        return r >= 27 && g >= 180 && b >= 185;
//    }


//endregion

    //region PROCESO DE HERO

    private void llenarInfoHERO() {
        nombreMoto.clear();
        List<String> h5 = new ArrayList<>();
        List<String> h4 = new ArrayList<>();
        List<String> informacion = new ArrayList<>();
        try {
            //conexion
            Document doc = Jsoup.connect(url).get();

            //Ficha tecnica
            Elements FichaTecnica = doc.select("div.elementor-tabs");
            Elements descripcionPagina = doc.select("div.elementor-widget-container");

            if (FichaTecnica != null) {
                // Selecciona el elemento <h5> dentro del div
                Elements h5Elements = FichaTecnica.select("h5");
                Elements pElements = FichaTecnica.select("p");
                Elements h3Elements = descripcionPagina.select("h3");

                for (Element h3Element : h3Elements) {
                    // Obtén el texto del elemento <h5>
                    informacion.add(h3Element.text());
                }
                if (!informacion.get(5).equals("Motor")) {
                    descripcion = informacion.get(0);
                }

                contador = 0;
                Elements h4Elements = FichaTecnica.select("h4");

                // Itera sobre los elementos <h5> seleccionados y realiza la operación deseada
                for (Element h4Element : h4Elements) {
                    // Obtén el texto del elemento <h4>
                    // Eliminar tildes y normalizar el texto

                    if (contador < 2) {
                        String textoNormalizado = Normalizer.normalize(h4Element.text(), Normalizer.Form.NFD);
                        textoNormalizado = textoNormalizado.replaceAll("[^\\p{ASCII}]", "");

                        // Eliminar signos de puntuación
                        textoNormalizado = textoNormalizado.replaceAll("[\\p{Punct}]", "");

                        // Convertir a minúsculas para comparación insensible a mayúsculas
                        textoNormalizado = textoNormalizado.toLowerCase();
                        h4.add(textoNormalizado);
                    } else {
                        h4.add(h4Element.text());
                    }
                }
                // Itera sobre los elementos <h5> seleccionados y realiza la operación deseada
                contador = 0;

                for (Element h5Element : h5Elements) {
                    // Obtén el texto del elemento <h5>
                    // Eliminar tildes y normalizar el texto

                    if (contador < 2) {
                        String textoNormalizado = Normalizer.normalize(h5Element.text(), Normalizer.Form.NFD);
                        textoNormalizado = textoNormalizado.replaceAll("[^\\p{ASCII}]", "");

                        // Eliminar signos de puntuación
                        textoNormalizado = textoNormalizado.replaceAll("[\\p{Punct}]", "");

                        // Convertir a minúsculas para comparación insensible a mayúsculas
                        textoNormalizado = textoNormalizado.toLowerCase();
                        h5.add(textoNormalizado);
                    } else {

                        h5.add(h5Element.text());
                    }
                    contador++;
                }

                // Divide el string en palabras utilizando espacios como separador
                String[] words = h5.get(0).split(" ");
                // Devuelve la primera palabra (si existe)
                String palabra = "";
                if (words.length > 0) {
                    if (words[0].equals("compartenos")) {
                        palabra = words[0];
                    }
                    if (words[0].equals("*los")) {
                        palabra = words[0];
                    }
                }

                borrarpalabrasHero(h5, palabra);
                words = h4.get(0).split(" ");
                // Devuelve la primera palabra (si existe)
                if (words.length > 0) {
                    if (words[0].equals("compartenos")) {
                        palabra = words[0];
                    }
                    if (words[0].equals("*los")) {
                        palabra = words[0];
                    }
                }
                borrarpalabrasHero(h4, palabra);


                int indice = h4.indexOf("delanteros");
                //esto es por que hay unas motos que tienen <P> en lugar de H4
                if (h5.size() != h4.size()) {
                    for (Element pElement : pElements) {
                        String dato = pElement.text();

                        String[] palabrasBuscar = {"disco", "Disco, ABS", "Disco", "Hidráulico", "Tambor", "IBS"};
                        String patron = String.join("|", palabrasBuscar);
                        Pattern pattern = Pattern.compile("\\b(" + patron + ")\\b", Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(dato);
                        while (matcher.find()) {
                            h5.add(indice, dato);
                        }

                    }
                }
            }


            nombreMoto.clear();
            for (int i = 0; i < h5.size(); i++) {
                nombreMoto.add(h4.get(i));
                nombreMoto.add(h5.get(i));
            }

            //Año modelo
            Elements modelo = doc.select("div.elementor-container.elementor-column-gap-default");
            if (modelo != null) {
                // Selecciona el elemento <h5> dentro del div
                Elements h4Elements = modelo.select("h4");

                // Itera sobre los elementos <h5> seleccionados y realiza la operación deseada
                for (Element h4Element : h4Elements) {
                    // Obtén el texto del elemento <h5>
                    String anoModelo = h4Element.text();
                    if (anoModelo.contains("2025")) {
                        modelo2 = "2025";
                    }
                    if (anoModelo.contains("2024")) {
                        modelo2 = "2024";
                    }
                }
            }

            //Imagenes - colores
            imagenesColores1.clear();
            imagenesColores2.clear();
            Elements colorPrincipalDiv = doc.select("div.swiper-slide");
            for (Element swiperSlideDiv : colorPrincipalDiv) {
                // Selecciona todas las imágenes <img> con la clase "swiper-slide-image" dentro del div "swiper-slide"
                Elements imagenes = swiperSlideDiv.select("img.swiper-slide-image");

                // Itera sobre cada imagen <img> seleccionada
                for (Element image : imagenes) {
                    // Obtén el atributo "src" para obtener la URL de la imagen
                    imagenesColores1.add(image.attr("src"));
                    imagenesColores2.add(image.attr("alt"));


                    String[] colores = {
                            "Negro", "Negra", "Gris", "Rojo", "Roja", "Azul", "Lima", "Verde", "Amarillo",
                            "Marrón", "Blanco", "Blanca", "Naranja", "Morado", "Turquesa", "Cian",
                            "Violeta", "Magenta", "Plateado", "Dorado", "Rosa", "Celeste",
                            "Carmesi", "VerdeOscuro", "VerdeClaro", "VerdeLima", "AzulCielo",

                    };

                    // Combina todos los colores en un patrón de expresión regular
                    String colorPattern = String.join("|", colores);
                    Pattern pattern = Pattern.compile("_(?:" + colorPattern + ")(?:_(" + colorPattern + "))*");


                    // Itera sobre cada nombre de archivo
                    for (String fileName : imagenesColores2) {
                        // Encuentra coincidencias de colores usando la expresión regular
                        Matcher matcher = pattern.matcher(fileName);
                        if (matcher.find()) {
                            String foundColors = matcher.group(0).substring(1); // Quitar el primer guion bajo
                            String[] colorMatches = foundColors.split("_");
                            String colornew = "";
                            for (String color : colorMatches) {
                                colornew = colornew + "-" + color;
                            }
                            coloresLista.add(colornew.toUpperCase().substring(1));
                        } else {
                            // Si no se encuentra coincidencia, asume color negro como predeterminado
                            contador++;
                            coloresLista.add("NEGRO" + contador);
                        }
                        imagenesColores2.clear();
                    }
                    contador = 0;
                }
            }
            for (int i = 0; i < imagenesColores1.size(); i++) {
                if (i == 0) {
                    try {
                        //convierto las url a imagenes
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        imagen1 = futureTarget.get();
                        guardarImagenesPrincipales(imagen1, null, null, null, null, null, null, null);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                } else if (i == 1) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files1.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 2) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files2.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 3) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files3.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 4) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files4.add(futureTarget.get());

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 5) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files5.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 6) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files6.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                imagenesColorPrincipal.clear();
            }


            //ficha tecnica
            Map<String, Object> updates = new HashMap<>();
            for (int i = 0; i < nombreMoto.size(); i++) {
                //quita los espacios, tildes, parentesis, puntos:, y lo coloca en minuscula
                String textoSinEspacios = nombreMoto.get(i)
                        .replaceAll("\\s+|\\p{InCombiningDiacriticalMarks}|\\(.*?\\)|:", "")
                        .toLowerCase();
                //quita las tildes
                textoSinEspacios = Normalizer.normalize(textoSinEspacios, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                textoSinEspacios = textoSinEspacios.replace(".", "");
                textoSinEspacios = textoSinEspacios.replace("/", "");
                updates.put(textoSinEspacios, nombreMoto.get(i + 1));
                i++;
            }

            updates.put("modelo", modelo2);
            updates.put("descripcion", descripcion);
            updates.put("consumoPorGalon", consumoGasolina);
            updates.put("visible", true);
            mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //eliminar datos de auteco
                    Query query = mpostProvider.buscarPorNombreMoto(nombreImagen);
                    query.get().addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            QuerySnapshot querySnapshot = task2.getResult();
                            if (!querySnapshot.isEmpty()) {
                                // Obtiene el primer documento de los resultados
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                                List<String> camposNulos = new ArrayList<>();

                                // Verifica campos nulos en el documento
                                Map<String, Object> data = document.getData();
                                if (data != null) {
                                    for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                                        String nombreCampo = entry.getKey();
                                        Object valor = entry.getValue();

                                        // Verificar si el valor es null
                                        if (valor == null) {
                                            camposNulos.add(nombreCampo);
                                        }
                                    }
                                }
                                mpostProvider.eliminarDatosFichaTecnica(id, camposNulos).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                            }
                        }
                    });
                }
            });
        } catch (IOException e) {
            Log.e("MainActivity", "" + e.getMessage());
        }

    }

    private void borrarpalabrasHero(List<String> list, String prefix) {
        // Crear un iterator para recorrer la lista
        Iterator<String> iterator = list.iterator();

        // Recorrer la lista con el iterator
        while (iterator.hasNext()) {
            String word = iterator.next();
            // Verificar si la palabra empieza con el prefijo
            if (word.startsWith(prefix)) {
                // Eliminar la palabra de la lista
                iterator.remove();
            }
        }
    }

    //endregion

    //region PROCESO DE SUZUKI


    private void llenarInfoSUZUKI() {
        nombreMoto.clear();
        List<String> titulo = new ArrayList<>();
        List<String> contenido = new ArrayList<>();
        List<String> informacion = new ArrayList<>();

        String descripcion = "";
        try {
            //conexion
            Document doc = Jsoup.connect(url).get();


            Elements descripcionPagina = doc.select("div.descripcion.col-md-12.col-sm-12.col-xs-12");
            Elements extraerModelo = doc.select("div.legal.col-md-12.col-sm-12.col-xs-12");
            Elements FichaTecnica = doc.select("div.col-md-12.col-sm-12.col-xs-12.cont-items.d-f.f-w-w");

            Elements ExtraerImagenesColores = doc.select("div.col-md-7.col-sm-7.col-xs-12.moto-color");
            Elements extraerImagenesAdd = doc.select("div.swiper-wrapper");

            if (descripcionPagina != null) {
                // Selecciona el elemento <h5> dentro del div
                Elements pElements = descripcionPagina.select("p");

                for (Element pElement : pElements) {
                    // Obtén el texto del elemento <h5>
                    descripcion = pElement.text();
                }
            }
            if (extraerModelo != null) {
                // Selecciona el elemento <h5> dentro del div
                Elements pElements = extraerModelo.select("p.modelo");


                for (Element pElement : pElements) {
                    // obtener el modelo de la moto
                    modelo = pElement.text();
                }
            }
            if (FichaTecnica != null) {
                // Selecciona el elemento <h5> dentro del div
                Elements h3Elements = FichaTecnica.select("h3");
                Elements liElements = FichaTecnica.select("li");

                for (Element h3Element : h3Elements) {
                    String textoNormalizado = Normalizer.normalize(h3Element.text(), Normalizer.Form.NFD);
                    textoNormalizado = textoNormalizado.replaceAll("[^\\p{ASCII}]", "");

                    // Eliminar signos de puntuación
                    textoNormalizado = textoNormalizado.replaceAll("[\\p{Punct}]", "");

                    // Convertir a minúsculas para comparación insensible a mayúsculas
                    textoNormalizado = textoNormalizado.toLowerCase();
                    titulo.add(textoNormalizado);
                }
                for (Element liElement : liElements) {
                    String textoNormalizado = Normalizer.normalize(liElement.text(), Normalizer.Form.NFD);
                    textoNormalizado = textoNormalizado.replaceAll("[^\\p{ASCII}]", "");

                    // Eliminar signos de puntuación
                    textoNormalizado = textoNormalizado.replaceAll("[\\p{Punct}]", "");

                    // Convertir a minúsculas para comparación insensible a mayúsculas
                    textoNormalizado = textoNormalizado.toLowerCase();
                    contenido.add(textoNormalizado);
                }
            }

            nombreMoto.clear();
            for (int i = 0; i < titulo.size(); i++) {
                nombreMoto.add(titulo.get(i));
                nombreMoto.add(contenido.get(i));
            }

            recuperarImagenes.clear();
            if (!extraerImagenesAdd.isEmpty()) {
                imagenesAdd.clear();
                imagenesColores.clear();
                //obtener las imagenes adicionales
                Elements imgElements = extraerImagenesAdd.first().select("img");
                for (Element imgElement : imgElements) {
                    String inicioPag = "http://www.suzuki.com.co";
                    if (!imgElement.text().contains(inicioPag)) {
                        recuperarImagenes.add(inicioPag + imgElement.attr("src"));
                    }
                }
                for (int i = 0; i < recuperarImagenes.size(); i++) {
                    try {
                        //convierto las url a imagenes
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(recuperarImagenes.get(i))
                                .submit();

                        if (i == 0) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 1) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 2) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 3) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 4) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 5) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 6) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 7) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 8) {
                            imagenesAdd.add(futureTarget.get());
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }

            imagenesColores1.clear();
            if (ExtraerImagenesColores != null) {
                // Selecciona el elemento <h5> dentro del div
                Elements imgElements = ExtraerImagenesColores.select("img.img-responsive");
                Elements liElements = ExtraerImagenesColores.select("li");


                for (Element imgElement : imgElements) {
                    //imagenes
                    String inicioPag = "http://www.suzuki.com.co";
                    if (!imgElement.text().contains(inicioPag)) {
                        imagenesColores1.add(inicioPag + imgElement.attr("src"));
                    }
                }
                for (Element liElement : liElements) {
                    String style = liElement.attr("style");
                    // Extraer el color hexadecimal del atributo 'style'
                    String hexColor = style.replaceAll("background-color: ", "").trim();
                    coloresLista.add(hexColor);
                }
            }

            for (int i = 0; i < imagenesColores1.size(); i++) {
                if (i == 0) {
                    try {
                        //convierto las url a imagenes
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        imagen1 = futureTarget.get();
                        guardarImagenesPrincipales(imagen1, null, null, null, null, null, null, null);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                } else if (i == 1) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files1.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 2) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files2.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 3) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files3.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 4) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files4.add(futureTarget.get());

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 5) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files5.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 6) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColores1.get(i))
                                .submit();
                        files6.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                imagenesColorPrincipal.clear();
            }


            //ficha tecnica
            Map<String, Object> updates = new HashMap<>();
            for (int i = 0; i < nombreMoto.size(); i++) {
                //quita los espacios, tildes, parentesis, puntos:, y lo coloca en minuscula
                String textoSinEspacios = nombreMoto.get(i)
                        .replaceAll("\\s+|\\p{InCombiningDiacriticalMarks}|\\(.*?\\)|:", "")
                        .toLowerCase();
                //quita las tildes
                textoSinEspacios = Normalizer.normalize(textoSinEspacios, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                textoSinEspacios = textoSinEspacios.replace(".", "");
                textoSinEspacios = textoSinEspacios.replace("/", "");
                updates.put(textoSinEspacios, nombreMoto.get(i + 1));
                i++;
            }

            updates.put("modelo", modelo);
            updates.put("descripcion", descripcion);
            updates.put("consumoPorGalon", consumoGasolina);
            updates.put("visible", true);
            mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //eliminar datos de auteco
                    Query query = mpostProvider.buscarPorNombreMoto(nombreImagen);
                    query.get().addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            QuerySnapshot querySnapshot = task2.getResult();
                            if (!querySnapshot.isEmpty()) {
                                // Obtiene el primer documento de los resultados
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                                List<String> camposNulos = new ArrayList<>();

                                // Verifica campos nulos en el documento
                                Map<String, Object> data = document.getData();
                                if (data != null) {
                                    for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                                        String nombreCampo = entry.getKey();
                                        Object valor = entry.getValue();

                                        // Verificar si el valor es null
                                        if (valor == null) {
                                            camposNulos.add(nombreCampo);
                                        }
                                    }
                                }
                                mpostProvider.eliminarDatosFichaTecnica(id, camposNulos).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                            }
                        }
                    });
                }
            });
        } catch (IOException e) {
            Log.e("MainActivity", "" + e.getMessage());
        }

    }
    //endregion

    //region PROCESO DE HONDA

    private void llenarInfoHONDA() {
        nombreMoto.clear();
        List<String> titulo = new ArrayList<>();
        List<String> contenido = new ArrayList<>();
        List<String> informacion = new ArrayList<>();
        recuperarImagenes.clear();
        coloresLista.clear();
        imagenesColorPrincipal.clear();
        try {
            //conexion
            Document doc = Jsoup.connect(url).get();

            Elements fichaTecnica = doc.select("div.wrapperOwn");
            Elements imagenesMoto = doc.select("div.swiper-slide");
            Elements imagenesAdicionales = doc.select("div.boxDetail");
            Elements descripcionMoto = doc.select("div.boxBanner");

            //motos que tienen otros diseños
            if (fichaTecnica.isEmpty()) {
                fichaTecnica = doc.select("section.technical-specifications");
            }


            if (fichaTecnica != null) {
                // Selecciona el elemento <h5> dentro del div
                Elements h3Elements = fichaTecnica.select("P");

                //paginas con otro diseño
                if (h3Elements.isEmpty()) {
                    Elements divElementTitutlo = fichaTecnica.select("div.col.subtitle");
                    Elements divElementDescripcion = fichaTecnica.select("div.col.result");
                    for (Element divElement : divElementTitutlo) {
                        String textoNormalizado = Normalizer.normalize(divElement.text(), Normalizer.Form.NFD);
                        textoNormalizado = textoNormalizado.replaceAll("[^\\p{ASCII}]", "");

                        // Eliminar signos de puntuación
                        textoNormalizado = textoNormalizado.replaceAll("[\\p{Punct}]", "");
                        // Convertir a minúsculas para comparación insensible a mayúsculas
                        textoNormalizado = textoNormalizado.toLowerCase();
                        // Eliminar espacios
                        textoNormalizado = textoNormalizado.replaceAll("\\s", "");
                        titulo.add(textoNormalizado);
                    }
                    for (Element divElement2 : divElementDescripcion) {
                        contenido.add(divElement2.text());
                    }

                    nombreMoto.clear();
                    for (int i = 0; i < titulo.size(); i++) {
                        nombreMoto.add(titulo.get(i));
                        nombreMoto.add(contenido.get(i));
                    }
                } else { // paginas normales
                    for (int i = 0; i < h3Elements.size(); i++) {
                        // Obtén el elemento actual de h3Elements
                        Element pElements = h3Elements.get(i);
                        Element pElements2 = h3Elements.get(i + 1);

                        //SE NORMALIZA EL TEXTO DEL TITULO:
                        String textoNormalizado = Normalizer.normalize(pElements.text(), Normalizer.Form.NFD);
                        textoNormalizado = textoNormalizado.replaceAll("[^\\p{ASCII}]", "");

                        // Eliminar signos de puntuación
                        textoNormalizado = textoNormalizado.replaceAll("[\\p{Punct}]", "");
                        // Convertir a minúsculas para comparación insensible a mayúsculas
                        textoNormalizado = textoNormalizado.toLowerCase();
                        // Eliminar espacios
                        textoNormalizado = textoNormalizado.replaceAll("\\s", "");

                        nombreMoto.add(textoNormalizado);
                        nombreMoto.add(pElements2.text());
                        i++;
                    }
                }


            }
            //Como esta moto tiene los colores en la ficha tecnica, es mas facil
            int posicion = nombreMoto.indexOf("colores");

            if (posicion != -1) {
                String[] palabrasArray = nombreMoto.get(posicion + 1).split(",");
                for (String palabra : palabrasArray) {
                    // Eliminar espacios en blanco al principio y al final de cada palabra
                    palabra = palabra.trim();

                    // Verificar si la palabra no está vacía (puede haber una coma final)
                    if (!palabra.isEmpty()) {
                        coloresLista.add(palabra.toUpperCase());
                    }
                }
            }

            if (descripcionMoto != null) {
                // Selecciona el elemento <h5> dentro del div
                Elements pElements = descripcionMoto.select("p");

                for (Element pElement : pElements) {
                    // obtener la descripcion
                    descripcion += pElement.text();
                    descripcion += " ";
                }
            }


            if (imagenesMoto != null) {
                // Selecciona el elemento <h5> dentro del div
                Elements imgElements = imagenesMoto.select("img");

                for (Element imgElement : imgElements) {
                    imagenesColorPrincipal.add("https://motos.honda.com.co" + imgElement.attr("src"));
                    //aqui estan tanto el pincipal como los demas colores
                }
            }


            //IMAGENES DE MOTO
            for (int i = 0; i < imagenesColorPrincipal.size(); i++) {
                if (i == 0) {
                    try {
                        //convierto las url a imagenes
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColorPrincipal.get(i))
                                .submit();
                        imagen1 = futureTarget.get();
                        guardarImagenesPrincipales(imagen1, null, null, null, null, null, null, null);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                } else if (i == 1) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColorPrincipal.get(i))
                                .submit();
                        files1.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 2) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColorPrincipal.get(i))
                                .submit();
                        files2.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 3) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColorPrincipal.get(i))
                                .submit();
                        files3.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 4) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColorPrincipal.get(i))
                                .submit();
                        files4.add(futureTarget.get());

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 5) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColorPrincipal.get(i))
                                .submit();
                        files5.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else if (i == 6) {
                    try {
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(imagenesColorPrincipal.get(i))
                                .submit();
                        files6.add(futureTarget.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }


            if (!imagenesAdicionales.isEmpty()) {
                imagenesAdd.clear();
                imagenesColores.clear();

                Elements imgElements = imagenesAdicionales.select("img");
                Elements h3Elements = imagenesAdicionales.select("h3");//titulo
                Elements pElements = imagenesAdicionales.select("p");//info de las imagenes

                for (int i = 0; i < imgElements.size(); i++) {
                    Element imagen = imgElements.get(i);
                    recuperarImagenes.add("https://motos.honda.com.co" + imagen.attr("src"));
                }

                for (Element h3Element : h3Elements) {
                    Element hElement = h3Element.selectFirst("h3");
                    if (hElement != null) {
                        //OBTIENE EL TITULO DE LAS IMAGENES
                        String h2Text = hElement.text();
                        if (h2Text != "") {
                            imagenesColores.add(h2Text);
                        }
                    }
                }
                List<String> clon = new ArrayList<>();
                clon.addAll(imagenesColores);
                imagenesColores.clear();
                contador = 0;
                //obetener texto de imagenes:
                for (int i = 0; i < pElements.size(); i++) {
                    // Acceder al elemento en el índice i
                    Element pElement = pElements.get(i);

                    // Seleccionar el primer elemento <p> dentro de pElement
                    Element p2Element = pElement.selectFirst("p");

                    // Si p2Element no es nulo, obtener el texto y agregarlo a imagenesColores si no está vacío
                    if (p2Element != null) {
                        // Obtener el texto del elemento <p>
                        String pText = p2Element.text();
                        // Si el texto no está vacío, agregarlo a imagenesColores
                        if (!pText.isEmpty()) {
                            if (contador <= clon.size()) {
                                imagenesColores.add(clon.get(contador) + "\n" + pText);
                                contador++;
                            }
                        }
                    }
                }

                // IMAGENES ADD
                contador = 0;
                for (int i = 0; i < recuperarImagenes.size(); i++) {
                    try {
                        //convierto las url a imagenes
                        FutureTarget<File> futureTarget = Glide.with(context)
                                .asFile()
                                .load(recuperarImagenes.get(i))
                                .submit();

                        if (i == 0) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 1) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 2) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 3) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 4) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 5) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 6) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 7) {
                            imagenesAdd.add(futureTarget.get());
                        } else if (i == 8) {
                            imagenesAdd.add(futureTarget.get());
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
            guardarImagenesPrincipales(imagen1, null, null, null, null, null, null, null);

            //ficha tecnica
            Map<String, Object> updates = new HashMap<>();
            for (int i = 0; i < nombreMoto.size(); i++) {
                //quita los espacios, tildes, parentesis, puntos:, y lo coloca en minuscula
                String textoSinEspacios = nombreMoto.get(i)
                        .replaceAll("\\s+|\\p{InCombiningDiacriticalMarks}|\\(.*?\\)|:", "")
                        .toLowerCase();
                //quita las tildes
                textoSinEspacios = Normalizer.normalize(textoSinEspacios, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                textoSinEspacios = textoSinEspacios.replace(".", "");
                textoSinEspacios = textoSinEspacios.replace("/", "");
                updates.put(textoSinEspacios, nombreMoto.get(i + 1));
                i++;
            }
            if (!descripcion.isEmpty()) {
                updates.put("descripcion", descripcion);
            }
            updates.put("consumoPorGalon", consumoGasolina);
            updates.put("visible", true);
            mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //eliminar datos de auteco
                    Query query = mpostProvider.buscarPorNombreMoto(nombreImagen);
                    query.get().addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            QuerySnapshot querySnapshot = task2.getResult();
                            if (!querySnapshot.isEmpty()) {
                                // Obtiene el primer documento de los resultados
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                                List<String> camposNulos = new ArrayList<>();

                                // Verifica campos nulos en el documento
                                Map<String, Object> data = document.getData();
                                if (data != null) {
                                    for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                                        String nombreCampo = entry.getKey();
                                        Object valor = entry.getValue();

                                        // Verificar si el valor es null
                                        if (valor == null) {
                                            camposNulos.add(nombreCampo);
                                        }
                                    }
                                }
                                mpostProvider.eliminarDatosFichaTecnica(id, camposNulos).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                            }
                        }
                    });
                }
            });
        } catch (IOException e) {
            Log.e("MainActivity", "" + e.getMessage());
        }

    }
    //endregion

    //region PROCESO DE BAJAJ


    public void llenarInfoBAJAJ() {
        nombreMoto.clear();
        List<String> titulo = new ArrayList<>();
        List<String> contenido = new ArrayList<>();
        List<String> informacion = new ArrayList<>();

        String descripcion = "";

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")
                    .header("Accept-Language", "es-ES,es;q=0.9")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Referer", "https://www.google.com/")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("Cache-Control", "max-age=0")
                    .timeout(10000) // Aumentar el timeout a 10 segundos
                    .get();


            Elements FichaTecnicaExtraer = doc.select("div.col-lg-12");
            Element FichaTecnica = FichaTecnicaExtraer.get(1);
            Elements listaItems = FichaTecnica.select("ul > li");
            Element imagenesAddi = FichaTecnicaExtraer.get(0);
            Elements imagenesPrincipales = doc.select("div.newsheader");
            Elements imagenesPrincipales2 = doc.select("div._spin_block");

            if (listaItems != null) {
                for (Element item : listaItems) {
                    String texto = item.text();
                    String[] partes = texto.split(":");
                    if (partes.length > 1) {
                        String nombreCampo = partes[0].trim().toLowerCase().replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                        String valorCampo = partes[1].trim();
                        nombreMoto.add(nombreCampo);
                        nombreMoto.add(valorCampo);
                    }
                }
            }

            if (imagenesAddi != null) {
                Elements imagenesAdicionales = imagenesAddi.select("div.col-lg-7.tech_feature_img");
                Elements imgElements = imagenesAdicionales.select("img");

                for (int i = 0; i < imgElements.size(); i++) {
                    Element imagen = imgElements.get(i);
                    String urlmod = imagen.attr("src");
                    String urlSinParametros = urlmod.replaceAll("\\.png\\?.*", ".png");
                    recuperarImagenes.add(urlSinParametros);
                }

            }
            //imagenes adicionales
            for (int i = 0; i < recuperarImagenes.size(); i++) {
                try {
                    //convierto las url a imagenes
                    FutureTarget<File> futureTarget = Glide.with(context)
                            .asFile()
                            .load(recuperarImagenes.get(i))
                            .submit();

                    if (i == 0) {
                        imagenesAdd.add(futureTarget.get());
                    } else if (i == 1) {
                        imagenesAdd.add(futureTarget.get());
                    } else if (i == 2) {
                        imagenesAdd.add(futureTarget.get());
                    } else if (i == 3) {
                        imagenesAdd.add(futureTarget.get());
                    } else if (i == 4) {
                        imagenesAdd.add(futureTarget.get());
                    } else if (i == 5) {
                        imagenesAdd.add(futureTarget.get());
                    } else if (i == 6) {
                        imagenesAdd.add(futureTarget.get());
                    } else if (i == 7) {
                        imagenesAdd.add(futureTarget.get());
                    } else if (i == 8) {
                        imagenesAdd.add(futureTarget.get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            if (imagenesPrincipales2 != null) {
                Elements imgElements = imagenesPrincipales2.select("div[src]");
                List<Integer> cantidad = new ArrayList<>();
                //saber la cantidad de imagenes por cada div
                // Iterar sobre los elementos div seleccionados
                for (Element div : imagenesPrincipales2) {
                    // Seleccionar todos los elementos div hijos
                    Elements innerDivs = div.select("div");

                    // Contar cuántos elementos div hay dentro de cada elemento div
                    cantidad.add(innerDivs.size());
                }


                //color princiopal y colores
                for (int i = 0; i < imgElements.size(); i++) {
                    Element imagen = imgElements.get(i);
                    String urlmod = imagen.attr("src");
                    String urlSinParametros = urlmod.replaceAll("\\.png\\?.*", ".png");
                    if (!urlmod.equals(urlSinParametros)) {
                        imagenesColorPrincipal.add(urlSinParametros);
                    }
                }

                //coloresnombre
                for (Element spinBlock : imagenesPrincipales2) {
                    String dataPath = spinBlock.attr("data-path");
                    System.out.println("data-path: " + dataPath);
                    // Encontrar la última aparición del carácter "/"
                    int lastIndex = dataPath.lastIndexOf("/", dataPath.lastIndexOf("/") - 1);

                    // Obtener el color a partir de la URL
                    String color = dataPath.substring(lastIndex + 1);

                    // Imprimir el color
                    coloresLista.add(color.replace("/", "").toUpperCase());
                }


                //organizar:

                Map<String, List<String>> urlsPorColor = new HashMap<>();

                int indiceUrl = 0;

                for (int i = 0; i < coloresLista.size(); i++) {
                    String color = coloresLista.get(i);
                    int cantidadImagenes = cantidad.get(i) - 1;

                    // Creamos una lista para almacenar las URLs de este color
                    List<String> urlsDelColor = new ArrayList<>();

                    // Agregamos las URLs correspondientes a este color
                    for (int j = 0; j < cantidadImagenes; j++) {
                        urlsDelColor.add(imagenesColorPrincipal.get(indiceUrl));
                        indiceUrl++;
                    }

                    // Agregamos la lista de URLs al HashMap
                    urlsPorColor.put(color, urlsDelColor);
                }

                contador = 0;
                for (Map.Entry<String, List<String>> entry : urlsPorColor.entrySet()) {
                    String color = entry.getKey(); // Obtener el color
                    List<String> urls = entry.getValue(); // Obtener la lista de URLs para el color dado

                    // Iterar sobre las URLs para el color actual
                    for (String url : urls) {
                        // Procesar cada URL usando Glide
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(url)
                                    .submit();
                            if (contador == 0){
                                files1.add(futureTarget.get());
                            }else if (contador == 1){
                                files2.add(futureTarget.get());
                            }else if (contador == 3){
                                files3.add(futureTarget.get());
                            }else if (contador == 4){
                                files4.add(futureTarget.get());
                            }else if (contador == 5){
                                files5.add(futureTarget.get());
                            }else if (contador == 6){
                                files6.add(futureTarget.get());
                            }

                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    contador = contador+1;
                }
                imagenesColorPrincipal.clear();
                imagenesColorPrincipal.addAll(urlsPorColor.get(coloresLista.get(0)));
                for (int i = 0; i < imagenesColorPrincipal.size(); i++) {
                    if (i == 0) {
                        try {
                            //convierto las url a imagenes
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColorPrincipal.get(i))
                                    .submit();
                            imagen1 = futureTarget.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 1) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColorPrincipal.get(i))
                                    .submit();
                            imagen2 = futureTarget.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 2) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColorPrincipal.get(i))
                                    .submit();
                            imagen3 = futureTarget.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 3) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColorPrincipal.get(i))
                                    .submit();
                            imagen4 = futureTarget.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 4) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColorPrincipal.get(i))
                                    .submit();
                            imagen5 = futureTarget.get();

                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 5) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColorPrincipal.get(i))
                                    .submit();
                            imagen6 = futureTarget.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 6) {
                        try {
                            FutureTarget<File> futureTarget = Glide.with(context)
                                    .asFile()
                                    .load(imagenesColorPrincipal.get(i))
                                    .submit();
                            imagen7 = futureTarget.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
                guardarImagenesPrincipales(imagen1, imagen2, imagen3, imagen4, imagen5, imagen6, imagen7, null);
            } else {
                if (imagenesPrincipales != null) {
                    Elements imgElements = imagenesPrincipales.select("img");

                    for (int i = 0; i < imgElements.size(); i++) {
                        Element imagen = imgElements.get(i);
                        String urlmod = imagen.attr("src");
                        String urlSinParametros = urlmod.replaceAll("\\.png\\?.*", ".png");
                        if (!urlmod.equals(urlSinParametros)) {
                            imagenesColorPrincipal.add(urlSinParametros);
                        }
                    }
                    for (int i = 0; i < imagenesColorPrincipal.size(); i++) {
                        if (i == 0) {
                            try {
                                //convierto las url a imagenes
                                FutureTarget<File> futureTarget = Glide.with(context)
                                        .asFile()
                                        .load(imagenesColorPrincipal.get(i))
                                        .submit();
                                imagen1 = futureTarget.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        } else if (i == 1) {
                            try {
                                FutureTarget<File> futureTarget = Glide.with(context)
                                        .asFile()
                                        .load(imagenesColorPrincipal.get(i))
                                        .submit();
                                imagen2 = futureTarget.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        } else if (i == 2) {
                            try {
                                FutureTarget<File> futureTarget = Glide.with(context)
                                        .asFile()
                                        .load(imagenesColorPrincipal.get(i))
                                        .submit();
                                imagen3 = futureTarget.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        } else if (i == 3) {
                            try {
                                FutureTarget<File> futureTarget = Glide.with(context)
                                        .asFile()
                                        .load(imagenesColorPrincipal.get(i))
                                        .submit();
                                imagen4 = futureTarget.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        } else if (i == 4) {
                            try {
                                FutureTarget<File> futureTarget = Glide.with(context)
                                        .asFile()
                                        .load(imagenesColorPrincipal.get(i))
                                        .submit();
                                imagen5 = futureTarget.get();

                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        } else if (i == 5) {
                            try {
                                FutureTarget<File> futureTarget = Glide.with(context)
                                        .asFile()
                                        .load(imagenesColorPrincipal.get(i))
                                        .submit();
                                imagen6 = futureTarget.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        } else if (i == 6) {
                            try {
                                FutureTarget<File> futureTarget = Glide.with(context)
                                        .asFile()
                                        .load(imagenesColorPrincipal.get(i))
                                        .submit();
                                imagen7 = futureTarget.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    guardarImagenesPrincipales(imagen1, imagen2, imagen3, imagen4, imagen5, imagen6, imagen7, null);
                }
            }
            Map<String, Object> updates = new HashMap<>();
            for (int i = 0; i < nombreMoto.size(); i++) {
                //quita los espacios, tildes, parentesis, puntos:, y lo coloca en minuscula
                String textoSinEspacios = nombreMoto.get(i)
                        .replaceAll("\\s+|\\p{InCombiningDiacriticalMarks}|\\(.*?\\)|:", "")
                        .toLowerCase();
                //quita las tildes
                textoSinEspacios = Normalizer.normalize(textoSinEspacios, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                textoSinEspacios = textoSinEspacios.replace(".", "");
                textoSinEspacios = textoSinEspacios.replace("/", "");
                updates.put(textoSinEspacios, nombreMoto.get(i + 1));
                i++;
            }
            if (!descripcion.isEmpty()) {
                updates.put("descripcion", descripcion);
            }
            updates.put("consumoPorGalon", consumoGasolina);
            updates.put("visible", true);
            mpostProvider.updatePost(id, updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //eliminar datos de auteco
                    Query query = mpostProvider.buscarPorNombreMoto(nombreImagen);
                    query.get().addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            QuerySnapshot querySnapshot = task2.getResult();
                            if (!querySnapshot.isEmpty()) {
                                // Obtiene el primer documento de los resultados
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                                List<String> camposNulos = new ArrayList<>();

                                // Verifica campos nulos en el documento
                                Map<String, Object> data = document.getData();
                                if (data != null) {
                                    for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                                        String nombreCampo = entry.getKey();
                                        Object valor = entry.getValue();

                                        // Verificar si el valor es null
                                        if (valor == null) {
                                            camposNulos.add(nombreCampo);
                                        }
                                    }
                                }
                                mpostProvider.eliminarDatosFichaTecnica(id, camposNulos).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                            }
                        }
                    });
                }
            });




        } catch (
                IOException e) {
            Log.e("MainActivity", "" + e.getMessage());

        }
    }
//endregion

}



